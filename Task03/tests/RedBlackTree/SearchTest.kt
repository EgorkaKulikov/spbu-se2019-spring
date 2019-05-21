package RedBlackTree

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import java.util.Random
import RedBlackTree

class SearchTest {

    private val expectedAnswers = mutableMapOf<Int, Int>()
    private val actualTree = RedBlackTree<Int, Int>()
    private val random = Random()
    private val SIZE = 1e6.toInt()

    @BeforeEach
    fun createTreeForSearching() {
        while (expectedAnswers.size < SIZE) {
            val key = random.nextInt()
            val value = random.nextInt()
            expectedAnswers[key] = value
            actualTree[key] = value
        }
    }

    @Test
    fun searchForAll() {
        for (key in expectedAnswers.keys)
            assertEquals(actualTree[key], expectedAnswers[key])
    }

    @Test
    fun searchForNonExistentKey() {
        for (key in 1..(2 * SIZE)) {
            if ( ! (key in expectedAnswers.keys))
                assertNull(actualTree[key])
        }
    }

    @Test
    fun searchInEmptyTree() {
        val emptyTree = RedBlackTree<Int, Int>()
        for (key in 1..SIZE)
            assertNull(emptyTree[key])
    }

}
