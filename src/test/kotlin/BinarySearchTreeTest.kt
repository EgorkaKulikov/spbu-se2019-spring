import org.junit.Assert.assertEquals
import org.junit.Test

class BinarySearchTreeTest {

    private val tree = BinarySearchTree<Int, Int>()

    @Test
    fun simple_iterator_test() {
        for (index in 1..5) {
            tree[index] = index
        }
        val result: ArrayList<Int> = arrayListOf()
        for (node in tree) {
            result.add(node.value)
        }
        val expected = arrayListOf(1, 2, 3, 4, 5)
        assertEquals(result, expected)
    }
}