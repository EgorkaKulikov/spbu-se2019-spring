package binaryTrees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BalancedSearchTreeTest {
    @Test
    fun rotateLeftBasicTest() {
        val tree = RBTree<Int, Int>()
        val node1 = tree.RBNode(0, 0, null)
        val node2 = tree.RBNode(0, 0, node1)

        node1.right = node2

        node1.rotateLeft()

        assertEquals(node2.left, node1)
        assertEquals(node1.parent, node2)
    }

    @Test
    fun rotateRightBasicTest() {
        val tree = RBTree<Int, Int>()
        val node2 = tree.RBNode(0, 0, null)
        val node1 = tree.RBNode(0, 0, node2)

        node2.left = node1

        node2.rotateRight()

        assertEquals(node1.right, node2)
        assertEquals(node2.parent, node1)
    }

    @Test
    fun rotateLeftTestIsLeftChild() {
        val tree = RBTree<Int, Int>()
        val parent = tree.RBNode(0, 0, null)
        val node1 = tree.RBNode(0, 0, parent)
        val node2 = tree.RBNode(0, 0, node1)

        node1.right = node2
        parent.left = node1

        node1.rotateLeft()

        assertEquals(parent.left, node2)
        assertEquals(parent, node2.parent)
    }

    @Test
    fun rotateRightTestIsLeftChild() {
        val tree = RBTree<Int, Int>()
        val parent = tree.RBNode(0, 0, null)
        val node2 = tree.RBNode(0, 0, parent)
        val node1 = tree.RBNode(0, 0, node2)

        node2.left = node1
        parent.left = node2

        node2.rotateRight()

        assertEquals(parent.left, node1)
        assertEquals(parent, node1.parent)
    }

    @Test
    fun rotateLeftTestIsRightChild() {
        val tree = RBTree<Int, Int>()
        val parent = tree.RBNode(0, 0, null)
        val node1 = tree.RBNode(0, 0, parent)
        val node2 = tree.RBNode(0, 0, node1)

        node1.right = node2
        parent.right = node1

        node1.rotateLeft()

        assertEquals(parent.right, node2)
        assertEquals(parent, node2.parent)
    }

    @Test
    fun rotateRightTestIsRightChild() {
        val tree = RBTree<Int, Int>()
        val parent = tree.RBNode(0, 0, null)
        val node2 = tree.RBNode(0, 0, parent)
        val node1 = tree.RBNode(0, 0, node2)

        node2.left = node1
        parent.right = node2

        node2.rotateRight()

        assertEquals(parent.right, node1)
        assertEquals(parent, node1.parent)
    }

    @Test
    fun rotateLeftTestHasLeftChild() {
        val tree = RBTree<Int, Int>()
        val node1 = tree.RBNode(0, 0, null)
        val node2 = tree.RBNode(0, 0, node1)
        val leftChild = tree.RBNode(0, 0, node1)

        node1.right = node2
        node1.left = leftChild

        node1.rotateLeft()

        assertEquals(leftChild, node1.left)
        assertEquals(leftChild.parent, node1)
    }

    @Test
    fun rotateRightTestHasRightChild() {
        val tree = RBTree<Int, Int>()
        val node2 = tree.RBNode(0, 0, null)
        val node1 = tree.RBNode(0, 0, node2)
        val rightChild = tree.RBNode(0, 0, node2)

        node2.right = rightChild
        node2.left = node1

        node2.rotateRight()
        assertEquals(rightChild, node2.right)
        assertEquals(rightChild.parent, node2)
    }

    @Test
    fun rotateLeftTestHasGrandchildren() {
        val tree = RBTree<Int, Int>()
        val node1 = tree.RBNode(0, 0, null)
        val node2 = tree.RBNode(0, 0, node1)
        val grandChildLeft = tree.RBNode(0, 0, node2)
        val grandChildRight = tree.RBNode(0, 0, node2)

        node1.right = node2
        node2.right = grandChildRight
        node2.left = grandChildLeft

        node1.rotateLeft()

        assertEquals(grandChildRight, node2.right)
        assertEquals(grandChildLeft, node1.right)
        assertEquals(node1, grandChildLeft.parent)
    }

    @Test
    fun rotateRightTestHasGrandchildren() {
        val tree = RBTree<Int, Int>()
        val node2 = tree.RBNode(0, 0, null)
        val node1 = tree.RBNode(0, 0, node2)
        val grandChildLeft = tree.RBNode(0, 0, node2)
        val grandChildRight = tree.RBNode(0, 0, node2)

        node2.left = node1
        node1.right = grandChildRight
        node1.left = grandChildLeft

        node2.rotateRight()

        assertEquals(grandChildLeft, node1.left)
        assertEquals(grandChildRight, node2.left)
        assertEquals(node2, grandChildRight.parent)
    }
}