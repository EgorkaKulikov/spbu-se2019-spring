package trees

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.random.Random

class AVLTreeTests {
    private fun randomAVLTree(size: Int): AVLTree<Int, Int> {
        val tree = AVLTree<Int, Int>()

        for (i in 1..size) {
            tree.insert(Random.nextInt(), i)
        }

        return tree
    }

    @Test
    fun basicInsertFind() {
        val tree = AVLTree<Int, Int>()

        for (i in 1..10) {
            tree.insert(i, i * i)

            assertEquals(i * i, tree.find(i))
        }

        assertEquals(null, tree.find(0))
    }

    @Test
    fun basicStructure() {
        for (i in 1..10) {
            val tree = randomAVLTree(Random.nextInt(0, 10))

            assertTrue(tree.isAVLTree())
            assertTrue(tree.isBinarySearchTree())
            assertTrue(tree.parentsCorrectness())
        }
    }

    @Test
    fun stressFindInsert() {
        val tree = AVLTree<Int, Int>()

        for (i in 1..10000) {
            tree.insert(i, i)

            assertEquals(i, tree.find(i))
            assertEquals(null, tree.find(i + 1))
        }
    }

    @Test
    fun stressStructure() {
        val tree = randomAVLTree(10000)

        for (i in 1..100) {
            tree.insert(i, i)

            assertTrue(tree.isAVLTree())
            assertTrue(tree.isBinarySearchTree())
            assertTrue(tree.parentsCorrectness())
        }
    }
}