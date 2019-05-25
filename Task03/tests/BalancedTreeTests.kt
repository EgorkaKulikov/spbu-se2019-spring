package trees


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test


class BalancedSearchTreeTests {
    private class BalancedTree<K : Comparable<K>, V> : BalancedSearchTree<K, V>()

    @Test
    fun basicRotateLeft() {
        val tree = BalancedTree<Int, Int>()

        val node1 = tree.BalancedNode(0, 0, null)
        val node2 = tree.BalancedNode(1, 1, node1)

        node1.right = node2

        node1.rotateLeft()

        assertEquals(node1, node2.left)
        assertEquals(node1.parent, node2)
    }

    @Test
    fun basicRotateRight() {
        val tree = BalancedTree<Int, Int>()

        val node1 = tree.BalancedNode(0, 0, null)
        val node2 = tree.BalancedNode(-1, 1, node1)

        node1.left = node2

        node1.rotateRight()

        assertEquals(node1.parent, node2)
        assertEquals(node1, node2.right)
    }

    @Test
    fun rotateLeftIsLeftChild() {
        val tree = BalancedTree<Int, Int>()

        val node0 = tree.BalancedNode(0, 0, null)
        val node1 = tree.BalancedNode(2, 1, node0)
        val node2 = tree.BalancedNode(1, 2, node1)

        node0.left = node1
        node1.right = node2

        node1.rotateLeft()

        assertEquals(node2.left, node1)
        assertEquals(node1.parent, node2)
        assertEquals(node0.left, node2)
        assertEquals(node0, node2.parent)
    }

    @Test
    fun rotateRightIsLeftChild() {
        val tree = BalancedTree<Int, Int>()

        val node0 = tree.BalancedNode(2, 0, null)
        val node1 = tree.BalancedNode(1, 1, node0)
        val node2 = tree.BalancedNode(0, 2, node1)

        node0.left = node1
        node1.left = node2

        node1.rotateRight()

        assertEquals(node2.right, node1)
        assertEquals(node1.parent, node2)
        assertEquals(node0.left, node2)
        assertEquals(node0, node2.parent)
    }

    @Test
    fun rotateLeftIsRightChild() {
        val tree = BalancedTree<Int, Int>()

        val node0 = tree.BalancedNode(0, 0, null)
        val node1 = tree.BalancedNode(1, 1, node0)
        val node2 = tree.BalancedNode(2, 2, node1)

        node0.right = node1
        node1.right = node2

        node1.rotateLeft()

        assertEquals(node2.left, node1)
        assertEquals(node1.parent, node2)
        assertEquals(node0.right, node2)
        assertEquals(node0, node2.parent)
    }

    @Test
    fun rotateRightIsRightChild() {
        val tree = BalancedTree<Int, Int>()

        val node0 = tree.BalancedNode(0, 0, null)
        val node1 = tree.BalancedNode(2, 1, node0)
        val node2 = tree.BalancedNode(1, 2, node1)

        node0.right = node1
        node1.left = node2

        node1.rotateRight()

        assertEquals(node2.right, node1)
        assertEquals(node1.parent, node2)
        assertEquals(node0.right, node2)
        assertEquals(node0, node2.parent)
    }

    @Test
    fun rotateLeftHasLeftChild() {
        val tree = BalancedTree<Int, Int>()

        val node0 = tree.BalancedNode(0, 0, null)
        val node1 = tree.BalancedNode(-1, 1, node0)
        val node2 = tree.BalancedNode(1, 2, node0)

        node0.left = node1
        node0.right = node2

        node0.rotateLeft()

        assertEquals(node2.left, node0)
        assertEquals(node0.parent, node2)
        assertEquals(node1, node0.left)
        assertEquals(node1.parent, node0)
    }

    @Test
    fun rotateRightHasRightChild() {
        val tree = BalancedTree<Int, Int>()

        val node0 = tree.BalancedNode(0, 0, null)
        val node1 = tree.BalancedNode(-1, 1, node0)
        val node2 = tree.BalancedNode(1, 2, node0)

        node0.left = node1
        node0.right = node2

        node0.rotateRight()

        assertEquals(node1.right, node0)
        assertEquals(node0.parent, node1)
        assertEquals(node2, node0.right)
        assertEquals(node2.parent, node0)
    }

    @Test
    fun rotateLeftHasGrandchildren() {
        val tree = BalancedTree<Int, Int>()

        val node0 = tree.BalancedNode(0, 0, null)
        val node1 = tree.BalancedNode(2, 1, node0)
        val node2 = tree.BalancedNode(1, 2, node1)
        val node3 = tree.BalancedNode(3, 3, node1)

        node0.right = node1
        node1.left = node2
        node1.right = node3

        node0.rotateLeft()

        assertEquals(node1.left, node0)
        assertEquals(node0.parent, node1)
        assertEquals(node2, node0.right)
        assertEquals(node3, node1.right)
    }

    @Test
    fun rotateRightHasGrandchildren() {
        val tree = BalancedTree<Int, Int>()

        val node0 = tree.BalancedNode(0, 0, null)
        val node1 = tree.BalancedNode(-2, 1, node0)
        val node2 = tree.BalancedNode(-3, 2, node1)
        val node3 = tree.BalancedNode(-1, 3, node1)

        node0.left = node1
        node1.left = node2
        node1.right = node3

        node0.rotateRight()

        assertEquals(node1.right, node0)
        assertEquals(node0.parent, node1)
        assertEquals(node2, node1.left)
        assertEquals(node3, node0.left)
    }
}