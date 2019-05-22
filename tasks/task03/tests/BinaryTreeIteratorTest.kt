import binary.BinaryNode
import binary.BinaryTreeIterator
import org.junit.Test
import org.junit.Assert.assertArrayEquals

class BinaryTreeIteratorTest {

    private fun createIterableTree(root: BinaryNode<Int>?) = object : Iterable<Int> {

        override fun iterator() = BinaryTreeIterator(root) { it }
    }

    @Test
    fun `Test iteration for empty tree`() {
        val tree = createIterableTree(null as BinaryNode<Int>?)

        val actualList = mutableListOf<Int>()

        for (value in tree) {
            actualList.add(value)
        }

        val expected = intArrayOf()
        val actual = actualList.toIntArray()

        assertArrayEquals(expected, actual)
    }

    @Test
    fun `Test iteration`() {
        val root = BinaryNode(5).apply {
            left = BinaryNode(3).apply {
                left = BinaryNode(2).apply {
                    left = BinaryNode(1)
                }
                right = BinaryNode(4)
            }
            right = BinaryNode(8).apply {
                left = BinaryNode(6).apply {
                    right = BinaryNode(7)
                }
                right = BinaryNode(10).apply {
                    left = BinaryNode(9)
                }
            }
        }

        val tree = createIterableTree(root)

        val actualList = mutableListOf<Int>()

        for (value in tree) {
            actualList.add(value)
        }

        val expected = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
        val actual = actualList.toIntArray()

        assertArrayEquals(expected, actual)
    }
}
