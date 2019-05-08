package RedBlackTree

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests for Red Black Tree")
internal class TestRedBlackTree {

    private val Tree = RedBlackTree<Int, Int>()

    private var maxBlackHeight: Int = -1

    private fun checkStructure(node: RedBlackTreeNode<Int, Int>? = Tree.root, blackHeight: Int = 1): Boolean {

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

        if (node.color == Color.RED) {
            return if (node.leftChild?.color == Color.RED || node.rightChild?.color == Color.RED) {
                false
            } else {
                checkStructure(node.leftChild, blackHeight + 1) &&
                        checkStructure(node.rightChild, blackHeight + 1)
            }
        } else {
            val checkLeft: Boolean = if (node.leftChild?.color == Color.BLACK) {
                checkStructure(node.leftChild, blackHeight + 1)
            } else {
                checkStructure(node.leftChild, blackHeight)
            }
            val checkRight: Boolean = if (node.rightChild?.color == Color.BLACK) {
                checkStructure(node.rightChild, blackHeight + 1)
            } else {
                checkStructure(node.rightChild, blackHeight)
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

        println("${Tree.find(3)}")

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

        for (testInputLength in 0..10) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }

            testInput.shuffle()

            for (data in testInput) {
                Tree.insert(data, data)
                maxBlackHeight = -1
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
                maxBlackHeight = -1
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
                maxBlackHeight = -1
                assertTrue(checkStructure())
            }

            Tree.root?.leftChild?.parent = null
            Tree.root?.rightChild?.parent = null
            Tree.root = null

        }

    }

    @DisplayName("Simple left rotate")
    @Test
    fun testSimpleLeftRotate() {

        val thisNode = RedBlackTreeNode(0, 0)

        thisNode.leftRotate()

        assertEquals(0, thisNode.key )

    }

    @DisplayName("Simple right rotate")
    @Test
    fun testSimpleRightRotate() {

        val thisNode = RedBlackTreeNode(0, 0)

        thisNode.rightRotate()

        assertEquals(0, thisNode.key)

    }

    @DisplayName("Pair left rotate")
    @Test
    fun testPairLeftRotate() {

        val thisNode = RedBlackTreeNode(0, 0)
        thisNode.rightChild = RedBlackTreeNode(1, 1)
        thisNode.rightChild!!.parent = thisNode

        thisNode.leftRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)

    }

    @DisplayName("Pair right rotate")
    @Test
    fun testPairRightRotate() {

        val thisNode = RedBlackTreeNode(0, 0)
        thisNode.leftChild = RedBlackTreeNode(1, 1)
        thisNode.leftChild!!.parent = thisNode

        thisNode.rightRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)

    }

    @DisplayName("Pair&parent left rotate")
    @Test
    fun testPairAndParentLeftRotate() {

        val thisNode = RedBlackTreeNode(0, 0)
        thisNode.rightChild = RedBlackTreeNode(1, 1)
        thisNode.rightChild!!.parent = thisNode
        thisNode.parent = RedBlackTreeNode(2, 2)
        thisNode.parent!!.leftChild = thisNode

        thisNode.leftRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.parent!!.parent!!.key)

    }

    @DisplayName("Pair&parent right rotate")
    @Test
    fun testPairAndParentRightRotate() {

        val thisNode = RedBlackTreeNode(0, 0)
        thisNode.leftChild = RedBlackTreeNode(1, 1)
        thisNode.leftChild!!.parent = thisNode
        thisNode.parent = RedBlackTreeNode(2, 2)
        thisNode.parent!!.leftChild = thisNode

        thisNode.rightRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.parent!!.parent!!.key)

    }

    @DisplayName("Pair left rotate Case 1")
    @Test
    fun testPairLeftRotateCase1() {

        val thisNode = RedBlackTreeNode(0, 0)
        thisNode.rightChild = RedBlackTreeNode(1, 1)
        thisNode.rightChild!!.parent = thisNode
        thisNode.leftChild = RedBlackTreeNode(2, 2)
        thisNode.leftChild!!.parent = thisNode

        thisNode.leftRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.leftChild!!.key)

    }

    @DisplayName("Pair right rotate Case 1")
    @Test
    fun testPairRightRotateCase1() {

        val thisNode = RedBlackTreeNode(0, 0)
        thisNode.leftChild = RedBlackTreeNode(1, 1)
        thisNode.leftChild!!.parent = thisNode
        thisNode.leftChild!!.leftChild = RedBlackTreeNode(2, 2)
        thisNode.leftChild!!.leftChild!!.parent = thisNode.leftChild

        thisNode.rightRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.parent!!.leftChild!!.key)

    }

    @DisplayName("Pair left rotate Case 2")
    @Test
    fun testPairLeftRotateCase2() {

        val thisNode = RedBlackTreeNode(0, 0)
        thisNode.rightChild = RedBlackTreeNode(1, 1)
        thisNode.rightChild!!.parent = thisNode
        thisNode.rightChild!!.leftChild = RedBlackTreeNode(2, 2)
        thisNode.rightChild!!.leftChild!!.parent = thisNode.rightChild

        thisNode.leftRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.rightChild!!.key)

    }

    @DisplayName("Pair right rotate Case 2")
    @Test
    fun testPairRightRotateCase2() {

        val thisNode = RedBlackTreeNode(0, 0)
        thisNode.leftChild = RedBlackTreeNode(1, 1)
        thisNode.leftChild!!.parent = thisNode
        thisNode.leftChild!!.rightChild = RedBlackTreeNode(2, 2)
        thisNode.leftChild!!.rightChild!!.parent = thisNode.leftChild

        thisNode.rightRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.leftChild!!.key)

    }

    @DisplayName("Pair left rotate Case 3")
    @Test
    fun testPairLeftRotateCase3() {

        val thisNode = RedBlackTreeNode(0, 0)
        thisNode.rightChild = RedBlackTreeNode(1, 1)
        thisNode.rightChild!!.parent = thisNode
        thisNode.rightChild!!.rightChild = RedBlackTreeNode(2, 2)
        thisNode.rightChild!!.rightChild!!.parent = thisNode.rightChild

        thisNode.leftRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.parent!!.rightChild!!.key)

    }

    @DisplayName("Pair right rotate Case 3")
    @Test
    fun testPairRightRotateCase3() {

        val thisNode = RedBlackTreeNode(0, 0)
        thisNode.leftChild = RedBlackTreeNode(1, 1)
        thisNode.leftChild!!.parent = thisNode
        thisNode.rightChild = RedBlackTreeNode(2, 2)
        thisNode.rightChild!!.parent = thisNode

        thisNode.rightRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)
        assertEquals(2, thisNode.rightChild!!.key)

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


