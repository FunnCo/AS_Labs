package labs.lab3.data

enum class ProcessorState {
    WAITING,        // Свободен
    WORKING,        // Занят
    FINISHING       // Закончил обработку прошлого пакета, взял новый, но не начал его обрабатывать.
}