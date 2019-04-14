import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

fun <K: Comparable<K>, V>BSTree<K, V>.isCorrect(treeIsEmpty: Boolean = false) =
    if (root == null)
        treeIsEmpty
    else
        DebugInfoBSSubtree(root!!).isCorrect && ! treeIsEmpty

@DisplayName("Tests for BinarySearchTree")
internal class TestsForBSTree {
    @DisplayName("Test #1: Simple test for structure tree")
    @Test
    fun test1() {
        val tree = BSTree<Int, Int>()
        assert(tree.isCorrect(treeIsEmpty = true))
        tree[7] = 5
        assert(tree.isCorrect())
        tree[7] = 1
        assert(tree.isCorrect())
        tree[3] = 8
        assert(tree.isCorrect())
        tree[7] = null
        assert(tree.isCorrect())
        tree[3] = null
        assert(tree.isCorrect(treeIsEmpty = true))
        tree[7] = null
        assert(tree.isCorrect(treeIsEmpty = true))
        tree[7] = 6
        assert(tree.isCorrect())
        tree[8] = null
        assert(tree.isCorrect())
        tree[8] = 3
        assert(tree.isCorrect())
        tree[4] = 11
        assert(tree.isCorrect())
        tree[6] = 7
        assert(tree.isCorrect())
        tree[5] = null
        assert(tree.isCorrect())
        tree[0] = 88
        assert(tree.isCorrect())
        tree[5] = 0
        assert(tree.isCorrect())
        tree[7] = null
        assert(tree.isCorrect())
        tree[9] = 55
        assert(tree.isCorrect())
        tree[1] = 3
        assert(tree.isCorrect())
        tree[1] = 8
        assert(tree.isCorrect())
        tree[8] = 10
        assert(tree.isCorrect())
        tree[2] = 11
        assert(tree.isCorrect())
        tree[3] = 12
        assert(tree.isCorrect())
        tree[4] = null
        assert(tree.isCorrect())
        tree[7] = null
        assert(tree.isCorrect())
        tree[6] = null
        assert(tree.isCorrect())
        tree[0] = null
        assert(tree.isCorrect())
        tree[2] = null
        assert(tree.isCorrect())
        tree[2] = 36
        assert(tree.isCorrect())
        tree[9] = 8
        assert(tree.isCorrect())
        tree[2] = null
        assert(tree.isCorrect())
        tree[9] = null
        assert(tree.isCorrect())
        tree[3] = null
        assert(tree.isCorrect())
        tree[1] = 9
        assert(tree.isCorrect())
        tree[5] = null
        assert(tree.isCorrect())
        tree[0] = 8
        assert(tree.isCorrect())
        tree[1] = null
        assert(tree.isCorrect())
        tree[7] = 77
        assert(tree.isCorrect())
        tree[8] = null
        assert(tree.isCorrect())
        tree[0] = null
        assert(tree.isCorrect())
        tree[7] = null
        assert(tree.isCorrect(treeIsEmpty = true))
        tree[9] = 99
        assert(tree.isCorrect())
        tree[2] = 13
        assert(tree.isCorrect())
        tree[1] = null
        assert(tree.isCorrect())
        tree[2] = 11
        assert(tree.isCorrect())
        tree[8] = 6
        assert(tree.isCorrect())
        tree[9] = null
        assert(tree.isCorrect())
    }
    @DisplayName("Test #2: Thorough analysis of structure tree")
    @Test
    fun test2() {
        val tree = BSTree<Int, Int>()
        assertEquals(tree.toString(), "BSTree: {}")
        tree[7] = 5
        assertEquals(tree.toString(), "BSTree: {(7 to 5)}")
        tree[7] = 1
        assertEquals(tree.toString(), "BSTree: {(7 to 1)}")
        tree[3] = 8
        assertEquals(tree.toString(), "BSTree: {(7 to 1) left: {(3 to 8)}}")
        tree[7] = null
        assertEquals(tree.toString(), "BSTree: {(3 to 8)}")
        tree[3] = null
        assertEquals(tree.toString(), "BSTree: {}")
        tree[7] = null
        assertEquals(tree.toString(), "BSTree: {}")
        tree[7] = 6
        assertEquals(tree.toString(), "BSTree: {(7 to 6)}")
        tree[8] = null
        assertEquals(tree.toString(), "BSTree: {(7 to 6)}")
        tree[8] = 3
        assertEquals(tree.toString(), "BSTree: {(7 to 6) right: {(8 to 3)}}")
        tree[4] = 11
        assertEquals(tree.toString(), "BSTree: {(7 to 6) left: {(4 to 11)} right: {(8 to 3)}}")
        tree[6] = 7
        assertEquals(tree.toString(), "BSTree: {(7 to 6) left: {(4 to 11) right: {(6 to 7)}} " +
                "right: {(8 to 3)}}")
        tree[5] = null
        assertEquals(tree.toString(), "BSTree: {(7 to 6) left: {(4 to 11) right: {(6 to 7)}} " +
                "right: {(8 to 3)}}")
        tree[5] = 0
        assertEquals(tree.toString(), "BSTree: {(7 to 6) left: {(4 to 11) right: {(6 to 7) left: " +
                "{(5 to 0)}}} right: {(8 to 3)}}")
        tree[7] = null
        assertEquals(tree.toString(), "BSTree: {(8 to 3) left: {(4 to 11) right: {(6 to 7) left: " +
                "{(5 to 0)}}}}")
        tree[9] = 55
        assertEquals(tree.toString(), "BSTree: {(8 to 3) left: {(4 to 11) right: {(6 to 7) left: " +
                "{(5 to 0)}}} right: {(9 to 55)}}")
        tree[1] = 3
        assertEquals(tree.toString(), "BSTree: {(8 to 3) left: {(4 to 11) left: {(1 to 3)} right: " +
                "{(6 to 7) left: {(5 to 0)}}} right: {(9 to 55)}}")
        tree[1] = 8
        assertEquals(tree.toString(), "BSTree: {(8 to 3) left: {(4 to 11) left: {(1 to 8)} right: " +
                "{(6 to 7) left: {(5 to 0)}}} right: {(9 to 55)}}")
        tree[8] = 10
        assertEquals(tree.toString(), "BSTree: {(8 to 10) left: {(4 to 11) left: {(1 to 8)} right: " +
                "{(6 to 7) left: {(5 to 0)}}} right: {(9 to 55)}}")
        tree[2] = 11
        assertEquals(tree.toString(), "BSTree: {(8 to 10) left: {(4 to 11) left: {(1 to 8) right: " +
                "{(2 to 11)}} right: {(6 to 7) left: {(5 to 0)}}} right: {(9 to 55)}}")
        tree[3] = 12
        assertEquals(tree.toString(), "BSTree: {(8 to 10) left: {(4 to 11) left: {(1 to 8) right: " +
                "{(2 to 11) right: {(3 to 12)}}} right: {(6 to 7) left: {(5 to 0)}}} right: {(9 to 55)}}")
        tree[4] = null
        assertEquals(tree.toString(), "BSTree: {(8 to 10) left: {(5 to 0) left: {(1 to 8) right: " +
                "{(2 to 11) right: {(3 to 12)}}} right: {(6 to 7)}} right: {(9 to 55)}}")
        tree[7] = null
        assertEquals(tree.toString(), "BSTree: {(8 to 10) left: {(5 to 0) left: {(1 to 8) right: " +
                "{(2 to 11) right: {(3 to 12)}}} right: {(6 to 7)}} right: {(9 to 55)}}")
        tree[6] = null
        assertEquals(tree.toString(), "BSTree: {(8 to 10) left: {(5 to 0) left: {(1 to 8) right: " +
                "{(2 to 11) right: {(3 to 12)}}}} right: {(9 to 55)}}")
        tree[2] = null
        assertEquals(tree.toString(), "BSTree: {(8 to 10) left: {(5 to 0) left: {(1 to 8) right: " +
                "{(3 to 12)}}} right: {(9 to 55)}}")
        tree[2] = 36
        assertEquals(tree.toString(), "BSTree: {(8 to 10) left: {(5 to 0) left: {(1 to 8) right: " +
                "{(3 to 12) left: {(2 to 36)}}}} right: {(9 to 55)}}")
        tree[9] = 8
        assertEquals(tree.toString(), "BSTree: {(8 to 10) left: {(5 to 0) left: {(1 to 8) right: " +
                "{(3 to 12) left: {(2 to 36)}}}} right: {(9 to 8)}}")
        tree[2] = null
        assertEquals(tree.toString(), "BSTree: {(8 to 10) left: {(5 to 0) left: {(1 to 8) right: " +
                "{(3 to 12)}}} right: {(9 to 8)}}")
        tree[9] = null
        assertEquals(tree.toString(), "BSTree: {(8 to 10) left: {(5 to 0) left: {(1 to 8) right: " +
                "{(3 to 12)}}}}")
        tree[3] = null
        assertEquals(tree.toString(), "BSTree: {(8 to 10) left: {(5 to 0) left: {(1 to 8)}}}")
        tree[1] = 9
        assertEquals(tree.toString(), "BSTree: {(8 to 10) left: {(5 to 0) left: {(1 to 9)}}}")
        tree[5] = null
        assertEquals(tree.toString(), "BSTree: {(8 to 10) left: {(1 to 9)}}")
        tree[1] = null
        assertEquals(tree.toString(), "BSTree: {(8 to 10)}")
        tree[7] = 77
        assertEquals(tree.toString(), "BSTree: {(8 to 10) left: {(7 to 77)}}")
        tree[8] = null
        assertEquals(tree.toString(), "BSTree: {(7 to 77)}")
        tree[7] = null
        assertEquals(tree.toString(), "BSTree: {}")
    }
}
