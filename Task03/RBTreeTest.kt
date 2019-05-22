package binaryTrees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.random.Random

class RBTreeTest {
    private fun randomRBTree(size: Int): RBTree<Int, Int> {
        val tree = RBTree<Int, Int>()

        for (i in 1..size) {
            tree.insert(Random.Default.nextInt(), Random.Default.nextInt())
        }

        return tree
    }

    @Test
    fun basicInsertionTest() {
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
    fun randomInsertionTest() {
        val tree = randomRBTree(1000)
        assert(tree.parentsCorrectness())
        assert(tree.isRBTree())
        assert(tree.isBinarySearchTree())
    }

}