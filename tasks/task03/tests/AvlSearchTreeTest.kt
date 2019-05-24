import avl.AvlSearchData
import avl.AvlSearchTree
import binary.BinaryNode
import kotlin.math.max

class AvlSearchTreeTest : BalancedBinarySearchTreeTest<AvlSearchData<Int, Int>> {

    private companion object {
        const val INVALID_HEIGHT = -1
    }

    private val BinaryNode<AvlSearchData<Int, Int>>?.height: Int
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

            if (rightHeight - leftHeight != data.state.value) {
                return INVALID_HEIGHT
            }

            return max(leftHeight, rightHeight) + 1
        }

    override fun checkBalance(root: BinaryNode<AvlSearchData<Int, Int>>?): Boolean {
        return root.height != INVALID_HEIGHT
    }

    override fun createTree() = AvlSearchTree<Int, Int>()
}
