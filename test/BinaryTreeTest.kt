import binaryTree.BSTNode
import binaryTree.BinaryTree
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests for Binary Search Tree")
internal class TestBinarySearchTree {

    private val Tree = BinaryTree<Int, Int>()

    @DisplayName("SearchExistingKey")
    @Test
    fun testSearchingKey() {

        val nodesToInsert: MutableList<Int> = MutableList(100) { it + 1 }

        nodesToInsert.shuffle()

        for (x in nodesToInsert) {
            Tree.insert(x, x)
        }

        for (x in nodesToInsert) {
            assertEquals(Tree.find(x), Pair(x, x))
        }

    }

    @DisplayName("Searching nonexistent key")
    @Test
    fun failSearchingKeyTest() {

        val nodesToInsert: MutableList<Int> = MutableList(100) { it + 1 }

        nodesToInsert.shuffle()

        for (x in nodesToInsert) {
            Tree.insert(x, x)
        }

        assertEquals(null, Tree.find(1000))

    }

    @DisplayName("Searching in empty tree")
    @Test
    fun searchingEmptyTree(){

        assertNull(Tree.find(0))
    }

    @DisplayName("Insert with the same keys")
    @Test
    fun doubleInsertKeyTest(){

        Tree.insert(1, 5)
        Tree.insert(1, 10)

        assertEquals(Tree.root!!.key, 1)
        assertEquals(Tree.root!!.value, 2)
        assertTrue(Tree.root!!.parent == null)
        assertTrue(Tree.root!!.left == null)
        assertTrue(Tree.root!!.right == null)
    }


    @DisplayName("Test for iterator using BFS algorithm")
    @Test
    fun testBFSIterator() {
        val expected: MutableList<Int> = mutableListOf(7, 3, 11, 2, 5, 9, 13, 1, 4, 6, 10, 12, 14, 15)
        val actual: MutableList<Int> = mutableListOf()

        for (x in expected) {
            Tree.insert(x, x)
        }

        for (x in Tree)
            actual.add(x.value)
        assertEquals(expected, actual)
    }

    @DisplayName("CommonTestIterator")
    @Test
    fun testIterator() {
        val nodesToInsert = MutableList(5) { it + 1 }

        nodesToInsert.shuffle()

        for (x in nodesToInsert) {
            Tree.insert(x, x)
        }

        for (x in Tree) {
            assertEquals(Tree.find(x.key), Pair(x.key, x.value))
        }

    }

    @DisplayName("Equality with different children")
    @Test
    fun testEqualityWithDifferentLeftChildren() {

        val cur = BSTNode(1, 1, null)
        cur.left = BSTNode(2, 2, null)
        cur.left!!.parent = cur

        val otherNode = BSTNode(1, 1, null)
        otherNode.left = BSTNode(3, 3, null)
        otherNode.left!!.parent = cur

        assertNotEquals(cur, otherNode)

    }


    @DisplayName("EmptyIteratorTest")
    @Test
    fun emptyIterator() {

        for (x in Tree) {
            assertEquals(null, x.value)
        }

    }

    @DisplayName("Property of right key test")
    @Test
    fun propRightKey() {
        val nodesToInsert = MutableList(100) { it + 1 }

        nodesToInsert.shuffle()

        for (x in nodesToInsert) {
            Tree.insert(x, x)
        }
        var cur: BSTNode<Int, Int> = Tree.root ?: throw Exception("Insert error")
        var actual = true

        while (cur.right != null) {
            actual = cur.value < cur.right!!.value
            if (!actual) return
            cur = cur.right!!
        }

        assertEquals(true, actual)
    }

    @DisplayName("Property of left key test")
    @Test
    fun propLeftKey() {
        val nodesToInsert = MutableList(100) { it + 1 }

        nodesToInsert.shuffle()

        for (x in nodesToInsert) {
            Tree.insert(x, x)
        }
        var cur: BSTNode<Int, Int> = Tree.root ?: throw Exception("Insert error")
        var actual = true

        while (cur.left != null) {
            actual = cur.value > cur.left!!.value
            if (!actual) return
            cur = cur.left!!
        }

        assertTrue(actual)

    }
}

