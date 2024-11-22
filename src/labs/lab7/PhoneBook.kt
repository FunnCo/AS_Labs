package labs.lab7

import java.util.Comparator

class PhoneBook {

    // Просто все записи
    private val allEntries = mutableListOf<Entry>()

    // Словари, для избежания дублирования имен или телефонов
    private val phoneToEntries = mutableMapOf<String, Entry>()
    private val nameToEntries = mutableMapOf<String, Entry>()

    fun getEntryByNumber(number: String): Entry? {
        return phoneToEntries[number]
    }

    fun getEntryByName(name: String): Entry? {
        return nameToEntries[name]
    }

    fun getEntriesByGroup(group: String): List<Entry> {
        return allEntries.stream()
            .filter { entry -> entry.group == group }
            .sorted(Comparator.comparing(Entry::name))
            .toList()
    }

    fun getEntriesByTag(tag: String): List<Entry> {
        return allEntries.stream()
            .filter { entry -> entry.tags.contains(tag) }
            .sorted(Comparator.comparing(Entry::name))
            .toList()
    }

    fun getAllEntriesSorted(sortType: String): List<Entry> {
        return if (sortType == "name") {
            allEntries.stream().sorted(Comparator.comparing(Entry::name)).toList()
        } else {
            allEntries.stream().sorted(Comparator.comparing(Entry::phone)).toList()
        }
    }

    fun addEntry(number: String, name: String, group: String?) {
        var entry = phoneToEntries[number] ?: nameToEntries[name]
        if (entry != null) {
            throw IllegalStateException("duplicate entry")
        }
        entry = Entry(number, name, group, mutableListOf())
        if(isEntryValid(entry)) {
            allEntries.add(entry)
            phoneToEntries[number] = entry
            nameToEntries[name] = entry
        } else {
            throw IllegalStateException("entry fields are not valid")
        }
    }

    private fun isEntryValid(entry: Entry): Boolean {
        // В задании сказано только латинские буквы, но в примере есть и числа. Пробелы, и иные символы считаются некорректными
        // Проверять на название "not found" нет смысла, т.к. в имени подразумевается только одно слово.
        val isNameLatin = entry.name.matches(Regex("^[A-Za-z\\d]*$"))
        val isNameShort = entry.name.length <= 15
        val doesPhoneHaveLeadingZero = entry.phone[0] != '0'
        val doesPhoneHaveCorrectLength = entry.phone.length <= 7
        return isNameLatin && isNameShort && doesPhoneHaveLeadingZero && doesPhoneHaveCorrectLength
    }

    fun deleteEntryByNumber(number: String) {
        val entryToDelete = phoneToEntries[number] ?: throw NoSuchElementException("no entry with number $number")
        deleteEntry(entryToDelete)
    }

    fun deleteEntryByName(name: String) {
        val entryToDelete = nameToEntries[name] ?: throw NoSuchElementException("no entry with name $name")
        deleteEntry(entryToDelete)
    }

    private fun deleteEntry(entry: Entry) {
        allEntries.remove(entry)
        phoneToEntries.remove(entry.phone)
        nameToEntries.remove(entry.name)
    }
}