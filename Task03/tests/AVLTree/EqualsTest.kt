package AVLTree

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertTrue
import AVLNode
import AVLTree
import BinarySearchTree

class EqualsTest {
    private val first = AVLTree<Int, Int>()
    private val second = AVLTree<Int, Int>()

    @Test
    fun nullEquals() {
        first.root = null
        second.root = null

        assertTrue(first == second)
    }

    @Test
    fun otherTreeTypeEquals() {
        val binarytree = BinarySearchTree<Int, Int>()
        binarytree.root = null
        first.root = null

        assertTrue(binarytree != first)
    }

    @Test
    fun nullAndNotNullEquals() {
        // adding some stuff into first tree
        first.root = AVLNode(5, 7)
        first.root?.addRightSon(3, 5)

        second.root = null

        assertTrue(first != second)
    }

    @Test
    fun similarTreesEquals() {
        // building similar trees
        first.root = AVLNode(5, 7)
        second.root = AVLNode(5, 7)

        first.root?.addRightSon(7, 5)
        second.root?.addRightSon(7, 5)

        first.root?.addLeftSon(-1, 28)
        second.root?.addLeftSon(-1, 28)

        assertTrue(first == second)
    }

    @Test
    fun valueDifferenceEquals() {
        first.root = AVLNode(1, 1)
        second.root = AVLNode(1, -1)

        assertTrue(first != second)
    }

    @Test
    fun keyDifferenceEquals() {
        first.root = AVLNode(1, 1)
        second.root = AVLNode(1, 1)

        first.root?.addLeftSon(0, 5)
        second.root?.addLeftSon(-1, 5)

        assertTrue(first != second)
    }
}
