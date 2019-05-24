class AVLTree<K : Comparable<K>, V> : Tree<K, V, AVLNode<K, V>>() {
    override fun set(key: K, value: V) {
        val node = AVLNode(key, value)
        insert(node)
    }

    override fun insert(node: AVLNode<K, V>) {
        super.insert(node)
        fixHeights(node.parent)
    }

    private fun fixHeights(node: AVLNode<K, V>?) {
        fun leftLeftCase(node: AVLNode<K, V>) {
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

        fun leftRightCase(node: AVLNode<K, V>) {
            node.left = node.left?.leftRotate()
            (node.left)?.left?.updateHeight()
            (node.left)?.updateHeight()
            leftLeftCase(node)
        }

        fun rightRightCase(node: AVLNode<K, V>) {
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

        fun rightLeftCase(node: AVLNode<K, V>) {
            node.right = node.right?.rightRotate()
            (node.right)?.right?.updateHeight()
            (node.right)?.updateHeight()
            rightRightCase(node)
        }

        if (node == null) return

        val leftHeight = node.left?.height ?: 0
        val rightHeight = node.right?.height ?: 0

        if (leftHeight - rightHeight > 1) { //left case

            val leftLeftHeight = node.left!!.left?.height ?: 0
            val leftRightHeight = node.left!!.right?.height ?: 0

            if (leftLeftHeight - leftRightHeight == 1) { //left left case
                leftLeftCase(node) //root exists
            }
            if (leftLeftHeight - leftRightHeight == -1) { //left right case
                leftRightCase(node) //root exists
            }
        }
        if (leftHeight - rightHeight < -1) {//right case

            val rightLeftHeight = node.right!!.left?.height ?: 0
            val rightRightHeight = node.right!!.right?.height ?: 0

            if (rightLeftHeight - rightRightHeight == 1) { // right left case
                rightLeftCase(node)
            }
            if (rightLeftHeight - rightRightHeight == -1) { // right right case
                rightRightCase(node)
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