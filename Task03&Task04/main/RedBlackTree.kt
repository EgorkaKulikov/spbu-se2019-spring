class RedBlackTree<K : Comparable<K>, V> : Tree<K, V, RBNode<K, V>>() {
    override fun set(key: K, value: V) {
        val node = RBNode(key, value, Color.Red)
        insert(node)
    }

    override fun insert(node : RBNode<K,V>) {
        super.insert(node)
        fixColors(node)
    }

    private fun fixColors(node: RBNode<K, V>) {
        fun leftleftCase(node: RBNode<K, V>) {
            if (node.parent != null) {
                if (node.key < node.parent!!.key) {
                    (node.parent)!!.left = node.rightRotate()
                } else {
                    (node.parent)!!.right = node.rightRotate()
                }
                node.swapColors(node.parent!!)
            } else {
                root = node.rightRotate()
                root!!.swapColors(root!!.right!!)
            }
        }

        fun leftrightCase(node: RBNode<K, V>) {
            node.left = node.left?.leftRotate()
            leftleftCase(node)
        }

        fun rightrightCase(node: RBNode<K, V>) {
            if (node.parent != null) {
                if (node.key < node.parent!!.key) {
                    (node.parent)!!.left = node.leftRotate()
                } else {
                    (node.parent)!!.right = node.leftRotate()
                }
                node.swapColors(node.parent!!)
            } else {
                root = node.leftRotate()
                root!!.swapColors(root!!.left!!)
            }
        }

        fun rightleftCase(node: RBNode<K, V>) {
            node.right = node.right?.rightRotate()
            rightrightCase(node)
        }

        if (node.parent == null) {
            node.color = Color.Black
            return
        }
        if ((node.parent)?.color == Color.Black) {
            return
        }
        if (node.uncle == null || (node.uncle)?.color == Color.Black) { //uncle black
            if ((node.parent)?.left == node) {
                if ((node.grandparent)?.left == node.parent) {
                    leftleftCase(node.grandparent!!)
                } else {
                    rightleftCase(node.grandparent!!)
                }
            } else {
                if ((node.grandparent)?.left == node.parent) {
                    leftrightCase(node.grandparent!!)
                } else {
                    rightrightCase(node.grandparent!!)
                }
            }
        } else { //uncle red
            (node.uncle)?.color = Color.Black
            (node.parent)?.color = Color.Black
            (node.grandparent)?.color = Color.Red
            fixColors(node.grandparent!!)
        }

        root?.color = Color.Black
    }

    override fun equals(other: Any?): Boolean {
        return (other is RedBlackTree<*, *>
                && this.root == other.root)
    }
}