import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.omg.CORBA.INTERNAL
import redBlackTree.Color
import redBlackTree.RBNode
import redBlackTree.RedBlackTree

@DisplayName("Tests for Red Black Tree BSTNode")
internal class TestRedBlackTreeBSTABLRBNode {

    private val Tree = RedBlackTree<Int, Int>()

    //BASE TEST, COPIES FROM BSTNode
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
        val expected = MutableList(5) { it + 1 }

        expected.shuffle()

        for (x in expected) {
            Tree.insert(x, x)
        }

        for (x in Tree) {
            assertEquals(Tree.find(x.key), Pair(x.key, x.value))
        }

    }

    @DisplayName("Equality with different children")
    @Test
    fun testEqualityWithDifferentLeftChildren() {

        val cur = RBNode(1, 1, Color.Black)
        cur.left = RBNode(2, 2, Color.Red)
        cur.left!!.parent = cur

        val otherNode = RBNode(1, 1, Color.Black)
        otherNode.left = RBNode(3, 3, Color.Red)
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
        var cur: RBNode<Int, Int> = Tree.root ?: throw Exception("Insert error")
        var actual = true

        while (cur.right != null) {
            actual = cur.value < cur.right!!.value
            if (!actual) return
            cur = cur.right!!
        }

        assertTrue(actual)
    }

    @DisplayName("Property of left key test")
    @Test
    fun propLeftKey() {
        val nodesToInsert = MutableList(100) { it + 1 }

        nodesToInsert.shuffle()

        for (x in nodesToInsert) {
            Tree.insert(x, x)
        }
        var cur: RBNode<Int, Int> = Tree.root ?: throw Exception("Insert error")
        var actual = true

        while (cur.left != null) {
            actual = cur.value > cur.left!!.value
            if (!actual) return
            cur = cur.left!!
        }

        assertTrue(actual)

    }
    //New test for RBTree
    @DisplayName("Recoloring Test")
    @Test
    fun recolorTest(){
        val nodesToInsert = listOf(7, 4, 9, 1)
        val expectTree = RedBlackTree<Int, Int>()
        expectTree.root = RBNode(7, 7, Color.Black)
        expectTree.root?.left = RBNode(4, 4, Color.Black, expectTree.root)
        expectTree.root?.right = RBNode(9,  9, Color.Black, expectTree.root)
        expectTree.root?.left?.left = RBNode(1, 1, Color.Red, expectTree.root?.left)

        for (x in nodesToInsert){
            Tree.insert(x, x)
        }

        val actual = expectTree.root == Tree.root
                && expectTree.root?.right == Tree.root?.right
                && expectTree.root?.left == Tree.root?.left
                && expectTree.root?.left?.left == Tree.root?.left?.left

        assertTrue(actual)
    }

    @DisplayName("Pair left rotate")
    @Test
    fun testPairLeftRotate() {

        val thisNode = RBNode(0, 0)
        thisNode.right = RBNode(1, 1)
        thisNode.right!!.parent = thisNode

        thisNode.leftRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)

    }

    @DisplayName("Pair right rotate")
    @Test
    fun testPairRightRotate() {

        val thisNode = RBNode(0, 0)
        thisNode.left = RBNode(1, 1)
        thisNode.left!!.parent = thisNode

        thisNode.rightRotate()

        assertEquals(0, thisNode.key)
        assertEquals(1, thisNode.parent!!.key)

    }

    @DisplayName("Left Right Case Check Test")
    @Test
    fun lrCaseTest(){
        val nodesToInsert = listOf(7, 1, 4)
        val expectTree = RedBlackTree<Int, Int>()
        expectTree.root = RBNode(4, 4, Color.Black)
        expectTree.root?.left = RBNode(1, 1, Color.Red, expectTree.root)
        expectTree.root?.right = RBNode(7, 7, Color.Red, expectTree.root)

        for (x in nodesToInsert){
            Tree.insert(x, x)
        }

        val actual = expectTree.root == Tree.root
                && expectTree.root?.right == Tree.root?.right
                && expectTree.root?.left == Tree.root?.left

        assertTrue(actual)
    }

    @DisplayName("Left Left Case Check Test")
    @Test
    fun llCaseTest() {
        val nodesToInsert = listOf(7, 4, 1)
        val expectTree = RedBlackTree<Int, Int>()
        expectTree.root = RBNode(4, 4, Color.Black)
        expectTree.root?.left = RBNode(1, 1, Color.Red, expectTree.root)
        expectTree.root?.right = RBNode(7, 7, Color.Red, expectTree.root)

        for (x in nodesToInsert){
            Tree.insert(x, x)
        }

        val actual = expectTree.root == Tree.root
                && expectTree.root?.right == Tree.root?.right
                && expectTree.root?.left == Tree.root?.left

        assertTrue(actual)
    }

    @DisplayName("Right Left Case Test")
    @Test
    fun rlCaseTest(){
        val nodesToInsert = listOf(7, 11, 9)
        val expectTree = RedBlackTree<Int, Int>()
        expectTree.root = RBNode(9, 9, Color.Black)
        expectTree.root?.left = RBNode(7, 7, Color.Red, expectTree.root)
        expectTree.root?.right = RBNode(11, 11, Color.Red, expectTree.root)

        for (x in nodesToInsert){
            Tree.insert(x, x)
        }

        val actual = expectTree.root == Tree.root
                && expectTree.root?.right == Tree.root?.right
                && expectTree.root?.left == Tree.root?.left

        assertTrue(actual)
    }

    @DisplayName("Right Right Test")
    @Test
    fun rrCaseTest(){
        val nodesToInsert = listOf(7, 9, 11)
        val expectTree = RedBlackTree<Int, Int>()
        expectTree.root = RBNode(9, 9, Color.Black)
        expectTree.root?.left = RBNode(7, 7, Color.Red, expectTree.root)
        expectTree.root?.right = RBNode(11, 11, Color.Red, expectTree.root)

        for (x in nodesToInsert){
            Tree.insert(x, x)
        }

        val actual = expectTree.root == Tree.root
                && expectTree.root?.right == Tree.root?.right
                && expectTree.root?.left == Tree.root?.left

        assertTrue(actual)
    }

    @DisplayName("Check black color after red color node")
    @Test
    fun checkBlackColor(){

        val nodeToInsert = MutableList(100){it}

        nodeToInsert.shuffle()

        for (x in nodeToInsert){
            Tree.insert(x, x)
        }
        var actual = true

        for (x in Tree){
            if (x.color == Color.Red && (x.left?.color == Color.Red || x.right?.color == Color.Red)){
                actual = false
                break
            }
        }

        assertTrue(actual)
    }

    @DisplayName("Check root color")
    @Test
    fun checkRootColor(){

        val nodeToInsert = MutableList(100){it}

        nodeToInsert.shuffle()

        for (x in nodeToInsert){
            Tree.insert(x, x)
        }
        val actual = Tree.root?.color != Color.Red

        assertTrue(actual)
    }

}