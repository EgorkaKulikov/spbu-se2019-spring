package BinarySearchTree

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests for Binary Search Tree Node")
internal class TestBinarySearchTreeNode {

    @DisplayName("Comparison with null")
    @Test
    fun testComparisonWithNull() {

        val node = Node(0, 0)

        assertNotEquals(node, null)

    }

    @DisplayName("Self comparison")
    @Test
    fun testSelfComparison() {

        val node = Node(0, 0)

        assertEquals(node, node)

    }

    @DisplayName("Self hashCode")
    @Test
    fun testSelfHashCode() {

        val node = Node(0, 0)

        assertEquals(node.hashCode(), node.hashCode())

    }

    @DisplayName("Different hashCode")
    @Test
    fun testDifferentHashCode() {

        val node = Node(0, 0)

        val other = Node(1, 1)

        assertNotEquals(node, other)

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

}