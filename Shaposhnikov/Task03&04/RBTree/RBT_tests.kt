import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assert

class RBTests{

    private var tree = RBTree<Int, Int>()

    @DisplayName("Unexisting call")
    @Test
    fun `find(null)`(){
        for (num in 1..1000) tree.insert(num, num)
        for (num in 1001..2000)
            assertNull(tree.find(num))
    }

    @DisplayName("Testing find function + root == black")
    @Test
    fun testingFind(){
        val list = MutableList(3000) {it}
        list.shuffle()
        for (num in list)
            tree.insert(num, num)
        for (num in list)
            assertEquals(Pair(num, num), tree.find(num))
        assert(!tree.root!!.isRed)
    }

    @DisplayName("Testing iterator")
    @Test
    fun iteratorTest(){
        for (num in 1..1000)
            tree.insert(num, num)
        for (node in tree)
            assertEquals(Pair(node.key, node.value), tree.find(node.key))
    }

    @DisplayName("Left keys condition")
    @Test
    fun leftCondition(){
        val list = MutableList(1000) {it}
        list.shuffle()
        for (num in list)
            tree.insert(num, num)
        for (node in tree)
            assert(node.left?.key ?: continue < node.key)
    }

    @DisplayName("Right keys condition")
    @Test
    fun rightCondition(){
        val list = MutableList(1000) {it}
        list.shuffle()
        for (num in list)
            tree.insert(num, num)
        for (node in tree)
            assert(node.right?.key ?: continue >= node.key)
    }

    @DisplayName("Overlap of iterator")
    @Test
    fun iteratorOverlap(){
        val list = MutableList(1000) {it}
        list.shuffle()
        for (num in list)
            tree.insert(num, num)
        var amount = 0
        for (node in tree)
            amount++
        assertEquals(list.size, amount)
    }

    @DisplayName("Empty tree iteration")
    @Test
    fun iterateNull(){
        for (node in tree)
            assertEquals(false, true)
    }

    // these one were copy-pasted from BST_tests due to our architecture

    @DisplayName("Left rotate test")
    @Test
    fun `test left rotate`(){
        tree.insert(10, 10)
        tree.insert(20, 20)
        tree.insert(30, 30)
        assert(tree.root!!.key == 20)
    }

    @DisplayName("Right rotate test")
    @Test
    fun `test right rotate`(){
        tree.insert(30, 30)
        tree.insert(20, 20)
        tree.insert(10, 10)
        assert(tree.root!!.key == 20)
    }

    @DisplayName("Insert case 1: left-left")
    @Test
    fun `test left-left insertion`(){
        tree.insert(50, 50)  //root
        tree.insert(75, 75)  //right child
        tree.insert(25, 25)  //left child
        tree.root!!.right!!.isRed = false
        tree.insert(10, 10) //here come right rotate + colors swap
        val root = tree.root
        assert(!root!!.isRed) //basically black root
        assert(root.right!!.isRed)
        assert(root.left?.key ?: false == 10)
        assert(root.right?.right?.key ?: false == 75)
    }

    @DisplayName("Insert case 2: left-right")
    @Test
    fun `test left-right insertion`(){
        tree.insert(50, 50)  //root
        tree.insert(75, 75)  //right child
        tree.insert(25, 25)  //left child
        tree.root!!.right!!.isRed = false
        tree.insert(40, 40) //here come left rotate then right rotate + swap
        val root = tree.root
        assert(!root!!.isRed) //basically black root
        assert(root.key == 40)
        assert(root.right!!.isRed)
        assert(root.left?.key ?: false == 25)
        assert(root.right?.right?.key ?: false == 75)
    }

    @DisplayName("Insert case 3: right-right")
    @Test
    fun `test right-right insertion`(){
        tree.insert(50, 50)  //root
        tree.insert(75, 75)  //right child
        tree.insert(25, 25)  //left child
        tree.root!!.left!!.isRed = false
        tree.insert(100, 100) //here come left rotate + colors swap
        val root = tree.root
        assert(!root!!.isRed) //basically black root
        assert(root.left!!.isRed)
        assert(root.right?.key ?: false == 100)
        assert(root.left?.left?.key ?: false == 25)
    }

    @DisplayName("Insert case 4: right-left")
    @Test
    fun `test right-left insertion`(){
        tree.insert(50, 50)  //root
        tree.insert(75, 75)  //right child
        tree.insert(25, 25)  //left child
        tree.root!!.left!!.isRed = false
        tree.insert(60, 60) //here come right rotate then left rotate + swap
        val root = tree.root
        assert(!root!!.isRed) //basically black root
        assert(root.key == 60)
        assert(root.left!!.isRed)
        assert(root.right?.key ?: false == 75)
        assert(root.left?.left?.key ?: false == 25)
    }

    @DisplayName("Insertion case 5: dad+ uncle are red")
    @Test
    fun `test swap colors`(){
        tree.insert(50, 50)  //root
        tree.insert(75, 75)  //right child
        tree.insert(25, 25)  //left child
        tree.insert(10, 10)
        val root = tree.root
        assert(!root!!.isRed) //basically always black
        assert(root.left?.left?.key ?: false == 10)
        assert(root.left!!.left!!.isRed)
        assert(!root.left!!.isRed) //dad's black
        assert(!root.right!!.isRed) //uncle's black
    }

    @DisplayName("Testing color condition")
    @Test
    fun `test colors`(){
        for (num in 1..1000)
            tree.insert(num, num)
        for (node in tree)
            assertFalse(node.isRed && (node.left?.isRed ?: false || node.right?.isRed ?: false))
    }

    @DisplayName( "Checking black height parameter")
    @Test
    fun `check black height`(){
        val list = MutableList(1000) {it}
        list.shuffle()
        for (num in list)
            tree.insert(num, num)
        var leftHeight = 0
        var rightHeight = 0
        var localHeight : Int
        var x :RBNode<Int, Int>?
        for (node in tree){
            localHeight = 0
            x = node
            while (x!= tree.root) {
                if (!x!!.isRed) ++localHeight
                x = x.parent
            }
            when {
                x!!.key < node.key && localHeight > leftHeight -> leftHeight = localHeight
                x.key > node.key && localHeight > rightHeight -> rightHeight = localHeight
            }
        }
        assertEquals(leftHeight, rightHeight)
    }
}
