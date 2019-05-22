import binary.BinaryNode
import org.junit.Test
import org.junit.Assert.assertTrue

class BinaryNodeTest {

    @Test
    fun `Left rotation of node without right child fails`() {
        val node = BinaryNode(0)

        val exceptionFound = try {
            node.rotateLeft()
            false
        } catch (exception: IllegalStateException) {
            true
        }

        assertTrue(exceptionFound)
    }

    @Test
    fun `Test left rotation of node without parent and grandchild`() {
        val node = BinaryNode(0)
        val child = BinaryNode(1)
        node.right = child

        node.rotateLeft()

        assertTrue(child.parent === null)
        assertTrue(child.left === node)
        assertTrue(node.parent === child)
    }

    @Test
    fun `Test left rotation of node without parent`() {
        val node = BinaryNode(0)
        val child = BinaryNode(1)
        val grandchild = BinaryNode(2)
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
        val parent = BinaryNode(-1)
        val node = BinaryNode(0)
        val child = BinaryNode(1)
        parent.right = node
        node.right = child

        node.rotateLeft()

        assertTrue(parent.right === child)
        assertTrue(child.parent === parent)
        assertTrue(child.left === node)
        assertTrue(node.parent === child)
    }

    @Test
    fun `Test left rotation of node`() {
        val parent = BinaryNode(-1)
        val node = BinaryNode(0)
        val child = BinaryNode(1)
        val grandchild = BinaryNode(2)
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
    fun `Test right rotation without left child`() {
        val node = BinaryNode(0)

        val exceptionFound = try {
            node.rotateRight()
            false
        } catch (exception: IllegalStateException) {
            true
        }

        assertTrue(exceptionFound)
    }

    @Test
    fun `Test right rotation without parent and grandchild`() {
        val node = BinaryNode(0)
        val child = BinaryNode(1)
        node.left = child

        node.rotateRight()

        assertTrue(child.parent === null)
        assertTrue(child.right === node)
        assertTrue(node.parent === child)
    }

    @Test
    fun `Test right rotation without parent`() {
        val node = BinaryNode(0)
        val child = BinaryNode(1)
        val grandchild = BinaryNode(2)
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
        val parent = BinaryNode(-1)
        val node = BinaryNode(0)
        val child = BinaryNode(1)
        parent.left = node
        node.left = child

        node.rotateRight()

        assertTrue(parent.left === child)
        assertTrue(child.parent === parent)
        assertTrue(child.right === node)
        assertTrue(node.parent === child)
    }

    @Test
    fun `Test right rotation of node`() {
        val parent = BinaryNode(-1)
        val node = BinaryNode(0)
        val child = BinaryNode(1)
        val grandchild = BinaryNode(2)
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
