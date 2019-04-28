class Tree<K : Comparable<K>, V> : Iterable<V> {

    private var root: Node<K, V>? = null

    override fun iterator() = when (val begin = root) {
        null -> EmptyTreeIterator<V>()
        else -> TreeIterator(begin)
    }
}
