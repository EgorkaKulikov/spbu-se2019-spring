enum class Color {
    Red,
    Black
}

class RedBlackNode<K: Comparable<K>, V>(key: K, value: V, var color: Color) :
        Node<K, V, RedBlackNode<K, V>>(key, value) {

    override fun equals(other: Any?): Boolean {
        return (other is RedBlackNode<*, *>
                && left == other.left
                && right == other.right
                && parent?.key == other.parent?.key
                && parent?.color == other.parent?.color
                && key == other.key
                && value == other.value
                && color == other.color)
    }

    internal fun addLeftSon(key: K, value: V, color: Color) {
        val newSon = RedBlackNode(key, value, color)
        this.left = newSon
        newSon.parent = this
    }

    internal fun addRightSon(key: K, value: V, color: Color) {
        val newSon = RedBlackNode(key, value, color)
        this.right = newSon
        newSon.parent = this
    }

    internal fun leftRotate() {
        NodeRotator.leftRotate(this)
    }

    internal fun rightRotate() {
        NodeRotator.rightRotate(this)
    }
}