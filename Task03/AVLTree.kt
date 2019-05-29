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

        internal var balanceFactor: Int = 0
            get() {
                return ((right as AVLNode?)?.height ?: 0) -
                        ((left as AVLNode?)?.height ?: 0)
            }

        internal fun verifyAVL(): Pair<Boolean, Int> {
            var leftTreeIsAVL = true
            var leftHeight = 0

            if (left != null) {
                val leftVerify = (left as AVLNode).verifyAVL()
                leftTreeIsAVL = leftVerify.first
                leftHeight = leftVerify.second
            }

            var rightTreeIsAVL = true
            var rightHeight = 0

            if (right != null) {
                val rightVerify = (right as AVLNode).verifyAVL()
                rightTreeIsAVL = rightVerify.first
                rightHeight = rightVerify.second
            }

            return Pair(leftTreeIsAVL
                    && rightTreeIsAVL
                    && abs(rightHeight - leftHeight) < 2
                    , max(rightHeight, leftHeight) + 1)
        }
    }

    override fun createNode(key: K, value: V, parent: Node?): Node {
        return AVLNode(key, value, parent)
    }

    override fun innerInsert(key: K, value: V): Node {
        val newNode: AVLNode = super.innerInsert(key, value) as AVLNode
        var tmpNode: AVLNode? = newNode
        while (tmpNode != null) {
            tmpNode.updateHeight()
            if (tmpNode.balanceFactor == 2) {
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
            else if (tmpNode.balanceFactor == -2) {
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