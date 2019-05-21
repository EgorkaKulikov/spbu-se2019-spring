class RedBlackTree<K: Comparable<K>, V>:
        AbstractBinarySearchTree<K, V, RedBlackNode<K, V>>() {

    override operator fun set(key: K, value: V) {
        val newNode = RedBlackNode(key, value, Color.Red)
        insert(newNode)
        fixColors(newNode)
    }

    private fun fixColors(node: RedBlackNode<K, V>) {

        var current = node

        if (current == root) {
            current.color = Color.Black
            return
        }

        while (current.parent?.color == Color.Red) {
            if (current.grandparent?.left == current.parent) {
                // In case our parent is left son
                if (current.uncle?.color == Color.Red) {
                    // Uncle is red
                    current.parent?.color = Color.Black
                    current.uncle?.color = Color.Black
                    current.grandparent?.color = Color.Red
                    current = current.grandparent!!
                }
                else {
                    // Uncle is black, or doesn't exist
                    if (current.parent?.right == current) {
                        current = current.parent!!
                        current.leftRotate()
                    }
                    current.parent?.color = Color.Black
                    current.grandparent?.color = Color.Red
                    current.grandparent?.rightRotate()
                }
            }
            else {
                // In case our parent is right son
                if (current.uncle?.color == Color.Red) {
                    // Uncle is red
                    current.parent?.color = Color.Black
                    current.uncle?.color = Color.Black
                    current.grandparent?.color = Color.Red
                    current = current.grandparent!!
                }
                else {
                    // Uncle is black, or doesn't exist
                    if (current.parent?.left == current) {
                        current = current.parent!!
                        current.rightRotate()
                    }
                    current.parent?.color = Color.Black
                    current.grandparent?.color = Color.Red
                    current.grandparent?.leftRotate()
                }
            }
        }

        while (root?.parent != null)
            root = root?.parent
        root?.color = Color.Black
    }

    override fun equals(other: Any?): Boolean {
        return (other is RedBlackTree<*, *>
                && this.root == other.root)
    }

}