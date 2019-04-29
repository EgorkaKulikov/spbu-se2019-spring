abstract class TreeWithBalancing<K: Comparable<K>, V, NT: NodeWithBalancing<K, V, NT>>: Tree<K, V, NT>() {
    protected abstract fun deleteNode(path: MutableList<NT>)
    override fun setWithInfo(key: K, value: V?): InfoAboutNode {
        if (root == null) {
            if (value == null)
                return InfoAboutNode.NodeNotFounded
            root = createRoot(key, value)
            return InfoAboutNode.NodeCreate
        }
        var node = root
        val path = mutableListOf<NT>()
        while (node != null) {
            path.add(node)
            node = node.nextNode(key)
        }
        if (value != null) {
            when (path.last().findKey(key)) {
                TypeSon.Root -> {
                    path.last().value = value
                    return InfoAboutNode.NodeUpdate
                }
                TypeSon.LeftSon -> {
                    path.last().createSon(key, value, TypeSon.LeftSon)
                    path.add(path.last().left!!)
                }
                TypeSon.RightSon -> {
                    path.last().createSon(key, value, TypeSon.RightSon)
                    path.add(path.last().right!!)
                }
            }
            for (nodeOnPath in path.asReversed())
                nodeOnPath.balancing()
            while (root!!.type != TypeSon.Root)
                root = root!!.parent
            return InfoAboutNode.NodeCreate
        }
        if (path.last().key != key)
            return InfoAboutNode.NodeNotFounded
        deleteNode(path)
        return InfoAboutNode.NodeDelete
    }
}
