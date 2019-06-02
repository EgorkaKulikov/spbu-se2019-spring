import avl.AvlSearchTree
import avl.BalanceFactor

class AvlSearchTreeTest : BalancedBinarySearchTreeTest<BalanceFactor> {

    override fun createTree() = AvlSearchTree<Int, Int>()
}
