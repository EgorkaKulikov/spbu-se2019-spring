package tests.binarySearchTree

import binarySearchTree.Node
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests for Binary Search Tree Node")
internal class TestBinarySearchTreeNode {

    @DisplayName("Comparison with null")
    @Test
    fun node_compare_null_false_asserted()
    {

        val node = Node(5, 5)

        assertNotEquals(node, null)

    }

    @DisplayName("Self comparison")
    @Test
    fun node_compare_node_true_asserted()
    {

        val node = Node(0, 0)

        assertEquals(node, node)

    }

    @DisplayName("Self hashCode")
    @Test
    fun node_hash_compare_self_true_asserted()
    {

        val node = Node(0, 0)

        assertEquals(node.hashCode(), node.hashCode())

    }

    @DisplayName("Different hashCode")
    @Test
    fun node_hash_compare_another_false_asserted()
    {

        val node = Node(0, 0)

        val other = Node(1, 1)

        assertNotEquals(node, other)

    }

    @DisplayName("Simple equality")
    @Test
    fun same_nodes_comparsion_true_asserted()
    {

        val thisNode = Node(1, 1, null)
        val otherNode = Node(1, 1, null)

        assertEquals(thisNode, otherNode)

    }

    @DisplayName("Equality with same parents")
    @Test
    fun same_nodes_with_same_parents_comparsion_true_asserted()
    {

        val thisNode = Node(1, 1, null)
        thisNode.parent = Node(3, 3, null)
        thisNode.parent!!.leftChild = thisNode

        val otherNode = Node(1, 1, null)
        otherNode.parent = Node(3, 3, null)
        otherNode.parent!!.leftChild = otherNode

        assertEquals(thisNode, otherNode)

    }

    @DisplayName("Equality with different parents")
    @Test
    fun same_nodes_with_different_parents_comparsion_false_asserted()
    {

        val thisNode = Node(1, 1, null)
        thisNode.parent = Node(2, 2, null)
        thisNode.parent!!.leftChild = thisNode

        val otherNode = Node(1, 1, null)
        otherNode.parent = Node(3, 3, null)
        otherNode.parent!!.leftChild = otherNode

        assertNotEquals(thisNode, otherNode)

    }

    @DisplayName("Equality with same children")
    @Test
    fun same_nodes_with_same_child_comparsion_true_asserted()
    {

        val thisNode = Node(1, 1, null)
        thisNode.leftChild = Node(2, 2, null)
        thisNode.leftChild!!.parent = thisNode

        val otherNode = Node(1, 1, null)
        otherNode.leftChild = Node(2, 2, null)
        otherNode.leftChild!!.parent = thisNode

        assertEquals(thisNode, otherNode)

    }

    @DisplayName("Equality with different children")
    @Test
    fun same_nodes_with_diff_parents_comparsion_false_asserted()
    {

        val thisNode = Node(1, 1, null)
        thisNode.leftChild = Node(2, 2, null)
        thisNode.leftChild!!.parent = thisNode

        val otherNode = Node(1, 1, null)
        otherNode.leftChild = Node(3, 3, null)
        otherNode.leftChild!!.parent = thisNode

        assertNotEquals(thisNode, otherNode)

    }
}