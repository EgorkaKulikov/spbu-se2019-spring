class Tree<K: Comparable<K>, V>: Iterable<V> {
    private var root: Node<K, V>? = null

    override fun iterator() = when(root) {
        null -> EmptyTreeIterator<V>()
        else -> TreeIterator(root!!)
    }
}
