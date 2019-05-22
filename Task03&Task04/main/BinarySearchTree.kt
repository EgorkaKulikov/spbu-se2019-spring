class BinarySearchTree<K : Comparable<K>, V> : Tree<K, V, BinaryNode<K, V>>() {
    override fun set(key: K, value: V) {
        val node = BinaryNode(key, value)
        super.insert(node)
    }

    override fun equals(other: Any?): Boolean {
        return (other is BinarySearchTree<*, *>
                && this.root == other.root)
    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}