package labs.lab4

import common.InterpretationUtils
import labs.Lab


/*
Реализовать стек с поддержкой операций push, pop и max.

Вход. Последовательность запросов push, pop и max.

Выход. Для каждого запроса max вывести максимальное число, находящееся на стеке.

Формат входа. Первая строка содержит число запросов q. Каждая из последующих q
строк задаёт запрос в одном из следующих форматов: push v, pop, max.

Формат выхода. Для каждого запроса max выведите (в отдельной строке) текущий
максимум на стеке.

Ограничения. 1 ≤ q ≤ 400 000, 0 ≤ v ≤ 100 000.

Дополнительно:

1. Поддержка операций min и среднее значение (avg). Добавить операцию min,
которая возвращает минимальное значение на стеке. Добавить операцию avg,
которая возвращает среднее значение элементов на стеке. Эта операция также
должна работать за константное время.

2. Ограничение размера стека. Ввести ограничение на максимальный размер
стека. Если стек заполнен, операции push должны возвращать ошибку или
игнорироваться.
*/

class Lab4Impl(val inputFilePath: String, val outputFilePath: String, maxStackSize: Int = 5): Lab {
    private val stack = Stack(maxStackSize)

    override fun run() {
        InterpretationUtils.interpretFilesCommands(inputFilePath, outputFilePath, this::executeCommand)
    }

    fun executeCommand(command: String): String {
        val parts = command.split(" ")
        val commandType = parts[0].lowercase()

        return when (commandType) {
            Commands.POP.name.lowercase() -> executePop()
            Commands.PUSH.name.lowercase() -> executePush(parts)
            Commands.AVG.name.lowercase() -> executeAvg()
            Commands.MAX.name.lowercase() -> executeMax()
            Commands.MIN.name.lowercase() -> executeMin()
            else -> "unknown command"
        }
    }

    private fun executeMin(): String {
        return stack.min().toString()
    }

    private fun executeMax(): String {
        return stack.max().toString()
    }

    private fun executeAvg(): String {
        return stack.avg().toString()
    }

    private fun executePush(parts: List<String>): String {
        stack.push(parts[1].toInt())
        return ""
    }

    private fun executePop(): String {
        stack.pop()
        return ""
    }


}