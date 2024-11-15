package common

import java.io.File

// Утилита для работы с файлами. Чтение строк и запись строки в файл.
object FileUtils {
    fun getLinesFromFile(fileName: String): List<String> = File(fileName).bufferedReader().readLines()

    fun saveStringToFile(filePath: String, content: String) {
        if(File(filePath).exists()) {
            File(filePath).delete()
        }
        File(filePath).createNewFile()
        File(filePath).writeText(content)
    }
}