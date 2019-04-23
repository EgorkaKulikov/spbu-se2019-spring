import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BinarySearchTreeTest {

    private val tree = BinarySearchTree<Int, Int>()

    @Test
    fun basicTest() {
        for (i in 1..5) {
            tree.insert(i * i, i)
        }

        for (i in 1..5) {
            assertEquals(tree.find(i), i * i)
        }

        assertEquals(tree.find(6), null)
    }
}