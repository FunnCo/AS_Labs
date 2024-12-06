package labs.lab3.model

import labs.lab3.StatisticService
import labs.lab3.data.Packet
import java.util.Queue
import java.util.concurrent.ConcurrentLinkedQueue

object PacketBuffer {

    var maxBufferSize = 1
    val buffer: Queue<Packet> = ConcurrentLinkedQueue()

    fun putPacketInBuffer(packet: Packet) {
        if(buffer.size < maxBufferSize){
            buffer.add(packet)
        } else {
            StatisticService.registerBufferLeftOutPacket()
            packet.isFinished = true
        }
    }

    fun getPacketFromBuffer(): Packet? {
        return buffer.poll()
    }
}