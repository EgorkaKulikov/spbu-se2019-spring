class BinarySearchTree<K: Comparable<K>, V> :
        AbstractBinarySearchTree<K, V, BinaryNode<K, V>>() {

    override operator fun set(key: K, value: V) {
        val newNode = BinaryNode(key, value)
        insert(newNode)
    }

    override fun equals(other: Any?): Boolean {
        return (other is BinarySearchTree<*, *>
                && this.root == other.root)
    }

}