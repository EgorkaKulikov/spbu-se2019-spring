package trees


import kotlin.math.max


class AVLTree<K : Comparable<K>, V> : BalancedSearchTree<K, V>() {

    inner class AVLNode(key: K, value: V, parent: Node?) : BalancedNode(key, value, parent) {

        internal var height = 1

        internal fun updateHeight() {

            val leftHeight = (left as? AVLNode)?.height ?: 0
            val rightHeight = (right as? AVLNode)?.height ?: 0

            val nodeHeight = max(leftHeight, rightHeight) + 1

            height = nodeHeight
        }

        internal fun verifyAVL(): Pair<Boolean, Int> {
    
            val leftTemporary = (left as? AVLNode)?.verifyAVL() ?: Pair(true, 0)
            val leftCorrect = leftTemporary.first
            val leftHeight = leftTemporary.second
            
            val rightTemporary = (right as? AVLNode)?.verifyAVL() ?: Pair(true, 0)
            val rightCorrect = rightTemporary.first
            val rightHeight = rightTemporary.second

            val nodeCorrect = 
                (leftCorrect) 
                && (rightCorrect)
                && ((leftHeight - rightHeight) in -1..1)

            val nodeHeight = max(leftHeight, rightHeight) + 1

            return Pair(nodeCorrect, nodeHeight)
        }
    }

    override fun createNode(key: K, value: V, parent: Node?): Node {

        return AVLNode(key, value, parent)
    }

    override fun innerInsert(key: K, value: V): Node {

        val oldSize = size
        val inserted = super.innerInsert(key, value)

        if (size == oldSize || size == 1) {
            return inserted
        } else {
            var parent: AVLNode
            var current = inserted as AVLNode
            var child = current

            while (current.parent != null) {
                parent = current.parent as AVLNode
                parent.updateHeight()

                var leftHeight = 0
                if (parent.left != null) {
                    val leftChild = (parent.left as AVLNode)
                    leftChild.updateHeight()
                    leftHeight = leftChild.height
                }

                var rightHeight = 0
                if (parent.right != null) {
                    val rightChild = (parent.right as AVLNode)
                    rightChild.updateHeight()
                    rightHeight = rightChild.height
                }

                val heightDifference = leftHeight - rightHeight

                var next = parent
                var nextChild = current

                if (heightDifference !in -1..1) {
                    next = current
                    nextChild = child

                    if (current == parent.left) {
                        if (child == current.right) {
                            current.rotateLeft()
                            next = child
                            nextChild = current
                        }

                        parent.rotateRight()
                        parent.updateHeight()
                        current.updateHeight()
                        child.updateHeight()
                    } else {
                        if (child == current.left) {
                            current.rotateRight()
                            next = child
                            nextChild = current
                        }

                        parent.rotateLeft()
                        parent.updateHeight()
                        current.updateHeight()
                        child.updateHeight()
                    }
                }

                current = next
                child = nextChild
            }

            return inserted
        }
    }

    internal fun isAVLTree(): Boolean =
        (root as? AVLNode)?.verifyAVL()?.first ?: true
}
