class BinaryNode<K: Comparable<K>, V>(key: K, value: V) :
        Node<K, V, BinaryNode<K, V>>(key, value) {

    override fun equals(other: Any?): Boolean {
        return (other is BinaryNode<*, *>
                && left == other.left
                && right == other.right
                && parent?.key == other.parent?.key
                && key == other.key
                && value == other.value)
    }

    internal fun addLeftSon(key: K, value: V) {
        val newSon = BinaryNode(key, value)
        this.left = newSon
        newSon.parent = this
    }

    internal fun addRightSon(key: K, value: V) {
        val newSon = BinaryNode(key, value)
        this.right = newSon
        newSon.parent = this
    }
}