import binary.BinarySearchTreeNode
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BinaryNodeTest {

    private fun createNode() = BinarySearchTreeNode(0, 0, 0)

    @Test
    fun `Left rotation of node without right child fails`() {
        val node = createNode()

        assertThrows<IllegalStateException> {
            node.rotateLeft()
        }
    }

    @Test
    fun `Test left rotation of node without parent and grandchild`() {
        val node = createNode()
        val child = createNode()
        node.right = child

        node.rotateLeft()

        assertTrue(child.parent === null)
        assertTrue(child.left === node)
        assertTrue(node.parent === child)
    }

    @Test
    fun `Test left rotation of node without parent`() {
        val node = createNode()
        val child = createNode()
        val grandchild = createNode()
        node.right = child
        child.left = grandchild

        node.rotateLeft()

        assertTrue(child.parent === null)
        assertTrue(child.left === node)
        assertTrue(node.parent === child)
        assertTrue(node.right === grandchild)
        assertTrue(grandchild.parent === node)
    }

    @Test
    fun `Test left rotation of node without grandchild`() {
        val parent = createNode()
        val node = createNode()
        val child = createNode()
        parent.right = node
        node.right = child

        node.rotateLeft()

        assertTrue(parent.right === child)
        assertTrue(child.parent === parent)
        assertTrue(child.left === node)
        assertTrue(node.parent === child)
    }

    @Test
    fun `Test left rotation`() {
        val parent = createNode()
        val node = createNode()
        val child = createNode()
        val grandchild = createNode()
        parent.left = node
        node.right = child
        child.left = grandchild

        node.rotateLeft()

        assertTrue(parent.left === child)
        assertTrue(child.parent === parent)
        assertTrue(child.left === node)
        assertTrue(node.parent === child)
        assertTrue(node.right === grandchild)
        assertTrue(grandchild.parent === node)
    }

    @Test
    fun `Right rotation of node without left child fails`() {
        val node = createNode()

        assertThrows<IllegalStateException> {
            node.rotateRight()
        }
    }

    @Test
    fun `Test right rotation of node without parent and grandchild`() {
        val node = createNode()
        val child = createNode()
        node.left = child

        node.rotateRight()

        assertTrue(child.parent === null)
        assertTrue(child.right === node)
        assertTrue(node.parent === child)
    }

    @Test
    fun `Test right rotation of node without parent`() {
        val node = createNode()
        val child = createNode()
        val grandchild = createNode()
        node.left = child
        child.right = grandchild

        node.rotateRight()

        assertTrue(child.parent === null)
        assertTrue(child.right === node)
        assertTrue(node.parent === child)
        assertTrue(node.left === grandchild)
        assertTrue(grandchild.parent === node)
    }

    @Test
    fun `Test right rotation of node without grandchild`() {
        val parent = createNode()
        val node = createNode()
        val child = createNode()
        parent.left = node
        node.left = child

        node.rotateRight()

        assertTrue(parent.left === child)
        assertTrue(child.parent === parent)
        assertTrue(child.right === node)
        assertTrue(node.parent === child)
    }

    @Test
    fun `Test right rotation`() {
        val parent = createNode()
        val node = createNode()
        val child = createNode()
        val grandchild = createNode()
        parent.left = node
        node.left = child
        child.right = grandchild

        node.rotateRight()

        assertTrue(parent.left === child)
        assertTrue(child.parent === parent)
        assertTrue(child.right === node)
        assertTrue(node.parent === child)
        assertTrue(node.left === grandchild)
        assertTrue(grandchild.parent === node)
    }
}
