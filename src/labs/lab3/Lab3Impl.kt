package labs.lab3

import labs.Lab

class Lab3Impl(val inputFilePath: String): Lab {

    override fun run() {
        // Временно просто бахну три процессора, и посрать

        var processor1 = Processor()
        var processor2 = Processor()
        var processor3 = Processor()

        SimulatorClock.processedPackets[processor1] = null
        SimulatorClock.processedPackets[processor2] = null
        SimulatorClock.processedPackets[processor3] = null

        var packets = mutableListOf<Packet>()
        packets.add(Packet(0, 4))
        packets.add(Packet(6, 4))

        while (SimulatorClock.currentTime < 100){
            packets.forEach { packet ->
                if(SimulatorClock.currentTime == packet.arrival){
                    PacketBuffer.putPacketInBuffer(packet)
                }
            }
            SimulatorClock.update()
            println("Proc1: ${processor1.currentState} || Proc2: ${processor2.currentState} || Proc3: ${processor3.currentState} || currentBufferCount: ${PacketBuffer.buffer.size} || currentSystemTime: ${SimulatorClock.currentTime}")
        }
    }

}