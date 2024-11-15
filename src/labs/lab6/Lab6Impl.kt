package labs.lab6

import common.FileUtils
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

Ограничения. 1 ≤ n ≤ 105; 0 ≤ A[i] ≤ 109 для всех 0 ≤ i ≤ n−1; все A[i] попарно различны;
i != j.
*/

class Lab6Impl(var arrayToHeapify: Array<Int>) : Lab {

    private var totalSwaps = 0
    private var swaps: MutableList<Pair<Int, Int>> = mutableListOf()

    override fun run() {
        rearrangeToMinHeap(arrayToHeapify)
        println(formResult())
    }

    fun formResult(): String {
        var result = "$totalSwaps"
        for (swap in swaps) {
            result += "\n${swap.first} ${swap.second}"
        }
        return result
    }

    fun rearrangeToMinHeap(inputList: Array<Int>){
        for(i in inputList.size-1 downTo 0){
            minHeapify(inputList, i)
        }
    }

    fun minHeapify(inputList: Array<Int>, baseMinIndex: Int) {
        var currentMinIndex = baseMinIndex
        var leftChildIndex = baseMinIndex * 2 + 1
        var rightChildIndex = baseMinIndex * 2 + 2

        if (leftChildIndex < inputList.size && inputList[leftChildIndex] < inputList[currentMinIndex]) {
            currentMinIndex = leftChildIndex
        }

        if (rightChildIndex < inputList.size && inputList[rightChildIndex] < inputList[currentMinIndex]) {
            currentMinIndex = rightChildIndex
        }

        if(currentMinIndex != baseMinIndex) {
            swap(inputList, currentMinIndex, baseMinIndex)
            minHeapify(inputList, currentMinIndex)
        }
    }

    fun swap(inputList: Array<Int>, index1: Int, index2: Int) {
        totalSwaps++
        swaps.add(index2 to index1)

        val temp = inputList[index1]
        inputList[index1] = inputList[index2]
        inputList[index2] = temp
    }
}