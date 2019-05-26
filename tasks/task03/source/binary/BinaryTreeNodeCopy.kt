package binary

interface Copyable<Data> {

    fun copy(): Data
}

class BinaryTreeNodeCopy<Data : Copyable<Data>>(private val original: BinaryTreeNode<Data>) : BinaryNode<Data> {

    override val data = original.data.copy()

    override val parent get() = original.parent?.let { BinaryTreeNodeCopy(it) }
    override val left get() = original.left?.let { BinaryTreeNodeCopy(it) }
    override val right get() = original.right?.let { BinaryTreeNodeCopy(it) }
}
