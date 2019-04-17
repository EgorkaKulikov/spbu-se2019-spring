class BinarySearchTree<K: Comparable<K>, V> :
        Tree<K, V, BinaryNode<K, V>>() {

    override operator fun set(key: K, value: V) {
        val newNode = BinaryNode(key, value)
        insert(newNode)
    }

}