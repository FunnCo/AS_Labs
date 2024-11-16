import labs.Lab
import labs.lab1.Lab1Impl
import labs.lab2.Lab2Impl
import labs.lab3.Lab3Impl
import labs.lab6.Lab6Impl
import labs.lab7.Lab7Impl
import labs.lab8.Lab8Impl


fun main() {
    initConfiguredLab(1, listOf("/home/funnco/AlgLabs/ЛР1/test14/original.txt", "/home/funnco/AlgLabs/ЛР1/test14/output")).run()

//    val lab = initLab()
//    lab.run()

//    Запуск во время тестирования производился следующим образом:
//    for(i in 1 .. 10){
//        val lab = initConfiguredLab(1, listOf("/home/funnco/AlgLabs/ЛР1/test${i}/original.txt", "/home/funnco/AlgLabs/ЛР1/test${i}/output"))
//        lab.run()
//    }
}



fun initConfiguredLab(number: Int, config: List<String>): Lab {
    when (number) {
        1 -> {
            val inputFilePath = config[0]
            val outputDirectoryPath = config[1]
            return Lab1Impl(inputFilePath, outputDirectoryPath)
        }

        2 -> {
            val inputFilePath = config[0]
            return Lab2Impl(inputFilePath)
        }

        3 -> {
            val inputFilePath = config[0]
            return Lab3Impl(inputFilePath)
        }

        6 -> {
            val array = config[0].split(" ").map{ it.toInt() }.toTypedArray()
            return Lab6Impl(array)
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
}

fun initLab(): Lab {
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
            println("Введите путь до файла с деревом")
            val inputFilePath = readln()
            return Lab2Impl(inputFilePath)
        }

        6 -> {
            println("Введите данные, в формате как в методичке")
            readln() // По факту размер массива не имеет значения
            val array = readln().split(" ").map{ it.toInt() }.toTypedArray()
            println()
            return Lab6Impl(array)
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
}

