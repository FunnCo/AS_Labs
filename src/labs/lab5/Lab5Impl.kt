package labs.lab5

import labs.Lab
import java.util.*
import java.util.stream.Collectors

/*
Цель: Найти максимум в каждом окне размера m данного массива чисел A[1 ... n].
Задание: Программа может быть написана на любом языке программирования.
Вход: Массив чисел A[1 ... n] и число 1 ≤ m ≤ n.
Выход: Максимум подмассива A[i ... i+m−1] для всех 1 ≤ i ≤ n−m+1.
Формат входа: Первая строка входа содержит число n, вторая – массив A[1 ... n], третья – число m.
Формат выхода: n−m+1 максимумов, разделённых пробелами.
Ограничения: 1 ≤ n ≤ 10^5, 1 ≤ m ≤ n, 0 ≤ A[i] ≤ 10^5 для всех 1 ≤ i ≤ n.
 */

private const val MIN_POSSIBLE_VALUE = 0
private const val MAX_POSSIBLE_VALUE = 100000
private const val MIN_ARRAY_SIZE = 1
private const val MAX_ARRAY_SIZE = 100000


class Lab5Impl(val arraySize: Int, val array: Array<Int>, val windowSize: Int): Lab {

    override fun run() {
        if(isTaskValid()){
            val result = maxInSlidingWindow(array, windowSize)
            val textResult = Arrays.stream(result)
                .map(Int::toString)
                .collect(Collectors.joining(" "))
            println(textResult)
        } else {
            println("Ошибка: введенные данные не соответствую условию задачи")
        }
    }

    private fun isTaskValid(): Boolean {
        val isSizeValid = arraySize in MIN_ARRAY_SIZE..MAX_ARRAY_SIZE
        val isArrayValid = array.size == arraySize && array.all { value -> value in MIN_POSSIBLE_VALUE .. MAX_POSSIBLE_VALUE }
        val isWindowSizeValid = windowSize in MIN_ARRAY_SIZE..arraySize
        return isSizeValid && isArrayValid && isWindowSizeValid
    }

    fun maxInSlidingWindow(inputArray: Array<Int>, windowSize: Int): Array<Int> {
        val result = Array(inputArray.size - windowSize + 1){0}
        val deque: Deque<Int> = LinkedList()

        for (i in inputArray.indices) {
            // Удаляем элементы, которые выходят за пределы окна
            if (deque.isNotEmpty() && deque.peek() == i - windowSize) {
                deque.poll()
            }

            // Удаляем все элементы, которые меньше текущего элемента
            while (deque.isNotEmpty() && inputArray[deque.peekLast()] < inputArray[i]) {
                deque.pollLast()
            }

            // Добавляем текущий элемент в очередь
            deque.offer(i)

            // Записываем максимальное значение для текущего окна
            if (i >= windowSize - 1) {
                result[i - windowSize + 1] = inputArray[deque.peek()]
            }
        }
        return result
    }
}