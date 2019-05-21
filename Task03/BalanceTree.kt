abstract class BalanceTree<K: Comparable<K>, V, NT: BalanceNode<K, V, NT>>: Tree<K, V, NT>() {
    protected abstract fun deleteNode(path: MutableList<NT>)

    override fun setWithInfo(key: K, value: V?): NodeStatus {
        if (root == null) {
            if (value == null)
                return NodeStatus.NodeNotFounded
            root = createRoot(key, value)
            return NodeStatus.NodeCreate
        }
        var node = root
        val path = mutableListOf<NT>()
        while (node != null) {
            path.add(node)
            node = node.nextNode(key)
        }
        if (value != null) {
            when (path.last().findKeyType(key)) {
                SonType.Root -> {
                    path.last().value = value
                    return NodeStatus.NodeUpdate
                }
                SonType.LeftSon -> {
                    path.last().createSon(key, value, SonType.LeftSon)
                    path.add(path.last().left!!)
                }
                SonType.RightSon -> {
                    path.last().createSon(key, value, SonType.RightSon)
                    path.add(path.last().right!!)
                }
            }
            for (nodeOnPath in path.asReversed())
                nodeOnPath.balancing()
            while (root!!.type != SonType.Root)
                root = root!!.parent
            return NodeStatus.NodeCreate
        }
        if (path.last().key != key)
            return NodeStatus.NodeNotFounded
        deleteNode(path)
        return NodeStatus.NodeDelete
    }
}
