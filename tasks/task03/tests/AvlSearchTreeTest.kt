import avl.AvlSearchData
import avl.AvlSearchTree
import binary.BinaryTreeRunner
import kotlin.math.max

class AvlSearchTreeTest : BalancedBinarySearchTreeTest<AvlSearchData<Int, Int>> {

    private companion object {
        const val INVALID_HEIGHT = -1
    }

    private fun computeHeightOf(runner: BinaryTreeRunner<AvlSearchData<Int, Int>>): Int = with(runner) {
        val leftHeight = if (currentHasLeftChild) {
            moveToLeftChild()
            computeHeightOf(this).also { moveToParent() }
        } else {
            0
        }

        if (leftHeight == INVALID_HEIGHT) {
            return INVALID_HEIGHT
        }

        val rightHeight = if (currentHasRightChild) {
            moveToRightChild()
            computeHeightOf(this).also { moveToParent() }
        } else {
            0
        }

        if (rightHeight == INVALID_HEIGHT) {
            return INVALID_HEIGHT
        }

        if (rightHeight - leftHeight != currentData.state.value) {
            return INVALID_HEIGHT
        }

        max(leftHeight, rightHeight) + 1
    }

    override fun checkBalance(runner: BinaryTreeRunner<AvlSearchData<Int, Int>>): Boolean {
        return computeHeightOf(runner) != INVALID_HEIGHT
    }

    override fun createTree() = AvlSearchTree<Int, Int>()
}
