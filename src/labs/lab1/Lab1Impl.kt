package labs.lab1

import common.FileUtils
import labs.Lab
import kotlin.time.measureTimedValue

/*
Написать программу, для сортировки и анализа текстовых данных. В соответствии с
параметрами варианта задания нужно реализовать сортировку слов в тексте и провести его
простейший анализ.

Необходимо реализовать программу, которая будет считывать текст из файла
(например, «original.txt») и с минимальными погрешностями разбивать его на массив
отдельных слов.

Далее, согласно параметрам выбранного варианта, необходимо
отсортировать этот массив слов. Путь либо название считываемого файла должно вводиться
через консоль.

Примечание. Символы и знаки препинания игнорируются и не включаются в
слово/длину слова.

Отсортированный массив необходимо вывести в выходной файл, например, «result.txt».
В консоль результат выводить не нужно.

Также необходимо вывести информацию с простейшим анализом введенного текста
(эту информацию выводим в консоль и в файл, например, «analysis.txt»):
• исходный текст
• параметры выбранного варианта
• количество слов в исходном тексте
• время выполнения сортировки
• количество слов на каждую букву алфавита (для варианта «сортировка по
алфавиту»)
• количество слов каждой длины (для варианта «сортировка по количеству
символов в слове»)

Вариант: Латиница, сортировка по длине слова, сортировка расческой, по убыванию, учитывать числа.
*/

class Lab1Impl(val inputFilePath: String, val outputDirectoryPath: String) : Lab {

    val IGNORED_SYMBOLS = "!\"№;%:?*()-=_+\',.<>/\\|][{}".toCharArray().toList()
    val GAP_FACTOR = 1.247330950103979

    override fun run() {
        // Получение оригинального текста
        val lines = FileUtils.getLinesFromFile(inputFilePath)
        if(lines.isEmpty()){
            println("Ошибка: файл с входными данными не существует, или получен пустой текст")
            return
        }
        val originalText = lines.joinToString("\n")

        // Разбивка текста на слова, и измерение времени сортировки (+ сама сортировка)
        val words = splitAndFilterText(lines)
        val testResults = measureTimedValue { combSort(words) }

        // Результаты измеререний и сортировки
        val sortResult = testResults.value
        val sortTime = testResults.duration.inWholeMilliseconds

        // Формирование отчета
        val resultFileContent = formResultString(sortResult)
        val analysisFileContent = formAnalysisString(originalText, words.size, getWordsLengthFrequency(words), sortTime)

        // Вывод результата анализа в консоль
        println(analysisFileContent)

        // Сохранение отчета
        FileUtils.saveStringToFile("$outputDirectoryPath/result.txt", resultFileContent)
        FileUtils.saveStringToFile("$outputDirectoryPath/analysis.txt", analysisFileContent)
    }

    // Разделение текста из файла на слова + фильтрация игнорируемых символов
    private fun splitAndFilterText(lines: List<String>): List<String> {
        return lines.stream()
            .map { line -> line.split(" ").stream() }
            .flatMap { lineWords -> lineWords }
            .filter(String::isNotBlank)
            .map(this::cleanWord)
            .toList()
    }

    // Очистка слова от игнорируемых сиволов
    private fun cleanWord(word: String): String {
        var cleanedWord = word
        IGNORED_SYMBOLS.forEach { symbol ->
            cleanedWord = cleanedWord.replace(symbol.toString(), "")
        }
        return cleanedWord
    }

    // Сортировка "Расческой"
    private fun combSort(listToSort: List<String>): List<String> {
        val list = listToSort.toMutableList()
        var gap: Double = list.size.toDouble()
        var swaps = true

        while (gap > 1 || swaps) {
            gap /= GAP_FACTOR
            if (gap < 1) {
                gap = 1.0
            }
            var i = 0
            swaps = false
            while (i + gap < list.size) {
                val igap = i + gap.toInt()
                if (list[i].length < list[igap].length) {
                    val temp = list[i]
                    list[i] = list[igap]
                    list[igap] = temp
                    swaps = true
                }
                i++
            }
        }
        return list
    }

    // Получение словаря {частота слова : количество слов}
    private fun getWordsLengthFrequency(words: List<String>): Map<Int, Int> {
        val resultMap = mutableMapOf<Int, Int>()
        for (word in words) {
            if (!resultMap.containsKey(word.length)) {
                resultMap[word.length] = 0
            }
            resultMap[word.length] = resultMap[word.length]!! + 1
        }
        return resultMap
    }

    // Формирование содержиомого файла с результатом сортировки
    private fun formResultString(words: List<String>): String {
        val resultString = StringBuilder()
        var previousLength = words[0].length
        words.forEach { word ->
            if (word.length > previousLength) {
                resultString.append("\n")
                previousLength = word.length
            }
            resultString.append(word)
            resultString.append(" ")
        }
        return resultString.toString()
    }

    // Формирование содержиомого файла с анализом
    private fun formAnalysisString(
        originalText: String,
        wordCount: Int,
        lengthFrequency: Map<Int, Int>,
        sortTime: Long
    ): String {
        val resultString = StringBuilder()
            .append("Вариант: Латиница, сортировка по длине слова, сортировка расческой, по убыванию, учитывать числа.\n")
            .append("Слов в исходном тексте: $wordCount\n")
            .append("Время сортировки: $sortTime мс.\n")
            .append("\n")
            .append("Статистика (Длина слова : количество слов этой длины):")
        lengthFrequency.entries.stream()
            .toList()
            .sortedByDescending { it.key }
            .forEach { entry -> resultString.append("\n${entry.key} : ${entry.value}") }
        resultString.append("\n\n\n")
            .append("Оригинальный текст:\n")
            .append(originalText)
        return resultString.toString()
    }
}