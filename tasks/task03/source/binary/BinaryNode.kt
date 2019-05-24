package binary

interface BinaryNode<Data> {

    val data: Data

    val parent: BinaryNode<Data>?
    val left: BinaryNode<Data>?
    val right: BinaryNode<Data>?
}
