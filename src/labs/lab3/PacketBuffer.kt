package labs.lab3

import java.util.concurrent.ConcurrentLinkedQueue

object PacketBuffer {

    var size = 5
    val buffer: ConcurrentLinkedQueue<Packet> = ConcurrentLinkedQueue()

    fun putPacketInBuffer(packet: Packet) {
        if(buffer.size < size){
            buffer.add(packet)
        }
    }

    fun getPacketFromBuffer(): Packet? {
        return buffer.poll()
    }
}