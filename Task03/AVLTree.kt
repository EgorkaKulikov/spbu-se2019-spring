class AVLTree<K: Comparable<K>, V>(): BalancingTree<K, V, AVLNode<K, V>>() {
    constructor(size: Int, init: (Int) -> Pair<K, V>): this() {
        for (i in 0 until size)
            set(init(i).first, init(i).second)
    }
    constructor(root: AVLNode<K, V>): this() {
        this.root = root
    }
    override fun createRoot(key: K, value: V): AVLNode<K, V> = AVLNode(key, value)
    override fun toString(): String = "AVLTree: {${root!!.subtreeToString()}}"
}

fun <K: Comparable<K>, V>avlTreeOf(vararg elements: Pair<K, V>): AVLTree<K, V> =
    AVLTree(elements.size) {elements[it]}
