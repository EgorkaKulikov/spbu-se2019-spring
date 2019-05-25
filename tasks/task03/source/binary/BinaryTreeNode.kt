package binary

class BinaryTreeNode<VisibleData, Data : VisibleData>(
    override val data: Data
): BinaryNode<VisibleData>, RotatableBinaryNode<Data> {

    override var parent: BinaryTreeNode<VisibleData, Data>? = null
        private set

    override var left: BinaryTreeNode<VisibleData, Data>? = null
        set(value) {
            value?.parent = this
            field = value
        }

    override var right: BinaryTreeNode<VisibleData, Data>? = null
        set(value) {
            value?.parent = this
            field = value
        }

    private fun updateParent(child: BinaryTreeNode<VisibleData, Data>) {
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

    override fun rotateLeft() = right?.let {
        updateParent(it)
        right = it.left
        it.left = this
    } ?: throw IllegalStateException("Right child does not exist")

    override fun rotateRight() = left?.let {
        updateParent(it)
        left = it.right
        it.right = this
    } ?: throw IllegalStateException("Left child does not exist")
}
