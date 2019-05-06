import java.util.Stack

abstract class Tree<K: Comparable<K>, V, NodeType: Node<K, V, NodeType>>: Iterable<NodeType> {
    var root: NodeType? = null
        internal set

    val keys: Array<out Any>?
        get() {
            val res = arrayListOf<K>()
            for (node in this)
                res.add(node.key)
            return res.toArray()
        }

    open operator fun set(key: K, value: V) {}

    operator fun get(key: K): V? {
        return findNodeByKey(key)?.value
    }

    protected fun insert(newNode: NodeType) {
        val node = findNodeByKey(newNode.key)
        if (node != null) {
            node.value = newNode.value
            return // simple changing of the value if current node is already exists
        }

        val parent = findParentForKey(newNode.key)
        if (parent == null) {
            // our tree is empty if we can't find a parent
            root = newNode
        }
        else {
            if (parent.key < newNode.key)
                parent.right = newNode
            else
                parent.left = newNode
            newNode.parent = parent
        }
    }

    protected fun findParentForKey(key: K): NodeType? {
        var curNode = root
        var parent: NodeType? = null
        while (curNode != null && curNode.key != key) {
            parent = curNode
            curNode = if (curNode.key > key)
                curNode.left
            else
                curNode.right
        }
        return parent
    }

    protected fun findNodeByKey(key: K): NodeType? {
        var curNode = root
        while (curNode != null && curNode.key != key) {
            curNode = if (curNode.key > key)
                curNode.left
            else
                curNode.right
        }
        return curNode
    }

    override fun iterator(): Iterator<NodeType> {
        return TreeIterator()
    }

    private inner class TreeIterator :
            Iterator<NodeType> {
        private var path = Stack<NodeType>()

        override fun next(): NodeType = path.peek()

        // adds all nodes into @path which placed to the left from @startNode (including itself)
        private fun addAllLeftFrom(startNode: NodeType?) {
            var curNode: NodeType? = startNode
            while (curNode != null) {
                path.push(curNode)
                curNode = curNode.left
            }
        }

        override fun hasNext() : Boolean {
            if (path.empty()) {
                addAllLeftFrom(root)
                return !path.empty()
                // it can be 'false' if @root is null
            }
            if (path.peek().right != null) {
                addAllLeftFrom(path.peek().right)
                return true
            }
            while (path.peek().parent?.right == path.peek()) {
                // while a node on the top of @path is a right node,
                // we need to delete it because we watched its whole subtree
                path.pop()
            }
            // deleting the first non-right node whose subtree we just finished
            path.pop()
            return !path.empty()
        }
    }
}