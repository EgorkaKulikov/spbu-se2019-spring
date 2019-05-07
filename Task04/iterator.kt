import java.util.*

class IterableSearchTree<T, K : Comparable<K>>: Iterable<T> {
    public inner class SearchTreeIterator: Iterator<T> {
        private val nodesList = createNodesList()

        private fun createNodesList(): LinkedList<Node> {
            val nodesList = LinkedList<Node>()
            val nodesVisited = LinkedList<Node>()
            val tempRoot = root ?: return nodesList

            nodesVisited.add(tempRoot)

            while (nodesVisited.isNotEmpty()) {
                val currentNode = nodesVisited.remove()
                nodesList.add(currentNode)

                val tempLeft = currentNode.left //local copy to perform null checks
                if (tempLeft != null) {
                    nodesVisited.add(tempLeft)
                }
                val tempRight = currentNode.right //local copy to perform null checks
                if (tempRight != null) {
                    nodesVisited.add(tempRight)
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

    private inner class Node(var value: T, var key: K, var parent: Node?) {
        var left: Node? = null
        var right: Node? = null
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
