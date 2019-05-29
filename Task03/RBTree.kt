package trees

internal enum class Color {
    RED,
    BLACK,
}

class RBTree<K : Comparable<K>, V> : BalancedSearchTree<K, V>() {
    inner class RBNode(_key: K, _value: V, _parent: Node?) : BalancedNode(_key, _value, _parent) {
        internal var color = Color.RED

        internal fun verifyRB() : Pair<Boolean, Int> {
            if (color == Color.RED) {
                if ((left != null
                            && (left as RBNode).color == Color.RED)
                    || (right != null
                            && (right as RBNode).color == Color.RED)) {
                    return Pair(false, 0)
                }
            }

            var leftTreeBlackNodesCorrectness = true
            var leftBlackHeight = 1

            if (left != null) {
                val leftVerify = (left as RBNode).verifyRB()
                leftTreeBlackNodesCorrectness = leftVerify.first
                leftBlackHeight = leftVerify.second
            }

            var rightTreeBlackNodesCorrectness = true
            var rightBlackHeight = 1

            if (right != null) {
                val rightVerify = (right as RBNode).verifyRB()
                rightTreeBlackNodesCorrectness = rightVerify.first
                rightBlackHeight = rightVerify.second
            }

            return Pair(leftTreeBlackNodesCorrectness
                    && rightTreeBlackNodesCorrectness
                    && rightBlackHeight == leftBlackHeight
                , leftBlackHeight + if (color == Color.BLACK) 1 else 0)
        }


    }

    override fun createNode(key: K, value: V, parent: Node?): Node {
        return RBNode(key, value, parent)
    }

    override fun innerInsert(key: K, value: V): Node {
        val newNode = super.innerInsert(key, value) as RBNode
        var tmpNode = newNode
        while (tmpNode.parent != null) {
            var parent = tmpNode.parent as RBNode
            val uncle = tmpNode.uncle as RBNode?
            if (parent.color == Color.BLACK) {
                return newNode
            }
            val grandparent = parent.parent as RBNode // exists because parent col red
            if (uncle != null && uncle.color == Color.RED) {
                parent.color = Color.BLACK
                uncle.color = Color.BLACK
                grandparent.color = Color.RED
                tmpNode = grandparent
            }
            else {
                if (tmpNode == parent.right && parent == grandparent.left) {
                    parent.rotateLeft()
                    tmpNode = tmpNode.left as RBNode

                }
                else if (tmpNode == parent.left && parent == grandparent.right) {
                    parent.rotateRight()
                    tmpNode = tmpNode.right as RBNode
                }
                parent = tmpNode.parent as RBNode
                parent.color = Color.BLACK
                grandparent.color = Color.RED
                if (tmpNode == parent.left && parent == grandparent.left) {
                    grandparent.rotateRight()
                }
                else {
                    grandparent.rotateLeft()
                }
                return newNode
            }
        }
        tmpNode.color = Color.BLACK
        return newNode
    }

    internal fun isRBTree(): Boolean {
        return if (root == null) {
            true
        }
        else {
            (root as RBNode).color == Color.BLACK && (root as RBNode).verifyRB().first
        }
    }
}