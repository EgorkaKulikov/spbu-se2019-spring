import binary.BinaryTreeNode
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BinaryNodeTest {

    @Test
    fun `Left rotation of node without right child fails`() {
        val node = BinaryTreeNode(0)

        assertThrows<IllegalStateException> {
            node.rotateLeft()
        }
    }

    @Test
    fun `Test left rotation of node without parent and grandchild`() {
        val node = BinaryTreeNode(0)
        val child = BinaryTreeNode(1)
        node.right = child

        node.rotateLeft()

        assertTrue(child.parent === null)
        assertTrue(child.left === node)
        assertTrue(node.parent === child)
    }

    @Test
    fun `Test left rotation of node without parent`() {
        val node = BinaryTreeNode(0)
        val child = BinaryTreeNode(1)
        val grandchild = BinaryTreeNode(2)
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
        val parent = BinaryTreeNode(-1)
        val node = BinaryTreeNode(0)
        val child = BinaryTreeNode(1)
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
        val parent = BinaryTreeNode(-1)
        val node = BinaryTreeNode(0)
        val child = BinaryTreeNode(1)
        val grandchild = BinaryTreeNode(2)
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
        val node = BinaryTreeNode(0)

        assertThrows<IllegalStateException> {
            node.rotateRight()
        }
    }

    @Test
    fun `Test right rotation of node without parent and grandchild`() {
        val node = BinaryTreeNode(0)
        val child = BinaryTreeNode(1)
        node.left = child

        node.rotateRight()

        assertTrue(child.parent === null)
        assertTrue(child.right === node)
        assertTrue(node.parent === child)
    }

    @Test
    fun `Test right rotation of node without parent`() {
        val node = BinaryTreeNode(0)
        val child = BinaryTreeNode(1)
        val grandchild = BinaryTreeNode(2)
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
        val parent = BinaryTreeNode(-1)
        val node = BinaryTreeNode(0)
        val child = BinaryTreeNode(1)
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
        val parent = BinaryTreeNode(-1)
        val node = BinaryTreeNode(0)
        val child = BinaryTreeNode(1)
        val grandchild = BinaryTreeNode(2)
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
