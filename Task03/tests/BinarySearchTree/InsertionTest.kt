package BinarySearchTree

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import BinarySearchNode
import BinarySearchTree

class InsertionTest {

    private val value = 100
    private val expectedTree = BinarySearchTree<Int, Int>()
    private val actualTree = BinarySearchTree<Int, Int>()

    @Test
    fun insertRootCase() {
        // Building expected structure of tree
        expectedTree.root = BinarySearchNode(0, value)

        // Making tree using existing methods
        actualTree[0] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootLeftCase() {
        // Building expected structure of tree
        expectedTree.root = BinarySearchNode(0, value)

        expectedTree.root!!.addLeftSon(-100, value)

        // Making tree using existing methods
        for (key in arrayOf(0, -100))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootRightCase() {
        // Building expected structure of tree
        expectedTree.root = BinarySearchNode(0, value)

        expectedTree.root!!.addRightSon(100, value)

        // Making tree using existing methods
        for (key in arrayOf(0, 100))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootLeftLeftCase() {
        // Building expected structure of tree
        expectedTree.root = BinarySearchNode(0, value)

        expectedTree.root!!.addLeftSon(-100, value)

        expectedTree.root!!.left!!.addLeftSon(-200, value)

        // Making tree using existing methods
        for (key in arrayOf(0, -100, -200))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootLeftRightCase() {
        // Building expected structure of tree
        expectedTree.root = BinarySearchNode(0, value)

        expectedTree.root!!.addLeftSon(-100, value)

        expectedTree.root!!.left!!.addRightSon(-50, value)

        // Making tree using existing methods
        for (key in arrayOf(0, -100, -50))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootRightLeftCase() {
        // Building expected structure of tree
        expectedTree.root = BinarySearchNode(0, value)

        expectedTree.root!!.addRightSon(100, value)

        expectedTree.root!!.right!!.addLeftSon(50, value)

        // Making tree using existing methods
        for (key in arrayOf(0, 100, 50))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootRightRightCase() {
        // Building expected structure of tree
        expectedTree.root = BinarySearchNode(0, value)

        expectedTree.root!!.addRightSon(100, value)

        expectedTree.root!!.right!!.addRightSon(200, value)

        // Making tree using existing methods
        for (key in arrayOf(0, 100, 200))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertByScenario() {
        // Tree structure:
        //................0.................
        //......-100..............100.......
        //..-200...............50.....200...
        //......-150...............150...300
        //......................125.........

        // Node with number i has sons with numbers (2*i) and (2*i + 1)
        // Root has number 1

        // Building expected structure of tree
        expectedTree.root = BinarySearchNode(0, value)

        expectedTree.root!!.addLeftSon(-100, value)

        expectedTree.root!!.addRightSon(100, value)

        expectedTree.root!!.left!!.addLeftSon(-200, value)

        expectedTree.root!!.right!!.addLeftSon(50, value)

        expectedTree.root!!.right!!.addRightSon(200, value)

        expectedTree.root!!.left!!.left!!.addRightSon(-150, value)

        expectedTree.root!!.right!!.right!!.addLeftSon(150, value)

        expectedTree.root!!.right!!.right!!.addRightSon(300, value)

        expectedTree.root!!.right!!.right!!.left!!.addLeftSon(125, value)

        // Making tree using existing methods
        for (key in arrayOf(0, 100, -100, 50, -200, 200, 150, 125, -150, 300))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

}
