package labs.lab5

import labs.Lab

class Lab5Impl(val inputFilePath: String): Lab {

    override fun run() {

    }

    // TODO: Сгенерил чат жпт, пока не разобрался, чуть позже буду сдавать
    fun findMaxInSubarrays(arr: IntArray, m: Int): IntArray {
        if (arr.isEmpty() || m > arr.size) return intArrayOf()

        val deque = ArrayDeque<Int>()  // двусторонняя очередь для хранения индексов
        val result = IntArray(arr.size - m + 1)  // массив для результатов

        for (i in arr.indices) {
            // Удаляем элементы из начала очереди, которые вышли за пределы текущего окна размера m
            if (deque.isNotEmpty() && deque.first() <= i - m) {
                deque.removeFirst()
            }

            // Удаляем из конца очереди индексы, элементы по которым меньше текущего элемента,
            // так как они не могут быть максимумами в последующих окнах
            while (deque.isNotEmpty() && arr[deque.last()] <= arr[i]) {
                deque.removeLast()
            }

            // Добавляем текущий элемент в очередь
            deque.addLast(i)

            // Когда формируется первое окно размера m, добавляем в результат максимум (элемент с индексом deque.first)
            if (i >= m - 1) {
                result[i - m + 1] = arr[deque.first()]
            }
        }

        return result
    }
}