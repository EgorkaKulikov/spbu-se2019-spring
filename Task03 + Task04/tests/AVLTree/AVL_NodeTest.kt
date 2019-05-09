package tests.AVLTree

import AVLTree.Node
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests for AVL Tree Node")
internal class TestAVLTreeNode {

    @DisplayName("Comparison with null")
    @Test
    fun node_compare_null_false_asserted() {

        val node = Node(0, 0)

        assertNotEquals(null, node )

    }

    @DisplayName("Self comparison")
    @Test
    fun node_compare_node_true_asserted() {

        val node = Node(0, 0)

        assertEquals(node, node)

    }

    @DisplayName("Self hashCode")
    @Test
    fun node_hash_compare_self_true_asserted() {

        val node = Node(0, 0)

        assertEquals(node.hashCode(), node.hashCode())

    }

    @DisplayName("Different hashCode")
    @Test
    fun node_hash_compare_another_false_asserted() {

        val node = Node(0, 0)

        val other = Node(1, 1)

        assertNotEquals(node, other)

    }

    @DisplayName("Simple equality")
    @Test
    fun same_nodes_comparsion_true_asserted() {

        val thisNode = Node(1, 1, null)
        val otherNode = Node(1, 1, null)

        assertEquals(thisNode, otherNode)

    }

    @DisplayName("Equality with same parents")
    @Test
    fun same_nodes_with_same_parents_comparsion_true_asserted() {

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
    fun same_nodes_with_different_parents_comparsion_false_asserted() {

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
    fun same_nodes_with_same_child_comparsion_true_asserted() {

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
    fun same_nodes_with_diff_parents_comparsion_false_asserted() {

        val thisNode = Node(1, 1, null)
        thisNode.leftChild = Node(2, 2, null)
        thisNode.leftChild!!.parent = thisNode

        val otherNode = Node(1, 1, null)
        otherNode.leftChild = Node(3, 3, null)
        otherNode.leftChild!!.parent = thisNode

        assertNotEquals(thisNode, otherNode)

    }

    @DisplayName("Simple left rotate")
    @Test
    fun left_rotate_success_asserted() {

        val thisNode = Node(0, 0)

        thisNode.rotateLeft()

        assertEquals(0, thisNode.key )

    }

    @DisplayName("Simple right rotate")
    @Test
    fun right_rotate_success_asserted() {

        val thisNode = Node(0, 0)

        thisNode.rotateRight()

        assertEquals(0, thisNode.key)

    }

    @DisplayName("Pair left rotate")
    @Test
    fun pair_left_rotate_success_asserted() {

        val thisNode = Node(0, 0)
        thisNode.rightChild = Node(1, 1)
        thisNode.rightChild!!.parent = thisNode

        thisNode.rotateLeft()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)

    }

    @DisplayName("Pair right rotate")
    @Test
    fun pair_right_rotate_success_asserted() {

        val thisNode = Node(0, 0)
        thisNode.leftChild = Node(1, 1)
        thisNode.leftChild!!.parent = thisNode

        thisNode.rotateRight()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)

    }

    @DisplayName("Pair + parent left rotate")
    @Test
    fun pair_and_parent_left_rotate_success_asserted() {

        val thisNode = Node(0, 0)
        thisNode.rightChild = Node(1, 1)
        thisNode.rightChild!!.parent = thisNode
        thisNode.parent = Node(2, 2)
        thisNode.parent!!.leftChild = thisNode

        thisNode.rotateLeft()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.parent!!.parent!!.key)

    }

    @DisplayName("Pair&parent right rotate")
    @Test
    fun pair_and_parent_right_rotate_success_asserted() {

        val thisNode = Node(0, 0)
        thisNode.leftChild = Node(1, 1)
        thisNode.leftChild!!.parent = thisNode
        thisNode.parent = Node(2, 2)
        thisNode.parent!!.leftChild = thisNode

        thisNode.rotateRight()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.parent!!.parent!!.key)

    }

    @DisplayName("Pair left rotate Case 1")
    @Test
    fun pair_left_rotate_success_asserted_1() {

        val thisNode = Node(0, 0)
        thisNode.rightChild = Node(1, 1)
        thisNode.rightChild!!.parent = thisNode
        thisNode.leftChild = Node(2, 2)
        thisNode.leftChild!!.parent = thisNode

        thisNode.rotateLeft()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.leftChild!!.key)

    }

    @DisplayName("Pair right rotate Case 1")
    @Test
    fun pair_right_rotate_success_asserted_1() {

        val thisNode = Node(0, 0)
        thisNode.leftChild = Node(1, 1)
        thisNode.leftChild!!.parent = thisNode
        thisNode.leftChild!!.leftChild = Node(2, 2)
        thisNode.leftChild!!.leftChild!!.parent = thisNode.leftChild

        thisNode.rotateRight()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.parent!!.leftChild!!.key)

    }

    @DisplayName("Pair left rotate Case 2")
    @Test
    fun pair_left_rotate_success_asserted_2() {

        val thisNode = Node(0, 0)
        thisNode.rightChild = Node(1, 1)
        thisNode.rightChild!!.parent = thisNode
        thisNode.rightChild!!.leftChild = Node(2, 2)
        thisNode.rightChild!!.leftChild!!.parent = thisNode.rightChild

        thisNode.rotateLeft()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.rightChild!!.key)

    }

    @DisplayName("Pair right rotate Case 2")
    @Test
    fun pair_right_rotate_success_asserted_2() {

        val thisNode = Node(0, 0)
        thisNode.leftChild = Node(1, 1)
        thisNode.leftChild!!.parent = thisNode
        thisNode.leftChild!!.rightChild = Node(2, 2)
        thisNode.leftChild!!.rightChild!!.parent = thisNode.leftChild

        thisNode.rotateRight()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.leftChild!!.key)

    }

    @DisplayName("Pair left rotate Case 3")
    @Test
    fun pair_left_rotate_success_asserted_3() {

        val thisNode = Node(0, 0)
        thisNode.rightChild = Node(1, 1)
        thisNode.rightChild!!.parent = thisNode
        thisNode.rightChild!!.rightChild = Node(2, 2)
        thisNode.rightChild!!.rightChild!!.parent = thisNode.rightChild

        thisNode.rotateLeft()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.parent!!.rightChild!!.key)

    }

    @DisplayName("Pair right rotate Case 3")
    @Test
    fun pair_right_rotate_success_asserted_3() {

        val thisNode = Node(0, 0)
        thisNode.leftChild = Node(1, 1)
        thisNode.leftChild!!.parent = thisNode
        thisNode.rightChild = Node(2, 2)
        thisNode.rightChild!!.parent = thisNode

        thisNode.rotateRight()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.rightChild!!.key)

    }

}