import java.lang.Exception
import java.util.LinkedList

class IterableTree<K: Comparable<K>, V>: Iterable<V> {

    inner class Node(var key: K, var value: V, var parent: Node?) {
        var left: Node? = null
        var right: Node? = null
    }

    var root: Node? = null

    fun find(key: K): V? {
        var current = root
        while (current != null) {
            when {
                current.key == key -> return current.value
                current.key > key -> current = current.left
                current.key < key -> current = current.right
            }
        }
        return null
    }

    fun insert(key: K, value: V) {
        if (root == null) {
            root = Node(key, value, null)
        } else {
            var current = root
            while (current != null) {
                when {
                    current.key == key -> {
                        current.value = value
                        return
                    } current.key < key -> {
                        if (current.right == null) {
                            current.right = Node(key, value, current)
                            return
                        }
                        current = current.right
                    } else -> {
                        if (current.left == null) {
                            current.left = Node(key, value, current)
                            return
                        }
                        current = current.left
                    }
                }
            }
        }

    }

    fun printTreeByLevels() {
        if (root == null) {
            return
        }
        var currentLevel = LinkedList<Node>()
        var nextLevel = LinkedList<Node>()
        currentLevel.add(root!!)
        while (!currentLevel.isEmpty()) {
            for (node in currentLevel.iterator()) {
                if (node.left != null) {
                    nextLevel.add(node.left!!)
                }
                if (node.right != null) {
                    nextLevel.add(node.right!!)
                }
                print("${node.value} ")
            }
            println()
            currentLevel = nextLevel
            nextLevel = LinkedList()
        }
    }

    inner class TreeIterator: Iterator<V> {

        private fun findLeastKeyFrom(node: Node?): Node? {
            var current = node
            while (current?.left != null) {
                current = current.left
            }
            return current
        }

        private var currentNode = findLeastKeyFrom(root)

        override fun hasNext(): Boolean {
            return currentNode != null
        }

        override fun next(): V {
            if (!hasNext()) {
                throw Exception("There's no more elements!")
            } else {
                val value = currentNode!!.value
                if (currentNode?.right != null) {
                    currentNode = findLeastKeyFrom(currentNode?.right)
                } else {
                    while (currentNode?.parent != null) {
                        if(currentNode?.parent.left == currentNode) {
                            currentNode = currentNode?.parent
                            return value
                        }
                        currentNode = currentNode?.parent
                    }
                    currentNode = null
                }
                return value
            }
        }
    }

    override fun iterator(): Iterator<V> = TreeIterator()
}
