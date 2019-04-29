class BinarySearchNode<K: Comparable<K>, V>(override val key: K, override var value: V):
    Node<K, V, BinarySearchNode<K, V>>() {
    protected override fun createNode(key: K, value: V): BinarySearchNode<K, V> = BinarySearchNode(key, value)
    public override fun copy() = BinarySearchNode(key, value)
    override fun equals(other: Any?): Boolean =
        (other is BinarySearchNode<*, *> &&
                other.left == left &&
                other.right == right &&
                other.key == key &&
                other.value == value)
}
