import org.junit.Assert
import org.junit.Test
import java.util.*

class BinarySearchTreeIterationTest {

    private val value = 100
    private val actualTree = BinarySearchTree<Int, Int>()
    private val random = Random()

    @Test
    fun iterateBamboo() {
        val keys = arrayOf(0, 1, 2, 3, 4, 5)

        for (key in keys)
            actualTree[key] = value

        Assert.assertArrayEquals(keys, actualTree.keys)
    }

    @Test
    fun iterateFullTree() {
        val keys = arrayOf(
            8,
            4, 12,
            2, 6, 10, 14,
            1, 3, 5, 7, 9, 11, 13, 15
        )

        for (key in keys)
            actualTree[key] = value

        Assert.assertArrayEquals(keys.sortedArray(), actualTree.keys)
    }

    @Test
    fun iterateBentBamboo() {
        val keys = arrayOf(10, 3, 4, 9, 7, 6, 5)

        for (key in keys)
            actualTree[key] = value

        Assert.assertArrayEquals(keys.sortedArray(), actualTree.keys)
    }

    @Test
    fun iterateEmpty() {
        val keys = arrayOf<Int>()
        var message : String? = ""
        try {
            actualTree.iterator().next()
        }
        catch (e : NoSuchElementException)
        {
            message = e.message
        }
        Assert.assertEquals(message, ("Empty tree"))
    }

    @Test
    fun iterateOnlyRoot() {
        val keys = arrayOf(0)

        actualTree[0] = value

        Assert.assertArrayEquals(keys, actualTree.keys)
    }

    @Test
    fun iterateRandomStress() {
        val keys = Array(SIZE) { random.nextInt() }

        for (key in keys)
            actualTree[key] = value

        Assert.assertArrayEquals(keys.sortedArray(), actualTree.keys)
    }

    @Test
    fun iteratorBamboo() {
        val keys = arrayOf(0, 1, 2, 3, 4, 5)
        for (key in keys)
            actualTree[key] = value
        val iterator = actualTree.iterator()
        val result = arrayListOf<Int>()
        while (iterator.hasNext())
        {
            result.add(iterator.next().key)
        }
        Assert.assertArrayEquals(keys, result.toArray())
    }

    @Test
    fun iteratorFullTree() {
        val keys = arrayOf(
            8,
            4, 12,
            2, 6, 10, 14,
            1, 3, 5, 7, 9, 11, 13, 15)
        for (key in keys)
            actualTree[key] = value
        val iterator = actualTree.iterator()
        val result = arrayListOf<Int>()
        while (iterator.hasNext())
        {
            result.add(iterator.next().key)
        }
        Assert.assertArrayEquals(keys.sortedArray(), result.toArray())
    }

    @Test
    fun iteratorBentBamboo() {
        val keys = arrayOf(10, 3, 4, 9, 7, 6, 5)
        for (key in keys)
            actualTree[key] = value
        val iterator = actualTree.iterator()
        val result = arrayListOf<Int>()
        while (iterator.hasNext())
        {
            result.add(iterator.next().key)
        }
        Assert.assertArrayEquals(keys.sortedArray(), result.toArray())
    }

    @Test
    fun iteratorOnlyRoot() {
        val keys = arrayOf(0)
        actualTree[0] = value
        val iterator = actualTree.iterator()
        val result = arrayListOf<Int>()
        while (iterator.hasNext())
        {
            result.add(iterator.next().key)
        }
        Assert.assertArrayEquals(keys.sortedArray(), result.toArray())
    }

    @Test
    fun iteratorRandomStress() {
        val keys = Array(SIZE) { random.nextInt() }
        for (key in keys)
            actualTree[key] = value
        val iterator = actualTree.iterator()
        val result = arrayListOf<Int>()
        while (iterator.hasNext())
        {
            result.add(iterator.next().key)
        }
        Assert.assertArrayEquals(keys.sortedArray(), result.toArray())
    }
}