import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class BinarySearchTreeEqualsTest {
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
        val rbTree = RedBlackTree<Int, Int>()
        rbTree.root = null
        first.root = null

        assertFalse(first == rbTree)
    }

    @Test
    fun nullAndNotNullEquals() {
        // adding some stuff into first tree
        first.root = BinaryNode(5, 7)
        first.root?.addRightSon(3, 5)

        second.root = null

        assertFalse(first == second)
    }

    @Test
    fun similarTreesEquals() {
        // building similar trees
        first.root = BinaryNode(5, 7)
        second.root = BinaryNode(5, 7)

        first.root?.addRightSon(7, 5)
        second.root?.addRightSon(7, 5)

        first.root?.addLeftSon(-1, 28)
        second.root?.addLeftSon(-1, 28)

        assertTrue(first == second)
    }

    @Test
    fun valueDifferenceEquals() {
        first.root = BinaryNode(1, 1)
        second.root = BinaryNode(1, -1)

        assertFalse(first == second)
    }

    @Test
    fun keyDifferenceEquals() {
        first.root = BinaryNode(1, 1)
        second.root = BinaryNode(1, 1)

        first.root?.addLeftSon(0, 5)
        second.root?.addLeftSon(-1, 5)

        assertFalse(first == second)
    }
}