abstract class Tree<K: Comparable<K>, V, NodeType: Node<K, V, NodeType>> : Iterable<NodeType> {

    operator fun get(key: K): V? {
        val parentNode = searchForParent(key) ?: return root?.value
        return if (parentNode.key < key)
            parentNode.right?.value
        else
            parentNode.left?.value
    }

    open operator fun set(key: K, value: V) {}

    fun maxKey(): K? {
        return getMaxNode(root)?.key
    }

    fun minKey(): K? {
        return getMinNode(root)?.key
    }

    override fun iterator(): Iterator<NodeType> {
        return TreeIterator(this)
    }



    protected open var root: NodeType? = null

    protected fun insert(node: NodeType) {
        val parentNode = searchForParent(node.key)
        node.parent = parentNode
        if (parentNode == null) {
            root = node
        }
        else {
            if (node.key > parentNode.key) {
                parentNode.right = node
            }
            else {
                parentNode.left = node
            }
        }
    }

    private fun searchForParent(key: K, currentNode: NodeType? = root): NodeType? {
        if (currentNode == null) {
            return null // In case of empty tree
        }
        if (key < currentNode.key && currentNode.left == null
                || key > currentNode.key && currentNode.right == null) {
            return currentNode // There is no node with such key
        }

        return when {
            key < currentNode.key -> searchForParent(key, currentNode.left)
            key > currentNode.key -> searchForParent(key, currentNode.right)
            else -> currentNode.parent // Founded proper node
        }
    }

    private fun getMinNode(currentNode: NodeType?): NodeType? {
        if (currentNode?.left == null) {
            return currentNode
        }
        return getMinNode(currentNode.left)
    }

    private fun getMaxNode(currentNode: NodeType?): NodeType? {
        if (currentNode?.right == null) {
            return currentNode
        }
        return getMaxNode(currentNode.right)
    }

    private inner class TreeIterator(private val tree: Tree<K, V, NodeType>) : Iterator<NodeType> {

        private val leftBoundary = tree.getMinNode(root)
        private val rightBoundary = tree.getMaxNode(root)
        private var currentNode = leftBoundary


        override fun next(): NodeType {
            var nextNode: NodeType?
            if (currentNode?.right != null) {
                nextNode = tree.getMinNode(currentNode?.right)!!
            }
            else {
                nextNode = currentNode
                while (nextNode?.parent != null && nextNode.parent?.left != nextNode) {
                    nextNode = nextNode.parent
                }
                nextNode = nextNode?.parent
            }
            val prevNode = currentNode
            currentNode = nextNode
            return prevNode!!
        }


        override fun hasNext(): Boolean {
            return if (rightBoundary != null && currentNode != null)
                currentNode!!.key <= rightBoundary.key
            else
                false
        }

    }
}