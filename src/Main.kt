import labs.Lab
import labs.lab1.Lab1Impl
import labs.lab2.Lab2Impl
import labs.lab3.Lab3Impl
import labs.lab6.Lab6Impl
import labs.lab7.Lab7Impl
import labs.lab8.Lab8Impl


fun main() {
//    val lab = initLab()
//    lab.run()

//    Запуск во время тестирования производился следующим образом:
    initConfiguredLab(6, listOf("5", "5 4 3 2 1"))?.run()
    initConfiguredLab(6, listOf("5", "1 2 3 4 5"))?.run()
    // Некорректные запуски:
    initConfiguredLab(6, listOf("5123456203475602389465", "1 2 3 4 5"))?.run()
    initConfiguredLab(6, listOf("0", "1 2 3 4 5"))?.run()
    initConfiguredLab(6, listOf("5", "152738947502938457 2 3 4 5"))?.run()
    initConfiguredLab(6, listOf("5", "1 1 3 4 4"))?.run()
    initConfiguredLab(6, listOf("5", "1 2 3 4 a"))?.run()
    initConfiguredLab(6, listOf("a", "1 2 3 4 5"))?.run()
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

