package binary

class BinaryTreeIterator<Data : Copyable<Data>>(begin: BinaryNode<Data>?) : Iterator<Data> {

    private val path = mutableListOf<BinaryNode<Data>>()

    init {
        rebuildPath(begin)
    }

    private fun rebuildPath(from: BinaryNode<Data>?) {
        var node = from

        while (node != null) {
            path.add(node)
            node = node.left
        }
    }

    override fun hasNext() = path.isNotEmpty()

    override fun next(): Data {
        if (path.isEmpty()) {
            throw NoSuchElementException("")
        }

        val next = path.removeAt(path.lastIndex)

        if (next.right != null) {
            rebuildPath(next.right)
        }

        return next.data.copy()
    }
}
