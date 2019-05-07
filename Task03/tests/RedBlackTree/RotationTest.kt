package RedBlackTree

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import RedBlackNode
import RedBlackTree

class RotationTest {
    private val expected = RedBlackTree<Int, Int>()
    private val actual = RedBlackTree<Int, Int>()

    @Test
    fun rightRotation() {
        /*          actual          expected
         *            6                6
         *         4     8  -->    3       8
         *       3   5           1   4
         *      1                     5
         */

        // building expected tree
        expected.root = RedBlackNode(6, 0, Color.Black)
        expected.root?.addRightSon(8, 0, Color.Black)
        expected.root?.addLeftSon(3, 0, Color.Black)
        expected.root?.left?.addRightSon(4, 0, Color.Black)
        expected.root?.left?.addLeftSon(1, 0, Color.Black)
        expected.root?.left?.right?.addRightSon(5, 0, Color.Black)

        // building actual tree
        actual.root = RedBlackNode(6, 0, Color.Black)
        actual.root?.addRightSon(8, 0, Color.Black)
        actual.root?.addLeftSon(4, 0, Color.Black)
        actual.root?.left?.addRightSon(5, 0, Color.Black)
        actual.root?.left?.addLeftSon(3, 0, Color.Black)
        actual.root?.left?.left?.addLeftSon(1, 0, Color.Black)

        actual.root?.left?.left?.rightRotate()

        assertEquals(actual, expected)
    }

    @Test
    fun leftRotation() {
        /*      actual              expected
         *        6                    6
         *     3      9       -->   3      12
         *   1      8   12        1      9
         *             11               8 11
         */

        // building expected tree
        expected.root = RedBlackNode(6, 0, Color.Black)
        expected.root?.addLeftSon(3, 0, Color.Black)
        expected.root?.left?.addLeftSon(1, 0, Color.Black)
        expected.root?.addRightSon(12, 0, Color.Black)
        expected.root?.right?.addLeftSon(9, 0, Color.Black)
        expected.root?.right?.left?.addLeftSon(8, 0, Color.Black)
        expected.root?.right?.left?.addRightSon(11, 0, Color.Black)

        // building actual tree
        actual.root = RedBlackNode(6, 0, Color.Black)
        actual.root?.addLeftSon(3, 0, Color.Black)
        actual.root?.left?.addLeftSon(1, 0, Color.Black)
        actual.root?.addRightSon(9, 0, Color.Black)
        actual.root?.right?.addLeftSon(8, 0, Color.Black)
        actual.root?.right?.addRightSon(12, 0, Color.Black)
        actual.root?.right?.right?.addLeftSon(11, 0, Color.Black)

        actual.root?.right?.right?.leftRotate()

        assertEquals(actual, expected)
    }
}
