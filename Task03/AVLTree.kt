package binaryTrees

import kotlin.math.abs
import kotlin.math.max

class AVLTree<K : Comparable<K>, V> : BalancedSearchTree<K, V>() {
    inner class AVLNode(_key: K, _value: V, _parent: Node?) : BalancedNode(_key, _value, _parent) {
        private var height = 1

        val heightDiff: Int
            get() {
                val leftHeight = (left as AVLNode?)?.height ?: 0
                val rightHeight = (right as AVLNode?)?.height ?: 0
                return rightHeight - leftHeight
            }

        public fun updateNodeHeight() {
            val localLeft = left
            val localRight = right
            if (localLeft is AVLNode? && localRight is AVLNode?) {
                val leftHeight = localLeft?.height ?: 0
                val rightHeight = localRight?.height ?: 0
                height = max(leftHeight, rightHeight) + 1
            }
        }

        public fun balanceWithLeftRotate() {
            val localRightNode = right ?: return //local copy to avoid using !!
            if (localRightNode is AVLNode) {
                when (localRightNode.heightDiff) {
                    -1 -> {
                        localRightNode.rotateRightHeightFix()
                        this.rotateLeftHeightFix()
                    }
                    else -> {
                        this.rotateLeftHeightFix()
                    }
                }
            }
        }

        public fun balanceWithRightRotate() {
            val localLeftNode = (left as AVLNode?) ?: return //local copy to avoid using !!
            when (localLeftNode.heightDiff) {
                1 -> {
                    localLeftNode.rotateLeftHeightFix()
                    this.rotateRightHeightFix()

                }
                else -> {
                    this.rotateRightHeightFix()
                }
            }
        }

        override fun print(indentation: Int, side: Int) {
            this.right?.print(indentation + 1, -1)

            for (i in 1..indentation) {
                print(" ")
            }

            when (side) {
                -1 -> print("/")
                1 -> print("\\")
            }
            println("$key $value $height")

            left?.print(indentation + 1, 1)
        }

        internal fun verifyAVL(): Pair<Boolean, Int> {
            val localLeft = left
            val localRight = right
            var correctnessAndHeight: Pair<Boolean, Int> = Pair(false, 0)

            if (localLeft is AVLNode? && localRight is AVLNode?) {
                val leftCorrectnessAndHeight = localLeft?.verifyAVL()

                val leftCorrectness = leftCorrectnessAndHeight?.first ?: true
                val leftHeight = leftCorrectnessAndHeight?.second ?: 0

                val rightCorrectnessAndHeight = localRight?.verifyAVL()

                val rightCorrectness = rightCorrectnessAndHeight?.first ?: true
                val rightHeight = rightCorrectnessAndHeight?.second ?: 0

                correctnessAndHeight = Pair(
                    leftCorrectness
                            && rightCorrectness
                            && abs(rightHeight - leftHeight) < 2
                    , max(rightHeight, leftHeight) + 1
                )
            }

            return correctnessAndHeight
        }

        //rotations with height updating
        private fun rotateLeftHeightFix() {
            this.rotateLeft()
            val parent = this.parent!! as AVLNode
            this.updateNodeHeight()
            parent.updateNodeHeight()
        }

        private fun rotateRightHeightFix() {
            this.rotateRight()
            val parent = this.parent!! as AVLNode
            this.updateNodeHeight()
            parent.updateNodeHeight()
        }
    }

    override fun createNode(key: K, value: V, parent: Node?): Node {
        return AVLNode(key, value, parent)
    }

    override fun innerInsert(key: K, value: V): Node {
        val insertedNode = super.innerInsert(key, value) as AVLNode
        var currentNode: AVLNode = insertedNode

        while (currentNode.parent != null) {
            when (currentNode.heightDiff) {
                2 -> currentNode.balanceWithLeftRotate()
                -2 -> currentNode.balanceWithRightRotate()
            }
            currentNode.updateNodeHeight()
            currentNode = currentNode.parent as AVLNode
        }

        when (currentNode.heightDiff) {
            2 -> currentNode.balanceWithLeftRotate()
            -2 -> currentNode.balanceWithRightRotate()
        }
        currentNode.updateNodeHeight()

        return currentNode
    }

    override fun print() {
        if (root is AVLNode?) {
            root?.print(0, 0)
        }
    }

    internal fun isAVLTree(): Boolean {
        val localRoot = root//local copy of root

        return if (localRoot is AVLNode?) {
            localRoot?.verifyAVL()?.first ?: true
        } else {
            false
        }
    }
}
