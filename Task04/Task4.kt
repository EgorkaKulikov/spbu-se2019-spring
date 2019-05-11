import java.util.*

class IterableSearchTree<K: Comparable<K>, T>: Iterable<T> {
    inner class Node (_key: K, _value: T) {
        var left: Node? = null
        var right: Node? = null
        var value = _value
        val key = _key
    }
    private var root: Node? = null

    fun find (key: K): T? {
        var tempNode = root
        while(tempNode != null) {
            when {
                tempNode.key == key -> return tempNode.value
                tempNode.key > key -> tempNode = tempNode.left
                tempNode.key < key -> tempNode = tempNode.right
            }
        }

        return null
    }

    fun inserte (key: K, value: T) {
        if (root == null) {
            root = Node(key, value)
        }

        var tempNode = root!!
        while (true) {
                if (tempNode.key > key) {
                    if (tempNode.left == null) {
                        val newNode = Node(key, value)
                        tempNode.left = newNode
                        return
                    }
                    else {
                        tempNode = tempNode.left!!
                    }
                }
                if (tempNode.key < key) {
                    if (tempNode.right == null) {
                        val newNode = Node(key, value)
                        tempNode.right = newNode
                        return
                    }
                    else {
                        tempNode = tempNode.right!!
                    }
                }
                if (tempNode.key == key) {
                    tempNode.value = value
                    return
                }
        }
    }

    inner class TreeIterator: Iterator<T> {
        private val ListOfNodes = ListOfNodesWithBFS()
        private fun ListOfNodesWithBFS (): LinkedList<Node> {
            val ListOfNodes = LinkedList<Node>()
            val queue: Queue<Node> = LinkedList<Node>()

            if (root != null) {
                queue.add(root)
                ListOfNodes.add(root!!)
            }

            while(!queue.isEmpty()) {
                val tempNode = queue.remove()
                if (tempNode.left != null) {
                    queue.add(tempNode.left)
                    ListOfNodes.add(tempNode.left!!)
                }
                if (tempNode.right != null) {
                    queue.add(tempNode.right)
                    ListOfNodes.add(tempNode.right!!)
                }
            }
            return ListOfNodes
        }

        override fun hasNext(): Boolean {
            return !ListOfNodes.isEmpty()
        }

        override fun next(): T {
            if (!hasNext())
                throw Exception("There are no elements left")

            return ListOfNodes.remove().value
        }

    }

    override fun iterator(): Iterator<T> {
        return TreeIterator()
    }
}