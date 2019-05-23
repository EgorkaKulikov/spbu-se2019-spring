import org.junit.Assert.assertEquals
import org.junit.Test

class AVLTreeInsertionTest {

    private val value = 100
    private val expectedTree = AVLTree<Int, Int>()
    private val actualTree = AVLTree<Int, Int>()

    @Test
    fun insertRootCase() {
        // Building expected structure of tree
        expectedTree.root = AVLNode(0, value)

        // Making tree using existing methods
        actualTree[0] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootLeftCase() {
        // Building expected structure of tree
        expectedTree.root = AVLNode(0, value)

        expectedTree.root!!.addLeftSon(-100, value)

        expectedTree.root?.updateHeight()

        // Making tree using existing methods
        for (key in arrayOf(0, -100))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootRightCase() {
        // Building expected structure of tree
        expectedTree.root = AVLNode(0, value)

        expectedTree.root!!.addRightSon(100, value)

        expectedTree.root?.updateHeight()

        // Making tree using existing methods
        for (key in arrayOf(0, 100))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootLeftLeftCase() {
        // Building expected structure of tree
        expectedTree.root = AVLNode(-100, value)

        expectedTree.root!!.addLeftSon(-200, value)

        expectedTree.root!!.addRightSon(0, value)

        expectedTree.root?.updateHeight()

        // Making tree using existing methods
        for (key in arrayOf(0, -100, -200))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootLeftRightCase() {
        // Building expected structure of tree
        expectedTree.root = AVLNode(-50, value)

        expectedTree.root!!.addLeftSon(-100, value)

        expectedTree.root!!.addRightSon(0, value)

        expectedTree.root?.updateHeight()

        // Making tree using existing methods
        for (key in arrayOf(0, -100, -50))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootRightLeftCase() {
        // Building expected structure of tree
        expectedTree.root = AVLNode(50, value)

        expectedTree.root!!.addLeftSon(0, value)

        expectedTree.root!!.addRightSon(100, value)

        expectedTree.root?.updateHeight()

        // Making tree using existing methods
        for (key in arrayOf(0, 100, 50))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootRightRightCase() {
        // Building expected structure of tree
        expectedTree.root = AVLNode(100, value)

        expectedTree.root!!.addLeftSon(0, value)

        expectedTree.root!!.addRightSon(200, value)

        expectedTree.root?.updateHeight()

        // Making tree using existing methods
        for (key in arrayOf(0, 100, 200))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertManyBalancing() {
        // Building expected structure of tree
        expectedTree.root = AVLNode(50, value)

        expectedTree.root!!.addLeftSon(0, value)

        expectedTree.root!!.addRightSon(100, value)

        expectedTree.root!!.left!!.addLeftSon(-50, value)

        expectedTree.root!!.left!!.addRightSon(25, value)

        expectedTree.root!!.right!!.addLeftSon(62, value)

        expectedTree.root!!.right!!.addRightSon(200, value)

        expectedTree.root!!.left!!.left!!.addLeftSon(-75, value)

        expectedTree.root!!.left!!.left!!.addRightSon(-25, value)

        expectedTree.root!!.right!!.left!!.addLeftSon(56, value)

        expectedTree.root!!.right!!.left!!.addRightSon(75, value)

        // Making tree using existing methods
        for (key in arrayOf(0, 100, 200, 50, -25, 75, 62, 56, 25, -75, -50))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

}