class RedBlackTree<K: Comparable<K>, V> : Tree<K, V, RedBlackNode<K, V>>() {
    override operator fun set(key: K, value: V) {
        val newNode = RedBlackNode(key, value, Color.Red)
        insert(newNode)
        balance(newNode)
    }

    private fun balance(node: RedBlackNode<K, V>) {
        balanceNode(node)
        while (root?.parent != null)
            root = root!!.parent
        root?.color = Color.Black
        // redefining root in case of rotations and setting it as a black node
    }

    private fun balanceNode(node: RedBlackNode<K, V>?) {
        if (node == null || node.parent?.color == Color.Black) // nothing to balance here
            return

        if (node.uncle?.color == Color.Red) {
            node.parent?.color = Color.Black
            node.uncle?.color = Color.Black
            node.grandparent?.color = Color.Red
            balanceNode(node.grandparent)
        }
        else {
            if (node.grandparent?.left == node.parent) {
                var fixNode = node
                if (node.parent?.right == node) {
                    fixNode = fixNode.parent!!
                    fixNode.leftRotate()
                }
                fixNode.parent?.color = Color.Black
                fixNode.grandparent?.color = Color.Red
                fixNode.grandparent?.rightRotate()
            }
            else {
                var fixNode = node
                if (node.parent?.left == node) {
                    fixNode = fixNode.parent!!
                    fixNode.rightRotate()
                }
                fixNode.parent?.color = Color.Black
                fixNode.grandparent?.color = Color.Red
                fixNode.grandparent?.leftRotate()
            }
        }
    }

    override fun equals(other: Any?): Boolean {
        return (other is RedBlackTree<*, *>
                && other.root == root)
    }
}