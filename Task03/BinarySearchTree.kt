class BinarySearchTree<K: Comparable<K>, V> : Tree<K, V, BinaryNode<K, V>>() {
    override operator fun set(key: K, value: V) {
        insert(BinaryNode(key, value))
    }

    override fun equals(other: Any?): Boolean {
        return (other is BinarySearchTree<*, *>
                && other.root == root)
    }

    override fun hashCode(): Int {
        return root.hashCode()
    }
}