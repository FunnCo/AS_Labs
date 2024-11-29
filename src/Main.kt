import labs.Lab
import labs.lab1.Lab1Impl
import labs.lab2.Lab2Impl
import labs.lab3.Lab3Impl
import labs.lab5.Lab5Impl
import labs.lab6.Lab6Impl
import labs.lab7.Lab7Impl
import labs.lab8.Lab8Impl


fun main() {
//    Консольный ввод
//    initLab()?.run()

//    Запуск во время тестирования производился следующим образом:
    initConfiguredLab(5, listOf("8", "2 7 3 1 5 2 6 2", "4"))?.run()
    initConfiguredLab(5, listOf("3", "2 1 5", "1"))?.run()
    initConfiguredLab(5, listOf("3", "2 3 9", "3"))?.run()

//    Некорректные запуски:
//    initConfiguredLab(5, listOf("9", "2 7 3 1 5 2 6 2", "4"))?.run() // Размер массива не совпадает с заявленным
//    initConfiguredLab(5, listOf("3", "2 1 5", "5"))?.run() // Размер окна больше, чем сам массив
//    initConfiguredLab(5, listOf("a", "2 3 9", "3"))?.run() // Проверки на правильный тип данных
//    initConfiguredLab(5, listOf("3", "a 3 9", "3"))?.run()
//    initConfiguredLab(5, listOf("3", "2 3 9", "a"))?.run()
}


fun initConfiguredLab(number: Int, config: List<String>): Lab? {
    try {
        when (number) {
            1 -> {
                val inputFilePath = config[0]
                val outputDirectoryPath = config[1]
                return Lab1Impl(inputFilePath, outputDirectoryPath)
            }

            2 -> {
                val arraySize = config[0].toInt()
                val array = config[1].split(" ").map { it.toInt() }.toList()
                return Lab2Impl(arraySize, array)
            }

            3 -> {
                val inputFilePath = config[0]
                return Lab3Impl(inputFilePath)
            }

            5 -> {
                val arraySize = config[0].toInt()
                val array = config[1].split(" ").map { it.toInt() }.toTypedArray()
                val windowSize = config[2].toInt()
                return Lab5Impl(arraySize, array, windowSize)
            }

            6 -> {
                val arraySize = config[0].toInt()
                val array = config[1].split(" ").map { it.toInt() }.toTypedArray()
                return Lab6Impl(arraySize, array)
            }

            7 -> {
                val inputFilePath = config[0]
                val outputDirectoryPath = config[1]
                return Lab7Impl(inputFilePath, outputDirectoryPath)
            }

            8 -> {
                val inputFilePath = config[0]
                val outputDirectoryPath = config[1]
                return Lab8Impl(inputFilePath, outputDirectoryPath)
            }

            else -> throw Exception("Заданная лабораторная работа еще не сделана, или ее не существует")
        }
    } catch (e: Exception) {
        println("Ошибка: введенные данные некорректны: ${e.message}")
        return null
    }
}

fun initLab(): Lab? {
    try {
        println("Введите номер лабораторной работы")
        val number = readln().toInt()
        when (number) {
            1 -> {
                println("Введите путь до файла с изначальным текстом")
                val inputFilePath = readln()
                println("Введите путь до папки, куда сохранить данные")
                val outputDirectoryPath = readln()
                return Lab1Impl(inputFilePath, outputDirectoryPath)
            }

            2 -> {
                println("Введите данные, в формате как в методичке")
                val arraySize = readln() // По факту размер массива не имеет значения
                val array = readln().split(" ").map { it.toInt() }.toList()
                println()
                return Lab2Impl(arraySize.toInt(), array)
            }

            5 -> {
                println("Введите данные, в формате как в методичке")
                val arraySize = readln().toInt()
                val array = readln().split(" ").map { it.toInt() }.toTypedArray()
                val windowSize = readln().toInt()
                return Lab5Impl(arraySize, array, windowSize)
            }

            6 -> {
                println("Введите данные, в формате как в методичке")
                val arraySize = readln().toInt() // По факту размер массива не имеет значения
                val array = readln().split(" ").map { it.toInt() }.toTypedArray()
                println()
                return Lab6Impl(arraySize, array)
            }

            7 -> {
                println("Введите путь до файла с запросами к телефонной книге")
                val inputFilePath = readln()
                println("Введите путь до файла, куда сохранять результаты запросов")
                val outputFilePath = readln()
                return Lab7Impl(inputFilePath, outputFilePath)
            }

            8 -> {
                println("Введите путь до файла с описанием бинарного дерева")
                val inputFilePath = readln()
                println("Введите путь до файла, куда сохранить результаты обходов")
                val outputFilePath = readln()
                return Lab8Impl(inputFilePath, outputFilePath)
            }

            else -> throw Exception("Заданная лабораторная работа еще не сделана, или ее не существует")
        }
    } catch (e: Exception) {
        println("Ошибка: введенные данные некорректны: ${e.message}")
        return null
    }
}

