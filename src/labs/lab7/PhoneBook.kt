package labs.lab7

import java.util.Comparator

class PhoneBook {

    // Просто все записи
    private val allEntries = mutableListOf<Entry>()

    // Словари, для избежания дублирования имен или телефонов
    private val phoneToEntries = mutableMapOf<Int, Entry>()
    private val nameToEntries = mutableMapOf<String, Entry>()

    fun getEntriesByNumberPart(numberPart: Int): List<Entry> {
        return allEntries.stream()
            .filter { entry -> entry.phone.toString().contains(numberPart.toString()) }
            .sorted(Comparator.comparing(Entry::name))
            .toList()
    }

    fun getEntryByNumber(number: Int): Entry? {
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

    fun addEntry(number: Int, name: String, group: String?) {
        var entry = phoneToEntries[number] ?: nameToEntries[name]
        if (entry != null) {
            throw IllegalStateException("Duplicate entry $number")
        }
        entry = Entry(number, name, group, mutableListOf())
        allEntries.add(entry)
        phoneToEntries[number] = entry
        nameToEntries[name] = entry
    }

    fun deleteEntryByNumber(number: Int) {
        val entryToDelete = phoneToEntries[number] ?: throw NoSuchElementException("No entry with number $number")
        deleteEntry(entryToDelete)
    }

    fun deleteEntryByName(name: String) {
        val entryToDelete = nameToEntries[name] ?: throw NoSuchElementException("No entry with name $name")
        deleteEntry(entryToDelete)
    }

    private fun deleteEntry(entry: Entry) {
        allEntries.remove(entry)
        phoneToEntries.remove(entry.phone)
        nameToEntries.remove(entry.name)
    }
}