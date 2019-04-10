abstract class BalancingTree<K: Comparable<K>, V, NT: BalancingNode<K, V, NT>>: Tree<K, V, NT>() {
    override operator fun set(key: K, value: V) {
        if (root == null) {
            root = createRoot(key, value)
            return
        }
        var node = root
        val path = mutableListOf<NT>()
        while (node != null) {
            path.add(node)
            node = node.nextNode(key)
        }
        when (path.last().findKey(key)) {
            TypeSon.Root -> {
                path.last().value = value
                return
            }
            TypeSon.LeftSon -> path.last().createSon(key, value, TypeSon.LeftSon)
            TypeSon.RightSon -> path.last().createSon(key, value, TypeSon.RightSon)
        }
        for (node in path.asReversed())
            node.balancing()
        while (root!!.type != TypeSon.Root)
            root = root!!.parent
    }
}
