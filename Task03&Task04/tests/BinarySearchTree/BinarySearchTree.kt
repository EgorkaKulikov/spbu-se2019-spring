package BinarySearchTree

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests for Binary Search Tree")
internal class TestBinarySearchTree {

    private val Tree = BinarySearchTree<Int, Int>()

    private fun checkStructure(node: BinarySearchTreeNode<Int, Int>? = Tree.root): Boolean {

        if (node == null) return true

        val checkLeft = checkStructure(node.leftChild)
        val checkRight = checkStructure(node.rightChild)

        return if (node.leftChild == null && node.rightChild== null) {
            true
        } else if (node.leftChild == null || node.rightChild == null) {
            if (node.leftChild == null) {
                (node.key < node.rightChild!!.key) && checkRight
            }
            else {
                (node.key > node.leftChild!!.key) && checkLeft
            }
        }
        else {
            (node.leftChild!!.key < node.key && node.key < node.rightChild!!.key) &&
                    checkLeft && checkRight
        }

    }

    @DisplayName("Search existing key")
    @Test
    fun testSearchExistingKey() {

        for (testInputLength in 0..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }

            testInput.shuffle()

            for (data in testInput) {
                Tree.insert(data, data)
            }

            for (data in testInput) {
                assertEquals(Pair(data, data), Tree.find(data))
            }

            Tree.root?.leftChild?.parent = null
            Tree.root?.rightChild?.parent = null
            Tree.root = null

        }

    }

    @DisplayName("Search in root")
    @Test
    fun testSearchInRoot() {

        Tree.insert(1, 1)

        assertEquals(Pair(1, 1), Tree.find(1))

    }

    @DisplayName("Search case 1")
    @Test
    fun testSearchCase1() {

        Tree.insert(1, 1)
        Tree.insert(2, 2)

        assertEquals(Pair(2, 2), Tree.find(2))
        assertEquals(Pair(1, 1), Tree.find(1))

    }

    @DisplayName("Search case 2")
    @Test
    fun testSearchCase2() {

        Tree.insert(2, 2)
        Tree.insert(1, 1)

        assertEquals(Pair(2, 2), Tree.find(2))
        assertEquals(Pair(1, 1), Tree.find(1))

    }

    @DisplayName("Search case 3")
    @Test
    fun testSearchCase3() {

        Tree.insert(1, 1)
        Tree.insert(2, 2)
        Tree.insert(3, 3)

        assertEquals(Pair(3, 3), Tree.find(3))
        assertEquals(Pair(2, 2), Tree.find(2))
        assertEquals(Pair(1, 1), Tree.find(1))

    }

    @DisplayName("Search case 4")
    @Test
    fun testSearchCase4() {

        Tree.insert(3, 3)
        Tree.insert(2, 2)
        Tree.insert(1, 1)

        assertEquals(Pair(3, 3), Tree.find(3))
        assertEquals(Pair(2, 2), Tree.find(2))
        assertEquals(Pair(1, 1), Tree.find(1))

    }


    @DisplayName("Search case 5")
    @Test
    fun testSearchCase5() {

        Tree.insert(2, 2)
        Tree.insert(3, 3)
        Tree.insert(1, 1)

        assertEquals(Pair(3, 3), Tree.find(3))
        assertEquals(Pair(2, 2), Tree.find(2))
        assertEquals(Pair(1, 1), Tree.find(1))

    }

    @DisplayName("Search nonexistent key in empty tree")
    @Test
    fun testSearchNonExistingKeyInEmptyTree() {

        assertNull(Tree.find(0))
        assertNull(Tree.find(101))

    }

    @DisplayName("Search non existing key in nonempty tree")
    @Test
    fun testSearchNonExistingKeyInNonEmptyTree() {

        val testInput: MutableList<Int> = MutableList(100) { it + 1 }

        testInput.shuffle()

        for (data in testInput) {
            Tree.insert(data, data)
        }

        assertNull(Tree.find(0))
        assertNull(Tree.find(101))

    }

    @DisplayName("Double insert check")
    @Test
    fun testDoubleInsert() {

        Tree.insert(1, 1)
        Tree.insert(1, 1)

        assertEquals(Tree.root!!.key, 1)
        assertTrue(Tree.root!!.parent == null)
        assertTrue(Tree.root!!.leftChild == null)
        assertTrue(Tree.root!!.rightChild == null)

    }

    @DisplayName("Insert check")
    @Test
    fun testInsert() {

        for (testInputLength in 0..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }

            testInput.shuffle()

            for (data in testInput) {
                Tree.insert(data, data)
            }

            for (x in testInput) {
                assertEquals(Tree.find(x), Pair(x, x))
            }

            Tree.root?.leftChild?.parent = null
            Tree.root?.rightChild?.parent = null
            Tree.root = null

        }

    }

    @DisplayName("Insert save structure")
    @Test
    fun testInsertSaveStructure() {

        for (testInputLength in 0..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }

            testInput.shuffle()

            for (data in testInput) {
                Tree.insert(data, data)
                assertTrue(checkStructure())
            }

            Tree.root?.leftChild?.parent = null
            Tree.root?.rightChild?.parent = null
            Tree.root = null

        }

    }

    @DisplayName("Insert save structure Direct order")
    @Test
    fun testInsertSaveStructureDirectOrder() {

        for (testInputLength in 0..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }

            for (data in testInput) {
                Tree.insert(data, data)
                assertTrue(checkStructure())
            }

            Tree.root?.leftChild?.parent = null
            Tree.root?.rightChild?.parent = null
            Tree.root = null

        }

    }

    @DisplayName("Insert save structure Reverse order")
    @Test
    fun testInsertSaveStructureReverseOrder() {

        for (testInputLength in 0..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { testInputLength - it }

            for (data in testInput) {
                Tree.insert(data, data)
                assertTrue(checkStructure())
            }

            Tree.root?.leftChild?.parent = null
            Tree.root?.rightChild?.parent = null
            Tree.root = null

        }

    }


    @DisplayName("Iterate empty tree")
    @Test
    fun testIterateEmptyTree() {

        for (i in Tree)
            assertEquals(-1, 1)

    }

    @DisplayName("Iterate normal tree")
    @Test
    fun testIterateNormal() {

        for (testInputLength in 1..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }

            testInput.shuffle()

            for (data in testInput) {
                Tree.insert(data, data)
            }

            var cur = 0

            for (i in Tree) {
                ++cur
                assertEquals(Pair(cur, cur), i)
            }

            Tree.root?.leftChild?.parent = null
            Tree.root?.rightChild?.parent = null
            Tree.root = null

        }

    }

    @DisplayName("Iterate tree in direct order")
    @Test
    fun testIterateDirectOrder() {

        for (testInputLength in 1..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }

            for (data in testInput) {
                Tree.insert(data, data)
            }

            var cur = 0

            for (i in Tree) {
                ++cur
                assertEquals(Pair(cur, cur), i)
            }

            Tree.root?.leftChild?.parent = null
            Tree.root?.rightChild?.parent = null
            Tree.root = null

        }

    }

    @DisplayName("Iterate tree in reverse order")
    @Test
    fun testIterateReverseOrder() {

        for (testInputLength in 1..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { testInputLength - it }

            for (data in testInput) {
                Tree.insert(data, data)
            }

            var cur = 0

            for (i in Tree) {
                ++cur
                assertEquals(Pair(cur, cur), i)
            }

            Tree.root?.leftChild?.parent = null
            Tree.root?.rightChild?.parent = null
            Tree.root = null

        }

    }

}


