import avl.AvlData
import avl.AvlTreeBalancer
import avl.BalanceFactor
import avl.BalanceFactor.*
import binary.BinaryTreeNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class AvlTreeBalancerTest {

    private class Data(override var state: BalanceFactor) : AvlData

    private val balancer = object : AvlTreeBalancer<Data> {}

    private fun createNode(state: BalanceFactor) = BinaryTreeNode(Data(state))

    @Test
    fun `Test balancing of balanced left child of root`() {
        val root = createNode(BALANCED)
        val child = createNode(BALANCED).also { root.left = it }

        balancer.balance(child)

        assertEquals(LEFT_HEAVY, root.data.state)
        assertEquals(BALANCED, child.data.state)

        assertTrue(root.left === child)
    }

    @Test
    fun `Test balancing of left-heavy left child of root`() {
        val root = createNode(LEFT_HEAVY)
        val child = createNode(LEFT_HEAVY).also { root.left = it }
        val grandchild = createNode(BALANCED).also { child.left = it }

        balancer.balance(child)

        assertEquals(BALANCED, root.data.state)
        assertEquals(BALANCED, child.data.state)
        assertEquals(BALANCED, grandchild.data.state)

        assertTrue(child.left === grandchild)
        assertTrue(child.right === root)
    }

    @Test
    fun `Test balancing of right-heavy left child of root`() {
        val root = createNode(LEFT_HEAVY)
        val child = createNode(RIGHT_HEAVY).also { root.left = it }
        val grandchild = createNode(BALANCED).also { child.right = it }

        balancer.balance(child)

        assertEquals(BALANCED, root.data.state)
        assertEquals(BALANCED, child.data.state)
        assertEquals(BALANCED, grandchild.data.state)

        assertTrue(grandchild.left === child)
        assertTrue(grandchild.right === root)
    }

    @Test
    fun `Test balancing of balanced right child of root`() {
        val root = createNode(BALANCED)
        val child = createNode(BALANCED).also { root.right = it }

        balancer.balance(child)

        assertEquals(RIGHT_HEAVY, root.data.state)
        assertEquals(BALANCED, child.data.state)

        assertTrue(root.right === child)
    }

    @Test
    fun `Test balancing of left-heavy right child of root`() {
        val root = createNode(RIGHT_HEAVY)
        val child = createNode(LEFT_HEAVY).also { root.right = it }
        val grandchild = createNode(BALANCED).also { child.left = it }

        balancer.balance(child)

        assertEquals(BALANCED, root.data.state)
        assertEquals(BALANCED, child.data.state)
        assertEquals(BALANCED, grandchild.data.state)

        assertTrue(grandchild.right === child)
        assertTrue(grandchild.left === root)
    }

    @Test
    fun `Test balancing of right-heavy right child of root`() {
        val root = createNode(RIGHT_HEAVY)
        val child = createNode(RIGHT_HEAVY).also { root.right = it }
        val grandchild = createNode(BALANCED).also { child.right = it }

        balancer.balance(child)

        assertEquals(BALANCED, root.data.state)
        assertEquals(BALANCED, child.data.state)
        assertEquals(BALANCED, grandchild.data.state)

        assertTrue(child.right === grandchild)
        assertTrue(child.left === root)
    }
}
