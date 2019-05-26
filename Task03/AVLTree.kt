package trees

import kotlin.math.abs
import kotlin.math.max

class AVLTree<K : Comparable<K>, V> : BalancedSearchTree<K, V>() {
    inner class AVLNode(_key: K, _value: V, _parent: Node?) : BalancedNode(_key, _value, _parent) {
        internal var height = 1
        internal fun updateHeight() {
            height =  max((left as AVLNode?)?.height ?: 0
                         ,(right as AVLNode?)?.height ?: 0) + 1
        }

        internal fun balanceFactor() = ((right as AVLNode?)?.height ?: 0) -
                                       ((left as AVLNode?)?.height ?: 0)

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

    override fun innerInsert(key: K, value: V): Node {
        val newNode = super.innerInsert(key, value) as AVLNode
        var tmpNode = newNode as AVLNode?
        while (tmpNode != null) {
            tmpNode.updateHeight()
            if (tmpNode.balanceFactor() == 2) {
                val rightSon = tmpNode.right as AVLNode?
                if ((rightSon?.right as AVLNode?)?.height ?: 0
                    < (rightSon?.left as AVLNode?)?.height ?: 0) {
                    rightSon?.rotateRight()
                    tmpNode.rotateLeft()
                    rightSon?.updateHeight()
                }
                else {
                    tmpNode.rotateLeft()
                    tmpNode.updateHeight()
                }

            }
            else if (tmpNode.balanceFactor() == -2) {
                val leftSon = tmpNode.left as AVLNode?
                if ((leftSon?.left as AVLNode?)?.height ?: 0
                    < (leftSon?.right as AVLNode?)?.height ?: 0) {
                    leftSon?.rotateLeft()
                    tmpNode.rotateRight()
                    leftSon?.updateHeight()
                }
                else {
                    tmpNode.rotateRight()
                    tmpNode.updateHeight()
                }
            }
            tmpNode = tmpNode.parent as AVLNode?
        }
        return newNode
    }

    internal fun isAVLTree(): Boolean {
        return if (root == null) {
            true
        }
        else {
            (root as AVLNode).verifyAVL().first
        }
    }
}