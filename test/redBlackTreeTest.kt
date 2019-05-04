import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import redBlackTree.RBNode
import redBlackTree.RedBlackTree

@DisplayName("Tests for Red Black Tree BSTNode")
internal class TestRedBlackTreeBSTABLRBNode {

    private val Tree = RedBlackTree<Int, Int>()

    //BASE TEST, COPIES FROM BSTNode
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
        var cur: RBNode<Int, Int> = Tree.root ?: throw Exception("Insert error")
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
        var cur: RBNode<Int, Int> = Tree.root ?: throw Exception("Insert error")
        var actual = true

        while (cur.left != null) {
            actual = cur.value > cur.left!!.value
            if (!actual) return
            cur = cur.left!!
        }

        assertEquals(true, actual)

    }
    //New test for RBTree
    @DisplayName("Recoloring Test")
    @Test
    fun recolorTest(){
        val list = listOf(7, 4, 9, 1)
        val expectTree = RedBlackTree<Int, Int>()
        expectTree.root = RBNode(7, 7, true)
        expectTree.root?.left = RBNode(4, 4, true, expectTree.root)
        expectTree.root?.right = RBNode(9,  9, true, expectTree.root)
        expectTree.root?.left?.left = RBNode(1, 1, false, expectTree.root?.left)

        for (x in list){
            Tree.insert(x, x)
        }

        val actual = expectTree.root == Tree.root
                && expectTree.root?.right == Tree.root?.right
                && expectTree.root?.left == Tree.root?.left
                && expectTree.root?.left?.left == Tree.root?.left?.left

        assertEquals(true, actual)
    }

    @DisplayName("Left Right Case Check Test")
    @Test
    fun lrCaseTest(){
        val list = listOf(7, 1, 4)
        val expectTree = RedBlackTree<Int, Int>()
        expectTree.root = RBNode(4, 4, true)
        expectTree.root?.left = RBNode(1, 1, false, expectTree.root)
        expectTree.root?.right = RBNode(7, 7, false, expectTree.root)

        for (x in list){
            Tree.insert(x, x)
        }

        val actual = expectTree.root == Tree.root
                && expectTree.root?.right == Tree.root?.right
                && expectTree.root?.left == Tree.root?.left

        assertEquals(true, actual)
    }

    @DisplayName("Left Left Case Check Test")
    @Test
    fun llCaseTest() {
        val list = listOf(7, 4, 1)
        val expectTree = RedBlackTree<Int, Int>()
        expectTree.root = RBNode(4, 4, true)
        expectTree.root?.left = RBNode(1, 1, false, expectTree.root)
        expectTree.root?.right = RBNode(7, 7, false, expectTree.root)

        for (x in list){
            Tree.insert(x, x)
        }

        val actual = expectTree.root == Tree.root
                && expectTree.root?.right == Tree.root?.right
                && expectTree.root?.left == Tree.root?.left

        assertEquals(true, actual)
    }

    @DisplayName("Right Left Case Test")
    @Test
    fun rlCaseTest(){
        val list = listOf(7, 11, 9)
        val expectTree = RedBlackTree<Int, Int>()
        expectTree.root = RBNode(9, 9, true)
        expectTree.root?.left = RBNode(7, 7, false, expectTree.root)
        expectTree.root?.right = RBNode(11, 11, false, expectTree.root)

        for (x in list){
            Tree.insert(x, x)
        }

        val actual = expectTree.root == Tree.root
                && expectTree.root?.right == Tree.root?.right
                && expectTree.root?.left == Tree.root?.left

        assertEquals(true, actual)
    }

    @DisplayName("Right Right Test")
    @Test
    fun rrCaseTest(){
        val list = listOf(7, 9, 11)
        val expectTree = RedBlackTree<Int, Int>()
        expectTree.root = RBNode(9, 9, true)
        expectTree.root?.left = RBNode(7, 7, false, expectTree.root)
        expectTree.root?.right = RBNode(11, 11, false, expectTree.root)

        for (x in list){
            Tree.insert(x, x)
        }

        val actual = expectTree.root == Tree.root
                && expectTree.root?.right == Tree.root?.right
                && expectTree.root?.left == Tree.root?.left

        assertEquals(true, actual)
    }

}