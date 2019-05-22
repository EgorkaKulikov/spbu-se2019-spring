import binary.BinaryTreeRunner
import rb.Color
import rb.RedBlackSearchData
import rb.RedBlackSearchTree

class RedBlackSearchTreeTest: BalancedBinarySearchTreeTest<RedBlackSearchData<Int, Int>> {

    private companion object {
        const val INVALID_BLACK_HEIGHT = -1
    }

    private fun computeBlackHeightOf(runner: BinaryTreeRunner<RedBlackSearchData<Int, Int>>): Int = with(runner) {
        val leftHeight = if (currentHasLeftChild) {
            moveToLeftChild()
            computeBlackHeightOf(this).also { moveToParent() }
        } else {
            1
        }

        if (leftHeight == INVALID_BLACK_HEIGHT) {
            return INVALID_BLACK_HEIGHT
        }

        val rightHeight = if (currentHasRightChild) {
            moveToRightChild()
            computeBlackHeightOf(this).also { moveToParent() }
        } else {
            1
        }

        if (rightHeight != leftHeight) {
            return INVALID_BLACK_HEIGHT
        }

        leftHeight + if (currentData.color == Color.Black) 1 else 0
    }

    override fun checkBalance(runner: BinaryTreeRunner<RedBlackSearchData<Int, Int>>): Boolean {
        return computeBlackHeightOf(runner) != INVALID_BLACK_HEIGHT
    }

    override fun createTree() = RedBlackSearchTree<Int, Int>()
}