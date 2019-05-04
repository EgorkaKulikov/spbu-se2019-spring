import binaryTree.BSTNode
import binaryTree.BinaryTree
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("Tests for Binary Search Tree")
internal class TestBinarySearchTree {

    private val Tree = BinaryTree<Int, Int>()

    @DisplayName("SearchExistingKey")
    @Test
    fun testSearchingKey() {

        val a: MutableList<Int> = MutableList(100) { it + 1 }

        a.shuffle()

        for (x in a) {
            Tree.insert(x, x)
        }

        for (x in a) {
            assertEquals(Tree.find(x), Pair(x, x))
        }

    }

    @DisplayName("Searching nonexistent key")
    @Test
    fun failSearchingKeyTest() {

        val a: MutableList<Int> = MutableList(100) { it + 1 }

        a.shuffle()

        for (x in a) {
            Tree.insert(x, x)
        }

        assertEquals(null, Tree.find(1000))

    }

    @DisplayName("Test for iterator using BFS algorithm")
    @Test
    fun testBFSIterator() {
        val testList: MutableList<Int> = mutableListOf(7, 3, 11, 2, 5, 9, 13, 1, 4, 6, 10, 12, 14, 15)
        val res: MutableList<Int> = mutableListOf()

        for (x in testList) {
            Tree.insert(x, x)
        }

        for (x in Tree)
            res.add(x.value)
        assertEquals(testList, res)
    }

    @DisplayName("CommonTestIterator")
    @Test
    fun testIterator() {
        val expect = MutableList(5) { it + 1 }
        val res = mutableListOf<Int>()

        expect.shuffle()

        for (x in expect) {
            Tree.insert(x, x)
        }

        for (x in Tree) {
            assertEquals(Tree.find(x.key), Pair(x.key, x.value))
        }

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
        val a = MutableList(100) { it + 1 }

        a.shuffle()

        for (x in a) {
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
        val a = MutableList(100) { it + 1 }

        a.shuffle()

        for (x in a) {
            Tree.insert(x, x)
        }
        var cur: BSTNode<Int, Int> = Tree.root ?: throw Exception("Insert error")
        var actual = true

        while (cur.left != null) {
            actual = cur.value > cur.left!!.value
            if (!actual) return
            cur = cur.left!!
        }

        assertEquals(true, actual)

    }
}

