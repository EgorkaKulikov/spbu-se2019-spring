package RedBlackTree

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import RedBlackNode
import RedBlackTree
import AVLTree

class EqualsTest {
    // test for checking @equals in all trees

    private val first = RedBlackTree<Int, Int>()
    private val second = RedBlackTree<Int, Int>()

    @Test
    fun nullEquals() {
        first.root = null
        second.root = null

        assertTrue(first == second)
    }

    @Test
    fun otherTreeTypeEquals() {
        val avltree = AVLTree<Int, Int>()
        avltree.root = null
        first.root = null

        assertTrue(avltree != first)
    }

    @Test
    fun nullAndNotNullEquals() {
        // adding some stuff into first tree
        first.root = RedBlackNode(5, 7, Color.Black)
        first.root?.addRightSon(3, 5, Color.Black)

        second.root = null

        assertTrue(first != second)
    }

    @Test
    fun similarTreesEquals() {
        // building similar trees
        first.root = RedBlackNode(5, 7, Color.Black)
        second.root = RedBlackNode(5, 7, Color.Black)

        first.root?.addRightSon(7, 5, Color.Black)
        second.root?.addRightSon(7, 5, Color.Black)

        first.root?.addLeftSon(-1, 28, Color.Black)
        second.root?.addLeftSon(-1, 28, Color.Black)

        assertTrue(first == second)
    }

    @Test
    fun valueDifferenceEquals() {
        first.root = RedBlackNode(1, 1, Color.Black)
        second.root = RedBlackNode(1, -1, Color.Black)

        assertTrue(first != second)
    }

    @Test
    fun keyDifferenceEquals() {
        first.root = RedBlackNode(1, 1, Color.Black)
        second.root = RedBlackNode(1, 1, Color.Black)

        first.root?.addLeftSon(0, 5, Color.Red)
        second.root?.addLeftSon(-1, 5, Color.Red)

        assertTrue(first != second)
    }
}
