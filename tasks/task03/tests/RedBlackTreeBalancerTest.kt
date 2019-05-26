import binary.BinaryTreeNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import rb.Color
import rb.Color.Black
import rb.Color.Red
import rb.RedBlackData
import rb.RedBlackTreeBalancer

class RedBlackTreeBalancerTest {

    private val balancer = object : RedBlackTreeBalancer<RedBlackData> {}

    private fun createNode(color: Color) = BinaryTreeNode(RedBlackData(color))

    @Test
    fun `Test balancing of root`() {
        val root = createNode(Red)

        balancer.balance(root)

        assertEquals(Black, root.data.color)
    }

    @Test
    fun `Test balancing of left child of root`() {
        val root = createNode(Black)
        val child = createNode(Red).also { root.left = it }

        balancer.balance(child)

        assertEquals(Black, root.data.color)
        assertEquals(Red, child.data.color)

        assertTrue(root.left === child)
    }

    @Test
    fun `Test balancing of left child of left child of root`() {
        val root = createNode(Black)
        val parent = createNode(Red).also { root.left = it }
        val child = createNode(Red).also { parent.left = it }

        balancer.balance(child)

        assertEquals(Red, root.data.color)
        assertEquals(Black, parent.data.color)
        assertEquals(Red, child.data.color)

        assertTrue(parent.left === child)
        assertTrue(parent.right === root)
    }

    @Test
    fun `Test balancing of right child of left child of root`() {
        val root = createNode(Black)
        val parent = createNode(Red).also { root.left = it }
        val child = createNode(Red).also { parent.right = it }

        balancer.balance(child)

        assertEquals(Red, root.data.color)
        assertEquals(Red, parent.data.color)
        assertEquals(Black, child.data.color)

        assertTrue(child.left === parent)
        assertTrue(child.right === root)
    }

    @Test
    fun `Test balancing of left child of left child of root with red right child`() {
        val root = createNode(Black)
        val parent = createNode(Red).also { root.left = it }
        val uncle = createNode(Red).also { root.right = it }
        val child = createNode(Red).also { parent.left = it }

        balancer.balance(child)

        assertEquals(Black, root.data.color)
        assertEquals(Black, parent.data.color)
        assertEquals(Black, uncle.data.color)
        assertEquals(Red, child.data.color)

        assertTrue(root.left === parent)
        assertTrue(root.right === uncle)
        assertTrue(parent.left === child)
    }

    @Test
    fun `Test balancing of right child of left child of root with red right child`() {
        val root = createNode(Black)
        val parent = createNode(Red).also { root.left = it }
        val uncle = createNode(Red).also { root.right = it }
        val child = createNode(Red).also { parent.right = it }

        balancer.balance(child)

        assertEquals(Black, root.data.color)
        assertEquals(Black, parent.data.color)
        assertEquals(Black, uncle.data.color)
        assertEquals(Red, child.data.color)

        assertTrue(root.left === parent)
        assertTrue(root.right === uncle)
        assertTrue(parent.right === child)
    }

    @Test
    fun `Test balancing of right child of root`() {
        val root = createNode(Black)
        val child = createNode(Red).also { root.right = it }

        balancer.balance(child)

        assertEquals(Black, root.data.color)
        assertEquals(Red, child.data.color)

        assertTrue(root.right === child)
    }

    @Test
    fun `Test balancing of left child of right child of root`() {
        val root = createNode(Black)
        val parent = createNode(Red).also { root.right = it }
        val child = createNode(Red).also { parent.left = it }

        balancer.balance(child)

        assertEquals(Red, root.data.color)
        assertEquals(Red, parent.data.color)
        assertEquals(Black, child.data.color)

        assertTrue(child.right === parent)
        assertTrue(child.left === root)
    }

    @Test
    fun `Test balancing of right child of right child of root`() {
        val root = createNode(Black)
        val parent = createNode(Red).also { root.right = it }
        val child = createNode(Red).also { parent.right = it }

        balancer.balance(child)

        assertEquals(Red, root.data.color)
        assertEquals(Black, parent.data.color)
        assertEquals(Red, child.data.color)

        assertTrue(parent.right === child)
        assertTrue(parent.left === root)
    }

    @Test
    fun `Test balancing of left child of right child of root with red left child`() {
        val root = createNode(Black)
        val parent = createNode(Red).also { root.right = it }
        val uncle = createNode(Red).also { root.left = it }
        val child = createNode(Red).also { parent.left = it }

        balancer.balance(child)

        assertEquals(Black, root.data.color)
        assertEquals(Black, parent.data.color)
        assertEquals(Black, uncle.data.color)
        assertEquals(Red, child.data.color)

        assertTrue(root.right === parent)
        assertTrue(root.left === uncle)
        assertTrue(parent.left === child)
    }

    @Test
    fun `Test balancing of right child of right child of root with red left child`() {
        val root = createNode(Black)
        val parent = createNode(Red).also { root.right = it }
        val uncle = createNode(Red).also { root.left = it }
        val child = createNode(Red).also { parent.right = it }

        balancer.balance(child)

        assertEquals(Black, root.data.color)
        assertEquals(Black, parent.data.color)
        assertEquals(Black, uncle.data.color)
        assertEquals(Red, child.data.color)

        assertTrue(root.right === parent)
        assertTrue(root.left === uncle)
        assertTrue(parent.right === child)
    }
}
