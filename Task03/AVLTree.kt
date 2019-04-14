class AVLTree<K: Comparable<K>, V>(): BalancingTree<K, V, AVLNode<K, V>>() {
    constructor(size: Int, init: (Int) -> Pair<K, V>): this() {
        insert(size, init)
    }
    constructor(root: AVLNode<K, V>): this() {
        this.root = root
    }
    override fun createRoot(key: K, value: V): AVLNode<K, V> = AVLNode(key, value)
    override fun deleteNode(path: MutableList<AVLNode<K, V>>) {
        val node = path.last()
        if (node.right == null) {
            if (node.type == TypeSon.Root)
                root = node.left
            node.left?.setFather(node.parent, node.type)
        }
        else {
            var nodeWithNextKey = node.right!!
            while (nodeWithNextKey.left != null) {
                path.add(nodeWithNextKey)
                nodeWithNextKey = nodeWithNextKey.left!!
            }
            node.key = nodeWithNextKey.key
            node.value = nodeWithNextKey.value
            nodeWithNextKey.parent!!.setSon(nodeWithNextKey.right, nodeWithNextKey.type)
        }
        for (nodeOnPath in path.asReversed())
            nodeOnPath.balancing()
        while (root!!.type != TypeSon.Root)
            root = root!!.parent
    }
    override fun toString(): String = "AVLTree: {${root?.subtreeToString() ?: ""}}"
}

fun <K: Comparable<K>, V>avlTreeOf(vararg elements: Pair<K, V>): AVLTree<K, V> =
    AVLTree(elements.size) {elements[it]}
