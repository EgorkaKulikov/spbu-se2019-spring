package binary

interface RotatableBinaryNode<Data>: BinaryNode<Data> {

    override val parent: RotatableBinaryNode<Data>?
    override val left: RotatableBinaryNode<Data>?
    override val right: RotatableBinaryNode<Data>?

    fun rotateLeft()
    fun rotateRight()
}