package AVLTree

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import AVLNode
import AVLTree

class RotationTest {
    private val expected = AVLTree<Int, Int>()
    private val actual = AVLTree<Int, Int>()

    @Test
    fun rightRotation() {
        /*          actual          expected
         *            6                6
         *         4     8  -->    3       8
         *       3   5           1   4
         *      1                     5
         */

        // building expected tree
        expected.root = AVLNode(6, 0)
        expected.root?.addRightSon(8, 0)
        expected.root?.addLeftSon(3, 0)
        expected.root?.left?.addRightSon(4, 0)
        expected.root?.left?.addLeftSon(1, 0)
        expected.root?.left?.right?.addRightSon(5, 0)

        // building actual tree
        actual.root = AVLNode(6, 0)
        actual.root?.addRightSon(8, 0)
        actual.root?.addLeftSon(4, 0)
        actual.root?.left?.addRightSon(5, 0)
        actual.root?.left?.addLeftSon(3, 0)
        actual.root?.left?.left?.addLeftSon(1, 0)

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
        expected.root = AVLNode(6, 0)
        expected.root?.addLeftSon(3, 0)
        expected.root?.left?.addLeftSon(1, 0)
        expected.root?.addRightSon(12, 0)
        expected.root?.right?.addLeftSon(9, 0)
        expected.root?.right?.left?.addLeftSon(8, 0)
        expected.root?.right?.left?.addRightSon(11, 0)

        // building actual tree
        actual.root = AVLNode(6, 0)
        actual.root?.addLeftSon(3, 0)
        actual.root?.left?.addLeftSon(1, 0)
        actual.root?.addRightSon(9, 0)
        actual.root?.right?.addLeftSon(8, 0)
        actual.root?.right?.addRightSon(12, 0)
        actual.root?.right?.right?.addLeftSon(11, 0)

        actual.root?.right?.right?.leftRotate()

        assertEquals(actual, expected)
    }

    @Test
    fun bigLeftRotation() {
        /*        actual             expected
         *          8                   8
         *      3       9    -->    4       9
         *    1   5               3   5
         *       4 7             1     7
         *
         */

        // building expected tree
        expected.root = AVLNode(8, 0)
        expected.root?.addRightSon(9, 0)
        expected.root?.addLeftSon(4, 0)
        expected.root?.left?.addRightSon(5, 0)
        expected.root?.left?.right?.addRightSon(7, 0)
        expected.root?.left?.addLeftSon(3, 0)
        expected.root?.left?.left?.addLeftSon(1, 0)

        // building actual tree
        actual.root = AVLNode(8, 0)
        actual.root?.addRightSon(9, 0)
        actual.root?.addLeftSon(3, 0)
        actual.root?.left?.addRightSon(5, 0)
        actual.root?.left?.right?.addRightSon(7, 0)
        actual.root?.left?.right?.addLeftSon(4, 0)
        actual.root?.left?.addLeftSon(1, 0)

        actual.root?.left?.right?.bigLeftRotate()

        assertEquals(actual, expected)
    }

    @Test
    fun bigRightRotate() {
        /*       actual            expected
         *         8                  8
         *     5      10    -->   4      10
         *  3     7             3   5
         * 1 4                 1     7
         */

        // building expected tree
        expected.root = AVLNode(8, 0)
        expected.root?.addRightSon(10, 0)
        expected.root?.addLeftSon(4, 0)
        expected.root?.left?.addRightSon(5, 0)
        expected.root?.left?.right?.addRightSon(7, 0)
        expected.root?.left?.addLeftSon(3, 0)
        expected.root?.left?.left?.addLeftSon(1, 0)

        // building actual tree
        actual.root = AVLNode(8, 0)
        actual.root?.addRightSon(10, 0)
        actual.root?.addLeftSon(5, 0)
        actual.root?.left?.addRightSon(7, 0)
        actual.root?.left?.addLeftSon(3, 0)
        actual.root?.left?.left?.addRightSon(4, 0)
        actual.root?.left?.left?.addLeftSon(1, 0)

        actual.root?.left?.left?.bigRightRotate()

        assertEquals(actual, expected)
    }
}
