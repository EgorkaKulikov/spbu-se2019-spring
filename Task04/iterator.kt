import java.util.*

class IterableSearchTree<T, K : Comparable<K>>: Iterable<T> {
    public inner class SearchTreeIterator: Iterator<T> {
        private val nodesList = createNodesList()

        private fun createNodesList(): LinkedList<Node> {
            val nodesList = LinkedList<Node>()
            val queue = LinkedList<Node>()
            val tempRoot = root ?: return nodesList

            queue.add(tempRoot)

            while (queue.isNotEmpty()) {
                val currentNode = queue.remove()
                nodesList.add(currentNode)

                val tempLeft = currentNode.left //local copy to perform null checks
                if (tempLeft != null) {
                    queue.add(tempLeft)
                }
                val tempRight = currentNode.right //local copy to perform null checks
                if (tempRight != null) {
                    queue.add(tempRight)
                }
            }

            return nodesList
        }

        override fun hasNext(): Boolean {
            return nodesList.isNotEmpty()
        }

        override fun next(): T {
            if (!hasNext()) {
                throw IndexOutOfBoundsException()
            }
            return nodesList.remove().value
        }
    }

    override fun iterator(): Iterator<T> {
        return SearchTreeIterator()
    }

    private open inner class Node(var value: T, var key: K, open var parent: Node?) {
        open var left: Node? = null
        open var right: Node? = null
    }

    private var root: Node? = null

    public fun find(key: K): T? {
        var currentNode: Node? = this.root

        while (currentNode != null) {
            when {
                currentNode.key == key -> return currentNode.value
                currentNode.key > key -> currentNode = currentNode.left
                currentNode.key < key -> currentNode = currentNode.right
            }
        }

        return null
    }

    public fun insert(key: K, value: T){
        var tempRoot = this.root //local copy to perform null checks

        if (tempRoot == null) {
            tempRoot = Node(value, key, null)
            this.root = tempRoot
            return
        }

        var currentNode: Node = tempRoot

        while (currentNode.key != key) {
            var tempNode: Node? //local copy to perform null checks

            if (currentNode.key > key) {
                tempNode = currentNode.left
                if (tempNode == null) {
                    tempNode = Node(value, key, currentNode)
                }
                currentNode.left = tempNode

            } else {
                tempNode = currentNode.right
                if (tempNode == null) {
                    tempNode = Node(value, key, currentNode)
                }
                currentNode.right = tempNode
            }
            currentNode = tempNode

            if (currentNode.key == key) {
                currentNode.value = value
            }
        }
    }
}
