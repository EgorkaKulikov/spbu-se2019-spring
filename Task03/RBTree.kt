class RBTree<K: Comparable<K>, V>(): BalancingTree<K, V, RBNode<K, V>>() {
    constructor(size: Int, init: (Int) -> Pair<K, V>): this() {
        for (i in 0 until size)
            set(init(i).first, init(i).second)
    }
    constructor(root: RBNode<K, V>): this() {
        this.root = root
    }
    override fun createRoot(key: K, value: V): RBNode<K, V> = RBNode(key, value, Color.Black)
    override fun toString(): String = "RBTree: {${root!!.subtreeToString()}}"
}

fun <K: Comparable<K>, V>rbTreeOf(vararg elements: Pair<K, V>): RBTree<K, V> =
    RBTree(elements.size) {elements[it]}
