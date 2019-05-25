// import org.junit.jupiter.api.Assertions.*
// import org.junit.jupiter.api.DisplayName
// import org.junit.jupiter.api.Test
// import java.lang.Integer.max
// import AVLTree.AVLNode
// import AVLTree.AVLTree
// import binaryTree.BSTNode
// import binaryTree.BinaryTree
// import redBlackTree.RBNode
// import redBlackTree.RedBlackTree
//
// class TestBank{
//
// private var AVLTree = AVLTree<Int, Int>()
//
// @DisplayName("AVL: Unexisting call")
// @Test
// fun `avl find(null)`(){
// for (num in 1..1000) AVLTree.insert(num, num)
// for (num in 1001..2000)
// assertNull(AVLTree.find(num))
// }
//
// @DisplayName("AVL: Testing find function")
// @Test
// fun avlTestingFind(){
// val list = MutableList(1000) {it}
// list.shuffle()
// for (num in list)
// AVLTree.insert(num, num)
// for (num in list)
// assertEquals(AVLTree.find(num), Pair(num, num))
// }
//
// @DisplayName("AVL: Testing iterator")
// @Test
// fun avlIteratorTest(){
// for (num in 1..1000)
// AVLTree.insert(num, num)
// for (node in AVLTree)
// assertEquals(Pair(node.key, node.value), AVLTree.find(node.key))
// }
//
// @DisplayName("AVL: Left keys condition")
// @Test
// fun avlLeftCondition(){
// val list = MutableList(1000) {it}
// list.shuffle()
// for (num in list)
// AVLTree.insert(num, num)
// for (node in AVLTree)
// assert(node.left?.key ?: continue < node.key)
// }
//
// @DisplayName("AVL: Right keys condition")
// @Test
// fun avlRightCondition(){
// val list = MutableList(1000) {it}
// list.shuffle()
// for (num in list)
// AVLTree.insert(num, num)
// for (node in AVLTree)
// assert(node.right?.key ?: continue >= node.key)
// }
//
// @DisplayName("AVL: Overlap of iterator")
// @Test
// fun avlIteratorOverlap(){
// val list = MutableList(1000) {it}
// list.shuffle()
// for (num in list)
// AVLTree.insert(num, num)
// var amount = 0
// for (node in AVLTree)
// amount++
// assertEquals(list.size, amount)
// }
//
// @DisplayName("AVL: empty AVLTree iteration")
// @Test
// fun avlIterateNull(){
// for (node in AVLTree)
// assertEquals(false, true)
// }
//
// // these one were simply copy-pasted from BST_tests due to our architecture
//
// @DisplayName("AVL: left rotate test")
// @Test
// fun `avl test left rotate`(){
// AVLTree.insert(10, 10)
// AVLTree.insert(20, 20)
// AVLTree.insert(30, 30)
// assert(AVLTree.root!!.key == 20)
// }
//
// @DisplayName(" AVL: Right rotate test")
// @Test
// fun `avl test right rotate`(){
// AVLTree.insert(10, 10)
// AVLTree.insert(20, 20)
// AVLTree.insert(30, 30)
// assert(AVLTree.root!!.key == 20)
// }
//
// @DisplayName("AVL: Insert case 1: left-left")
// @Test
// fun `avl test left-left insertion`(){
// AVLTree.insert(50, 50) //root
// AVLTree.insert(25, 25) //left child
// AVLTree.insert(10, 10) // left-left child
// //here comes balancing
// val root = AVLTree.root!!
// assertEquals(root.key, 25)
// assertEquals(root.right!!.key, 50)
// assertEquals(root.left!!.key, 10)
// assertEquals(root.height, 2)
// assertEquals(root.left!!.height, 1)
// assertEquals(root.right!!.height, 1)
// }
//
// @DisplayName("AVL: Insert case 2: left-right")
// @Test
// fun `avl test left-right insertion`(){
// AVLTree.insert(50, 50) //root
// AVLTree.insert(25, 25) //left child
// AVLTree.insert(30, 30) // left-right child
// //here comes balancing
// val root = AVLTree.root!!
// assertEquals(root.key, 30)
// assertEquals(root.right!!.key, 50)
// assertEquals(root.left!!.key, 25)
// assertEquals(root.height, 2)
// assertEquals(root.right!!.height, 1)
// assertEquals(root.left!!.height, 1)
// }
//
// @DisplayName("AVL: Insert case 3: right-right")
// @Test
// fun `avl test right-right insertion`(){
// AVLTree.insert(50, 50) //root
// AVLTree.insert(75, 75) //right child
// AVLTree.insert(80, 80) // right-right child
// //here comes balancing
// val root = AVLTree.root!!
// assertEquals(root.key, 75)
// assertEquals(root.right!!.key, 80)
// assertEquals(root.left!!.key, 50)
// assertEquals(root.height, 2)
// assertEquals(root.left!!.height, 1)
// assertEquals(root.right!!.height, 1)
// }
//
// @DisplayName("AVL: Insert case 4: right-left")
// @Test
// fun `avl test right-left insertion`(){
// AVLTree.insert(50, 50) //root
// AVLTree.insert(75, 75) //right child
// AVLTree.insert(60, 60) // right-right child
// //here comes balancing
// val root = AVLTree.root!!
// assertEquals(root.key, 60)
// assertEquals(root.right!!.key, 75)
// assertEquals(root.left!!.key, 50)
// assertEquals(root.height, 2)
// assertEquals(root.right!!.height, 1)
// assertEquals(root.left!!.height, 1)
// }
//
// @DisplayName("Delta height condition")
// @Test
// fun `check delta height`(){
// for (num in 1..1000)
// AVLTree.insert(num, num)
// for (node in AVLTree)
// assert(node.getBalance() < 2)
// }
//
// @DisplayName("Correct heights in nodes")
// @Test
// fun `check heights correctivity`(){
// for (num in 1..1000)
// AVLTree.insert(num, num)
// for (node in AVLTree)
// assertEquals(node.height, max(node.left?.height ?: 0, node.right?.height ?: 0) + 1)
// }
//
// //next
//
// private var RBTree = RedBlackTree<Int, Int>()
//
// @DisplayName("RB: Unexisting call")
// @Test
// fun `rb find(null)`(){
// for (num in 1..1000) RBTree.insert(num, num)
// for (num in 1001..2000)
// assertNull(RBTree.find(num))
// }
//
// @DisplayName("RB: Testing find function + root == black")
// @Test
// fun rbTestingFind(){
// val list = MutableList(3000) {it}
// list.shuffle()
// for (num in list)
// RBTree.insert(num, num)
// for (num in list)
// assertEquals(RBTree.find(num), Pair(num, num))
// assert(RBTree.root!!.isBlack)
// }
//
// @DisplayName("RB: Testing iterator")
// @Test
// fun rbIteratorTest(){
// for (num in 1..1000)
// RBTree.insert(num, num)
// for (node in RBTree)
// assertEquals(Pair(node.key, node.value), RBTree.find(node.key))
// }
//
// @DisplayName("RB: Left keys condition")
// @Test
// fun rbLeftCondition(){
// val list = MutableList(1000) {it}
// list.shuffle()
// for (num in list)
// RBTree.insert(num, num)
// for (node in RBTree)
// assert(node.left?.key ?: continue < node.key)
// }
//
// @DisplayName("RB: Right keys condition")
// @Test
// fun rbRightCondition(){
// val list = MutableList(1000) {it}
// list.shuffle()
// for (num in list)
// RBTree.insert(num, num)
// for (node in RBTree)
// assert(node.right?.key ?: continue >= node.key)
// }
//
// @DisplayName("RB: Overlap of iterator")
// @Test
// fun rbIteratorOverlap(){
// val list = MutableList(1000) {it}
// list.shuffle()
// for (num in list)
// RBTree.insert(num, num)
// var amount = 0
// for (node in RBTree)
// amount++
// assertEquals(amount, list.size)
// }
//
// @DisplayName("RB: Empty RBTree iteration")
// @Test
// fun rbIterateNull(){
// for (node in RBTree)
// assertEquals(false, true)
// }
//
// // these one were copy-pasted from BST_tests due to our architecture
//
// @DisplayName("RB: Left rotate test")
// @Test
// fun `test left rotate`(){
// RBTree.insert(10, 10)
// RBTree.insert(20, 20)
// RBTree.insert(30, 30)
// assert(RBTree.root!!.key == 20)
// }
//
// @DisplayName("RB: Right rotate test")
// @Test
// fun `test right rotate`(){
// RBTree.insert(30, 30)
// RBTree.insert(20, 20)
// RBTree.insert(10, 10)
// assert(RBTree.root!!.key == 20)
// }
//
// @DisplayName("RB: Insert case 1: left-left")
// @Test
// fun `test left-left insertion`(){
// RBTree.insert(50, 50)  //root
// RBTree.insert(75, 75)  //right child
// RBTree.insert(25, 25)  //left child
// RBTree.root!!.right!!.isBlack = true
// RBTree.insert(10, 10) //here come right rotate + colors swap
// val root = RBTree.root
// assert(root!!.isBlack) //basically black root
// assert(!root.right!!.isBlack)
// assert(root.left?.key ?: false == 10)
// assert(root.right?.right?.key ?: false == 75)
// }
//
// @DisplayName("RB: Insert case 2: left-right")
// @Test
// fun `test left-right insertion`(){
// RBTree.insert(50, 50)  //root
// RBTree.insert(75, 75)  //right child
// RBTree.insert(25, 25)  //left child
// RBTree.root!!.right!!.isBlack = true
// RBTree.insert(40, 40) //here come left rotate then right rotate + swap
// val root = RBTree.root
// assert(root!!.isBlack) //basically black root
// assert(root.key == 40)
// assert(!root.right!!.isBlack)
// assert(root.left?.key ?: false == 25)
// assert(root.right?.right?.key ?: false == 75)
// }
//
// @DisplayName("RB: Insert case 3: right-right")
// @Test
// fun `test right-right insertion`(){
// RBTree.insert(50, 50)  //root
// RBTree.insert(75, 75)  //right child
// RBTree.insert(25, 25)  //left child
// RBTree.root!!.left!!.isBlack = true
// RBTree.insert(100, 100) //here come left rotate + colors swap
// val root = RBTree.root
// assert(root!!.isBlack) //basically black root
// assert(!root.left!!.isBlack)
// assert(root.right?.key ?: false == 100)
// assert(root.left?.left?.key ?: false == 25)
// }
//
// @DisplayName("RB: Insert case 4: right-left")
// @Test
// fun `test right-left insertion`(){
// RBTree.insert(50, 50)  //root
// RBTree.insert(75, 75)  //right child
// RBTree.insert(25, 25)  //left child
// RBTree.root!!.left!!.isBlack = true
// RBTree.insert(60, 60) //here come right rotate then left rotate + swap
// val root = RBTree.root
// assert(root!!.isBlack) //basically black root
// assert(root.key == 60)
// assert(!root.left!!.isBlack)
// assert(root.right?.key ?: false == 75)
// assert(root.left?.left?.key ?: false == 25)
// }
//
// @DisplayName("RB: Insertion case 5: dad+ uncle are red")
// @Test
// fun `test swap colors`(){
// RBTree.insert(50, 50)  //root
// RBTree.insert(75, 75)  //right child
// RBTree.insert(25, 25)  //left child
// RBTree.insert(10, 10)
// val root = RBTree.root
// assert(root!!.isBlack) //basically always black
// assert(root.left?.left?.key ?: false == 10)
// assert(!root.left!!.left!!.isBlack)
// assert(root.left!!.isBlack) //dad's black
// assert(root.right!!.isBlack) //uncle's black
// }
//
// @DisplayName("RB: Testing color condition")
// @Test
// fun `test colors`(){
// for (num in 1..1000)
// RBTree.insert(num, num)
// for (node in RBTree)
// assertFalse(!node.isBlack && !(node.left?.isBlack ?: true || node.right?.isBlack ?: false))
// }
//
// //next
//
// private var BSTree = BinaryTree<Int, Int>()
//
// @DisplayName("Unexisting call")
// @Test
// fun `find(null)`(){
// for (num in 1..1000) BSTree.insert(num, num)
// for (num in 1001..2000)
// assertNull(BSTree.find(num))
// }
//
// @DisplayName("Testing find function")
// @Test
// fun testingFind(){
// val list = MutableList(3000) {it+1}
// list.shuffle()
// for (num in list)
// BSTree.insert(num, num)
// for (num in list)
// assertEquals(BSTree.find(num), Pair(num, num))
// }
//
// @DisplayName("Testing iterator in BST")
// @Test
// fun iteratorTest(){
// for (num in 1..1000)
// BSTree.insert(num, num)
// for (node in BSTree)
// assertEquals(Pair(node.key, node.value), BSTree.find(node.key))
// }
//
// @DisplayName("Left keys condition")
// @Test
// fun leftCondition(){
// val list = MutableList(1000) {it}
// list.shuffle()
// for (num in list)
// BSTree.insert(num, num)
// for (node in BSTree)
// assert(node.left?.key ?: continue < node.key)
// }
//
// @DisplayName("Right keys condition")
// @Test
// fun rightCondition(){
// val list = MutableList(1000) {it}
// list.shuffle()
// for (num in list)
// BSTree.insert(num, num)
// for (node in BSTree)
// assert(node.right?.key ?: continue >= node.key)
// }
//
// @DisplayName("Overlap of iterator")
// @Test
// fun iteratorOverlap(){
// val list = MutableList(1000) {it}
// list.shuffle()
// for (num in list)
// BSTree.insert(num, num)
// var amount = 0
// for (node in BSTree)
// amount++
// assertEquals(amount, list.size)
// }
//
// @DisplayName("Empty BSTree iteration")
// @Test
// fun iterateNull(){
// for (node in BSTree)
// assertEquals(false, true)
// }
//
// @DisplayName("Tests for Red Black rBTree")
// internal class TestRedBlackTree {
//
// private val rBTree = RedBlackTree<Int, Int>()
//
// private var maxBlackHeight: Int = -1
//
// private fun checkStructure(node: RBNode<Int, Int>? = rBTree.root, blackHeight: Int = 1): Boolean {
//
// if (node == null) return true
//
// if (node.left == null && node.right == null) {
// if (maxBlackHeight == -1) {
// maxBlackHeight = blackHeight
// }
// if (maxBlackHeight != blackHeight) {
// return false
// }
// return true
// }
//
// if (!node.isBlack) {
// return if (node.left?.isBlack == false || node.right?.isBlack == false) {
// false
// } else {
// checkStructure(node.left, blackHeight + 1) &&
// checkStructure(node.right, blackHeight + 1)
// }
// } else {
// val checkLeft: Boolean = if (node.left?.isBlack == true) {
// checkStructure(node.left, blackHeight + 1)
// } else {
// checkStructure(node.left, blackHeight)
// }
// val checkRight: Boolean = if (node.right?.isBlack == true) {
// checkStructure(node.right, blackHeight + 1)
// } else {
// checkStructure(node.right, blackHeight)
// }
// return checkLeft && checkRight
// }
//
// }
//
// @DisplayName("Search existing key")
// @Test
// fun testSearchExistingKey() {
//
// for (testInputLength in 0..1000) {
//
// val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }
//
// testInput.shuffle()
//
// for (data in testInput) {
// rBTree.insert(data, data)
// }
//
// for (data in testInput) {
// assertEquals(Pair(data, data), rBTree.find(data))
// }
//
// rBTree.root?.left?.parent = null
// rBTree.root?.right?.parent = null
// rBTree.root = null
//
// }
//
// }
//
// @DisplayName("Search in root")
// @Test
// fun testSearchInRoot() {
//
// rBTree.insert(1, 1)
//
// assertEquals(Pair(1, 1), rBTree.find(1))
//
// }
//
// @DisplayName("Search case 1")
// @Test
// fun testSearchCase1() {
//
// rBTree.insert(1, 1)
// rBTree.insert(2, 2)
//
// assertEquals(Pair(2, 2), rBTree.find(2))
// assertEquals(Pair(1, 1), rBTree.find(1))
//
// }
//
// @DisplayName("Search case 2")
// @Test
// fun testSearchCase2() {
//
// rBTree.insert(2, 2)
// rBTree.insert(1, 1)
//
// assertEquals(Pair(2, 2), rBTree.find(2))
// assertEquals(Pair(1, 1), rBTree.find(1))
//
// }
//
// @DisplayName("Search case 3")
// @Test
// fun testSearchCase3() {
//
// rBTree.insert(1, 1)
// rBTree.insert(2, 2)
// rBTree.insert(3, 3)
//
// assertEquals(Pair(3, 3), rBTree.find(3))
// assertEquals(Pair(2, 2), rBTree.find(2))
// assertEquals(Pair(1, 1), rBTree.find(1))
//
// }
//
// @DisplayName("Search case 4")
// @Test
// fun testSearchCase4() {
//
// rBTree.insert(3, 3)
// rBTree.insert(2, 2)
// rBTree.insert(1, 1)
//
// assertEquals(Pair(3, 3), rBTree.find(3))
// assertEquals(Pair(2, 2), rBTree.find(2))
// assertEquals(Pair(1, 1), rBTree.find(1))
//
// }
//
//
// @DisplayName("Search case 5")
// @Test
// fun testSearchCase5() {
//
// rBTree.insert(2, 2)
// rBTree.insert(3, 3)
// rBTree.insert(1, 1)
//
// assertEquals(Pair(3, 3), rBTree.find(3))
// assertEquals(Pair(2, 2), rBTree.find(2))
// assertEquals(Pair(1, 1), rBTree.find(1))
//
// }
//
// @DisplayName("Search nonexistent key in empty tree")
// @Test
// fun testSearchNonExistingKeyInEmptyTree() {
//
// assertNull(rBTree.find(0))
// assertNull(rBTree.find(101))
//
// }
//
// @DisplayName("Search non existing key in nonempty tree")
// @Test
// fun testSearchNonExistingKeyInNonEmptyTree() {
//
// val testInput: MutableList<Int> = MutableList(100) { it + 1 }
//
// testInput.shuffle()
//
// for (data in testInput) {
// rBTree.insert(data, data)
// }
//
// assertNull(rBTree.find(0))
// assertNull(rBTree.find(101))
//
// }
//
// @DisplayName("Double insert check")
// @Test
// fun testDoubleInsert() {
//
// rBTree.insert(1, 1)
// rBTree.insert(1, 1)
//
// assertEquals(rBTree.root!!.key, 1)
// assertTrue(rBTree.root!!.parent == null)
// assertTrue(rBTree.root!!.left == null)
// assertTrue(rBTree.root!!.right == null)
//
// }
//
// @DisplayName("Insert check")
// @Test
// fun testInsert() {
//
// for (testInputLength in 0..1000) {
//
// val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }
//
// testInput.shuffle()
//
// for (data in testInput) {
// rBTree.insert(data, data)
// }
//
// for (x in testInput) {
// assertEquals(rBTree.find(x), Pair(x, x))
// }
//
// rBTree.root?.left?.parent = null
// rBTree.root?.right?.parent = null
// rBTree.root = null
//
// }
//
// }
//
// @DisplayName("Insert save structure")
// @Test
// fun testInsertSaveStructure() {
//
// for (testInputLength in 0..1000) {
//
// val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }
//
// testInput.shuffle()
//
// for (data in testInput) {
// rBTree.insert(data, data)
// maxBlackHeight = -1
// assertTrue(checkStructure())
// }
//
// rBTree.root?.left?.parent = null
// rBTree.root?.right?.parent = null
// rBTree.root = null
//
// }
//
// }
//
// @DisplayName("Insert save structure Direct order")
// @Test
// fun testInsertSaveStructureDirectOrder() {
//
// for (testInputLength in 0..1000) {
//
// val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }
//
// for (data in testInput) {
// rBTree.insert(data, data)
// maxBlackHeight = -1
// assertTrue(checkStructure())
// }
//
// rBTree.root?.left?.parent = null
// rBTree.root?.right?.parent = null
// rBTree.root = null
//
// }
//
// }
//
// @DisplayName("Insert save structure Reverse order")
// @Test
// fun testInsertSaveStructureReverseOrder() {
//
// for (testInputLength in 0..1000) {
//
// val testInput: MutableList<Int> = MutableList(testInputLength) { testInputLength - it }
//
// for (data in testInput) {
// rBTree.insert(data, data)
// maxBlackHeight = -1
// assertTrue(checkStructure())
// }
//
// rBTree.root?.left?.parent = null
// rBTree.root?.right?.parent = null
// rBTree.root = null
//
// }
//
// }
//
// @DisplayName("Iterate empty tree")
// @Test
// fun testIterateEmptyTree() {
//
// for (i in rBTree)
// assertEquals(-1, 1)
//
// }
//
// @DisplayName("Iterate normal tree")
// @Test
// fun testIterateNormal() {
//
// for (testInputLength in 1..1000) {
//
// val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }
//
// testInput.shuffle()
//
// for (data in testInput) {
// rBTree.insert(data, data)
// }
//
// var cur = 0
//
// for (i in rBTree) {
// ++cur
// assertEquals(Pair(cur, cur), i)
// }
//
// rBTree.root?.left?.parent = null
// rBTree.root?.right?.parent = null
// rBTree.root = null
//
// }
//
// }
//
// @DisplayName("Iterate tree in direct order")
// @Test
// fun testIterateDirectOrder() {
//
// for (testInputLength in 1..1000) {
//
// val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }
//
// for (data in testInput) {
// rBTree.insert(data, data)
// }
//
// var cur = 0
//
// for (i in rBTree) {
// ++cur
// assertEquals(Pair(cur, cur), i)
// }
//
// rBTree.root?.left?.parent = null
// rBTree.root?.right?.parent = null
// rBTree.root = null
//
// }
//
// }
//
// @DisplayName("Iterate tree in reverse order")
// @Test
// fun testIterateReverseOrder() {
//
// for (testInputLength in 1..1000) {
//
// val testInput: MutableList<Int> = MutableList(testInputLength) { testInputLength - it }
//
// for (data in testInput) {
// rBTree.insert(data, data)
// }
//
// var cur = 0
//
// for (i in rBTree) {
// ++cur
// assertEquals(Pair(cur, cur), i)
// }
//
// rBTree.root?.left?.parent = null
// rBTree.root?.right?.parent = null
// rBTree.root = null
//
// }
//
// }
//
// }
//
// //next
//
// private val bSTree = BinaryTree<Int, Int>()
//
// private fun bstCheckStructure(node: BSTNode<Int, Int>? = bSTree.root): Boolean {
//
// if (node == null) return true
//
// val checkLeft = bstCheckStructure(node.left)
// val checkRight = bstCheckStructure(node.right)
//
// return if (node.left == null && node.right == null) {
// true
// } else if (node.left == null || node.right == null) {
// if (node.left == null) {
// (node.key < node.right!!.key) && checkRight
// }
// else {
// (node.key > node.left!!.key) && checkLeft
// }
// }
// else {
// (node.left!!.key < node.key && node.key < node.right!!.key) &&
// checkLeft && checkRight
// }
//
// }
//
// @DisplayName("Search existing key")
// @Test
// fun testSearchExistingKey() {
//
// for (testInputLength in 0..1000) {
//
// val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }
//
// testInput.shuffle()
//
// for (data in testInput) {
// bSTree.insert(data, data)
// }
//
// for (data in testInput) {
// assertEquals(Pair(data, data), bSTree.find(data))
// }
//
// bSTree.root?.left?.parent = null
// bSTree.root?.right?.parent = null
// bSTree.root = null
//
// }
//
// }
//
// @DisplayName("Search in root")
// @Test
// fun testSearchInRoot() {
//
// bSTree.insert(1, 1)
//
// assertEquals(Pair(1, 1), bSTree.find(1))
//
// }
//
// @DisplayName("Search case 1")
// @Test
// fun testSearchCase1() {
//
// bSTree.insert(1, 1)
// bSTree.insert(2, 2)
//
// assertEquals(Pair(2, 2), bSTree.find(2))
// assertEquals(Pair(1, 1), bSTree.find(1))
//
// }
//
// @DisplayName("Search case 2")
// @Test
// fun testSearchCase2() {
//
// bSTree.insert(2, 2)
// bSTree.insert(1, 1)
//
// assertEquals(Pair(2, 2), bSTree.find(2))
// assertEquals(Pair(1, 1), bSTree.find(1))
//
// }
//
// @DisplayName("Search case 3")
// @Test
// fun testSearchCase3() {
//
// bSTree.insert(1, 1)
// bSTree.insert(2, 2)
// bSTree.insert(3, 3)
//
// assertEquals(Pair(3, 3), bSTree.find(3))
// assertEquals(Pair(2, 2), bSTree.find(2))
// assertEquals(Pair(1, 1), bSTree.find(1))
//
// }
//
// @DisplayName("Search case 4")
// @Test
// fun testSearchCase4() {
//
// bSTree.insert(3, 3)
// bSTree.insert(2, 2)
// bSTree.insert(1, 1)
//
// assertEquals(Pair(3, 3), bSTree.find(3))
// assertEquals(Pair(2, 2), bSTree.find(2))
// assertEquals(Pair(1, 1), bSTree.find(1))
//
// }
//
//
// @DisplayName("Search case 5")
// @Test
// fun testSearchCase5() {
//
// bSTree.insert(2, 2)
// bSTree.insert(3, 3)
// bSTree.insert(1, 1)
//
// assertEquals(Pair(3, 3), bSTree.find(3))
// assertEquals(Pair(2, 2), bSTree.find(2))
// assertEquals(Pair(1, 1), bSTree.find(1))
//
// }
//
// @DisplayName("Search nonexistent key in empty tree")
// @Test
// fun testSearchNonExistingKeyInEmptyTree() {
//
// assertNull(bSTree.find(0))
// assertNull(bSTree.find(101))
//
// }
//
// @DisplayName("Search non existing key in nonempty tree")
// @Test
// fun testSearchNonExistingKeyInNonEmptyTree() {
//
// val testInput: MutableList<Int> = MutableList(100) { it + 1 }
//
// testInput.shuffle()
//
// for (data in testInput) {
// bSTree.insert(data, data)
// }
//
// assertNull(bSTree.find(0))
// assertNull(bSTree.find(101))
//
// }
//
// @DisplayName("Double insert check")
// @Test
// fun testDoubleInsert() {
//
// bSTree.insert(1, 1)
// bSTree.insert(1, 1)
//
// assertEquals(bSTree.root!!.key, 1)
// assertTrue(bSTree.root!!.parent == null)
// assertTrue(bSTree.root!!.left == null)
// assertTrue(bSTree.root!!.right == null)
//
// }
//
// @DisplayName("Insert check")
// @Test
// fun testInsert() {
//
// for (testInputLength in 0..1000) {
//
// val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }
//
// testInput.shuffle()
//
// for (data in testInput) {
// bSTree.insert(data, data)
// }
//
// for (x in testInput) {
// assertEquals(bSTree.find(x), Pair(x, x))
// }
//
// bSTree.root?.left?.parent = null
// bSTree.root?.right?.parent = null
// bSTree.root = null
//
// }
//
// }
//
// @DisplayName("Insert save structure")
// @Test
// fun testInsertSaveStructure() {
//
// for (testInputLength in 0..1000) {
//
// val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }
//
// testInput.shuffle()
//
// for (data in testInput) {
// bSTree.insert(data, data)
// assertTrue(bstCheckStructure())
// }
//
// bSTree.root?.left?.parent = null
// bSTree.root?.right?.parent = null
// bSTree.root = null
//
// }
//
// }
//
// @DisplayName("Insert save structure Direct order")
// @Test
// fun testInsertSaveStructureDirectOrder() {
//
// for (testInputLength in 0..1000) {
//
// val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }
//
// for (data in testInput) {
// bSTree.insert(data, data)
// assertTrue(bstCheckStructure())
// }
//
// bSTree.root?.left?.parent = null
// bSTree.root?.right?.parent = null
// bSTree.root = null
//
// }
//
// }
//
// @DisplayName("Insert save structure Reverse order")
// @Test
// fun testInsertSaveStructureReverseOrder() {
//
// for (testInputLength in 0..1000) {
//
// val testInput: MutableList<Int> = MutableList(testInputLength) { testInputLength - it }
//
// for (data in testInput) {
// bSTree.insert(data, data)
// assertTrue(bstCheckStructure())
// }
//
// bSTree.root?.left?.parent = null
// bSTree.root?.right?.parent = null
// bSTree.root = null
//
// }
//
// }
//
// @DisplayName("Iterate empty tree")
// @Test
// fun testIterateEmptyTree() {
//
// for (i in bSTree)
// assertEquals(-1, 1)
//
// }
//
//
// private val avlTree = AVLTree<Int, Int>()
//
// private fun checkStructure(node: AVLNode<Int, Int>? = avlTree.root): Boolean {
//
// if (node == null) return true
//
// return if (node.getBalance() in -1..1) {
// val checkLeft = checkStructure(node.left)
// val checkRight = checkStructure(node.right)
// checkLeft && checkRight
// } else {
// false
// }
//
// }
//
// @DisplayName("AVL: Search existing key")
// @Test
// fun avlTestSearchExistingKey() {
//
// for (testInputLength in 0..1000) {
//
// val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }
//
// testInput.shuffle()
//
// for (data in testInput) {
// avlTree.insert(data, data)
// }
//
// for (data in testInput) {
// assertEquals(Pair(data, data), avlTree.find(data))
// }
//
// avlTree.root?.left?.parent = null
// avlTree.root?.right?.parent = null
// avlTree.root = null
//
// }
//
// }
//
// @DisplayName("AVL: Search in root")
// @Test
// fun avlTestSearchInRoot() {
//
// avlTree.insert(1, 1)
//
// assertEquals(Pair(1, 1), avlTree.find(1))
//
// }
//
// @DisplayName("AVL: Search case 1")
// @Test
// fun avlTestSearchCase1() {
//
// avlTree.insert(1, 1)
// avlTree.insert(2, 2)
//
// assertEquals(Pair(2, 2), avlTree.find(2))
// assertEquals(Pair(1, 1), avlTree.find(1))
//
// }
//
// @DisplayName("AVL: Search case 2")
// @Test
// fun avlTestSearchCase2() {
//
// avlTree.insert(2, 2)
// avlTree.insert(1, 1)
//
// assertEquals(Pair(2, 2), avlTree.find(2))
// assertEquals(Pair(1, 1), avlTree.find(1))
//
// }
//
// @DisplayName("AVL: Search case 3")
// @Test
// fun avlTestSearchCase3() {
//
// avlTree.insert(1, 1)
// avlTree.insert(2, 2)
// avlTree.insert(3, 3)
//
// assertEquals(Pair(3, 3), avlTree.find(3))
// assertEquals(Pair(2, 2), avlTree.find(2))
// assertEquals(Pair(1, 1), avlTree.find(1))
//
// }
//
// @DisplayName("AVL: Search case 4")
// @Test
// fun avlTestSearchCase4() {
//
// avlTree.insert(3, 3)
// avlTree.insert(2, 2)
// avlTree.insert(1, 1)
//
// assertEquals(Pair(3, 3), avlTree.find(3))
// assertEquals(Pair(2, 2), avlTree.find(2))
// assertEquals(Pair(1, 1), avlTree.find(1))
//
// }
//
//
// @DisplayName("AVL: Search case 5")
// @Test
// fun avlTestSearchCase5() {
//
// avlTree.insert(2, 2)
// avlTree.insert(3, 3)
// avlTree.insert(1, 1)
//
// assertEquals(Pair(3, 3), avlTree.find(3))
// assertEquals(Pair(2, 2), avlTree.find(2))
// assertEquals(Pair(1, 1), avlTree.find(1))
//
// }
//
// @DisplayName("AVL: Search nonexistent key in empty tree")
// @Test
// fun avlTestSearchNonExistingKeyInEmptyTree() {
//
// assertNull(avlTree.find(0))
// assertNull(avlTree.find(101))
//
// }
//
// @DisplayName("AVL: Search non existing key in nonempty tree")
// @Test
// fun avlTestSearchNonExistingKeyInNonEmptyTree() {
//
// val testInput: MutableList<Int> = MutableList(100) { it + 1 }
//
// testInput.shuffle()
//
// for (data in testInput) {
// avlTree.insert(data, data)
// }
//
// assertNull(avlTree.find(0))
// assertNull(avlTree.find(101))
//
// }
//
// @DisplayName("AVL: Double insert check")
// @Test
// fun avlTestDoubleInsert() {
//
// avlTree.insert(1, 1)
// avlTree.insert(1, 1)
//
// assertEquals(avlTree.root!!.key, 1)
// assertTrue(avlTree.root!!.parent == null)
// assertTrue(avlTree.root!!.left == null)
// assertTrue(avlTree.root!!.right == null)
//
// }
//
// @DisplayName("AVL: Insert check")
// @Test
// fun avlTestInsert() {
//
// for (testInputLength in 0..1000) {
//
// val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }
//
// testInput.shuffle()
//
// for (data in testInput) {
// avlTree.insert(data, data)
// }
//
// for (x in testInput) {
// assertEquals(avlTree.find(x), Pair(x, x))
// }
//
// avlTree.root?.left?.parent = null
// avlTree.root?.right?.parent = null
// avlTree.root = null
//
// }
//
// }
//
// @DisplayName("AVL: Insert save structure")
// @Test
// fun avlTestInsertSaveStructure() {
//
// for (testInputLength in 0..1000) {
//
// val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }
//
// testInput.shuffle()
//
// for (data in testInput) {
// avlTree.insert(data, data)
// assertTrue(checkStructure())
// }
//
// avlTree.root?.left?.parent = null
// avlTree.root?.right?.parent = null
// avlTree.root = null
//
// }
//
// }
//
// @DisplayName("AVL: Insert save structure Direct order")
// @Test
// fun avlTestInsertSaveStructureDirectOrder() {
//
// for (testInputLength in 0..1000) {
//
// val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }
//
// for (data in testInput) {
// avlTree.insert(data, data)
// assertTrue(checkStructure())
// }
//
// avlTree.root?.left?.parent = null
// avlTree.root?.right?.parent = null
// avlTree.root = null
//
// }
//
// }
//
// @DisplayName("AVL: Insert save structure Reverse order")
// @Test
// fun avlTestInsertSaveStructureReverseOrder() {
//
// for (testInputLength in 0..1000) {
//
// val testInput: MutableList<Int> = MutableList(testInputLength) { testInputLength - it }
//
// for (data in testInput) {
// avlTree.insert(data, data)
// assertTrue(checkStructure())
// }
//
// avlTree.root?.left?.parent = null
// avlTree.root?.right?.parent = null
// avlTree.root = null
//
// }
//
// }
//
// @DisplayName("AVL: Iterate empty tree")
// @Test
// fun avlTestIterateEmptyTree() {
//
// for (i in avlTree)
// assertEquals(-1, 1)
//
// }
//
// private val Tree = AVLTree<Int, Int>()
//
// //BASE TEST, COPIES FROM BSTNode
// @DisplayName("SearchExistingKey")
// @Test
// fun testSearchingKey() {
//
// val a: MutableList<Int> = MutableList(100) { it + 1 }
//
// a.shuffle()
//
// for (x in a) {
// Tree.insert(x, x)
// }
//
// for (x in a) {
// assertEquals(Tree.find(x), Pair(x, x))
// }
//
// }
//
// @DisplayName("Searching nonexistent key")
// @Test
// fun failSearchingKeyTest() {
//
// val a: MutableList<Int> = MutableList(100) { it + 1 }
//
// a.shuffle()
//
// for (x in a) {
// Tree.insert(x, x)
// }
//
// assertEquals(null, Tree.find(1000))
//
// }
//
// @DisplayName("Test for iterator using BFS algorithm")
// @Test
// fun testBFSIterator() {
// val testList: MutableList<Int> = mutableListOf(7, 3, 11, 2, 5, 9, 13, 1, 4, 6, 10, 12, 14, 15)
// val res: MutableList<Int> = mutableListOf()
//
// for (x in testList) {
// Tree.insert(x, x)
// }
//
// for (x in Tree)
// res.add(x.value)
// assertEquals(testList, res)
// }
//
// @DisplayName("CommonTestIterator")
// @Test
// fun testIterator() {
// val expect = MutableList(5) { it + 1 }
// val res = mutableListOf<Int>()
//
// expect.shuffle()
//
// for (x in expect) {
// Tree.insert(x, x)
// }
//
// for (x in Tree) {
// assertEquals(Tree.find(x.key), Pair(x.key, x.value))
// }
//
// }
//
// @DisplayName("EmptyIteratorTest")
// @Test
// fun emptyIterator() {
//
// for (x in Tree) {
// assertEquals(null, x.value)
// }
//
// }
//
// @DisplayName("Property of right key test")
// @Test
// fun propRightKey() {
// val a = MutableList(100) { it + 1 }
//
// a.shuffle()
//
// for (x in a) {
// Tree.insert(x, x)
// }
// var cur: AVLNode<Int, Int> = Tree.root ?: throw Exception("Insert error")
// var actual = true
//
// while (cur.right != null) {
// actual = cur.value < cur.right!!.value
// if (!actual) return
// cur = cur.right!!
// }
//
// assertEquals(true, actual)
// }
//
// @DisplayName("Property of left key test")
// @Test
// fun propLeftKey() {
// val a = MutableList(100) { it + 1 }
//
// a.shuffle()
//
// for (x in a) {
// Tree.insert(x, x)
// }
// var cur: AVLNode<Int, Int> = Tree.root ?: throw Exception("Insert error")
// var actual = true
//
// while (cur.left != null) {
// actual = cur.value > cur.left!!.value
// if (!actual) return
// cur = cur.left!!
// }
//
// assertEquals(true, actual)
//
// }
// //New test for AVLTree
//
// @DisplayName("Left Right Case Check Test")
// @Test
// fun lrCaseTest(){
// val list = listOf(7, 1, 4)
// val expectTree = AVLTree<Int, Int>()
// expectTree.root = AVLNode(4, 4)
// expectTree.root?.left = AVLNode(1, 1, expectTree.root)
// expectTree.root?.right = AVLNode(7, 7, expectTree.root)
// val expRoot = expectTree.root
// expRoot?.height = 2
//
// for (x in list){
// Tree.insert(x, x)
// }
//
// val actual = expectTree.root == Tree.root
// && expectTree.root?.right == Tree.root?.right
// && expectTree.root?.left == Tree.root?.left
//
// assertEquals(true, actual)
// }
//
// @DisplayName("Left Left Case Check Test")
// @Test
// fun llCaseTest() {
// val list = listOf(7, 4, 1)
// val expectTree = AVLTree<Int, Int>()
// expectTree.root = AVLNode(4, 4)
// expectTree.root?.left = AVLNode(1, 1,  expectTree.root)
// expectTree.root?.right = AVLNode(7, 7, expectTree.root)
// val expRoot = expectTree.root
// expRoot?.height = 2
//
// for (x in list){
// Tree.insert(x, x)
// }
//
// val actual = expectTree.root == Tree.root
// && expectTree.root?.right == Tree.root?.right
// && expectTree.root?.left == Tree.root?.left
//
// assertEquals(true, actual)
// }
//
// @DisplayName("Right Left Case Test")
// @Test
// fun rlCaseTest(){
// val list = listOf(7, 11, 9)
// val expectTree = AVLTree<Int, Int>()
// expectTree.root = AVLNode(9, 9)
// expectTree.root?.left = AVLNode(7, 7,  expectTree.root)
// expectTree.root?.right = AVLNode(11, 11,  expectTree.root)
// val expRoot = expectTree.root
// expRoot?.height = 2
//
// for (x in list){
// Tree.insert(x, x)
// }
//
// val actual = expectTree.root == Tree.root
// && expectTree.root?.right == Tree.root?.right
// && expectTree.root?.left == Tree.root?.left
//
// assertEquals(true, actual)
// }
//
// @DisplayName("Right Right Test")
// @Test
// fun rrCaseTest(){
// val list = listOf(7, 9, 11)
// val expectTree = AVLTree<Int, Int>()
// expectTree.root = AVLNode(9, 9)
// expectTree.root?.left = AVLNode(7, 7, expectTree.root)
// expectTree.root?.right = AVLNode(11, 11, expectTree.root)
// val expRoot = expectTree.root
// expRoot?.height = 2
//
// for (x in list){
// Tree.insert(x, x)
// }
//
// val actual = expectTree.root == Tree.root
// && expectTree.root?.right == Tree.root?.right
// && expectTree.root?.left == Tree.root?.left
//
// assertEquals(true, actual)
// }
//
// }