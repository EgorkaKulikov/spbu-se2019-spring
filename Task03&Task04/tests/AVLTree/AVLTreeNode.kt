package AVLTree

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests for AVL Tree Node")
internal class TestAVLTreeNode {

    @DisplayName("Comparison with null")
    @Test
    fun testComparisonWithNull() {

        val node = AVLTreeNode(0, 0)

        assertNotEquals(null, node )

    }

    @DisplayName("Self comparison")
    @Test
    fun testSelfComparison() {

        val node = AVLTreeNode(0, 0)

        assertEquals(node, node)

    }

    @DisplayName("Self hashCode")
    @Test
    fun testSelfHashCode() {

        val node = AVLTreeNode(0, 0)

        assertEquals(node.hashCode(), node.hashCode())

    }

    @DisplayName("Different hashCode")
    @Test
    fun testDifferentHashCode() {

        val node = AVLTreeNode(0, 0)

        val other = AVLTreeNode(1, 1)

        assertNotEquals(node, other)

    }

    @DisplayName("Simple equality")
    @Test
    fun testSimpleEquals() {

        val thisNode = AVLTreeNode(1, 1)
        val otherNode = AVLTreeNode(1, 1)

        assertEquals(thisNode, otherNode)

    }

    @DisplayName("Simple left rotate")
    @Test
    fun testSimpleLeftRotate() {

        val thisNode = AVLTreeNode(0, 0)

        thisNode.leftRotate()

        assertEquals(0, thisNode.key )

    }

    @DisplayName("Simple right rotate")
    @Test
    fun testSimpleRightRotate() {

        val thisNode = AVLTreeNode(0, 0)

        thisNode.leftRotate()

        assertEquals(0, thisNode.key)

    }

    @DisplayName("Pair left rotate")
    @Test
    fun testPairLeftRotate() {

        val thisNode = AVLTreeNode(0, 0)
        thisNode.rightChild = AVLTreeNode(1, 1)
        thisNode.rightChild!!.parent = thisNode

        thisNode.leftRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)

    }

    @DisplayName("Pair right rotate")
    @Test
    fun testPairRightRotate() {

        val thisNode = AVLTreeNode(0, 0)
        thisNode.leftChild = AVLTreeNode(1, 1)
        thisNode.leftChild!!.parent = thisNode

        thisNode.rightRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)

    }

    @DisplayName("Pair&parent left rotate")
    @Test
    fun testPairAndParentLeftRotate() {

        val thisNode = AVLTreeNode(0, 0)
        thisNode.rightChild = AVLTreeNode(1, 1)
        thisNode.rightChild!!.parent = thisNode
        thisNode.parent = AVLTreeNode(2, 2)
        thisNode.parent!!.leftChild = thisNode

        thisNode.leftRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.parent!!.parent!!.key)

    }

    @DisplayName("Pair&parent right rotate")
    @Test
    fun testPairAndParentRightRotate() {

        val thisNode = AVLTreeNode(0, 0)
        thisNode.leftChild = AVLTreeNode(1, 1)
        thisNode.leftChild!!.parent = thisNode
        thisNode.parent = AVLTreeNode(2, 2)
        thisNode.parent!!.leftChild = thisNode

        thisNode.rightRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.parent!!.parent!!.key)

    }

    @DisplayName("Pair left rotate Case 1")
    @Test
    fun testPairLeftRotateCase1() {

        val thisNode = AVLTreeNode(0, 0)
        thisNode.rightChild = AVLTreeNode(1, 1)
        thisNode.rightChild!!.parent = thisNode
        thisNode.leftChild = AVLTreeNode(2, 2)
        thisNode.leftChild!!.parent = thisNode

        thisNode.leftRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.leftChild!!.key)

    }

    @DisplayName("Pair right rotate Case 1")
    @Test
    fun testPairRightRotateCase1() {

        val thisNode = AVLTreeNode(0, 0)
        thisNode.leftChild = AVLTreeNode(1, 1)
        thisNode.leftChild!!.parent = thisNode
        thisNode.leftChild!!.leftChild = AVLTreeNode(2, 2)
        thisNode.leftChild!!.leftChild!!.parent = thisNode.leftChild

        thisNode.rightRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.parent!!.leftChild!!.key)

    }

    @DisplayName("Pair left rotate Case 2")
    @Test
    fun testPairLeftRotateCase2() {

        val thisNode = AVLTreeNode(0, 0)
        thisNode.rightChild = AVLTreeNode(1, 1)
        thisNode.rightChild!!.parent = thisNode
        thisNode.rightChild!!.leftChild = AVLTreeNode(2, 2)
        thisNode.rightChild!!.leftChild!!.parent = thisNode.rightChild

        thisNode.leftRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.rightChild!!.key)

    }

    @DisplayName("Pair right rotate Case 2")
    @Test
    fun testPairRightRotateCase2() {

        val thisNode = AVLTreeNode(0, 0)
        thisNode.leftChild = AVLTreeNode(1, 1)
        thisNode.leftChild!!.parent = thisNode
        thisNode.leftChild!!.rightChild = AVLTreeNode(2, 2)
        thisNode.leftChild!!.rightChild!!.parent = thisNode.leftChild

        thisNode.rightRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.leftChild!!.key)

    }

    @DisplayName("Pair left rotate Case 3")
    @Test
    fun testPairLeftRotateCase3() {

        val thisNode = AVLTreeNode(0, 0)
        thisNode.rightChild = AVLTreeNode(1, 1)
        thisNode.rightChild!!.parent = thisNode
        thisNode.rightChild!!.rightChild = AVLTreeNode(2, 2)
        thisNode.rightChild!!.rightChild!!.parent = thisNode.rightChild

        thisNode.leftRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.parent!!.rightChild!!.key)

    }

    @DisplayName("Pair right rotate Case 3")
    @Test
    fun testPairRightRotateCase3() {

        val thisNode = AVLTreeNode(0, 0)
        thisNode.leftChild = AVLTreeNode(1, 1)
        thisNode.leftChild!!.parent = thisNode
        thisNode.rightChild = AVLTreeNode(2, 2)
        thisNode.rightChild!!.parent = thisNode

        thisNode.rightRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.rightChild!!.key)

    }

}
