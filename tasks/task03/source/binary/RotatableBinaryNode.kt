package binary

interface RotatableBinaryNode<Data> {

    val data: Data

    val parent: RotatableBinaryNode<Data>?
    val left: RotatableBinaryNode<Data>?
    val right: RotatableBinaryNode<Data>?

    fun rotateLeft()
    fun rotateRight()
}
