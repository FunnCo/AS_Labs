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
        val commandType =  Commands.entries.find { it.name.lowercase() == parts[0] }

        return when (commandType) {
            Commands.POP -> executePop()
            Commands.PUSH -> executePush(parts)
            Commands.AVG -> executeAvg()
            Commands.MAX -> executeMax()
            Commands.MIN -> executeMin()
            else -> "unknown command"
        }
    }

    private fun executeMin(): String {
        return stack.min()?.toString() ?: "cant find, because no elements in stack"
    }

    private fun executeMax(): String {
        return stack.max()?.toString() ?: "cant find, because no elements in stack"
    }

    private fun executeAvg(): String {
        return stack.avg()?.toString() ?: "cant find, because no elements in stack"
    }

    private fun executePush(parts: List<String>): String {
        try{
            stack.push(parts[1].toInt())
        } catch (e: IllegalStateException){
            return "stack is full"
        } catch (e: Exception){
            return "error: ${e.message}}"
        }

        return ""
    }

    private fun executePop(): String {
        stack.pop()
        return ""
    }


}