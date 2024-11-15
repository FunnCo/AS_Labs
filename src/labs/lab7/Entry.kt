package labs.lab7

data class Entry(
    val phone: Int,
    val name: String,
    var group: String?, // Как я понял, может быть только одна группа
    var tags: MutableList<String> // А меток может быть много
)