class AVLTree<K : Comparable<K>, V> : Tree<K, V, AVLNode<K, V>>() {
    override fun set(key: K, value: V) {
        val node = AVLNode(key, value)
        insert(node)
        fixHeights(node.parent)
    }

    private fun fixHeights(node: AVLNode<K, V>?) {
        fun leftleftCase(node: AVLNode<K, V>) {
            if (node.parent != null) {
                if (node.key < node.parent!!.key) {
                    (node.parent)!!.left = node.rightRotate()
                    node.updateHeight()
                    (node.parent)!!.updateHeight()
                } else {
                    (node.parent)!!.right = node.rightRotate()
                    node.updateHeight()
                    (node.parent)!!.updateHeight()
                }
            } else {
                root = node.rightRotate()
                root!!.right?.updateHeight()
                root!!.updateHeight()
            }
        }

        fun leftrightCase(node: AVLNode<K, V>) {
            node.left = node.left?.leftRotate()
            (node.left)?.left?.updateHeight()
            (node.left)?.updateHeight()
            leftleftCase(node)
        }

        fun rightrightCase(node: AVLNode<K, V>) {
            if (node.parent != null) {
                if (node.key < node.parent!!.key) {
                    (node.parent)!!.left = node.leftRotate()
                    node.updateHeight()
                    (node.parent)!!.updateHeight()
                } else {
                    (node.parent)!!.right = node.leftRotate()
                    node.updateHeight()
                    (node.parent)!!.updateHeight()
                }
            } else {
                root = node.leftRotate()
                root!!.left?.updateHeight()
                root!!.updateHeight()
            }
        }

        fun rightleftCase(node: AVLNode<K, V>) {
            node.right = node.right?.rightRotate()
            (node.right)?.right?.updateHeight()
            (node.right)?.updateHeight()
            rightrightCase(node)
        }

        if (node == null) return
        val leftHeight = node.left?.height ?: 0
        val rightHeight = node.right?.height ?: 0
        if (leftHeight - rightHeight > 1) { //left case
            val leftleftHeight = node.left!!.left?.height ?: 0
            val leftrightHeight = node.left!!.right?.height ?: 0
            if (leftleftHeight - leftrightHeight == 1) { //left left case
                leftleftCase(node) //root exists
            }
            if (leftleftHeight - leftrightHeight == -1) { //left right case
                leftrightCase(node) //root exists
            }
        }
        if (leftHeight - rightHeight < -1) {//right case
            val rightleftHeight = node.right!!.left?.height ?: 0
            val rightrightHeight = node.right!!.right?.height ?: 0
            if (rightleftHeight - rightrightHeight == 1) { // right left case
                rightleftCase(node)
            }
            if (rightleftHeight - rightrightHeight == -1) { // right right case
                rightrightCase(node)
            }
        }

        node.updateHeight()
        fixHeights(node.parent)
    }

    override fun equals(other: Any?): Boolean {
        return (other is AVLTree<*, *>
                && this.root == other.root)
    }
}