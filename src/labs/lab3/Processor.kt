package labs.lab3

class Processor {

    var currentState = ProcessorState.WAITING

    fun processPacket(packet: Packet?) {
        if(packet == null) {
            currentState = ProcessorState.WAITING
            return
        }
        if(packet.startProcessingTime + packet.duration > SimulatorClock.currentTime){
            currentState = ProcessorState.WORKING
            return
        }
        if(packet.startProcessingTime + packet.duration == SimulatorClock.currentTime){
            currentState = ProcessorState.WAITING
        }
    }

}