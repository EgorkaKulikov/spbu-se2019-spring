package binary

abstract class BinaryTreeBalancer<Data> {

    fun balance(node: BinaryNode<Data>) = balance(BinaryTreeCorrector(node))

    abstract fun balance(corrector: BinaryTreeCorrector<Data>)
}
