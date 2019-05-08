package RedBlackTree

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests for Red Black Tree Node")
internal class TestRedBlackTreeNode {

    @DisplayName("Comparison with null")
    @Test
    fun testComparisonWithNull() {

        val node = RedBlackTreeNode(0, 0)

        assertNotEquals(null, node)

    }

    @DisplayName("Self comparison")
    @Test
    fun testSelfComparison() {

        val node = RedBlackTreeNode(0, 0)

        assertEquals(node, node)

    }

    @DisplayName("Self hashCode")
    @Test
    fun testSelfHashCode() {

        val node = RedBlackTreeNode(0, 0)

        assertEquals(node.hashCode(), node.hashCode())

    }

    @DisplayName("Different hashCode")
    @Test
    fun testDifferentHashCode() {

        val node = RedBlackTreeNode(0, 0)

        val other = RedBlackTreeNode(1, 1)

        assertNotEquals(node, other)

    }

    @DisplayName("Simple equality")
    @Test
    fun testSimpleEquals() {

        val thisNode = RedBlackTreeNode(1, 1, null)
        val otherNode = RedBlackTreeNode(1, 1, null)

        assertEquals(thisNode, otherNode)

    }

    @DisplayName("Equality with different parents")
    @Test
    fun testEqualityWithDifferentParents() {

        val thisNode = RedBlackTreeNode(1, 1, null)
        thisNode.parent = RedBlackTreeNode(2, 2, null)
        thisNode.parent!!.leftChild = thisNode

        val otherNode = RedBlackTreeNode(1, 1, null)
        otherNode.parent = RedBlackTreeNode(3, 3, null)
        otherNode.parent!!.leftChild = otherNode

        assertNotEquals(thisNode, otherNode)

    }

    @DisplayName("Equality with same parents")
    @Test
    fun testEqualityWithSameParents() {

        val thisNode = RedBlackTreeNode(1, 1)
        thisNode.parent = RedBlackTreeNode(3, 3)
        thisNode.parent!!.leftChild = thisNode

        val otherNode = RedBlackTreeNode(1, 1)
        otherNode.parent = RedBlackTreeNode(3, 3)
        otherNode.parent!!.leftChild = otherNode

        assertEquals(thisNode, otherNode)

    }

    @DisplayName("Equality with same left children")
    @Test
    fun testEqualityWithSameLeftChildren() {

        val thisNode = RedBlackTreeNode(1, 1)
        thisNode.leftChild = RedBlackTreeNode(2, 2)
        thisNode.leftChild!!.parent = thisNode

        val otherNode = RedBlackTreeNode(1, 1)
        otherNode.leftChild = RedBlackTreeNode(2, 2)
        otherNode.leftChild!!.parent = thisNode

        assertEquals(thisNode, otherNode)

    }

    @DisplayName("Equality with different left children")
    @Test
    fun testEqualityWithDifferentLeftChildren() {

        val thisNode = RedBlackTreeNode(1, 1)
        thisNode.leftChild = RedBlackTreeNode(2, 2)
        thisNode.leftChild!!.parent = thisNode

        val otherNode = RedBlackTreeNode(1, 1)
        otherNode.leftChild = RedBlackTreeNode(3, 3)
        otherNode.leftChild!!.parent = thisNode

        assertNotEquals(thisNode, otherNode)

    }
}