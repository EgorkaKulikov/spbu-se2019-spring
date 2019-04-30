import org.junit.Assert.assertArrayEquals
import org.junit.Test
import java.util.Random

class BinarySearchTreeIterationTest {

    private val value = 100
    private val actualTree = BinarySearchTree<Int, Int>()
    private val random = Random()

    @Test
    fun iterateBamboo() {
        val keys = arrayOf(0, 1, 2, 3, 4, 5)

        for (key in keys)
            actualTree[key] = value

        assertArrayEquals(keys, actualTree.keys)
    }

    @Test
    fun iterateFullTree() {
        val keys = arrayOf(
                               8,
                         4,          12,
                      2,    6,   10,     14,
                    1, 3, 5, 7, 9, 11, 13, 15
        )

        for (key in keys)
            actualTree[key] = value

        assertArrayEquals(keys.sortedArray(), actualTree.keys)
    }

    @Test
    fun iterateBentBamboo() {
        val keys = arrayOf(10, 3, 4, 9, 7, 6, 5)

        for (key in keys)
            actualTree[key] = value

        assertArrayEquals(keys.sortedArray(), actualTree.keys)
    }

    @Test
    fun iterateEmpty() {
        val keys = arrayOf<Int>()

        assertArrayEquals(keys, actualTree.keys)
    }

    @Test
    fun iterateOnlyRoot() {
        val keys = arrayOf(0)

        actualTree[0] = value

        assertArrayEquals(keys, actualTree.keys)
    }

    @Test
    fun iterateRandomStress() {
        val keys = Array(SIZE) { random.nextInt() }

        for (key in keys)
            actualTree[key] = value

        assertArrayEquals(keys.sortedArray(), actualTree.keys)
    }

}
