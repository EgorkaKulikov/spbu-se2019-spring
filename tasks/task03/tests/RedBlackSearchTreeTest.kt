import binary.BinaryNode
import rb.Color
import rb.RedBlackSearchTree
import rb.RedBlackVisibleSearchData

class RedBlackSearchTreeTest : BalancedBinarySearchTreeTest<RedBlackVisibleSearchData<Int, Int>> {

    private companion object {
        const val INVALID_BLACK_HEIGHT = -1
    }

    private val BinaryNode<RedBlackVisibleSearchData<Int, Int>>?.blackHeight: Int
        get() {
            this ?: return 1

            val leftHeight = left.blackHeight

            if (leftHeight == INVALID_BLACK_HEIGHT) {
                return INVALID_BLACK_HEIGHT
            }

            val rightHeight = right.blackHeight

            if (rightHeight != leftHeight) {
                return INVALID_BLACK_HEIGHT
            }

            return leftHeight + if (data.color == Color.Black) 1 else 0
        }

    override fun checkBalance(root: BinaryNode<RedBlackVisibleSearchData<Int, Int>>?): Boolean {
        return root.blackHeight != INVALID_BLACK_HEIGHT
    }

    override fun createTree() = RedBlackSearchTree<Int, Int>()
}
