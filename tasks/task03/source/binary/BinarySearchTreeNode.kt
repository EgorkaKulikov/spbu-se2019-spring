package binary

class BinarySearchTreeNode<Key, Value, Info>(
    val key: Key,
    var value: Value,
    var info: Info
) {
    var parent: BinarySearchTreeNode<Key, Value, Info>? = null
        private set

    var left: BinarySearchTreeNode<Key, Value, Info>? = null
        set(value) {
            value?.parent = this
            field = value
        }

    var right: BinarySearchTreeNode<Key, Value, Info>? = null
        set(value) {
            value?.parent = this
            field = value
        }

    private fun updateParent(child: BinarySearchTreeNode<Key, Value, Info>) {
        parent?.let {
            if (this === it.left) {
                it.left = child
            } else {
                it.right = child
            }
        } ?: run {
            child.parent = null
        }
    }

    fun rotateLeft() = right?.let {
        updateParent(it)
        right = it.left
        it.left = this
    } ?: throw IllegalStateException("Right child does not exist")

    fun rotateRight() = left?.let {
        updateParent(it)
        left = it.right
        it.right = this
    } ?: throw IllegalStateException("Left child does not exist")
}
