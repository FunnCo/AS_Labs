package labs.lab3.model

import labs.lab3.StatisticService
import labs.lab3.data.Packet
import labs.lab3.data.ProcessorState

class Processor {

    private var currentProcessingTime = 0;
    private var packet: Packet? = null
    var currentState = ProcessorState.WAITING

    fun processPacket() {
        currentProcessingTime++
        if (packet != null){
            if (currentProcessingTime == (packet?.duration ?: -1)){
                currentState =  ProcessorState.FINISHING
                packet?.isFinished = true
                packet = null
            } else {
                currentState = ProcessorState.WORKING
            }
        } else {
            currentState = ProcessorState.WAITING
        }
        StatisticService.updateProcessorStatistics(this)
    }

    fun assignNewPacket(packet: Packet) {
        this.packet = packet
        currentProcessingTime = 0
    }
}