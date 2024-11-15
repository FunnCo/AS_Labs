package labs.lab3

object SimulatorClock {

    var currentTime = 0
    var processedPackets: MutableMap<Processor, Packet?> = mutableMapOf()

    fun update() {
        currentTime++
        processedPackets.forEach { (processor, _) ->
            val packet = processedPackets[processor]
            when (processor.currentState) {
                ProcessorState.FAILED -> {
                    packet!!.arrival = currentTime
                    packet!!.duration = packet.duration * 2
                    PacketBuffer.putPacketInBuffer(packet)
                }
                ProcessorState.WORKING -> {
                }
                else -> {
                    assignNewPocketToProcessor(processor)
                }
            }
            processor.processPacket(processedPackets[processor])
        }
        var a = 0

    }

    private fun assignNewPocketToProcessor(processor: Processor){
        processedPackets[processor] = PacketBuffer.getPacketFromBuffer()
        processedPackets[processor]?.startProcessingTime = currentTime
    }

}