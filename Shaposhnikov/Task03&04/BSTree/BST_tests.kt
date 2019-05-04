import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class SimpleTreeTests {

    private var tree = BSTree<Int, Int>()

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
        val list = MutableList(3000) {it+1}
        list.shuffle()
        for (num in list)
            tree.insert(num, num)
        for (num in list)
            assertEquals(tree.find(num), Pair(num, num))
    }

    @DisplayName("Testing iterator in BST")
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
        assertEquals(amount, list.size)
    }

    @DisplayName("Empty tree iteration")
    @Test
    fun iterateNull(){
        for (node in tree)
            assertEquals(false, true)
    }
}
