import binary.BinarySearchTreeNode
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import rb.Color
import rb.Color.Black
import rb.Color.Red
import rb.RedBlackTreeBalancer

class RedBlackTreeBalancerTest {

    private fun createNode(color: Color) = BinarySearchTreeNode(0, 0, color)

    @Test
    fun `Test balancing of root`() {
        val root = createNode(Red)

        RedBlackTreeBalancer.balance(root)

        assertEquals(Black, root.info)
    }

    @Test
    fun `Test balancing of left child of root`() {
        val root = createNode(Black)
        val child = createNode(Red).also { root.left = it }

        RedBlackTreeBalancer.balance(child)

        assertEquals(Black, root.info)
        assertEquals(Red, child.info)

        assertTrue(root.left === child)
    }

    @Test
    fun `Test balancing of left child of left child of root`() {
        val root = createNode(Black)
        val parent = createNode(Red).also { root.left = it }
        val child = createNode(Red).also { parent.left = it }

        RedBlackTreeBalancer.balance(child)

        assertEquals(Red, root.info)
        assertEquals(Black, parent.info)
        assertEquals(Red, child.info)

        assertTrue(parent.left === child)
        assertTrue(parent.right === root)
    }

    @Test
    fun `Test balancing of right child of left child of root`() {
        val root = createNode(Black)
        val parent = createNode(Red).also { root.left = it }
        val child = createNode(Red).also { parent.right = it }

        RedBlackTreeBalancer.balance(child)

        assertEquals(Red, root.info)
        assertEquals(Red, parent.info)
        assertEquals(Black, child.info)

        assertTrue(child.left === parent)
        assertTrue(child.right === root)
    }

    @Test
    fun `Test balancing of left child of left child of root with red right child`() {
        val root = createNode(Black)
        val parent = createNode(Red).also { root.left = it }
        val uncle = createNode(Red).also { root.right = it }
        val child = createNode(Red).also { parent.left = it }

        RedBlackTreeBalancer.balance(child)

        assertEquals(Black, root.info)
        assertEquals(Black, parent.info)
        assertEquals(Black, uncle.info)
        assertEquals(Red, child.info)

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

        RedBlackTreeBalancer.balance(child)

        assertEquals(Black, root.info)
        assertEquals(Black, parent.info)
        assertEquals(Black, uncle.info)
        assertEquals(Red, child.info)

        assertTrue(root.left === parent)
        assertTrue(root.right === uncle)
        assertTrue(parent.right === child)
    }

    @Test
    fun `Test balancing of right child of root`() {
        val root = createNode(Black)
        val child = createNode(Red).also { root.right = it }

        RedBlackTreeBalancer.balance(child)

        assertEquals(Black, root.info)
        assertEquals(Red, child.info)

        assertTrue(root.right === child)
    }

    @Test
    fun `Test balancing of left child of right child of root`() {
        val root = createNode(Black)
        val parent = createNode(Red).also { root.right = it }
        val child = createNode(Red).also { parent.left = it }

        RedBlackTreeBalancer.balance(child)

        assertEquals(Red, root.info)
        assertEquals(Red, parent.info)
        assertEquals(Black, child.info)

        assertTrue(child.right === parent)
        assertTrue(child.left === root)
    }

    @Test
    fun `Test balancing of right child of right child of root`() {
        val root = createNode(Black)
        val parent = createNode(Red).also { root.right = it }
        val child = createNode(Red).also { parent.right = it }

        RedBlackTreeBalancer.balance(child)

        assertEquals(Red, root.info)
        assertEquals(Black, parent.info)
        assertEquals(Red, child.info)

        assertTrue(parent.right === child)
        assertTrue(parent.left === root)
    }

    @Test
    fun `Test balancing of left child of right child of root with red left child`() {
        val root = createNode(Black)
        val parent = createNode(Red).also { root.right = it }
        val uncle = createNode(Red).also { root.left = it }
        val child = createNode(Red).also { parent.left = it }

        RedBlackTreeBalancer.balance(child)

        assertEquals(Black, root.info)
        assertEquals(Black, parent.info)
        assertEquals(Black, uncle.info)
        assertEquals(Red, child.info)

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

        RedBlackTreeBalancer.balance(child)

        assertEquals(Black, root.info)
        assertEquals(Black, parent.info)
        assertEquals(Black, uncle.info)
        assertEquals(Red, child.info)

        assertTrue(root.right === parent)
        assertTrue(root.left === uncle)
        assertTrue(parent.right === child)
    }
}
