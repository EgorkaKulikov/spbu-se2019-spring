class RedBlackTree<K: Comparable<K>, V>:
        Tree<K, V, RedBlackNode<K, V>>() {

    override operator fun set(key: K, value: V) {
        val newNode = RedBlackNode(key, value, 'r')
        insert(newNode)
        fixColors(newNode)
    }

    private fun fixColors(node: RedBlackNode<K, V>) {

        var current = node

        if (current == root) {
            current.color = 'b'
            return
        }

        while (current.parent?.color == 'r') {
            if (current.grandparent?.left == current.parent) {
                // In case our parent is left son
                if (current.uncle?.color == 'r') {
                    // Uncle is red
                    current.parent?.color = 'b'
                    current.uncle?.color = 'b'
                    current.grandparent?.color = 'r'
                    current = current.grandparent!!
                }
                else {
                    // Uncle is black, or doesn't exist
                    if (current.parent?.right == current) {
                        current = current.parent!!
                        current.leftRotate()
                    }
                    current.parent?.color = 'b'
                    current.grandparent?.color = 'r'
                    current.grandparent?.rightRotate()
                }
            }
            else {
                // In case our parent is right son
                if (current.uncle?.color == 'r') {
                    // Uncle is red
                    current.parent?.color = 'b'
                    current.uncle?.color = 'b'
                    current.grandparent?.color = 'r'
                    current = current.grandparent!!
                }
                else {
                    // Uncle is black, or doesn't exist
                    if (current.parent?.left == current) {
                        current = current.parent!!
                        current.rightRotate()
                    }
                    current.parent?.color = 'b'
                    current.grandparent?.color = 'r'
                    current.grandparent?.leftRotate()
                }
            }
        }

        while (root?.parent != null)
            root = root?.parent
        root?.color = 'b'
    }

}