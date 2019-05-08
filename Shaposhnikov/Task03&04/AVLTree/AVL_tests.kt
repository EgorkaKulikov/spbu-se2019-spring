import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.lang.Integer.max

class AVLTests{

    private var tree = AVLTree<Int, Int>()

    @DisplayName("Unexisting call")
    @Test
    fun `find(null)`(){
        for (num in 1..1000) tree.insert(num, num)
        for (num in 1001..2000)
            assertNull(tree.find(num))
    }

    @DisplayName("Testing find function")
    @Test
    fun testingFind(){
        val list = MutableList(1000) {it}
        list.shuffle()
        for (num in list)
            tree.insert(num, num)
        for (num in list)
            assertEquals(tree.find(num), Pair(num, num))
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

    // these one were simply copy-pasted from BST_tests due to our architecture

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
        tree.insert(10, 10)
        tree.insert(20, 20)
        tree.insert(30, 30)
        assert(tree.root!!.key == 20)
    }

    @DisplayName("Insert case 1: left-left")
    @Test
    fun `test left-left insertion`(){
        tree.insert(50, 50) //root
        tree.insert(25, 25) //left child
        tree.insert(10, 10) // left-left child
        //here comes balancing
        val root = tree.root!!
        assertEquals(root.key, 25)
        assertEquals(root.right!!.key, 50)
        assertEquals(root.left!!.key, 10)
        assertEquals(root.height, 2)
        assertEquals(root.left!!.height, 1)
        assertEquals(root.right!!.height, 1)
    }

    @DisplayName("Insert case 2: left-right")
    @Test
    fun `test left-right insertion`(){
        tree.insert(50, 50) //root
        tree.insert(25, 25) //left child
        tree.insert(30, 30) // left-right child
        //here comes balancing
        val root = tree.root!!
        assertEquals(root.key, 30)
        assertEquals(root.right!!.key, 50)
        assertEquals(root.left!!.key, 25)
        assertEquals(root.height, 2)
        assertEquals(root.right!!.height, 1)
        assertEquals(root.left!!.height, 1)
    }

    @DisplayName("Insert case 3: right-right")
    @Test
    fun `test right-right insertion`(){
        tree.insert(50, 50) //root
        tree.insert(75, 75) //right child
        tree.insert(80, 80) // right-right child
        //here comes balancing
        val root = tree.root!!
        assertEquals(root.key, 75)
        assertEquals(root.right!!.key, 80)
        assertEquals(root.left!!.key, 50)
        assertEquals(root.height, 2)
        assertEquals(root.left!!.height, 1)
        assertEquals(root.right!!.height, 1)
    }

    @DisplayName("Insert case 4: right-left")
    @Test
    fun `test right-left insertion`(){
        tree.insert(50, 50) //root
        tree.insert(75, 75) //right child
        tree.insert(60, 60) // right-right child
        //here comes balancing
        val root = tree.root!!
        assertEquals(root.key, 60)
        assertEquals(root.right!!.key, 75)
        assertEquals(root.left!!.key, 50)
        assertEquals(root.height, 2)
        assertEquals(root.right!!.height, 1)
        assertEquals(root.left!!.height, 1)
    }

    @DisplayName("Delta height condition")
    @Test
    fun `chech delta height`(){
        for (num in 1..1000)
            tree.insert(num, num)
        for (node in tree)
            assert(node.heightDif() < 2)
    }

    @DisplayName("Correct heights in nodes")
    @Test
    fun `check heights correctivity`(){
        for (num in 1..1000)
            tree.insert(num, num)
        for (node in tree)
            assertEquals(node.height, max(node.left?.height ?: 0, node.right?.height ?: 0) + 1)
    }
}