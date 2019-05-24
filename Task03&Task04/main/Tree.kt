abstract class Tree<K : Comparable<K>, V, NodeType : Node<K, V, NodeType>> : Iterable<NodeType> {
    internal open var root: NodeType? = null

    operator fun get(key: K): V? {
        val parent = find_parent(key) ?: return root?.value
        return if (parent.key < key)
            parent.right?.value
        else parent.left?.value
    }

    open operator fun set(key: K, value: V) {}

    open fun insert(node: NodeType) {
        if (this.root == null) this.root = node
        else {
            val parent = find_parent(node.key)!!
            node.parent = parent
            if (node.key < parent.key) // never equals
            {
                parent.left = node
            } else {
                parent.right = node
            }
        }
    }

    internal fun find_parent(key: K): NodeType? {

        var node = this.root
        var parent: NodeType? = null
        while (node != null) {
            if (node.key > key) {
                parent = node
                node = node.left
                continue
            }
            if (node.key < key) {
                parent = node
                node = node.right
                continue
            }
            return parent //key equals, not valid case
        }
        return parent
    }

    private fun getMinNode(node: NodeType?): NodeType? {
        if (node?.left == null) {
            return node
        }
        return getMinNode(node.left)
    }

    private fun getMaxNode(node: NodeType?): NodeType? {
        if (node?.right == null) {
            return node
        }
        return getMaxNode(node.right)
    }

    override fun iterator(): Iterator<NodeType> {
        return TreeIterator()
    }

    val keys: Array<out Any>?
        get() {
            val result = arrayListOf<K>()
            this.forEach { result.add(it.key) }
            return result.toArray()
        }

    private inner class TreeIterator() : Iterator<NodeType> {
        private val minNode = getMinNode(root)
        private val maxNode = getMaxNode(root)
        private var node = minNode


        override fun next(): NodeType {
            var nextNode: NodeType?
            if (node?.right != null) {
                nextNode = getMinNode(node?.right)!!
            } else {
                nextNode = node
                while (nextNode?.parent != null && nextNode.parent?.left != nextNode) {
                    nextNode = nextNode.parent
                }
                nextNode = nextNode?.parent
            }
            val prevNode = node
            node = nextNode
            return prevNode ?: throw NoSuchElementException("Empty tree")
        }

        override fun hasNext(): Boolean {
            return if (maxNode != null && node != null)
                node!!.key <= maxNode.key
            else
                false
        }
    }
}