package labs.lab3.data

data class Packet(
    var arrival: Int,
    var duration: Int,

    var isFinished: Boolean = false,
    var processingTimeStart: Int = -1
)
