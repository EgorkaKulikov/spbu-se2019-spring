class TreeIterator<V>(begin: Node<*, V>) : Iterator<V> {

    private var current = mostLeftOf(begin)
    private var hasNext = true

    private fun mostLeftOf(node: Node<*, V>): Node<*, V> {
        var mostLeft = node
        var left = mostLeft.left

        while (left != null) {
            mostLeft = left
            left = mostLeft.left
        }

        return mostLeft
    }

    override fun hasNext() = hasNext

    override fun next(): V {
        val value = current.value
        val right = current.right

        if (right != null) {
            current = mostLeftOf(right)
            return value
        } else {
            var parent = current.parent

            while (parent != null) {
                if (current === parent.left) {
                    current = parent
                    return value
                }

                current = parent
                parent = current.parent
            }

            hasNext = false
        }

        return value
    }
}
