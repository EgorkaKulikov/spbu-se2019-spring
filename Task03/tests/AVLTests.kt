package trees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import kotlin.random.Random

class AVLTreeTests {
    private fun randomAVLTree(size: Int): AVLTree<Int, Int> {
        val tree = AVLTree<Int, Int>()

        for (i in 1..size) {
            tree.insert(Random.Default.nextInt(), Random.Default.nextInt())
        }

        return tree
    }

    @Test
    fun simpleInsertionAndFindingTest() {
        val tree = AVLTree<Int, Int>()
        for (i in 1..5) {
            tree.insert(i, i * i)
        }

        for (i in 1..5) {
            assertEquals(i * i, tree.find(i))
        }

        assertEquals(null, tree.find(6))
    }

    @Test
    fun randomInsertionTest() {
        for (i in 1..10) {
            val tree = randomAVLTree(Random.Default.nextInt(0, 10))
            assert(tree.isAVLTree())
            assert(tree.isBinarySearchTree())
        }
    }

    @Test
    fun stressTest() {
        val tree = randomAVLTree(10000)
        assert(tree.parentsCorrectness())
        assert(tree.isAVLTree())
        assert(tree.isBinarySearchTree())
    }
}
