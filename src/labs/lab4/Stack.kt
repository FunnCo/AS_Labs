package labs.lab4

class Stack(private val maxSize: Int) {
    private val stack = mutableListOf<Int>()
    private val maxStack = mutableListOf<Int>()
    private val minStack = mutableListOf<Int>()
    private var sum = 0

    fun push(value: Int) {
        if (stack.size == maxSize) {
            throw IllegalStateException("Stack is full")
        }
        stack.add(value)
        if (maxStack.isEmpty() || value >= maxStack.last()) {
            maxStack.add(value)
        }
        if (minStack.isEmpty() || value <= minStack.last()) {
            minStack.add(value)
        }
        sum += value
    }

    fun pop(): Int? {
        if (stack.isEmpty()) return null
        val value = stack.removeAt(stack.size - 1)
        if (value == maxStack.last()) {
            maxStack.removeAt(maxStack.size - 1)
        }
        if (value == minStack.last()) {
            minStack.removeAt(minStack.size - 1)
        }
        sum -= value
        return value
    }

    fun max(): Int? = maxStack.lastOrNull()

    fun min(): Int? = minStack.lastOrNull()

    fun avg(): Double? = if (stack.isNotEmpty()) sum.toDouble() / stack.size else null

    fun size(): Int = stack.size

    fun isFull(): Boolean = stack.size == maxSize
}