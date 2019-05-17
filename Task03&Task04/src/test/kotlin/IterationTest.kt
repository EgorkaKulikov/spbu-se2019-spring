import org.junit.Assert.assertArrayEquals
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException
import java.util.Random
import kotlin.NoSuchElementException

class BinarySearchTreeIterationTest {

    private val value = 100
    private val actualTree = BinarySearchTree<Int, Int>()
    private val random = Random()

    @Test
    fun iterateBamboo() {
        val keys = arrayOf(0, 1, 2, 3, 4, 5)

        for (key in keys)
            actualTree[key] = value

        val iterator = actualTree.iterator()
        val result = arrayListOf<Int>()
        while (iterator.hasNext()) {
            result.add(iterator.next().key)
        }

        assertArrayEquals(keys, result.toArray())
    }

    @Test
    fun iterateFullTree() {
        val keys = arrayOf(
                8,
                4, 12,
                2, 6, 10, 14,
                1, 3, 5, 7, 9, 11, 13, 15)

        for (key in keys)
            actualTree[key] = value

        val iterator = actualTree.iterator()
        val result = arrayListOf<Int>()
        while (iterator.hasNext()) {
            result.add(iterator.next().key)
        }

        assertArrayEquals(keys.sortedArray(), result.toArray())
    }

    @Test
    fun iterateBentBamboo() {
        val keys = arrayOf(10, 3, 4, 9, 7, 6, 5)

        for (key in keys)
            actualTree[key] = value

        val iterator = actualTree.iterator()
        val result = arrayListOf<Int>()
        while (iterator.hasNext()) {
            result.add(iterator.next().key)
        }

        assertArrayEquals(keys.sortedArray(), result.toArray())
    }

    @Test
    fun iterateOnlyRoot() {
        val keys = arrayOf(0)

        actualTree[0] = value

        val iterator = actualTree.iterator()
        val result = arrayListOf<Int>()
        while (iterator.hasNext()) {
            result.add(iterator.next().key)
        }

        assertArrayEquals(keys, result.toArray())
    }

    @Test
    fun iterateRandomStress() {
        val keys = Array(SIZE) { random.nextInt() }

        for (key in keys)
            actualTree[key] = value

        val iterator = actualTree.iterator()
        val result = arrayListOf<Int>()
        while (iterator.hasNext()) {
            result.add(iterator.next().key)
        }

        assertArrayEquals(keys.sortedArray(), result.toArray())
    }

    @get:Rule
    val thrown: ExpectedException = ExpectedException.none()

    @Test
    fun iterateEmpty() {
        thrown.expect(NoSuchElementException::class.java)
        thrown.expectMessage("Empty tree")
        actualTree.iterator().next()
    }


}
