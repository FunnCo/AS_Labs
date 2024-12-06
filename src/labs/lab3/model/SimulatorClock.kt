package labs.lab3.model

import labs.lab3.StatisticService
import labs.lab3.data.ProcessorState

class SimulatorClock(val processors: List<Processor>) {

    var currentTime = 0

    // "Запуск" процссоров, распредение начальных задач
    fun init() {
        processors.forEach { processor ->
            assignNewPocketToProcessor(processor)
            processor.processPacket()
        }
    }

    fun update() {

        processors.forEach { item ->
            if (item.currentState == ProcessorState.FINISHING) {
                StatisticService.registerProcessedPacket()
            }
            if (item.currentState != ProcessorState.WORKING) {
                assignNewPocketToProcessor(item)
            }
            item.processPacket()
        }
        currentTime++
        StatisticService.registerClockTimeUpdate()
        StatisticService.registerBufferPacketWaitTime(PacketBuffer.buffer.size)
    }

    private fun assignNewPocketToProcessor(processor: Processor) {
        var isAssigned = false
        while (!isAssigned && PacketBuffer.buffer.size != 0) {
            PacketBuffer.getPacketFromBuffer()?.also { packet ->
                if (packet.duration > 0) {
                    processor.assignNewPacket(packet)
                    packet.processingTimeStart = currentTime
                    isAssigned = true
                } else {
                    // если duration == 0, то пакет считается обработанным автоматически
                    packet.isFinished = true
                    packet.processingTimeStart = currentTime
                }
            }
        }
    }

}