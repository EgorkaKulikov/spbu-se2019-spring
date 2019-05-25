package trees


import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.random.Random


class BinarySearchTreeTests {
    private fun randomBinarySearchTree(size: Int): BinarySearchTree<Int, Int> {
        val tree = BinarySearchTree<Int, Int>()

        for (i in 1..size) {
            tree.insert(Random.nextInt(), i)
        }

        return tree
    }

    @Test
    fun emptyTree() {
        val tree = BinarySearchTree<Int, Int>()

        assertEquals(0, tree.size)
        assertEquals(null, tree.root)
    }

    @Test
    fun basicInsertFind() {
        val tree = BinarySearchTree<Int, Int>()

        for (i in 1..10) {
            tree.insert(i, i * i)

            assertEquals(i * i, tree.find(i))
        }

        assertEquals(null, tree.find(0))
    }

    @Test
    fun stressInsertFind() {
        val tree = BinarySearchTree<Int, Int>()

        for (i in 1..10000) {
            tree.insert(i, i)

            assertEquals(i, tree.find(i))
            assertEquals(null, tree.find(i + 1))
        }

    }

    @Test
    fun basicStructure() {
        for (i in 1..10) {
            val tree = randomBinarySearchTree(Random.nextInt(1, 10))

            assertTrue(tree.isBinarySearchTree())
            assertTrue(tree.parentsCorrectness())
        }
    }

    @Test
    fun stressStructure() {
        val tree = randomBinarySearchTree(100000)

        for (i in 1..100) {
            tree.insert(i, i)

            assertTrue(tree.isBinarySearchTree())
            assertTrue(tree.parentsCorrectness())
        }
    }

    @Test
    fun insertExistingKey() {
        val tree = BinarySearchTree<Int, Int>()

        for (i in 1..10) {
            tree.insert(i, i * i)
        }

        tree.insert(10, 0)

        for (i in 1..9) {
            assertEquals(i * i, tree.find(i))
        }

        assertEquals(0, tree.find(10))
    }

    @Test
    fun notEqualNodesWithNotEqualKeyValue() {
        val tree = BinarySearchTree<Int, Int>()

        val firstNode = tree.Node(0, 5, null)
        val secondNode = tree.Node(10, 5, firstNode)

        assertTrue(firstNode != secondNode)
    }

    @Test
    fun equalNodesWithEqualKeyValue() {
        val tree = BinarySearchTree<Int, Int>()

        val firstNode = tree.innerInsert(0, 1)
        val secondNode = tree.innerInsert(0, 1)

        assertTrue(firstNode == secondNode)
    }

    @Test
    fun sizeChanges() {
        val tree = BinarySearchTree<Int, Int>()
        var size = 0

        for (i in 1..100) {
            val inserted = Random.nextInt(1, 100)

            if (tree.find(inserted) == null) {
                tree.insert(inserted, i)
                assertEquals(++size, tree.size)
            } else {
                tree.insert(inserted, i)
                assertEquals(size, tree.size)
            }
        }
    }
}