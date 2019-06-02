import avl.AvlTreeBalancer
import avl.BalanceFactor
import avl.BalanceFactor.*
import binary.BinarySearchTreeNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AvlTreeBalancerTest {

    private fun createNode(state: BalanceFactor) = BinarySearchTreeNode(0, 0, state)

    @Test
    fun `Test balancing of balanced left child of root`() {
        val root = createNode(BALANCED)
        val child = createNode(BALANCED).also { root.left = it }

        AvlTreeBalancer.balance(child)

        assertEquals(LEFT_HEAVY, root.info)
        assertEquals(BALANCED, child.info)

        assertTrue(root.left === child)
    }

    @Test
    fun `Test balancing of left-heavy left child of root`() {
        val root = createNode(LEFT_HEAVY)
        val child = createNode(LEFT_HEAVY).also { root.left = it }
        val grandchild = createNode(BALANCED).also { child.left = it }

        AvlTreeBalancer.balance(child)

        assertEquals(BALANCED, root.info)
        assertEquals(BALANCED, child.info)
        assertEquals(BALANCED, grandchild.info)

        assertTrue(child.left === grandchild)
        assertTrue(child.right === root)
    }

    @Test
    fun `Test balancing of right-heavy left child of root`() {
        val root = createNode(LEFT_HEAVY)
        val child = createNode(RIGHT_HEAVY).also { root.left = it }
        val grandchild = createNode(BALANCED).also { child.right = it }

        AvlTreeBalancer.balance(child)

        assertEquals(BALANCED, root.info)
        assertEquals(BALANCED, child.info)
        assertEquals(BALANCED, grandchild.info)

        assertTrue(grandchild.left === child)
        assertTrue(grandchild.right === root)
    }

    @Test
    fun `Test balancing of balanced right child of root`() {
        val root = createNode(BALANCED)
        val child = createNode(BALANCED).also { root.right = it }

        AvlTreeBalancer.balance(child)

        assertEquals(RIGHT_HEAVY, root.info)
        assertEquals(BALANCED, child.info)

        assertTrue(root.right === child)
    }

    @Test
    fun `Test balancing of left-heavy right child of root`() {
        val root = createNode(RIGHT_HEAVY)
        val child = createNode(LEFT_HEAVY).also { root.right = it }
        val grandchild = createNode(BALANCED).also { child.left = it }

        AvlTreeBalancer.balance(child)

        assertEquals(BALANCED, root.info)
        assertEquals(BALANCED, child.info)
        assertEquals(BALANCED, grandchild.info)

        assertTrue(grandchild.right === child)
        assertTrue(grandchild.left === root)
    }

    @Test
    fun `Test balancing of right-heavy right child of root`() {
        val root = createNode(RIGHT_HEAVY)
        val child = createNode(RIGHT_HEAVY).also { root.right = it }
        val grandchild = createNode(BALANCED).also { child.right = it }

        AvlTreeBalancer.balance(child)

        assertEquals(BALANCED, root.info)
        assertEquals(BALANCED, child.info)
        assertEquals(BALANCED, grandchild.info)

        assertTrue(child.right === grandchild)
        assertTrue(child.left === root)
    }
}
