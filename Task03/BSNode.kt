class BSNode<K: Comparable<K>, V>(override var key: K, override var value: V):
    Node<K, V, BSNode<K, V>>() {
    override fun createNode(key: K, value: V): BSNode<K, V> = BSNode(key, value)
}
