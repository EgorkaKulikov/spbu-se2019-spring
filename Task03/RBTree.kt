package binaryTrees

class RBTree<K : Comparable<K>, V> : BalancedSearchTree<K, V>() {
    inner class RBNode(_key: K, _value: V, _parent: Node?) : BalancedNode(_key, _value, _parent) {

        internal var color = Color.RED

        override fun print(indentation: Int, side: Int) {
            this.right?.print(indentation + 1, -1)

            for (i in 1..indentation) {
                print(" ")
            }

            when (side) {
                -1 -> print("/")
                1 -> print("\\")
            }
            println("$key $value $color")

            this.left?.print(indentation + 1, 1)
        }

        //returns tree correctness and black height for recursive checks
        internal fun verifyRB(): Pair<Boolean, Int> {
            val localLeft = left
            val localRight = right
            var correctnessAndHeight: Pair<Boolean, Int> = Pair(false, 0)

            if (localLeft is RBNode? && localRight is RBNode?) {
                if (this.color == Color.RED) {
                    val leftRBNodeColor = localLeft?.color ?: Color.BLACK
                    val rightRBNodeColor = localRight?.color ?: Color.BLACK

                    if (leftRBNodeColor != Color.BLACK
                        || rightRBNodeColor != Color.BLACK
                    ) {
                        return Pair(false, 0)
                    }
                }

                val leftCorrectnessAndHeight = localLeft?.verifyRB()

                val leftCorrectness = leftCorrectnessAndHeight?.first ?: true
                val leftBlackHeight = leftCorrectnessAndHeight?.second ?: 1

                val rightCorrectnessAndHeight = localRight?.verifyRB()

                val rightCorrectness = rightCorrectnessAndHeight?.first ?: true
                val rightBlackHeight = rightCorrectnessAndHeight?.second ?: 1

                correctnessAndHeight = Pair(
                    leftCorrectness
                            && rightCorrectness
                            && rightBlackHeight == leftBlackHeight
                    , leftBlackHeight + if (this.color == Color.BLACK) 1 else 0
                )
            }

            return correctnessAndHeight
        }
    }

    private fun balance(insertedNode: RBNode) {
        val grandparent = insertedNode.grandparent as RBNode?
        val parent = insertedNode.parent as RBNode?

        if (parent == null) {
            insertedNode.color = Color.BLACK
            return
        }
        if (parent.color == Color.BLACK) {
            return
        }
        val uncle = insertedNode.uncle as RBNode?

        if (uncle != null && uncle.color == Color.RED) {
            //red uncle case
            parent.color = Color.BLACK
            uncle.color = Color.BLACK

            if (grandparent != null) {
                grandparent.color = Color.RED
                balance(grandparent)
            }
        } else {
            //black uncle case, includes uncle == null
            when {
                insertedNode == parent.right && parent == grandparent?.left -> {
                    parent.rotateLeft()
                    balance(parent)
                }
                insertedNode == parent.left && parent == grandparent?.right -> {
                    parent.rotateRight()
                    balance(parent)
                }
                insertedNode == parent.left && parent == grandparent?.left -> {
                    grandparent.rotateRight()
                    //color swap
                    val tempColor = grandparent.color
                    grandparent.color = parent.color
                    parent.color = tempColor
                }
                insertedNode == parent.right && parent == grandparent?.right -> {
                    grandparent.rotateLeft()
                    //color swap
                    val tempColor = grandparent.color
                    grandparent.color = parent.color
                    parent.color = tempColor
                }
            }

        }
    }

    override fun createNode(key: K, value: V, parent: Node?): Node {
        return RBNode(key, value, parent)
    }

    override fun innerInsert(key: K, value: V): Node {
        val insertedNode = super.innerInsert(key, value) as RBNode
        balance(insertedNode)
        return insertedNode
    }

    override fun print() {
        if (root is RBNode?) {
            root?.print(0, 0)
        }
    }

    internal fun isRBTree(): Boolean {
        val localRoot = root ?: return true

        return if (localRoot is RBNode) {
            localRoot.color == Color.BLACK && localRoot.verifyRB().first //first is boolean correctness
        } else {
            false
        }
    }
}