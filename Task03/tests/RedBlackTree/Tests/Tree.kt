package RedBlackTree

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests for Red Black Tree")
internal class TestRedBlackTree {

    private val Tree = RedBlackTree<Int, Int>()

    private var maxBlackHeight: Int = -1

    private fun checkStructure(node: Node<Int, Int>? = Tree.root, blackHeight: Int = 1): Boolean {

        if (node == null) return true

        if (node.isLeaf()) {
            if (maxBlackHeight == -1) {
                maxBlackHeight = blackHeight
            }
            if (maxBlackHeight != blackHeight) {
                return false
            }
            return true
        }

        if (!node.isBlack) {
            return if (node.left?.isBlack == false || node.right?.isBlack == false) {
                false
            } else {
                checkStructure(node.left, blackHeight + 1) &&
                        checkStructure(node.right, blackHeight + 1)
            }
        } else {
            val checkLeft: Boolean = if (node.left?.isBlack == true) {
                checkStructure(node.left, blackHeight + 1)
            } else {
                checkStructure(node.left, blackHeight)
            }
            val checkRight: Boolean = if (node.right?.isBlack == true) {
                checkStructure(node.right, blackHeight + 1)
            } else {
                checkStructure(node.right, blackHeight)
            }
            return checkLeft && checkRight
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

    @DisplayName("Insert check")
    @Test
    fun testInsert() {

        for (testInputLength in 0..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }

            testInput.shuffle()

            for (data in testInput) {
                Tree.insert(data, data)
            }

            for (data in testInput) {
                assertEquals(Pair(data, data), Tree.find(data))
            }

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
                maxBlackHeight = -1
                assertTrue(checkStructure())
            }

        }

    }

    @DisplayName("Insert save structure Direct order")
    @Test
    fun testInsertSaveStructureDirectOrder() {

        for (testInputLength in 0..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }

            for (data in testInput) {
                Tree.insert(data, data)
                maxBlackHeight = -1
                assertTrue(checkStructure())
            }

        }

    }

    @DisplayName("Insert save structure Reverse order")
    @Test
    fun testInsertSaveStructureReverseOrder() {

        for (testInputLength in 0..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { testInputLength - it }

            for (x in testInput) {
                Tree.insert(x, x)
                maxBlackHeight = -1
                assertTrue(checkStructure())
            }

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
                maxBlackHeight = -1
                assertTrue(checkStructure())
            }

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
                maxBlackHeight = -1
                assertTrue(checkStructure())
            }

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
                maxBlackHeight = -1
                assertTrue(checkStructure())
            }

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
                assertEquals(i, Pair(cur, cur))
            }
        }

    }

}


