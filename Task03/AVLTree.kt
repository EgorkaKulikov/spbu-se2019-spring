class AVLTree<K: Comparable<K>, V> : Tree<K, V, AVLNode<K, V>>() {
    override operator fun set(key: K, value: V) {
        val newNode = AVLNode(key, value)
        insert(newNode)
        balance(newNode)
    }

    private fun balance(node: AVLNode<K, V>) {
        var curNode = node.parent
        curNode?.updateHeight()
        while (curNode != null && curNode.deltaHeight != 0) {
            when (curNode.deltaHeight) {
                // left subtree has 2 more nodes than right one
                2 -> {
                    if (curNode.left?.deltaHeight == -1) {
                        curNode.bigRightRotate()
                    }
                    else {
                        curNode.rightRotate()
                    }
                }
                // right subtree has 2 more nodes
                -2 -> {
                    if (curNode.right?.deltaHeight == 1) {
                        curNode.bigLeftRotate()
                    }
                    else {
                        curNode.leftRotate()
                    }
                    curNode = curNode.parent
                }
                // we already visited its sons, so their @deltaHeights can not be 2 or -2
            }
            curNode = curNode?.parent
            curNode?.updateHeight()
        }
        while (root?.parent != null) {
            root = root?.parent
            root?.updateHeight()
        }
    }

    override fun equals(other: Any?): Boolean {
        return (other is AVLTree<*, *>
                && other.root == root)
    }
}