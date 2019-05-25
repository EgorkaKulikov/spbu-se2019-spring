package binary

interface BinaryTreeBalancer<Data> {

    operator fun invoke(inserted: RotatableBinaryNode<Data>)
}
