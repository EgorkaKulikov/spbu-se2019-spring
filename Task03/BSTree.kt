class BSTree<K: Comparable<K>, V>(): Tree<K, V, BSNode<K, V>>() {
    constructor(size: Int, init: (Int) -> Pair<K, V>): this() {
        insert(size, init)
    }
    constructor(root: BSNode<K, V>): this() {
        this.root = root
    }
    override fun createRoot(key: K, value: V): BSNode<K, V> = BSNode(key, value)
    override fun toString(): String = "BSTree: {${root?.subtreeToString() ?: ""}}"
}

fun <K: Comparable<K>, V>BSTreeOf(vararg elements: Pair<K, V>): BSTree<K, V> =
    BSTree(elements.size) {elements[it]}
