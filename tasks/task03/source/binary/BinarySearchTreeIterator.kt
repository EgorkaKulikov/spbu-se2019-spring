package binary

class BinarySearchTreeIterator<Key, Value>(
    begin: BinarySearchTreeNode<Key, Value, *>?
) : Iterator<Pair<Key, Value>> {

    private val path = mutableListOf<BinarySearchTreeNode<Key, Value, *>>()

    init {
        rebuildPath(begin)
    }

    private fun rebuildPath(from: BinarySearchTreeNode<Key, Value, *>?) {
        var node = from

        while (node != null) {
            path.add(node)
            node = node.left
        }
    }

    override fun hasNext() = path.isNotEmpty()

    override fun next(): Pair<Key, Value> {
        if (path.isEmpty()) {
            throw NoSuchElementException("")
        }

        val next = path.removeAt(path.lastIndex)

        if (next.right != null) {
            rebuildPath(next.right)
        }

        return Pair(next.key, next.value)
    }
}
