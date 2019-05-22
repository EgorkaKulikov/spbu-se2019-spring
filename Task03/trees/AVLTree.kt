package trees

import kotlin.math.abs
import kotlin.math.max

class AVLTree<K: Comparable<K>, V>: BalancedSearchTree<K, V>() {
    inner class AVLNode(key: K, value: V, parent: Node?): BalancedNode(key, value, parent) {
        internal var height = 1
        internal var balanceFactor = 0

        internal fun verifyAVL(): Pair<Boolean, Int> {
            var leftCorrectness = true
            var leftHeight = 0

            if (this.left != null) {
                val tmp = (this.left as AVLNode).verifyAVL()
                leftCorrectness = tmp.first
                leftHeight = tmp.second
            }

            var rightCorrectness = true
            var rightHeight = 0

            if (this.right != null) {
                val tmp = (this.right as AVLNode).verifyAVL()
                rightCorrectness = tmp.first
                rightHeight = tmp.second
            }

            return Pair(leftCorrectness
                    && rightCorrectness
                    && abs(rightHeight - leftHeight) < 2
                , max(rightHeight, leftHeight) + 1)
        }
    }

    override fun createNode(key: K, value: V, parent: Node?): Node {
        return AVLNode(key, value, parent)
    }

    private fun update(Node: AVLNode) {
        val leftChildHeight = if ((Node.left as AVLNode?) == null) 0 else (Node.left as AVLNode).height
        val rightChildHeight = if ((Node.right as AVLNode?) == null) 0 else (Node.right as AVLNode).height
        Node.height = max(leftChildHeight, rightChildHeight) + 1
        Node.balanceFactor = leftChildHeight - rightChildHeight
    }

    override fun innerInsert(key: K, value: V): Node {
        val oldSize = size
        val newNode = super.innerInsert(key, value) as AVLNode

        if (newNode.parent == null || size == oldSize) return newNode

        var tempNode = newNode

        var parent: AVLNode?
        var child = newNode
        var grandChild: AVLNode?

        while (tempNode.parent != null) {
            grandChild = child
            child = tempNode
            parent = tempNode.parent as AVLNode

            update(parent)
            tempNode = parent

            if (abs(tempNode.balanceFactor) > 1) {
                if (parent.left == child) {
                    if (child.right == grandChild) {
                        child.rotateLeft()
                        update(child)
                        update(grandChild)
                    }
                    parent.rotateRight()
                    update(parent)
                    update(child)
                }

                if (parent.right == child) {
                    if (child.left == grandChild) {
                        child.rotateRight()
                        update(child)
                        update(grandChild)
                    }
                    parent.rotateLeft()
                    update(parent)
                    update(child)
                }

                tempNode = child
            }

        }

        return newNode
    }

    internal fun isAVLTree(): Boolean {
        return root == null || (root as AVLNode).verifyAVL().first
    }
}