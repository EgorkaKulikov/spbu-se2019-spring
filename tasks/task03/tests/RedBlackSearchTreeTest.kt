import rb.Color
import rb.RedBlackSearchTree

class RedBlackSearchTreeTest : BalancedBinarySearchTreeTest<Color> {

    override fun createTree() = RedBlackSearchTree<Int, Int>()
}
