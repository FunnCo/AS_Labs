package labs.lab3

import common.FileUtils
import labs.Lab
import labs.lab3.data.Packet
import labs.lab3.model.PacketBuffer
import labs.lab3.model.Processor
import labs.lab3.model.SimulatorClock
import java.util.stream.Collectors

private const val MIN_TOTAL_PACKETS_SIZE = 1
private const val MAX_TOTAL_PACKETS_SIZE = 100000
private const val MIN_BUFFER_SIZE = 1
private const val MAX_BUFFER_SIZE = 100000
private const val MIN_POSSIBLE_ARRIVAL_TIME = 0
private const val MAX_POSSIBLE_ARRIVAL_TIME = 1000000
private const val MIN_POSSIBLE_DURATION = 0
private const val MAX_POSSIBLE_DURATION = 1000

class Lab3Impl(
    val inputFilePath: String,
    val outputFilePath: String,
    val analysisFilePath: String,
    val procssorCount: Int = 1,
    val debugMode: Boolean = false
) : Lab {

    override fun run() {
        try {
            val input = FileUtils.getLinesFromFile(inputFilePath)
            if (isTaskValid(input)) {
                val bufferSize = input[0].split(" ")[0].toInt()

                val packets = input.subList(1, input.size)
                    .map(this::buildPacket)

                if(debugMode){
                    println(simulate(packets, bufferSize, procssorCount))
                } else {
                    val result = simulate(packets, bufferSize, procssorCount)
                    val analysis = StatisticService.getAnalysis()
                    FileUtils.saveStringToFile(outputFilePath, result)
                    FileUtils.saveStringToFile(analysisFilePath, analysis)
                }

            } else {
                println("Ошибка: введены некорректные данные, т.е не удволетворяющие условию задачи")
            }
        } catch (e: Exception) {
            println("Ошибка: введены некорректные данные: ${e.message}")
        }
    }

    private fun buildPacket(inputString: String): Packet {
        val arrival = inputString.split(" ")[0].toInt()
        val duration = inputString.split(" ")[1].toInt()
        return Packet(arrival, duration)
    }

    private fun isTaskValid(input: List<String>): Boolean {
        val areAllValuesInteger = input
            .map { it.split(" ") }
            .flatten()
            .map { it.toIntOrNull() }
            .all { it != null }

        val bufferSize = input[0].split(" ")[0].toInt()
        val totalPackets = input[0].split(" ")[1].toInt()
        val isBufferSizeValid = bufferSize in MIN_BUFFER_SIZE..MAX_BUFFER_SIZE
        val isTotalPacketsValid =
            totalPackets in MIN_TOTAL_PACKETS_SIZE..MAX_TOTAL_PACKETS_SIZE && totalPackets == input.size - 1

        val durations = input.subList(1, input.size)
            .map { it.split(" ")[1].toInt() }
        val areDurationsValid = durations.all { it in MIN_POSSIBLE_DURATION..MAX_POSSIBLE_DURATION }

        val arrivals = input.subList(1, input.size)
            .map { it.split(" ")[0].toInt() }
        var areArrivalsValid = true
        for (i in 1..<arrivals.size) {
            val isRangeCorrect = arrivals[i] in MIN_POSSIBLE_ARRIVAL_TIME..MAX_POSSIBLE_ARRIVAL_TIME
            val isValueValid = arrivals[i] >= arrivals[i - 1]
            if (!(isRangeCorrect && isValueValid)) {
                areArrivalsValid = false
                break
            }
        }

        return areAllValuesInteger && isBufferSizeValid && isTotalPacketsValid && areArrivalsValid && areDurationsValid
    }

    fun simulate(packets: List<Packet>, bufferSize: Int, processorCount: Int): String {

        val result = StringBuilder()

        var listOfProcessors = mutableListOf<Processor>()
        for (i in 0..<processorCount) {
            listOfProcessors.add(Processor())
        }
        PacketBuffer.maxBufferSize = bufferSize

        listOfProcessors.forEach(StatisticService::registerProcessor)

        val clock = SimulatorClock(listOfProcessors)
        clock.init()

        while (!packets.all(Packet::isFinished)) {
            packets.forEach { packet ->
                if (clock.currentTime == packet.arrival) {
                    PacketBuffer.putPacketInBuffer(packet)
                }
            }
            clock.update()

            if (debugMode) {
                val processorStates = mutableListOf<String>()
                clock.processors.forEachIndexed { index, processor ->
                    processorStates.add("Proc ${index + 1}: ${processor.currentState}".padEnd(20))
                }
                result.append(
                    processorStates.stream().collect(Collectors.joining()) + "currentTime: ${clock.currentTime}\n"
                )
            }
        }
        if (debugMode) {
            result.append("\n${StatisticService.getAnalysis()}")
        } else {
            packets.forEach { packet -> result.append("${packet.processingTimeStart}\n") }
        }
        return result.toString()
    }
}