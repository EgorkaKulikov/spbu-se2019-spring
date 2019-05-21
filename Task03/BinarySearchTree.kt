class BinarySearchTree<K: Comparable<K>, V>(): Tree<K, V, BinarySearchNode<K, V>>() {
    constructor(size: Int, init: (Int) -> Pair<K, V>): this() {
        insert(size, init)
    }

    constructor(root: BinarySearchNode<K, V>): this() {
        this.root = root
    }

    protected override fun createRoot(key: K, value: V): BinarySearchNode<K, V> = BinarySearchNode(key, value)

    override fun equals(other: Any?): Boolean =
        (other is BinarySearchTree<*, *> && other.root == root)
}

fun <K: Comparable<K>, V>binarySearchTreeOf(vararg elements: Pair<K, V>): BinarySearchTree<K, V> =
    BinarySearchTree(elements.size) {elements[it]}
