class RedBlackTree<K: Comparable<K>, V>(): BalanceTree<K, V, RedBlackNode<K, V>>() {
    constructor(size: Int, init: (Int) -> Pair<K, V>): this() {
        insert(size, init)
    }

    constructor(root: RedBlackNode<K, V>): this() {
        this.root = root
    }

    protected override fun createRoot(key: K, value: V): RedBlackNode<K, V> = RedBlackNode(key, value, Color.Black)

    protected override fun deleteNode(path: MutableList<RedBlackNode<K, V>>) {
        var node = path.last()
        var nodeWithNextKey: RedBlackNode<K, V>
        while (node.left != null || node.right != null) {
            if (node.right == null) {
                node.left!!.color = Color.Black
                if (node.type == SonType.Root)
                    root = node.left!!
                node.left!!.setFather(node.parent, node.type)
                return
            }
            nodeWithNextKey = node.right!!
            while (nodeWithNextKey.left != null)
                nodeWithNextKey = nodeWithNextKey.left!!
            nodeWithNextKey.copy().moveOn(node)
            node = nodeWithNextKey
        }
        if (node.type == SonType.Root) {
            root = null
            return
        }
        if (node.color == Color.Red) {
            node.parent!!.setSon(null, node.type)
            return
        }
        var father = node.parent!!
        var brother = node.brother!!
        father.setSon(null, node.type)
        var balancingFinish = false
        while ( ! balancingFinish) {
            if (father.type == SonType.Root)
                father.color = Color.Red
            if (brother.color == Color.Red) {
                val grandfather = brother
                brother = if (brother.type == SonType.LeftSon)
                    brother.right!!
                else
                    brother.left!!
                grandfather.rotate()
            }
            val brotherSon = if (brother.type == SonType.LeftSon)
                brother.left
            else
                brother.right
            val brotherNephew = if (brother.type == SonType.LeftSon)
                brother.right
            else
                brother.left
            if (brotherNephew != null && brotherNephew.color == Color.Red) {
                brotherNephew.color = Color.Black
                brother.bigRotate()
                balancingFinish = true
            }
            else if (brotherSon != null && brotherSon.color == Color.Red) {
                brotherSon.color = Color.Black
                brother.rotate()
                balancingFinish = true
            }
            else if (father.color == Color.Red) {
                brother.color = Color.Red
                father.color = Color.Black
                balancingFinish = true
            }
            else {
                brother.color = Color.Red
                brother = father.brother!!
                father = father.parent!!
            }
        }
        while (root!!.type != SonType.Root)
            root = root!!.parent
        root!!.color = Color.Black
    }

    override fun equals(other: Any?): Boolean =
        (other is RedBlackTree<*, *> && other.root == root)
}

fun <K: Comparable<K>, V>redBlackTreeOf(vararg elements: Pair<K, V>): RedBlackTree<K, V> =
    RedBlackTree(elements.size) {elements[it]}
