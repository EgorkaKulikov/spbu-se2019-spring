package binary

interface BinaryTreeBalancer<Info> {

    fun balance(inserted: BinarySearchTreeNode<*, *, Info>)
}
