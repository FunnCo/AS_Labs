package labs.lab7

import common.FileUtils
import common.InterpretationUtils
import labs.Lab
import java.util.stream.Collectors

/*
Реализовать структуру данных, эффективно обрабатывающую запросы вида add
number name, del number и find number.

Вход. Последовательность запросов вида add number name, del number и find number,
где number – телефонный номер, содержащий не более семи знаков, а name – короткая строка.

Выход. Для каждого запроса find number выведите соответствующее имя или сообщите,
что такой записи нет.

Цель в данной лабораторной работе – реализовать простую телефонную книгу,
поддерживающую три следующих типа запросов. С указанными ограничениями данная задача
может быть решена с использованием таблицы с прямой адресацией.
• add number name: добавить запись с именем name и телефонным номером number.
Если запись с таким телефонным номером уже есть, нужно заменить в ней имя на name.
• del number: удалить запись с соответствующим телефонным номером. Если такой
записи нет, ничего не делать.
• find number: найти имя записи с телефонным номером number. Если запись с таким
номером есть, вывести имя. В противном случае вывести «not found» (без кавычек).

Формат входа. Первая строка содержит число запросов n. Каждая из следующих n
строк задаёт запрос в одном из трёх описанных выше форматов.
Формат выхода. Для каждого запроса find выведите в отдельной строке либо имя, либо
«not found».

Ограничения. 1 ≤ n ≤ 105. Телефонные номера содержат не более семи цифр и не
содержат ведущих нулей. Имена содержат только буквы латинского алфавита, не являются
пустыми строками и имеют длину не больше 15. Гарантируется, что среди имён не встречается
строка «not found».

Дополнительно:
1. Поддержка групп и меток. Добавить возможность создавать группы контактов
и назначать метки. Например, запросы add number name group и tag number
tag_name. При этом у одного контакта может быть несколько меток. Реализовать
возможность поиска по группе и метке.

2. Поиск по частичному совпадению. Добавить операцию find partial number,
которая ищет контакты по частичному совпадению номера. Например, find 123
вернет все контакты, где номер начинается с 123.

4. Защита от дубликатов имен. Ввести правило, что одно имя может быть связано
только с одним номером. Если пользователь пытается добавить имя, которое
уже существует с другим номером, программа должна выдать предупреждение
или проигнорировать операцию.

5. Поддержка поиска по имени. Добавить запросы вида find name, которые ищут
по имени и возвращают соответствующий номер. Если одно имя связано с
несколькими номерами, вернуть все соответствия.

6. Сортировка и вывод контактов. Добавить возможность сортировки контактов
по именам или номерам с помощью команд sort by name и sort by number.
Вывести все контакты в заданном порядке.
*/


class Lab7Impl(val inputFilePath: String, val outputFilePath: String) : Lab {
    var phoneBook = PhoneBook()

    override fun run() {
        InterpretationUtils.interpretFilesCommands(inputFilePath, outputFilePath, this::executeCommand)
    }

    fun executeCommand(command: String): String {
        val parts = command.split(" ")
        val commandType = parts[0].lowercase()

        return when (commandType) {
            Commands.ADD.name.lowercase() -> executeAdd(parts)
            Commands.FIND.name.lowercase() -> executeFind(parts)
            Commands.DEL.name.lowercase() -> executeDelete(parts)
            Commands.SORT.name.lowercase() -> executeSort(parts)
            Commands.TAG.name.lowercase() -> executeTag(parts)
            else -> "unknown command"
        }
    }

    // Добавление записи
    // Синтаксис: add <number> <name> [<group>]
    fun executeAdd(parts: List<String>): String {
        if (parts.size < 3 || parts.size > 4 || !parts[1].matches("^\\d+\$".toRegex())) {
            return "Error: wrong syntax"
        }
        var group: String? = null
        if (parts.size == 4) {
            group = parts[3]
        }
        return try {
            phoneBook.addEntry(parts[1].toInt(), parts[2], group)
            ""
        } catch (e: IllegalStateException) {
            "can't add duplicate number or name"
        }
    }

    // Поиск записи
    // Синтаксис: find <number|name>
    //            или find group <groupName>
    //            или find tag <tag>
    fun executeFind(parts: List<String>): String {
        return when {
            parts.size == 2 -> {
                val numberOrName = parts[1]
                if (numberOrName.matches("^\\d+\$".toRegex())) {
                    val result = phoneBook.getEntryByNumber(numberOrName.toInt())
                    result?.name ?: "not found"
                } else {
                    val result = phoneBook.getEntryByName(numberOrName)
                    if (result?.phone == null) "not found" else result.phone.toString()
                }
            }
            parts.size == 3 && parts[1].lowercase() == "group" -> {
                val groupName = parts[2]
                val entries = phoneBook.getEntriesByGroup(groupName)
                entries.stream().map(Entry::name).collect(Collectors.joining(" "))
            }
            parts.size == 3 && parts[1].lowercase() == "tag" -> {
                val tag = parts[2]
                val entries = phoneBook.getEntriesByTag(tag)
                entries.stream().map(Entry::name).collect(Collectors.joining(" "))
            }
            else -> "Error: wrong syntax"
        }
    }

    // Удаление записи
    // Синтаксис: del <number|name>
    fun executeDelete(parts: List<String>): String {
        if (parts.size != 2) {
            return "Error: wrong syntax"
        }
        val identifier = parts[1]
        return try {
            if (identifier.matches("^\\d+\$".toRegex())) {
                phoneBook.deleteEntryByNumber(identifier.toInt())
            } else {
                phoneBook.deleteEntryByName(identifier)
            }
            ""
        } catch (e: NoSuchElementException) {
            "not found"
        }
    }

    // Список записей
    // Синтаксис: list [name|number]
    fun executeSort(parts: List<String>): String {
        val sortType = if (parts.size == 2) parts[1] else "name"
        val entries = phoneBook.getAllEntriesSorted(sortType)
        return if (sortType == "name") {
            entries.stream()
                .map(Entry::name)
                .collect(Collectors.joining(" "))
        } else {
            entries.stream()
                .map(Entry::phone)
                .map(Int::toString)
                .collect(Collectors.joining(" "))
        }
    }

    // Добавление тега к записи
    // Синтаксис: tag <number> <tag>
    fun executeTag(parts: List<String>): String {
        if (parts.size != 3 || !parts[1].matches("^\\d+\$".toRegex())) {
            return "Error: wrong syntax"
        }
        val number = parts[1].toInt()
        val tag = parts[2]
        val entry = phoneBook.getEntryByNumber(number)
        return if (entry != null) {
            entry.tags.add(tag)
            ""
        } else {
            "not found"
        }
    }
}
