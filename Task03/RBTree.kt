package trees

enum class Color{
    RED,
    BLACK;
}

class RBTree<K : Comparable<K>, T> : BalancedSearchTree<K, T>() {
    inner class RBNode(key: K, value: T, parent: Node?) : BalancedNode(key, value, parent) {
        internal var color = Color.RED

        //part of the team tests
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

    override fun createNode(key: K, value: T, parent: Node?): Node {
        return RBNode(key, value, parent)
    }

    override fun balance(node: Node) {

        var currNode = node as RBNode

        if (currNode?.parent == null) {
            (root as RBNode).color = Color.BLACK
            return
        }

        //In this circle we are sure about existing grandparent
        //because parent has red color so it isn't root
        while ((currNode.parent as RBNode?)?.color == Color.RED){
            val uncle = currNode.getUncle() as RBNode?
            var grandparent = currNode.getGrandparent() as RBNode

            if (currNode.parent == grandparent.left){
                if (uncle != null && uncle?.color != Color.BLACK){
                    (currNode.parent as RBNode).color = Color.BLACK
                    uncle.color = Color.BLACK
                    grandparent.color = Color.RED
                    currNode = grandparent
                } else {
                    if (currNode == currNode.parent?.right){
                        currNode = currNode.parent as RBNode
                        currNode.rotateLeft()
                        grandparent = currNode.getGrandparent() as RBNode
                    }
                    (currNode.parent as RBNode).color = Color.BLACK
                    grandparent.color = Color.RED
                    grandparent.rotateRight()
                }
            } else {
                if (uncle != null && uncle?.color != Color.BLACK){
                    (currNode.parent as RBNode).color = Color.BLACK
                    uncle.color = Color.BLACK
                    grandparent.color = Color.RED
                    currNode = grandparent
                } else {
                    if (currNode == currNode.parent?.left){
                        currNode = currNode.parent as RBNode
                        currNode.rotateRight()
                        grandparent = currNode.getGrandparent() as RBNode
                    }
                    (currNode.parent as RBNode).color = Color.BLACK
                    grandparent.color = Color.RED
                    grandparent.rotateLeft()
                }
            }
            if (currNode.parent == null){
                currNode.color = Color.BLACK
            }
        }
    }

    //part of the team tests
    internal fun isRBTree(): Boolean {
        return if (root == null) {
            true
        }
        else {
            val rbRoot = root as RBNode
            rbRoot.color == Color.BLACK && rbRoot.verifyRB().first
        }
    }
}