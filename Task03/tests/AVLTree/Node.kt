package AVLTree

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests for AVL Tree Node")
internal class TestAVLTreeNode {

    @DisplayName("Comparison with null")
    @Test
    fun testComparisonWithNull() {

        val node = Node(0, 0)

        assertNotEquals(null, node )

    }

    @DisplayName("Self comparison")
    @Test
    fun testSelfComparison() {

        val node = Node(0, 0)

        assertEquals(node, node)

    }

    @DisplayName("Simple equality")
    @Test
    fun testSimpleEquals() {

        val thisNode = Node(1, 1, null)
        val otherNode = Node(1, 1, null)

        assertEquals(thisNode, otherNode)

    }

    @DisplayName("Equality with same parents")
    @Test
    fun testEqualityWithSameParents() {

        val thisNode = Node(1, 1, null)
        thisNode.parent = Node(3, 3, null)
        thisNode.parent!!.left = thisNode

        val otherNode = Node(1, 1, null)
        otherNode.parent = Node(3, 3, null)
        otherNode.parent!!.left = otherNode

        assertEquals(thisNode, otherNode)

    }

    @DisplayName("Equality with different parents")
    @Test
    fun testEqualityWithDifferentParents() {

        val thisNode = Node(1, 1, null)
        thisNode.parent = Node(2, 2, null)
        thisNode.parent!!.left = thisNode

        val otherNode = Node(1, 1, null)
        otherNode.parent = Node(3, 3, null)
        otherNode.parent!!.left = otherNode

        assertNotEquals(thisNode, otherNode)

    }

    @DisplayName("Equality with same left children")
    @Test
    fun testEqualityWithSameLeftChildren() {

        val thisNode = Node(1, 1, null)
        thisNode.left = Node(2, 2, null)
        thisNode.left!!.parent = thisNode

        val otherNode = Node(1, 1, null)
        otherNode.left = Node(2, 2, null)
        otherNode.left!!.parent = thisNode

        assertEquals(thisNode, otherNode)

    }

    @DisplayName("Equality with different left children")
    @Test
    fun testEqualityWithDifferentLeftChildren() {

        val thisNode = Node(1, 1, null)
        thisNode.left = Node(2, 2, null)
        thisNode.left!!.parent = thisNode

        val otherNode = Node(1, 1, null)
        otherNode.left = Node(3, 3, null)
        otherNode.left!!.parent = thisNode

        assertNotEquals(thisNode, otherNode)

    }

    @DisplayName("Equality with same right children")
    @Test
    fun testEqualityWithSameRightChildren() {

        val thisNode = Node(1, 1, null)
        thisNode.right = Node(2, 2, null)
        thisNode.right!!.parent = thisNode

        val otherNode = Node(1, 1, null)
        otherNode.right = Node(2, 2, null)
        otherNode.right!!.parent = thisNode

        assertEquals(thisNode, otherNode)

    }

    @DisplayName("Equality with different right children")
    @Test
    fun testEqualityWithDifferentRightChildren() {

        val thisNode = Node(1, 1, null)
        thisNode.right = Node(2, 2, null)
        thisNode.right!!.parent = thisNode

        val otherNode = Node(1, 1, null)
        otherNode.right = Node(3, 3, null)
        otherNode.right!!.parent = thisNode

        assertNotEquals(thisNode, otherNode)

    }

    @DisplayName("Simple left rotate")
    @Test
    fun testSimpleLeftRotate() {

        val thisNode = Node(0, 0)

        thisNode.rotateLeft()

        assertEquals(0, thisNode.key )

    }

    @DisplayName("Simple right rotate")
    @Test
    fun testSimpleRightRotate() {

        val thisNode = Node(0, 0)

        thisNode.rotateRight()

        assertEquals(0, thisNode.key)

    }

    @DisplayName("Pair left rotate")
    @Test
    fun testPairLeftRotate() {

        val thisNode = Node(0, 0)
        thisNode.right = Node(1, 1)
        thisNode.right!!.parent = thisNode

        thisNode.rotateLeft()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)

    }

    @DisplayName("Pair right rotate")
    @Test
    fun testPairRightRotate() {

        val thisNode = Node(0, 0)
        thisNode.left = Node(1, 1)
        thisNode.left!!.parent = thisNode

        thisNode.rotateRight()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)

    }

    @DisplayName("Pair&parent left rotate")
    @Test
    fun testPairAndParentLeftRotate() {

        val thisNode = Node(0, 0)
        thisNode.right = Node(1, 1)
        thisNode.right!!.parent = thisNode
        thisNode.parent = Node(2, 2)
        thisNode.parent!!.left = thisNode

        thisNode.rotateLeft()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.parent!!.parent!!.key)

    }

    @DisplayName("Pair&parent right rotate")
    @Test
    fun testPairAndParentRightRotate() {

        val thisNode = Node(0, 0)
        thisNode.left = Node(1, 1)
        thisNode.left!!.parent = thisNode
        thisNode.parent = Node(2, 2)
        thisNode.parent!!.left = thisNode

        thisNode.rotateRight()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.parent!!.parent!!.key)

    }

    @DisplayName("Pair left rotate Case 1")
    @Test
    fun testPairLeftRotateCase1() {

        val thisNode = Node(0, 0)
        thisNode.right = Node(1, 1)
        thisNode.right!!.parent = thisNode
        thisNode.left = Node(2, 2)
        thisNode.left!!.parent = thisNode

        thisNode.rotateLeft()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.left!!.key)

    }

    @DisplayName("Pair right rotate Case 1")
    @Test
    fun testPairRightRotateCase1() {

        val thisNode = Node(0, 0)
        thisNode.left = Node(1, 1)
        thisNode.left!!.parent = thisNode
        thisNode.left!!.left = Node(2, 2)
        thisNode.left!!.left!!.parent = thisNode.left

        thisNode.rotateRight()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.parent!!.left!!.key)

    }

    @DisplayName("Pair left rotate Case 2")
    @Test
    fun testPairLeftRotateCase2() {

        val thisNode = Node(0, 0)
        thisNode.right = Node(1, 1)
        thisNode.right!!.parent = thisNode
        thisNode.right!!.left = Node(2, 2)
        thisNode.right!!.left!!.parent = thisNode.right

        thisNode.rotateLeft()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.right!!.key)

    }

    @DisplayName("Pair right rotate Case 2")
    @Test
    fun testPairRightRotateCase2() {

        val thisNode = Node(0, 0)
        thisNode.left = Node(1, 1)
        thisNode.left!!.parent = thisNode
        thisNode.left!!.right = Node(2, 2)
        thisNode.left!!.right!!.parent = thisNode.left

        thisNode.rotateRight()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.left!!.key)

    }

    @DisplayName("Pair left rotate Case 3")
    @Test
    fun testPairLeftRotateCase3() {

        val thisNode = Node(0, 0)
        thisNode.right = Node(1, 1)
        thisNode.right!!.parent = thisNode
        thisNode.right!!.right = Node(2, 2)
        thisNode.right!!.right!!.parent = thisNode.right

        thisNode.rotateLeft()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.parent!!.right!!.key)

    }

    @DisplayName("Pair right rotate Case 3")
    @Test
    fun testPairRightRotateCase3() {

        val thisNode = Node(0, 0)
        thisNode.left = Node(1, 1)
        thisNode.left!!.parent = thisNode
        thisNode.right = Node(2, 2)
        thisNode.right!!.parent = thisNode

        thisNode.rotateRight()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.right!!.key)

    }

}