package binaryTrees

import kotlin.math.abs
import kotlin.math.max

class AVLTree<K: Comparable<K>, V> : BalancedSearchTree<K, V>() {

    inner class AVLNode(key: K, value: V, parent: Node?) : BalancedNode(key, value, parent) {
        internal var height = 1
            private set
            get() = max((left as AVLNode?)?.height ?: 0,
                        (right as AVLNode?)?.height ?: 0) + 1

        internal var heightDifference = 0
            private set
            get() = abs(((left as AVLNode?)?.height ?: 0) -
                        ((right as AVLNode?)?.height ?: 0))

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

    override fun balance(node: Node) {
        var curNode = node as AVLNode
        var son = curNode

        while (curNode.parent != null) {
            val nextSon = son
            son = curNode
            val dad = curNode.parent as AVLNode
            curNode = dad

            if (curNode.heightDifference == 2)  {
                if (son.isLeftSon()) {
                    if (nextSon.isRightSon()) {
                        son.rotateLeft()
                    }
                    dad.rotateRight()
                } else {
                    if (nextSon.isLeftSon()) {
                        son.rotateRight()
                    }
                    dad.rotateLeft()
                }
            }
        }

    }

    internal fun isAVLTree(): Boolean {
        return root == null || (root as AVLNode).verifyAVL().first
    }
}
