package common

import java.util.stream.Collectors

object InterpretationUtils {
    private val LOG_TO_CONSOLE = false

    fun interpretFilesCommands(inputFilePath: String, outputFilePath: String, executeCommand: (String) -> String) {
        var allCommands = FileUtils.getLinesFromFile(inputFilePath).toMutableList()
        var commandsCount = allCommands.get(0).toInt()
        if (commandsCount < 1 || commandsCount > 105) {
            println("Error: wrong command lines")
            return
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