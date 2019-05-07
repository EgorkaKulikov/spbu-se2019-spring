package BinarySearchTree

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import BinarySearchNode
import BinarySearchTree
import RedBlackTree

class EqualsTest {
    // test for checking @equals in all trees

    private val first = BinarySearchTree<Int, Int>()
    private val second = BinarySearchTree<Int, Int>()

    @Test
    fun nullEquals() {
        first.root = null
        second.root = null

        assertTrue(first == second)
    }

    @Test
    fun otherTreeTypeEquals() {
        val rbtree = RedBlackTree<Int, Int>()
        rbtree.root = null
        first.root = null

        assertTrue(rbtree != first)
    }

    @Test
    fun nullAndNotNullEquals() {
        // adding some stuff into first tree
        first.root = BinarySearchNode(5, 7)
        first.root?.addRightSon(3, 5)

        second.root = null

        assertTrue(first != second)
    }

    @Test
    fun similarTreesEquals() {
        // building similar trees
        first.root = BinarySearchNode(5, 7)
        second.root = BinarySearchNode(5, 7)

        first.root?.addRightSon(7, 5)
        second.root?.addRightSon(7, 5)

        first.root?.addLeftSon(-1, 28)
        second.root?.addLeftSon(-1, 28)

        assertTrue(first == second)
    }

    @Test
    fun valueDifferenceEquals() {
        first.root = BinarySearchNode(1, 1)
        second.root = BinarySearchNode(1, -1)

        assertTrue(first != second)
    }

    @Test
    fun keyDifferenceEquals() {
        first.root = BinarySearchNode(1, 1)
        second.root = BinarySearchNode(1, 1)

        first.root?.addLeftSon(0, 5)
        second.root?.addLeftSon(-1, 5)

        assertTrue(first != second)
    }
}
