import binary.BinaryTreeIterator
import binary.BinaryTreeNode
import org.junit.jupiter.api.Assertions.assertArrayEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class BinaryTreeIteratorTest {

    private fun createIterator(): BinaryTreeIterator<Int> {
        val root = BinaryTreeNode(5).apply {
            left = BinaryTreeNode(3).apply {
                left = BinaryTreeNode(2).apply {
                    left = BinaryTreeNode(1)
                }
                right = BinaryTreeNode(4)
            }
            right = BinaryTreeNode(8).apply {
                left = BinaryTreeNode(6).apply {
                    right = BinaryTreeNode(7)
                }
                right = BinaryTreeNode(10).apply {
                    left = BinaryTreeNode(9)
                }
            }
        }

        return BinaryTreeIterator(root)
    }

    @Test
    fun `Test iteration for empty tree`() {
        val iterator = BinaryTreeIterator<Int>(null)
        val actualList = mutableListOf<Int>()

        while (iterator.hasNext()) {
            actualList.add(iterator.next())
        }

        assertArrayEquals(intArrayOf(), actualList.toIntArray())
    }

    @Test
    fun `Iterator throws exception after request of data from empty tree`() {
        val iterator = BinaryTreeIterator<Int>(null)

        assertThrows<NoSuchElementException> {
            iterator.next()
        }
    }

    @Test
    fun `Test iteration`() {
        val iterator = createIterator()

        val actual = mutableListOf<Int>()

        while (iterator.hasNext()) {
            actual.add(iterator.next())
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
