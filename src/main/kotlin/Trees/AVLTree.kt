class AVLTree<K: Comparable<K>, V>:
        Tree<K, V, AVLNode<K, V>>() {

    override operator fun set(key: K, value: V) {
        val newNode = AVLNode(key, value)
        insert(newNode)
        fixHeights(newNode)
    }

    private fun fixHeights(node: AVLNode<K, V>) {

        var current = node.parent

        while (current != null && current.deltaHeight != 0) {
            current.updateHeight()
            when (current.deltaHeight) {
                // Right subtree has 2 extra nodes
                -2 -> {
                    if (current.right!!.deltaHeight > 0)
                        current.bigLeftRotate()
                    else
                        current.leftRotate()
                    current = current.parent
                }
                // Left subtree has 2 extra nodes
                2 -> {
                    if (current.left!!.deltaHeight < 0)
                        current.bigRightRotate()
                    else
                        current.rightRotate()
                    current = current.parent
                }
            }
            current = current?.parent
        }
        while (root?.parent != null) {
            root = root?.parent
            root?.updateHeight()
        }
    }

    override fun equals(other: Any?): Boolean {
        return (other is AVLTree<*, *>
                && this.root == other.root)
    }

}