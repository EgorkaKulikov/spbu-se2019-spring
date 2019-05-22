package binary

class BinaryNode<Data>(val data: Data) {

    var parent: BinaryNode<Data>? = null
        private set

    var left: BinaryNode<Data>? = null
        set(value) {
            value?.parent = this
            field = value
        }

    var right: BinaryNode<Data>? = null
        set(value) {
            value?.parent = this
            field = value
        }

    private fun updateParent(child: BinaryNode<Data>) {
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
    } ?: throw IllegalStateException("Right child must exists")

    fun rotateRight() = left?.let {
        updateParent(it)
        left = it.right
        it.right = this
    } ?: throw IllegalStateException("Left child must exists")
}
