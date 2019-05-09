package trees

import kotlin.math.abs
import kotlin.math.max

class  AVLTree<K : Comparable<K>, T> : BalancedSearchTree<K, T>() {
    inner class AVLNode(key_: K, value_: T, parent_: Node?) : BalancedNode(key_, value_, parent_) {
        internal var diffBetweenSubtrees = 0
        internal var height = 1

        internal fun updateHeight() {
            this.height =  max((this.left as AVLNode?)?.height ?: 0
                                ,(this.right as AVLNode?)?.height ?: 0) + 1
        }

        //part of the team tests
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

    override fun createNode(key: K, value: T, parent: Node?): Node {
        return AVLNode(key, value, parent)
    }

    override fun balance(node: Node){
        var insertedNode = node as AVLNode

        if (insertedNode == this.root) {
            return
        }
        var unbalancedNode = insertedNode.parent as AVLNode?
        while (unbalancedNode != null) {
            var rightSubtree = unbalancedNode.right as AVLNode?
            var leftSubtree = unbalancedNode.left as AVLNode?

            unbalancedNode.updateHeight()
            unbalancedNode.diffBetweenSubtrees = (leftSubtree?.height ?: 0) - (rightSubtree?.height ?: 0)
            if (abs(unbalancedNode.diffBetweenSubtrees) == 2){
                when(unbalancedNode.diffBetweenSubtrees) {
                    //It means right Subtree has height 2 + (left Subtree's).height
                    -2 -> {
                        if ((rightSubtree!!.right as AVLNode?)?.height ?: 0
                            >= (rightSubtree.left as AVLNode?)?.height ?: 0) {
                            unbalancedNode.rotateLeft()
                            unbalancedNode.updateHeight()
                            rightSubtree.updateHeight()
                        } else {
                            rightSubtree.rotateRight()
                            rightSubtree.updateHeight()
                            unbalancedNode.rotateLeft()
                            unbalancedNode.updateHeight()
                            rightSubtree.updateHeight()
                        }
                    }
                    //It means left Subtree has height 2 + (right Subtree's).height
                    2 -> {
                        if ((leftSubtree!!.left as AVLNode?)?.height ?: 0
                            >= (leftSubtree.right as AVLNode?)?.height ?: 0) {
                            unbalancedNode.rotateRight()
                            unbalancedNode.updateHeight()
                            leftSubtree.updateHeight()
                        } else {
                            leftSubtree.rotateLeft()
                            leftSubtree.updateHeight()
                            unbalancedNode.rotateRight()
                            unbalancedNode.updateHeight()
                            leftSubtree.updateHeight()
                        }
                    }
                }
            }
            unbalancedNode = unbalancedNode.parent as AVLNode?
        }
    }

    //part of the team tests
    internal fun isAVLTree(): Boolean {
        return root == null || (root as AVLNode).verifyAVL().first
    }
}