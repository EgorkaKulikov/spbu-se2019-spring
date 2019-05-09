package trees

enum class Color {
    RED, BLACK
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

    private fun getGrandparent(Node: RBNode): RBNode? = Node.parent?.parent as RBNode?

    private fun getBrother(Node: RBNode): RBNode? = when {
        Node.parent == null -> null
        Node.parent?.left == Node -> Node.parent?.right as RBNode?
        else -> Node.parent?.left as RBNode?
    }

    private fun getUncle(Node: RBNode): RBNode? = getBrother(Node.parent as RBNode)

    private fun swapColors(Node1: RBNode, Node2: RBNode) {
        val tempColor = Node1.color
        Node1.color = Node2.color
        Node2.color = tempColor
    }

    override fun innerInsert(key: K, value: V): Node {
        val oldSize = size
        val newNode = super.innerInsert(key, value) as RBNode
        var parent = newNode.parent as RBNode?

        if (size == oldSize) return newNode

        if (parent == null) {
            newNode.color = Color.BLACK
            return newNode
        }

        var tempNode = newNode

        while (parent != null) {
            if (parent.color == Color.RED) {
                val uncle = getUncle(tempNode)
                val grandParent = getGrandparent(tempNode)

                if (uncle != null && uncle.color == Color.RED) {
                    parent.color = Color.BLACK
                    uncle.color = Color.BLACK
                    grandParent?.color = Color.RED

                    tempNode = grandParent!!
                } else {
                    if (parent == grandParent?.left) {
                        if (tempNode == parent.right) {
                            parent.rotateLeft()
                            grandParent.rotateRight()
                            swapColors(grandParent, tempNode)
                        } else {
                            grandParent.rotateRight()
                            swapColors(grandParent, parent)
                        }
                    }

                    if (parent == grandParent?.right) {
                        if (tempNode == parent.left) {
                            parent.rotateRight()
                            grandParent.rotateLeft()
                            swapColors(grandParent, tempNode)
                        } else {
                            grandParent.rotateLeft()
                            swapColors(grandParent, parent)
                        }
                    }

                    break
                }

                parent = tempNode.parent as RBNode?

            } else
                break
        }

        if (parent == null) tempNode.color = Color.BLACK

        return newNode
    }

    internal fun isRBTree(): Boolean {
        return if (root == null) {
            true
        } else {
            val rbRoot = root as RBNode
            rbRoot.color == Color.BLACK && rbRoot.verifyRB().first
        }
    }
}