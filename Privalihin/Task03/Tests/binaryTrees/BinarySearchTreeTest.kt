package binaryTrees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.random.Random

class BinarySearchTreeTest {

    private fun randomBinarySearchTree(size: Int): BinarySearchTree<Int, Int> {
        val tree = BinarySearchTree<Int, Int>()

        for (i in 1..size) {
            tree.insert(Random.Default.nextInt(), Random.Default.nextInt())
        }

        return tree
    }

    @Test
    fun basicTest() {
        val tree = BinarySearchTree<Int, Int>()

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
            val tree = randomBinarySearchTree(Random.Default.nextInt(0, 10))
            assert(tree.isBinarySearchTree())
        }
    }

    @Test
    fun stressTest() {
        val tree = randomBinarySearchTree(1000)
        assert(tree.isBinarySearchTree())
    }
}