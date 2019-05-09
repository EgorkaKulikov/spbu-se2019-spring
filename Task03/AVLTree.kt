package binaryTrees

import kotlin.math.abs
import kotlin.math.max

class AVLTree<K : Comparable<K>, V> : BalancedSearchTree<K, V>() {
    inner class AVLNode(_key: K, _value: V, _parent: Node?) : BalancedNode(_key, _value, _parent) {
        private var height = 1

        val heightDiff: Int
            get() {
                val leftHeight = (this.left as AVLNode?)?.height ?: 0
                val rightHeight = (this.right as AVLNode?)?.height ?: 0
                return  rightHeight - leftHeight
            }

        public fun newHeight() {
            val leftHeight = (this.left as AVLNode?)?.height ?: 0
            val rightHeight = (this.right as AVLNode?)?.height ?: 0
            this.height = max(leftHeight, rightHeight) + 1
        }

        public fun balanceWithLeftRotate() {
            val localRightNode = (this.right as AVLNode?) ?: return
            when (localRightNode.heightDiff)
            {
                -1 -> {
                    localRightNode.rotateRightHeightFix()
                    this.rotateLeftHeightFix()
                }
                else -> {
                    this.rotateLeftHeightFix()
                }
            }
        }

        public fun balanceWithRightRotate() {
            val localLeftNode = (this.left as AVLNode?) ?: return
            when (localLeftNode.heightDiff)
            {
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

            when(side) {
                -1 -> print("/")
                1 -> print("\\")
            }
            println("$key $value $height")

            this.left?.print(indentation + 1, 1)
        }

        internal fun verifyAVL(): Pair<Boolean, Int> {
            val leftCorrectnessAndHeight = (this.left as AVLNode?)?.verifyAVL()

            val leftCorrectness = leftCorrectnessAndHeight?.first ?: true
            val leftHeight = leftCorrectnessAndHeight?.second ?: 0

            val rightCorrectnessAndHeight = (this.right as AVLNode?)?.verifyAVL()

            val rightCorrectness = rightCorrectnessAndHeight?.first ?: true
            val rightHeight = rightCorrectnessAndHeight?.second ?: 0

            return Pair(leftCorrectness
                    && rightCorrectness
                    && abs(rightHeight - leftHeight) < 2
                , max(rightHeight, leftHeight) + 1)
        }
    }

    override fun createNode(key: K, value: V, parent: Node?): Node {
        return AVLNode(key, value, parent)
    }

    //rotations with height updating
    private fun AVLNode.rotateLeftHeightFix() {
        this.rotateLeft()
        val parent = this.parent!! as AVLNode
        this.newHeight()
        parent.newHeight()
    }

    private fun AVLNode.rotateRightHeightFix() {
        this.rotateRight()
        val parent = this.parent!! as AVLNode
        this.newHeight()
        parent.newHeight()
    }

    override fun innerInsert(key: K, value: V): Node {
        val insertedNode = super.innerInsert(key, value) as AVLNode
        var currentNode: AVLNode = insertedNode

        while (currentNode.parent != null) {
            when (currentNode.heightDiff) {
                2 -> currentNode.balanceWithLeftRotate()
                -2 -> currentNode.balanceWithRightRotate()
            }
            currentNode.newHeight()
            currentNode = currentNode.parent as AVLNode
        }

        when (currentNode.heightDiff) {
            2 -> currentNode.balanceWithLeftRotate()
            -2 -> currentNode.balanceWithRightRotate()
        }
        currentNode.newHeight()

        return currentNode
    }

    override fun print() {
        root?.let { (root as AVLNode).print(0, 0) }
    }

    internal fun isAVLTree(): Boolean {
        return root == null || (root as AVLNode).verifyAVL().first
    }
}