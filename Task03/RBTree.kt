class RBTree<K: Comparable<K>, V>(): BalancingTree<K, V, RBNode<K, V>>() {
    constructor(size: Int, init: (Int) -> Pair<K, V>): this() {
        insert(size, init)
    }
    constructor(root: RBNode<K, V>): this() {
        this.root = root
    }
    override fun createRoot(key: K, value: V): RBNode<K, V> = RBNode(key, value, Color.Black)
    override fun deleteNode(path: MutableList<RBNode<K, V>>) {
        var node = path.last()
        var nodeWithNextKey: RBNode<K, V>
        while (node.left != null || node.right != null) {
            if (node.right == null) {
                node.left!!.color = Color.Black
                if (node.type == TypeSon.Root)
                    root = node.left!!
                node.left!!.setFather(node.parent, node.type)
                return
            }
            nodeWithNextKey = node.right!!
            while (nodeWithNextKey.left != null)
                nodeWithNextKey = nodeWithNextKey.left!!
            node.key = nodeWithNextKey.key
            node.value = nodeWithNextKey.value
            node = nodeWithNextKey
        }
        if (node.type == TypeSon.Root) {
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
            if (father.type == TypeSon.Root)
                father.color = Color.Red
            if (brother.color == Color.Red) {
                val grandfather = brother
                brother = if (brother.type == TypeSon.LeftSon)
                    brother.right!!
                else
                    brother.left!!
                grandfather.rotate()
            }
            val brotherSon = if (brother.type == TypeSon.LeftSon)
                brother.left
            else
                brother.right
            val brotherNephew = if (brother.type == TypeSon.LeftSon)
                brother.right
            else
                brother.left
            if (brotherNephew != null && brotherNephew.color == Color.Red) {
                brotherNephew.color = Color.Black
                brother.rotateBig()
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
        while (root!!.type != TypeSon.Root)
            root = root!!.parent
        root!!.color = Color.Black
    }
    override fun toString(): String = "RBTree: {${root?.subtreeToString() ?: ""}}"
}

fun <K: Comparable<K>, V>rbTreeOf(vararg elements: Pair<K, V>): RBTree<K, V> =
    RBTree(elements.size) {elements[it]}
