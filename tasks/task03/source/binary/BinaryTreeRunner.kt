package binary

open class BinaryTreeRunner<Data>(var current: BinaryNode<Data>) {

    val currentData get() = current.data

    val currentIsLeftChild get() = current === current.parent?.left
    val currentIsRightChild get() = current === current.parent?.right

    val currentHasLeftChild get() = current.left != null
    val currentHasRightChild get() = current.right != null
    val currentHasParent get() = current.parent != null
    val currentHasGrandparent get() = current.parent?.parent != null
    val currentHasUncle get() = current.parent?.parent?.run { left != null && right != null } ?: false

    protected val left get() = current.left ?: throw IllegalStateException("Left child must exists")

    val leftChildData get() = left.data

    protected val right get() = current.right ?: throw IllegalStateException("Right child must exists")

    val rightChildData get() = right.data

    protected val parent get() = current.parent ?: throw IllegalStateException("Parent must exists")

    val parentData get() = parent.data

    val parentIsLeftChild get() = parent === grandparent.left
    val parentIsRightChild get() = parent === grandparent.right

    protected val grandparent get() = parent.parent ?: throw IllegalStateException("Grandparent must exists")
    val grandparentData get() = grandparent.data

    protected val uncle
        get() = grandparent.let {
            if (parent === it.left) {
                it.right
            } else {
                it.left
            }!!
        }

    val uncleData get() = uncle.data

    fun moveToLeftChild() {
        current = left
    }

    fun moveToRightChild() {
        current = right
    }

    fun moveToParent() {
        current = parent
    }

    fun moveToGrandparent() {
        current = grandparent
    }

    fun moveToRoot() {
        while (currentHasParent) {
            current = parent
        }
    }
}
