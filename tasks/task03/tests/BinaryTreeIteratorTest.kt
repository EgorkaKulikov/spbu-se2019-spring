import binary.BinarySearchTreeIterator
import binary.BinarySearchTreeNode
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BinaryTreeIteratorTest {

    private fun createNode(key: Int) = BinarySearchTreeNode(key, 0, 0)

    private fun createIterator(): BinarySearchTreeIterator<Int, Int> {
        val root = createNode(5).apply {
            left = createNode(3).apply {
                left = createNode(2).apply {
                    left = createNode(1)
                }
                right = createNode(4)
            }
            right = createNode(8).apply {
                left = createNode(6).apply {
                    right = createNode(7)
                }
                right = createNode(10).apply {
                    left = createNode(9)
                }
            }
        }

        return BinarySearchTreeIterator(root)
    }

    @Test
    fun `Test iteration for empty tree`() {
        val iterator = BinarySearchTreeIterator<Int, Int>(null)
        val actualList = mutableListOf<Int>()

        while (iterator.hasNext()) {
            actualList.add(iterator.next().first)
        }

        assertArrayEquals(intArrayOf(), actualList.toIntArray())
    }

    @Test
    fun `Iterator throws exception after request of data from empty tree`() {
        val iterator = BinarySearchTreeIterator<Int, Int>(null)

        assertThrows<NoSuchElementException> {
            iterator.next()
        }
    }

    @Test
    fun `Test iteration`() {
        val iterator = createIterator()

        val actual = mutableListOf<Int>()

        while (iterator.hasNext()) {
            actual.add(iterator.next().first)
        }

        val expected = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)

        assertArrayEquals(expected, actual.toIntArray())
    }

    @Test
    fun `Iterator throws exception after request of data from node following the last one`() {
        val iterator = createIterator()

        while (iterator.hasNext()) {
            iterator.next()
        }

        assertThrows<NoSuchElementException> {
            iterator.next()
        }
    }
}
