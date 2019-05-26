package binary

class BinaryTreeNode<Data>(override val data: Data) : BinaryNode<Data> {

    override var parent: BinaryTreeNode<Data>? = null
        private set

    override var left: BinaryTreeNode<Data>? = null
        set(value) {
            value?.parent = this
            field = value
        }

    override var right: BinaryTreeNode<Data>? = null
        set(value) {
            value?.parent = this
            field = value
        }

    private fun updateParent(child: BinaryTreeNode<Data>) {
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
