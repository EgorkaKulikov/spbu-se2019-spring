import avl.AvlSearchData
import avl.AvlSearchTree
import avl.AvlVisibleSearchData
import avl.BalanceFactor
import binary.BinaryNode
import kotlin.math.max

class AvlSearchTreeTest : BalancedBinarySearchTreeTest<AvlVisibleSearchData<Int, Int>> {

    private companion object {
        const val INVALID_HEIGHT = -1
    }

    private val BinaryNode<AvlVisibleSearchData<Int, Int>>?.height: Int
        get() {
            this ?: return 0

            val leftHeight = left.height

            if (leftHeight == INVALID_HEIGHT) {
                return INVALID_HEIGHT
            }

            val rightHeight = right.height

            if (rightHeight == INVALID_HEIGHT) {
                return INVALID_HEIGHT
            }

            when (rightHeight - leftHeight) {
                -1 -> if (data.state != BalanceFactor.LEFT_HEAVY) return INVALID_HEIGHT
                0 -> if (data.state != BalanceFactor.BALANCED) return INVALID_HEIGHT
                1 -> if (data.state != BalanceFactor.RIGHT_HEAVY) return INVALID_HEIGHT
                else -> return INVALID_HEIGHT
            }

            return max(leftHeight, rightHeight) + 1
        }

    override fun checkBalance(root: BinaryNode<AvlVisibleSearchData<Int, Int>>?): Boolean {
        return root.height != INVALID_HEIGHT
    }

    override fun createTree() = AvlSearchTree<Int, Int>()
}
