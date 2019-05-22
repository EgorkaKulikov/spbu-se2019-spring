import binary.BinaryTreeRunner
import binary.BinarySearchTree
import binary.SearchData
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlin.random.Random

interface BalancedBinarySearchTreeTest<Data : SearchData<Int, Int>> {

    fun checkBalance(runner: BinaryTreeRunner<Data>): Boolean

    fun createTree(): BinarySearchTree<Int, Int, Data>

    private fun createRandomKeys(size: Int) = Array(size) { Random.nextInt() }

    private fun createSortedKeys(size: Int) = Array(size) { it }

    private fun testCorrectness(keys: Array<Int>) {
        val tree = createTree()

        for (key in keys) {
            tree[key] = key + 1
        }

        var hasAllKeys = true

        for (key in keys) {
            if (tree[key] != key + 1) {
                hasAllKeys = false
                break
            }
        }

        val isBalanced = checkBalance(tree.getRunner())

        assertTrue(hasAllKeys)
        assertTrue(isBalanced)
    }

    @Test
    fun `Test tree correctness with a sorted key set`() {
        testCorrectness(createSortedKeys(1000000))
    }

    @Test
    fun `Test tree correctness with a random key set`() {
        testCorrectness(createRandomKeys(1000000))
    }
}
