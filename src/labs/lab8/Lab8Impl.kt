package labs.lab8

import common.FileUtils
import labs.Lab

/*
Цель: Построить in-order, pre-order и post-order обходы данного двоичного дерева.

Задание: Программа может быть написана на любом языке программирования.

Вход: Двоичное дерево.

Выход: Все его вершины в трёх разных порядках: in-order, pre-order и post-order.
    - In-order обход соответствует следующей рекурсивной процедуре, получающей на вход
корень v текущего поддерева: произвести рекурсивный вызов для v.left, напечатать v.key,
произвести рекурсивный вызов для v.right.
    - Pre-order обход: напечатать v.key, произвести рекурсивный вызов для v.left, произвести
    рекурсивный вызов для v.right.
    - Post-order: произвести рекурсивный вызов для v.left, произвести рекурсивный вызов
для v.right, напечатать v.key.

Формат входа: Первая строка содержит число вершин n. Вершины дерева
пронумерованы числами от 0 до n−1. Вершина 0 является корнем. Каждая из следующих n
строк содержит информацию о вершинах 0, 1, ..., n−1: i-я строка задаёт числа key i, left i и right i,
где key i – ключ вершины i, left i – индекс левого сына вершины i, а right i – индекс правого сына
вершины i. Если у вершины i нет одного или обоих сыновей, соответствующее значение равно −1.

Формат выхода: Три строки: in-order, pre-order и post-order обходы.

Ограничения: 1 ≤ n ≤ 10^5; 0 ≤ keyi ≤ 10^9; −1 ≤ left i, right i ≤ n−1. Гарантируется, что вход
задаёт корректное двоичное дерево: в частности, если left i != −1 и right i != −1, то left i != right i;
никакая вершина не является сыном двух вершин; каждая вершина является потомком корня.
*/

private const val MIN_NODES_COUNT = 1
private const val MAX_NODES_COUNT = 100000
private const val MIN_POSSIBLE_VALUE = 0
private const val MAX_POSSIBLE_VALUE = 1000000000

class Lab8Impl(val inputFilePath: String, val outputFilePath: String) : Lab {

    override fun run() {
        try {
            var input = FileUtils.getLinesFromFile(inputFilePath)
            if(isTaskValid(input)){
                input = input.subList(1, input.size) // Количество чисел так-то не важно
                val nodes = mutableListOf<Node>()
                for (line in input) {
                    val lineParams = line.split(" ")
                    nodes.add(Node(lineParams[0].toInt(), lineParams[1].toInt(), lineParams[2].toInt()))
                }
                FileUtils.saveStringToFile(outputFilePath, formResultString(nodes))
            } else {
                println("Ошибка: введены некорректные данные, т.е не удволетворяющие условию задачи")
            }
        } catch (e: Exception) {
            println("Ошибка: введены некорректные данные: ${e.message}")
        }
    }

    private fun isTaskValid(input: List<String>): Boolean {
        try{
            val nodesCount = input.get(0).toInt()
            val isNodeCountValid = nodesCount == input.size - 1 && nodesCount in MIN_NODES_COUNT..MAX_NODES_COUNT
            val areNodesValid = input.subList(1, input.size).stream()
                .allMatch {isNodeValid(it, nodesCount)}
            return isNodeCountValid && areNodesValid
        } catch (e: Exception){
            return false
        }
    }

    private fun isNodeValid(node: String, nodesCount: Int): Boolean{
        try {
            val nodeParams = node.split(" ").map { it.toInt() }.toList()
            val isKeyValid = nodeParams[0] in MIN_POSSIBLE_VALUE .. MAX_POSSIBLE_VALUE
            val isLeftValid = nodeParams[1] in -1 .. nodesCount
            val isRightValid = nodeParams[2] in -1 .. nodesCount
            val areChildrenValid = nodeParams[1] != nodeParams[2] || nodeParams[1] == -1 || nodeParams[2] == -1
            return isKeyValid && isLeftValid && isRightValid && areChildrenValid
        } catch (e: Exception){
            return false
        }
    }

    private fun formResultString(nodes: List<Node>): String {
        val inOrderResult = getInOrderString(nodes[0], nodes)
        val preOrderResult = getPreOrderString(nodes[0], nodes)
        val postOrderResult = getPostOrderString(nodes[0], nodes)
        return "${beautifyString(inOrderResult)}\n${beautifyString(preOrderResult)}\n${beautifyString(postOrderResult)}"
    }

    private fun beautifyString(line: String): String {
        return line.replace("\\s+".toRegex(), " ").trim()
    }


    private fun getInOrderString(node: Node?, allNodes: List<Node>): String {
        if (node == null) {
            return ""
        }
        val leftNodeToCheck = if (node.leftChildIndex != -1) allNodes[node.leftChildIndex] else null
        val rightNodeToCheck = if (node.rightChildIndex != -1) allNodes[node.rightChildIndex] else null
        var result = " ${getInOrderString(leftNodeToCheck, allNodes)}"
        result += " ${node.key}"
        result += " ${getInOrderString(rightNodeToCheck, allNodes)}"
        return result
    }

    private fun getPreOrderString(node: Node?, allNodes: List<Node>): String {
        if (node == null) {
            return ""
        }
        val leftNodeToCheck = if (node.leftChildIndex != -1) allNodes[node.leftChildIndex] else null
        val rightNodeToCheck = if (node.rightChildIndex != -1) allNodes[node.rightChildIndex] else null
        var result = " ${node.key}"
        result += " ${getPreOrderString(leftNodeToCheck, allNodes)}"
        result += " ${getPreOrderString(rightNodeToCheck, allNodes)}"
        return result
    }

    private fun getPostOrderString(node: Node?, allNodes: List<Node>): String {
        if (node == null) {
            return ""
        }
        val leftNodeToCheck = if (node.leftChildIndex != -1) allNodes[node.leftChildIndex] else null
        val rightNodeToCheck = if (node.rightChildIndex != -1) allNodes[node.rightChildIndex] else null
        var result = " ${getPostOrderString(leftNodeToCheck, allNodes)}"
        result += " ${getPostOrderString(rightNodeToCheck, allNodes)}"
        result += " ${node.key}"
        return result
    }
}