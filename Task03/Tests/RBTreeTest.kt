import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.random.Random

class RBTreeTest {
    private fun randomRBTree(size: Int): RBTree<Int, Int> {
        val tree = RBTree<Int, Int>()

        for (i in 1..size) {
            tree.insert(Random.nextInt(), Random.nextInt())
        }

        return tree
    }

    @Test
    fun basicTest() {
        val tree = RBTree<Int, Int>()

        for (i in 1..5) {
            tree.insert(i, i * i)
        }

        for (i in 1..5) {
            assertEquals(tree.find(i), i * i)
        }

        assertEquals(tree.find(6), null)
    }

    @Test
    fun correctnessTest() {
        for (i in 1..10) {
            val tree = randomRBTree(Random.Default.nextInt(0, 10))
            assert(tree.isRBTree())
            assert(tree.isBinarySearchTree())
        }
    }

    @Test
    fun stressTest() {
        val tree = randomRBTree(10000)
        assert(tree.isRBTree())
        assert(tree.isBinarySearchTree())
    }
}