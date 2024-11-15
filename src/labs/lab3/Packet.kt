package labs.lab3

data class Packet(
    var arrival: Int,
    var duration: Int,
    var startProcessingTime: Int = arrival
)
