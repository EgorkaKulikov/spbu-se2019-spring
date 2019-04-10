class BinarySearchNode<K: Comparable<K>, V>(override var key: K, override var value: V):
    Node<K, V, BinarySearchNode<K, V>>() {
    override fun createNode(key: K, value: V): BinarySearchNode<K, V> = BinarySearchNode(key, value)
}
