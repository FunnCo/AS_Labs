package labs.lab8

import common.FileUtils
import labs.Lab

class Lab8Impl(val inputFilePath: String, val outputFilePath: String) : Lab {

    override fun run() {
        var input = FileUtils.getLinesFromFile(inputFilePath)
        input = input.subList(1, input.size) // Количество чисел так-то не важно
        var nodes = mutableListOf<Node>()
        for (line in input) {
            val lineParams = line.split(" ")
            nodes.add(Node(lineParams[0].toInt(), lineParams[1].toInt(), lineParams[2].toInt()))
        }
        FileUtils.saveStringToFile(outputFilePath, formResultString(nodes))
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
        var result = getInOrderString(leftNodeToCheck, allNodes)
        result += " ${node.key}"
        result += " " + getInOrderString(rightNodeToCheck, allNodes)
        return result
    }

    private fun getPreOrderString(node: Node?, allNodes: List<Node>): String {
        if (node == null) {
            return ""
        }
        val leftNodeToCheck = if (node.leftChildIndex != -1) allNodes[node.leftChildIndex] else null
        val rightNodeToCheck = if (node.rightChildIndex != -1) allNodes[node.rightChildIndex] else null
        var result = " ${node.key}"
        result += " " + getPreOrderString(leftNodeToCheck, allNodes)
        result += " " + getPreOrderString(rightNodeToCheck, allNodes)
        return result
    }

    private fun getPostOrderString(node: Node?, allNodes: List<Node>): String {
        if (node == null) {
            return ""
        }
        val leftNodeToCheck = if (node.leftChildIndex != -1) allNodes[node.leftChildIndex] else null
        val rightNodeToCheck = if (node.rightChildIndex != -1) allNodes[node.rightChildIndex] else null
        var result = getPostOrderString(leftNodeToCheck, allNodes)
        result += " " + getPostOrderString(rightNodeToCheck, allNodes)
        result += " ${node.key}"
        return result
    }

}