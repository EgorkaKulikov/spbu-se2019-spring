import binarySearchTree.BSTree
import binarySearchTree.Node
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

@DisplayName("Tests for Binary Search tree")
internal class TestBinarySearchTree {

    private val tree = BSTree<Int, Int>()

    @DisplayName("Search existing key")
    @Test
    fun searching_existing_key_succes_asserted()
    {

        for (testInputLength in 0..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }

            testInput.shuffle()

            for (data in testInput) {
                tree.insert(data, data)
            }

            for (data in testInput) {
                assertEquals(Pair(data, data), tree.find(data))
            }

            tree.clear()

        }

    }

    @DisplayName("Search in root")
    @Test
    fun searching_root_success_asserted()
    {

        tree.insert(1, 1)

        assertEquals(Pair(1, 1), tree.find(1))

    }

    @DisplayName("Search multiple")
    @Test
    fun searching_different_nodes_success_asserted()
    {

        tree.insert(1, 1)
        tree.insert(2, 2)

        assertEquals(Pair(2, 2), tree.find(2))
        assertEquals(Pair(1, 1), tree.find(1))
    }

    @DisplayName("Search non existent key in empty tree")
    @Test
    fun searching_not_existing_key_in_empty_tree_failure_asserted()
    {

        assertNull(tree.find(0))
        assertNull(tree.find(101))

    }

    @DisplayName("Search non existing key in nonempty tree")
    @Test
    fun searching_not_existing_key_in_not_empty_tree_failure_asserted()
    {

        val testInput: MutableList<Int> = MutableList(100) { it + 1 }

        testInput.shuffle()

        for (data in testInput) {
            tree.insert(data, data)
        }

        assertNull(tree.find(0))
        assertNull(tree.find(101))

    }

    @DisplayName("Multiple root insert check")
    @Test
    fun multiple_insert_to_root_success_asserted()
    {

        tree.insert(1, 1)
        tree.insert(1, 2)

        assertEquals(tree.root!!.key, 1)
        assertEquals(tree.root!!.value, 2)
        assertTrue(tree.root!!.parent == null)
        assertTrue(tree.root!!.leftChild == null)
        assertTrue(tree.root!!.rightChild == null)

    }

    @DisplayName("Insert check")
    @Test
    fun insert_success_asserted()
    {

        for (testInputLength in 0..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }

            testInput.shuffle()

            for (data in testInput) {
                tree.insert(data, data)
            }

            for (x in testInput) {
                assertEquals(tree.find(x), Pair(x, x))
            }

            tree.clear()

        }

    }

    @DisplayName("Iterate empty tree")
    @Test
    fun empty_tree_iteration_success_asserted()
    {

        for (i in tree)
            assertEquals(-1, 1)

    }

    @DisplayName("Iterate tree")
    @Test
    fun tree_iteration_succes_asserted()
    {

        for (testInputLength in 1..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1 }

            testInput.shuffle()

            for (data in testInput) {
                tree.insert(data, data)
            }

            var cur = 0

            for (i in tree) {
                ++cur
                assertEquals(tree.find(i.key), Pair(i.key, i.value))
            }

            tree.clear()

        }

    }

    @DisplayName("Iterate tree in direct order")
    @Test
    fun tree_iteration_in_direct_order_success_asserted()
    {

        for (testInputLength in 0..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { it + 1}

            for (data in testInput) {
                tree.insert(data, data)
            }

            var cur = 0

            for (i in tree) {
                ++cur
                assertEquals(tree.find(i.key), Pair(i.key, i.value))
            }

            tree.clear()

        }

    }

    @DisplayName("Iterate tree in reverse order")
    @Test
    fun tree_iteration_in_reverse_order_success_asserted()
    {

        for (testInputLength in 1..1000) {

            val testInput: MutableList<Int> = MutableList(testInputLength) { testInputLength - it }

            for (data in testInput) {
                tree.insert(data, data)
            }

            var cur = 0

            for (i in tree) {
                ++cur
                assertEquals(tree.find(i.key), Pair(i.key, i.value))
            }

            tree.clear()

        }

    }

    @DisplayName("Stack overflow if looped")
    @Test
    fun tree_loop_iteration_assert_stack_overflow()
    {
        assertFailsWith(StackOverflowError::class)
        {
            val B = Node(2, 2)
            val C = Node(3, 3)

            tree.insert(1, 1)

            tree.root!!.leftChild = B
            B.rightChild = tree.root!!

            tree.root!!.rightChild = C
            B.leftChild = C

            C.leftChild = tree.root!!
            C.rightChild = B

            for (i in tree)
            {

            }
        }

        tree.clear()
    }

    @DisplayName("Stack overflow if root is his own child")
    @Test
    fun tree_endless_iteration_assert_stack_overflow()
    {
        assertFailsWith(StackOverflowError::class)
        {
            tree.insert(1, 1)

            tree.root!!.leftChild = tree.root

            for (i in tree)
            {

            }
        }

        tree.clear()
    }
}
