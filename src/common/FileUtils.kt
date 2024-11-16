package common

import java.io.File
import java.io.FileNotFoundException

// Утилита для работы с файлами. Чтение строк и запись строки в файл.
object FileUtils {
    fun getLinesFromFile(fileName: String): List<String> {
        try {
            return File(fileName).bufferedReader().readLines()
        } catch (e: FileNotFoundException){
            println("Файл не найден")
        }
        return emptyList()
    }

    fun saveStringToFile(filePath: String, content: String) {
        if(File(filePath).exists()) {
            File(filePath).delete()
        }
        File(filePath).createNewFile()
        File(filePath).writeText(content)
    }
}