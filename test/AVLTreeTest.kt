import AVLTree.AVLTree
import AVLTree.AVLNode
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

@DisplayName("Tests for AVL Tree")
internal class TestAVLTree {
    private val Tree = AVLTree<Int, Int>()

    private fun isAVLTree(node: AVLNode<Int, Int>? = Tree.root): Boolean{
        if (node == null) {
            return true
        }
        return if (node.getBalance() in -1..1){
            val leftChild = isAVLTree(node.left)
            val rightChild = isAVLTree(node.right)
            leftChild && rightChild
        }else false

    }

    @DisplayName("Stress test case 1")
    @Test
    fun stressTest1(){

        val nodeToInsert = List(10000){it}

        for (x in nodeToInsert){
            Tree.insert(x, x)
        }

        assertTrue(isAVLTree(Tree.root))

    }

    @DisplayName("Stress test case 2")
    @Test
    fun stressTest2(){

        val nodeToInsert = List(10000){10000 - it}

        for (x in nodeToInsert){
            Tree.insert(x, x)
        }

        assertTrue(isAVLTree(Tree.root))

    }

    @DisplayName("Stress test case 3")
    @Test
    fun stressTest3(){

        val nodeToInsert = MutableList(10000){it}

        nodeToInsert.shuffle()

        for (x in nodeToInsert){
            Tree.insert(x, x)
        }

        assertTrue(isAVLTree(Tree.root))

    }

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

    @DisplayName("Searching in empty tree")
    @Test
    fun searchingEmptyTree(){

        Assertions.assertNull(Tree.find(0))
    }

    @DisplayName("Insert with the same keys")
    @Test
    fun doubleInsertKeyTest(){

        Tree.insert(1, 5)
        Tree.insert(1, 10)

        assertEquals(Tree.root!!.key, 1)
        assertEquals(Tree.root!!.value, 2)
        Assertions.assertTrue(Tree.root!!.parent == null)
        Assertions.assertTrue(Tree.root!!.left == null)
        Assertions.assertTrue(Tree.root!!.right == null)
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

    @DisplayName("Test for iterator using BFS algorithm")
    @Test
    fun testBFSIterator() {
        val nodesToInsert: MutableList<Int> = mutableListOf(7, 3, 11, 2, 5, 9, 13, 1, 4, 6, 10, 12, 14, 15)
        val res: MutableList<Int> = mutableListOf()

        for (x in nodesToInsert) {
            Tree.insert(x, x)
        }

        for (x in Tree)
            res.add(x.value)
        assertEquals(nodesToInsert, res)
    }

    @DisplayName("CommonTestIterator")
    @Test
    fun testIterator() {
        val expect = MutableList(5) { it + 1 }

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
        val nodesToInsert = MutableList(100) { it + 1 }

        nodesToInsert.shuffle()

        for (x in nodesToInsert) {
            Tree.insert(x, x)
        }
        var cur: AVLNode<Int, Int> = Tree.root ?: throw Exception("Insert error")
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
        var cur: AVLNode<Int, Int> = Tree.root ?: throw Exception("Insert error")
        var actual = true

        while (cur.left != null) {
            actual = cur.value > cur.left!!.value
            if (!actual) return
            cur = cur.left!!
        }

        assertEquals(true, actual)

    }


    @DisplayName("Left Right Case Check Test")
    @Test
    fun lrCaseTest(){
        val list = listOf(7, 1, 4)
        val expectTree = AVLTree<Int, Int>()
        expectTree.root = AVLNode(4, 4)
        expectTree.root?.left = AVLNode(1, 1, expectTree.root)
        expectTree.root?.right = AVLNode(7, 7, expectTree.root)
        val expRoot = expectTree.root
        expRoot?.height = 2

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
        val expectTree = AVLTree<Int, Int>()
        expectTree.root = AVLNode(4, 4)
        expectTree.root?.left = AVLNode(1, 1,  expectTree.root)
        expectTree.root?.right = AVLNode(7, 7, expectTree.root)
        val expRoot = expectTree.root
        expRoot?.height = 2

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
        val nodesToInsert = listOf(7, 11, 9)
        val expectTree = AVLTree<Int, Int>()
        expectTree.root = AVLNode(9, 9)
        expectTree.root?.left = AVLNode(7, 7,  expectTree.root)
        expectTree.root?.right = AVLNode(11, 11,  expectTree.root)
        val expRoot = expectTree.root
        expRoot?.height = 2

        for (x in nodesToInsert){
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
        val expectTree = AVLTree<Int, Int>()
        expectTree.root = AVLNode(9, 9)
        expectTree.root?.left = AVLNode(7, 7, expectTree.root)
        expectTree.root?.right = AVLNode(11, 11, expectTree.root)
        val expRoot = expectTree.root
        expRoot?.height = 2

        for (x in list){
            Tree.insert(x, x)
        }

        val actual = expectTree.root == Tree.root
                && expectTree.root?.right == Tree.root?.right
                && expectTree.root?.left == Tree.root?.left

        assertEquals(true, actual)
    }

}