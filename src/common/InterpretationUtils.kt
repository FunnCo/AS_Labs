package common

import java.util.stream.Collectors

private const val MIN_ARRAY_SIZE = 1
private const val MAX_ARRAY_SIZE = 400000
private const val LOG_TO_CONSOLE = true
// Утилита для работы с инртерпретацией команд. Используется в 4 и 7 лабораторных.
object InterpretationUtils {

    fun interpretFilesCommands(inputFilePath: String, outputFilePath: String, executeCommand: (String) -> String) {
        var allCommands = FileUtils.getLinesFromFile(inputFilePath).toMutableList()
        var commandsCount = allCommands.get(0).toInt()
        if (commandsCount < MIN_ARRAY_SIZE || commandsCount > MAX_ARRAY_SIZE) {
            println("Error: wrong command lines")
            return
        }

        // Если в файле заявлено больше команд, чем передано, то выполнены будут все переданные команды
        if(commandsCount > allCommands.size) {
            commandsCount = allCommands.size - 1
        }

        // Если в файле будет больше команд, чем заявлено в первой строке, то выполнено будет только заявленное кол-во
        var commandsToExecute = allCommands.slice(1..commandsCount)
        var result = ""
        for (command in commandsToExecute) {
            val output = executeCommand(command)
            if (output.isNotEmpty()) {
                result += "$output\n"
            }
        }
        result = result.substring(0, result.length - 1)
        FileUtils.saveStringToFile(outputFilePath, result)

        if(LOG_TO_CONSOLE) {
            val inputValue = allCommands.stream().collect(Collectors.joining("\n"))
            println("Input:\n$inputValue")
            println("\n\nOuput:\n$result")
        }
    }
}