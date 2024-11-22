package labs.lab6

import labs.Lab

/*
Переставить элементы заданного массива чисел так, чтобы он удовлетворял свойству
мин-куч.

Вход. Массив чисел A[0 ... n−1].

Выход. Переставить элементы массива так, чтобы выполнялись неравенства
A[i] ≤ A[2i + 1] и A[i] ≤ A[2i + 2] для всех i.

Формат входа. Первая строка содержит число n. Следующая строка задаёт массив
чисел A[0], ..., A[n−1].

Формат выхода. Первая строка выхода должна содержать число обменов m, которое
должно удовлетворять неравенству 0 ≤ m ≤ 4n. Каждая из последующих m строк должна
задавать обмен двух элементов массива A. Каждый обмен задаётся парой различных индексов
0 ≤ i != j ≤ n−1. После применения всех обменов в указанном порядке массив должен
превратиться в мин-кучу, то есть для всех 0 ≤ i ≤ n−1 должны выполняться следующие два
условия:
• если 2i + 1 ≤ n−1, то A[i] < A[2i + 1].
• если 2i + 2 ≤ n−1, то A[i] < A[2i + 2].

Ограничения. 1 ≤ n ≤ 10^5; 0 ≤ A[i] ≤ 10^9 для всех 0 ≤ i ≤ n−1; все A[i] попарно различны;
i != j.
*/

private const val MIN_POSSIBLE_VALUE = 0
private const val MAX_POSSIBLE_VALUE = 100000
private const val MIN_ARRAY_SIZE = 1
private const val MAX_ARRAY_SIZE = 1000000000


class Lab6Impl(var arraySize: Int, var arrayToHeapify: Array<Int>) : Lab {

    private var totalSwaps = 0
    private var swaps: MutableList<Pair<Int, Int>> = mutableListOf()

    override fun run() {
        if (isTaskValid()) {
            rearrangeToMinHeap(arrayToHeapify)
            println(formResult())
        } else {
            println("Ошибка: данные некорректны")
        }
    }

    private fun isTaskValid(): Boolean {
        val isSizeValid = arraySize in MIN_POSSIBLE_VALUE..MAX_POSSIBLE_VALUE
        val isSizeCorrect = arraySize == arrayToHeapify.size
        val areValuesDistinct = arrayToHeapify.distinct().size == arrayToHeapify.size
        val areValuesInCorrectRange = arrayToHeapify.all { it in MIN_ARRAY_SIZE..MAX_ARRAY_SIZE }
        return isSizeValid && isSizeCorrect && areValuesDistinct && areValuesInCorrectRange
    }

    private fun formResult(): String {
        var result = "$totalSwaps"
        swaps.forEach { result += "\n${it.first} ${it.second}" }
        return result
    }

    // Приведение массива к минкуче
    private fun rearrangeToMinHeap(inputList: Array<Int>) {
        for (i in inputList.size - 1 downTo 0) {
            minHeapify(inputList, i)
        }
    }

    // Приведение массива к минкуче для конкретного массива (вершины)
    private fun minHeapify(inputList: Array<Int>, baseMinIndex: Int) {
        var currentMinIndex = baseMinIndex
        val leftChildIndex = baseMinIndex * 2 + 1
        val rightChildIndex = baseMinIndex * 2 + 2

        if (leftChildIndex < inputList.size && inputList[leftChildIndex] < inputList[currentMinIndex]) {
            currentMinIndex = leftChildIndex
        }

        if (rightChildIndex < inputList.size && inputList[rightChildIndex] < inputList[currentMinIndex]) {
            currentMinIndex = rightChildIndex
        }

        if (currentMinIndex != baseMinIndex) {
            swap(inputList, currentMinIndex, baseMinIndex)
            minHeapify(inputList, currentMinIndex)
        }
    }

    // Обмен элементов в массиве
    private fun swap(inputList: Array<Int>, index1: Int, index2: Int) {
        totalSwaps++
        swaps.add(index2 to index1)

        val temp = inputList[index1]
        inputList[index1] = inputList[index2]
        inputList[index2] = temp
    }
}