package labs.lab2

import common.FileUtils
import labs.Lab

/*
Вычислить высоту данного дерева. Научиться хранить и
эффективно обрабатывать деревья, даже если в них сотни тысяч вершин.

Вход. Корневое дерево с вершинами {0, ..., n−1}, заданное как последовательность
parent0, ..., parentn−1, где parenti – родитель i-й вершины.

Выход. Высота дерева.

Формат входа. Первая строка содержит натуральное число n. Вторая строка содержит
n целых чисел parent0, ..., parentn−1. Для каждого 0 ≤ i ≤ n−1, parenti – родитель вершины i; если
parenti = −1, то i является корнем. Гарантируется, что корень ровно один. Гарантируется, что
данная последовательность задаёт дерево.

Формат выхода. Высота дерева.

Ограничения. 1 ≤ n ≤ 105.
*/

class Lab2Impl(val inputFilePath: String) : Lab {

    override fun run() {
        val allNodes = processInput(FileUtils.getLinesFromFile(inputFilePath))
        println(findHeightOfTree(allNodes))
    }

    private fun processInput(lines: List<String>): List<Node> {
        val totalNodes = lines[0].toInt()
        val parentIndexes = lines[1].split(' ')
            .map { it.toInt() }

        // Создаем n вершин
        val nodes = mutableListOf<Node>()
        for (index in 1..totalNodes) {
            nodes.add(Node(null))
        }

        // Задаем вершинам родителей
        for (i in parentIndexes.indices) {
            if (parentIndexes[i] != -1) {
                nodes[i].parent = nodes[parentIndexes[i]]
            }
        }
        return nodes
    }

    private fun findHeightOfTree(nodes: List<Node>): Int {
        var maxHeight = 0
        for (rootNode in nodes) {
            var currentNode = rootNode
            var currentHeight = 1
            while (currentNode.parent != null) {
                currentNode = currentNode.parent!!

                // Если мы уже посещали эту вершину, то нет смысла расчитывать ее высоту снова
                if (currentNode.height != 0) {
                    currentHeight += currentNode.height
                    break
                } else {
                    currentHeight++
                }
            }
            if (currentHeight > maxHeight) {
                rootNode.height = currentHeight
                maxHeight = currentHeight
            }
        }
        return maxHeight
    }
}