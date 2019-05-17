package BinarySearchTree

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests for Binary Search Tree")
internal class TestBinarySearchTree {

    private val Tree = BinarySearchTree<Int, Int>()

    private fun checkStructure(node: Node<Int, Int>? = Tree.root): Boolean {

        if (node == null) return true

        val checkLeft = checkStructure(node.left)
        val checkRight = checkStructure(node.right)

        return if (node.left == null && node.right == null) {
            true
        } else if (node.left == null || node.right == null) {
            if (node.left == null) {
                (node.key < node.right!!.key) && checkRight
            }
            else {
                (node.key > node.left!!.key) && checkLeft
            }
        }
        else {
            (node.left!!.key < node.key && node.key < node.right!!.key) &&
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

            Tree.root?.left?.parent = null
            Tree.root?.right?.parent = null
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
        Tree.insert(1, 2)

        assertEquals(Tree.root!!.key, 1)
        assertEquals(Tree.root!!.value, 2)
        assertTrue(Tree.root!!.parent == null)
        assertTrue(Tree.root!!.left == null)
        assertTrue(Tree.root!!.right == null)

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

            Tree.root?.left?.parent = null
            Tree.root?.right?.parent = null
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

            Tree.root?.left?.parent = null
            Tree.root?.right?.parent = null
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

            Tree.root?.left?.parent = null
            Tree.root?.right?.parent = null
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

            Tree.root?.left?.parent = null
            Tree.root?.right?.parent = null
            Tree.root = null

        }

    }

    @DisplayName("Delete root check")
    @Test
    fun testDeleteRoot() {

        Tree.insert(1, 1)

        Tree.delete(1)

        assertNull(Tree.root)

    }

    @DisplayName("Delete case 1")
    @Test
    fun testDeleteCase1() {

        Tree.insert(1, 1)
        Tree.insert(2, 2)

        Tree.delete(1)

        assertNotNull(Tree.find(2))
        assertNull(Tree.find(1))

    }

    @DisplayName("Delete case 2")
    @Test
    fun testDeleteCase2() {

        Tree.insert(2, 2)
        Tree.insert(1, 1)

        Tree.delete(2)

        assertNull(Tree.find(2))
        assertNotNull(Tree.find(1))

    }

    @DisplayName("Delete case 3")
    @Test
    fun testDeleteCase3() {

        Tree.insert(1, 1)
        Tree.insert(2, 2)
        Tree.insert(3, 3)

        Tree.delete(2)

        assertNotNull(Tree.find(3))
        assertNull(Tree.find(2))
        assertNotNull(Tree.find(1))

    }

    @DisplayName("Delete case 4")
    @Test
    fun testDeleteCase4() {

        Tree.insert(3, 3)
        Tree.insert(2, 2)
        Tree.insert(1, 1)

        Tree.delete(2)

        assertNotNull(Tree.find(3))
        assertNull(Tree.find(2))
        assertNotNull(Tree.find(1))

    }


    @DisplayName("Delete case 5")
    @Test
    fun testDeleteCase5() {

        Tree.insert(2, 2)
        Tree.insert(3, 3)
        Tree.insert(1, 1)

        Tree.delete(2)

        assertNotNull(Tree.find(3))
        assertNull(Tree.find(2))
        assertNotNull(Tree.find(1))

    }

    @DisplayName("Delete case 6")
    @Test
    fun testDeleteCase6() {

        Tree.insert(1, 1)
        Tree.insert(2, 2)

        Tree.delete(2)

        assertNull(Tree.find(2))
        assertNotNull(Tree.find(1))

    }

    @DisplayName("Delete case 7")
    @Test
    fun testDeleteCase7() {

        Tree.insert(2, 2)
        Tree.insert(1, 1)

        Tree.delete(1)

        assertNull(Tree.find(1))
        assertNotNull(Tree.find(2))

    }

    @DisplayName("Delete case 8")
    @Test
    fun testDeleteCase8() {

        Tree.insert(1, 1)
        Tree.insert(4, 4)
        Tree.insert(3, 3)
        Tree.insert(5, 5)

        Tree.delete(4)

        assertNull(Tree.find(4))
        assertNotNull(Tree.find(1))
        assertNotNull(Tree.find(3))
        assertNotNull(Tree.find(5))

    }

    @DisplayName("Delete check")
    @Test
    fun testDelete() {

        for (testInputLength in 1..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }

            testInput.shuffle()

            for (data in testInput) {
                Tree.insert(data, data)
            }

            val m = testInputLength / 2

            for (i in 0 until m) {
                Tree.delete(testInput[i])
            }

            for (i in 0 until m) {
                assertNull(Tree.find(testInput[i]))
            }

            for (i in m until testInputLength) {
                assertNotNull(Tree.find(testInput[i]))
            }

            Tree.root?.left?.parent = null
            Tree.root?.right?.parent = null
            Tree.root = null

        }

    }

    @DisplayName("Delete save structure")
    @Test
    fun testDeleteSaveStructure() {

        for (testInputLength in 1..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }

            testInput.shuffle()

            for (data in testInput) {
                Tree.insert(data, data)
            }

            val m = testInputLength / 2

            for (i in 0 until m) {
                Tree.delete(testInput[i])
                assertTrue(checkStructure())
            }

            Tree.root?.left?.parent = null
            Tree.root?.right?.parent = null
            Tree.root = null

        }

    }

    @DisplayName("Delete save structure, direct order")
    @Test
    fun testDeleteSaveStructureDirectOrder() {

        for (testInputLength in 1..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }

            for (data in testInput) {
                Tree.insert(data, data)
            }

            val m = testInputLength / 2

            for (i in 0 until m) {
                Tree.delete(testInput[i])
                assertTrue(checkStructure())
            }

            Tree.root?.left?.parent = null
            Tree.root?.right?.parent = null
            Tree.root = null

        }

    }

    @DisplayName("Delete save structure, reverse order")
    @Test
    fun testDeleteSaveStructureReverseOrder() {

        for (testInputLength in 1..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { testInputLength - it }

            for (data in testInput) {
                Tree.insert(data, data)
            }

            val m = testInputLength / 2

            for (i in 0 until m) {
                Tree.delete(testInput[i])
                assertTrue(checkStructure())
            }

            Tree.root?.left?.parent = null
            Tree.root?.right?.parent = null
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

            Tree.root?.left?.parent = null
            Tree.root?.right?.parent = null
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

            Tree.root?.left?.parent = null
            Tree.root?.right?.parent = null
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

            Tree.root?.left?.parent = null
            Tree.root?.right?.parent = null
            Tree.root = null

        }

    }

}


