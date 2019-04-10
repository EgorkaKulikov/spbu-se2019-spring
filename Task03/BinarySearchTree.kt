class BinarySearchTree<K: Comparable<K>, V>(): Tree<K, V, BinarySearchNode<K, V>>() {
    constructor(size: Int, init: (Int) -> Pair<K, V>): this() {
        for (i in 0 until size)
            set(init(i).first, init(i).second)
    }
    constructor(root: BinarySearchNode<K, V>): this() {
        this.root = root
    }
    override fun createRoot(key: K, value: V): BinarySearchNode<K, V> = BinarySearchNode(key, value)
    override fun toString(): String = "BinarySearchTree: {${root!!.subtreeToString()}}"
}

fun <K: Comparable<K>, V>binarySearchTreeOf(vararg elements: Pair<K, V>): BinarySearchTree<K, V> =
    BinarySearchTree(elements.size) {elements[it]}
