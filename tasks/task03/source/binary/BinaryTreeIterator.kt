package binary

class BinaryTreeIterator<Data, NodeData>(begin: BinaryNode<NodeData>?, val converter: (NodeData) -> Data) : Iterator<Data> {

    private val path = mutableListOf<BinaryNode<NodeData>>()

    init {
        rebuildPath(begin)
    }

    private fun rebuildPath(from: BinaryNode<NodeData>?) {
        var node = from

        while (node != null) {
            path.add(node)
            node = node.left
        }
    }

    override fun hasNext() = path.size != 0

    override fun next(): Data {
        if (path.isEmpty()) {
            throw NoSuchElementException("")
        }

        val next = path.removeAt(path.lastIndex)

        if (next.right != null) {
            rebuildPath(next.right)
        }

        return converter(next.data)
    }
}
