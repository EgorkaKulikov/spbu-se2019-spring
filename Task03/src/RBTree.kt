package binaryTrees

enum class Color {
    BLACK, RED
}

class RBTree<K: Comparable<K>, V> : BalancedSearchTree<K, V>() {

    inner class RBNode(key: K, value: V, parent: Node?) : BalancedNode(key, value, parent) {
        internal var color = Color.RED

        internal fun verifyRB() : Pair<Boolean, Int> {
            if (this.color == Color.RED) {
                if ((this.left != null
                            && (this.left as RBNode).color == Color.RED)
                    || (this.right != null
                            && (this.right as RBNode).color == Color.RED)) {
                    return Pair(false, 0)
                }
            }

            var leftCorrectness = true
            var leftBlackHeight = 1

            if (this.left != null) {
                val tmp = (this.left as RBNode).verifyRB()
                leftCorrectness = tmp.first
                leftBlackHeight = tmp.second
            }

            var rightCorrectness = true
            var rightBlackHeight = 1

            if (this.right != null) {
                val tmp = (this.right as RBNode).verifyRB()
                rightCorrectness = tmp.first
                rightBlackHeight = tmp.second
            }

            return Pair(leftCorrectness
                    && rightCorrectness
                    && rightBlackHeight == leftBlackHeight
                , leftBlackHeight + if (this.color == Color.BLACK) 1 else 0)
        }
    }

    override fun createNode(key: K, value: V, parent: Node?): Node {
        return RBNode(key, value, parent)
    }

    internal fun swapColors(node1: RBNode, node2: RBNode) {
        node1.color = node2.color.also { node2.color = node1.color }
    }

    override fun balance(node: Node) {
        val insertedNode = node as RBNode
        val granddad = insertedNode.getGrandparent() as RBNode?
        val dad = insertedNode.parent as RBNode?
        val uncle = insertedNode.getUncle() as RBNode?

        if (dad == null) {
            insertedNode.color = Color.BLACK
            return
        }

        if (dad.color == Color.BLACK) {
            return
        }

        if (uncle?.color == Color.RED) {
            dad.color = Color.BLACK
            uncle.color = Color.BLACK
            granddad!!.color = Color.RED
            balance(granddad)
        } else {
            when {
                insertedNode.isLeftSon() && dad.isLeftSon() -> {
                    granddad!!.rotateRight()
                    swapColors(dad, granddad)
                }

                insertedNode.isLeftSon() && dad.isRightSon() -> {
                    dad.rotateRight()
                    balance(dad)
                }

                insertedNode.isRightSon() && dad.isLeftSon() -> {
                    dad.rotateLeft()
                    balance(dad)
                }

                insertedNode.isRightSon() && dad.isRightSon() -> {
                    granddad!!.rotateLeft()
                    swapColors(dad, granddad)
                }
            }
        }
    }

    internal fun isRBTree(): Boolean {
        if (root == null) {
            return true
        } else {
            val rbRoot = root as RBNode
            return rbRoot.color == Color.BLACK && rbRoot.verifyRB().first
        }
    }
}
