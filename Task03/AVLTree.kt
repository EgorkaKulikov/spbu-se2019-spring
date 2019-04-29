class AVLTree<K: Comparable<K>, V>(): TreeWithBalancing<K, V, AVLNode<K, V>>() {
    constructor(size: Int, init: (Int) -> Pair<K, V>): this() {
        insert(size, init)
    }
    constructor(root: AVLNode<K, V>): this() {
        this.root = root
    }
    protected override fun createRoot(key: K, value: V): AVLNode<K, V> = AVLNode(key, value)
    protected override fun deleteNode(path: MutableList<AVLNode<K, V>>) {
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
            nodeWithNextKey.parent!!.setSon(nodeWithNextKey.right, nodeWithNextKey.type)
            nodeWithNextKey.moveOnNewPlace(node)
        }
        for (nodeOnPath in path.asReversed())
            nodeOnPath.balancing()
        while (root!!.type != TypeSon.Root)
            root = root!!.parent
    }
    override fun equals(other: Any?): Boolean =
        (other is AVLTree<*, *> && other.root == root)
}

fun <K: Comparable<K>, V>avlTreeOf(vararg elements: Pair<K, V>): AVLTree<K, V> =
    AVLTree(elements.size) {elements[it]}
