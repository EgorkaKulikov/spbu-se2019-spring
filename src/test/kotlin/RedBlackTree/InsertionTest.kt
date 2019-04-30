import org.junit.Assert.assertEquals
import org.junit.Test

class RedBlackTreeInsertionTest {

    private val value = 100
    private val expectedTree = RedBlackTree<Int, Int>()
    private val actualTree = RedBlackTree<Int, Int>()

    @Test
    fun emptyTreeCase() {
        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootCase() {
        // Building expected structure of tree
        expectedTree.root = RedBlackNode(0, value, Color.Black)

        // Making tree using existing methods
        actualTree[0] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootLeftCase() {
        // Building expected structure of tree
        expectedTree.root = RedBlackNode(0, value, Color.Black)

        expectedTree.root!!.addLeftSon(-100, value, Color.Red)

        // Making tree using existing methods
        for (key in arrayOf(0, -100))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootRightCase() {
        // Building expected structure of tree
        expectedTree.root = RedBlackNode(0, value, Color.Black)

        expectedTree.root!!.addRightSon(100, value, Color.Red)

        // Making tree using existing methods
        for (key in arrayOf(0, 100))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootLeftLeftCase() {
        // Building expected structure of tree
        expectedTree.root = RedBlackNode(-100, value, Color.Black)

        expectedTree.root!!.addLeftSon(-200, value, Color.Red)

        expectedTree.root!!.addRightSon(0, value, Color.Red)

        // Making tree using existing methods
        for (key in arrayOf(0, -100, -200))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootLeftRightCase() {
        // Building expected structure of tree
        expectedTree.root = RedBlackNode(-50, value, Color.Black)

        expectedTree.root!!.addLeftSon(-100, value, Color.Red)

        expectedTree.root!!.addRightSon(0, value, Color.Red)

        // Making tree using existing methods
        for (key in arrayOf(0, -100, -50))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootRightLeftCase() {
        // Building expected structure of tree
        expectedTree.root = RedBlackNode(50, value, Color.Black)

        expectedTree.root!!.addLeftSon(0, value, Color.Red)

        expectedTree.root!!.addRightSon(100, value, Color.Red)

        // Making tree using existing methods
        for (key in arrayOf(0, 100, 50))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRootRightRightCase() {
        // Building expected structure of tree
        expectedTree.root = RedBlackNode(100, value, Color.Black)

        expectedTree.root!!.addLeftSon(0, value, Color.Red)

        expectedTree.root!!.addRightSon(200, value, Color.Red)

        // Making tree using existing methods
        for (key in arrayOf(0, 100, 200))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRedFatherRootGrandfatherRedUncleCase() {
        // Building expected structure of tree
        expectedTree.root = RedBlackNode(0, value, Color.Black)

        expectedTree.root!!.addLeftSon(-100, value, Color.Black)

        expectedTree.root!!.addRightSon(100, value, Color.Black)

        expectedTree.root!!.left!!.addLeftSon(-200, value, Color.Red)

        // Making tree using existing methods
        for (key in arrayOf(0, -100, 100, -200))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRedFatherRedUncleCase() {
        // Building expected structure of tree
        expectedTree.root = RedBlackNode(0, value, Color.Black)

        expectedTree.root!!.addLeftSon(-100, value, Color.Red)

        expectedTree.root!!.addRightSon(100, value, Color.Black)

        expectedTree.root!!.left!!.addLeftSon(-200, value, Color.Black)

        expectedTree.root!!.left!!.addRightSon(-50, value, Color.Black)

        expectedTree.root!!.left!!.right!!.addLeftSon(-75, value, Color.Red)

        // Making tree using existing methods
        for (key in arrayOf(0, -100, 100, -50, -200, -75))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRedFatherNullUncleCase() {
        // Building expected structure of tree
        expectedTree.root = RedBlackNode(0, value, Color.Black)

        expectedTree.root!!.addLeftSon(-50, value, Color.Black)

        expectedTree.root!!.addRightSon(100, value, Color.Black)

        expectedTree.root!!.left!!.addLeftSon(-100, value, Color.Red)

        expectedTree.root!!.left!!.addRightSon(-25, value, Color.Red)

        // Making tree using existing methods
        for (key in arrayOf(0, 100, -100, -50, -25))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun insertRedFatherBlackUncleCase() {
        // Building expected structure of tree
        expectedTree.root = RedBlackNode(-50, value, Color.Black)

        expectedTree.root!!.addLeftSon(-100, value, Color.Red)

        expectedTree.root!!.addRightSon(0, value, Color.Red)

        expectedTree.root!!.left!!.addLeftSon(-200, value, Color.Black)

        expectedTree.root!!.left!!.addRightSon(-75, value, Color.Black)

        expectedTree.root!!.right!!.addLeftSon(-25, value, Color.Black)

        expectedTree.root!!.right!!.addRightSon(100, value, Color.Black)

        expectedTree.root!!.left!!.right!!.addRightSon(-62, value, Color.Red)

        expectedTree.root!!.right!!.right!!.addLeftSon(50, value, Color.Red)

        expectedTree.root!!.right!!.right!!.addRightSon(200, value, Color.Red)

        // Making tree using existing methods
        for (key in arrayOf(0, 100, -100, -50, 50, -25, -200, -75, -62, 200))
            actualTree[key] = value

        assertEquals(expectedTree, actualTree)
    }
}
