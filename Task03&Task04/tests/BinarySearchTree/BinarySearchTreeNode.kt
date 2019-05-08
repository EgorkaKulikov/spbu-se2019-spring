package BinarySearchTree

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests for Binary Search Tree Node")
internal class TestBinarySearchTreeNode {

    @DisplayName("Comparison with null")
    @Test
    fun testComparisonWithNull() {

        val node = BinarySearchTreeNode(0, 0)

        assertNotEquals(node, null)

    }

    @DisplayName("Self comparison")
    @Test
    fun testSelfComparison() {

        val node = BinarySearchTreeNode(0, 0)

        assertEquals(node, node)

    }

    @DisplayName("Self hashCode")
    @Test
    fun testSelfHashCode() {

        val node = BinarySearchTreeNode(0, 0)

        assertEquals(node.hashCode(), node.hashCode())

    }

    @DisplayName("Different hashCode")
    @Test
    fun testDifferentHashCode() {

        val node = BinarySearchTreeNode(0, 0)

        val other = BinarySearchTreeNode(1, 1)

        assertNotEquals(node, other)

    }

    @DisplayName("Simple equality")
    @Test
    fun testSimpleEquals() {

        val thisNode = BinarySearchTreeNode(1, 1)
        val otherNode = BinarySearchTreeNode(1, 1)

        assertEquals(thisNode, otherNode)

    }

}