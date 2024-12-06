package labs.lab3

import labs.lab3.data.ProcessorState
import labs.lab3.data.BaseStatistic
import labs.lab3.data.BufferStatistic
import labs.lab3.model.Processor
import labs.lab3.model.SimulatorClock
import kotlin.math.floor

object StatisticService {

    private var processors: MutableMap<Processor, BaseStatistic> = mutableMapOf()
    private var clockStatistic = BaseStatistic()
    private var bufferStatistic = BufferStatistic()

    fun registerProcessor(processor: Processor) {
        processors.put(processor, BaseStatistic())
    }

    fun updateProcessorStatistics(processor: Processor){
        if(processor.currentState != ProcessorState.WAITING) processors[processor]?.also { it.timeWorked++ }
        if(processor.currentState == ProcessorState.FINISHING) processors[processor]?.also { it.pocketsHandled++ }
    }

    fun registerProcessedPacket(){
        clockStatistic.pocketsHandled++
    }

    fun registerBufferPacketWaitTime(waitTime: Int){
        bufferStatistic.waitTime += waitTime
    }

    fun registerBufferLeftOutPacket(){
        bufferStatistic.leftOutPackets++
    }

    fun registerClockTimeUpdate(){
        clockStatistic.timeWorked++
    }

    fun getProcessorLoad(processor: Processor): Double{
        val processorWorkTime = processors[processor]?.timeWorked?.toDouble() ?: 0.0
        return processorWorkTime / clockStatistic.timeWorked
    }

    fun getPercentageValue(value: Double): Double{
        return floor(value * 1000) / 10
    }

    fun getAnalysis(): String{
        val result = StringBuilder()
        result.append("Total time worked: ${clockStatistic.timeWorked}\n")
        result.append("Total packets handled: ${clockStatistic.pocketsHandled}\n")
        result.append("Total left out packets: ${bufferStatistic.leftOutPackets}\n")

        val processorLoads = processors.keys.map(this::getProcessorLoad)
        result.append("Average processor load: ${getPercentageValue(processorLoads.sum() / processors.size)}%\n")
        result.append("Total time spent in buffer: ${bufferStatistic.waitTime}\n")

        result.append("Processors detailed info:\n")
        processors.keys.forEachIndexed { index, processor ->
            result.append("  - Processor ${index + 1}:\n")
            result.append("    Worked time from total time: ${getPercentageValue(getProcessorLoad(processor))}%\n")
            result.append("    Free time: ${clockStatistic.timeWorked - processors[processor]!!.timeWorked}\n")
            result.append("    Total handled packets: ${processors[processor]?.pocketsHandled}\n")
        }
        return result.toString()
    }
}