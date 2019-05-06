import java.lang.Exception
import java.util.*

class IterableSearchTree<K: Comparable<K>, V>: Iterable<V> {
    inner class Node(val key: K, var value: V, var parent: Node?) {
        var left: Node? = null
        var right: Node? = null
    }

    private var root: Node? = null

    private fun createNode(key: K, value: V, parent: Node?): Node {
        return Node(key, value, parent)
    }

    fun find(key: K): Node? {
        var tempNode = this.root

        while (tempNode != null) {
            when {
                key == tempNode.key -> return tempNode
                key < tempNode.key -> tempNode = tempNode.left
                key > tempNode.key -> tempNode = tempNode.right
            }
        }

        return null
    }

    fun insert(key: K, value: V): Node {
        if (this.root == null) {
            this.root = createNode(key, value,null)
            return this.root!!
        }

        var tempNode = this.root
        var parent: Node? = null
        var isRightSon = false
        var isLeftSon = false

        while (tempNode != null) {
            parent = tempNode
            when {
                key < tempNode.key -> {
                    tempNode = tempNode.left
                    if (tempNode == null) isLeftSon = true
                }
                key > tempNode.key -> {
                    tempNode = tempNode.right
                    if (tempNode == null) isRightSon = true
                }
                key == tempNode.key -> {
                    tempNode.value = value
                    return tempNode
                }
            }
        }

        val newNode = createNode(key, value, parent)
        if (isRightSon)
            parent!!.right = newNode
        if (isLeftSon)
            parent!!.left = newNode

        return newNode
    }

    inner class TreeIterator: Iterator<V> {
        private fun createValuesListWithBFS(): LinkedList<V> {
            val queue: Queue<Node> = LinkedList<Node>()
            val valuesList = LinkedList<V>()

            if (root != null)
                queue.add(root)

            while (!queue.isEmpty()) {
                val tempNode = queue.remove()
                valuesList.add(tempNode.value)

                if (tempNode.left != null)
                    queue.add(tempNode.left)
                if (tempNode.right != null)
                    queue.add(tempNode.right)
            }

            return valuesList
        }

        private val valuesList = createValuesListWithBFS()

        override fun hasNext(): Boolean {
            return !valuesList.isEmpty()
        }

        override fun next(): V {
            if (!hasNext())
                throw Exception("No Such Element!")

            return valuesList.remove()
        }
    }

    override fun iterator(): Iterator<V> {
        return TreeIterator()
    }
}