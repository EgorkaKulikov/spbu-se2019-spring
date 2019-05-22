package binary

class BinaryTreeCorrector<Data>(current: BinaryNode<Data>): BinaryTreeRunner<Data>(current) {

    fun rotateCurrentToLeft() = current.rotateLeft()

    fun rotateCurrentToRight() = current.rotateRight()

    fun rotateParentToLeft() = parent.rotateLeft()

    fun rotateParentToRight() = parent.rotateRight()

    fun rotateGrandparentToLeft() = grandparent.rotateLeft()

    fun rotateGrandparentToRight() = grandparent.rotateRight()
}
