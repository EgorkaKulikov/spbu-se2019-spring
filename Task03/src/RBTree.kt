class RBTree<T, K : Comparable<K>> : BalancedSearchTree<T, K>() {
    inner class RBNode(_key: K, _value: T, _parent: Node?) : BalancedNode(_key, _value, _parent) {
        internal var color = Color.RED

        internal fun balanceNode() {
            var dad: RBNode? = null
            if (this.parent != null){
                dad = this.parent as RBNode
            }
            var grand: RBNode? = null
            if (this.grandparent() != null){
                grand = this.grandparent() as RBNode
            }
            var uncle: RBNode? = null
            if (this.uncle() != null){
                uncle = this.uncle() as RBNode
            }

            if(dad != null && grand != null) {
                if (dad.color == Color.RED && uncle?.color == Color.RED) {
                    dad.color = Color.BLACK
                    uncle.color = Color.BLACK
                    grand.color = Color.RED
                    grand.balanceNode()
                }
                if (dad.color == Color.RED && (uncle == null || uncle.color == Color.BLACK)) {
                    if (grand.left == dad) {
                        if (dad.right == this) {
                            dad.rotateLeft()
                            this.color = Color.BLACK
                        }
                        else{
                            dad.color = Color.BLACK
                        }
                        grand.rotateRight()
                    }
                    if (grand.right == dad) {
                        if (dad.left == this) {
                            dad.rotateRight()
                            this.color = Color.BLACK
                        }
                        else{
                            dad.color = Color.BLACK
                        }
                        grand.rotateLeft()
                    }
                    grand.color = Color.RED
                }
            }
            if ((root as RBNode).color == Color.RED) {
                (root as RBNode).color = Color.BLACK
            }
        }

        fun verifyRB(): Pair<Boolean, Int>{
            val right = this.right as RBNode?
            val left = this.left as RBNode?

            if (this.color == Color.RED) {
                if (left != null && left.color == Color.RED) {
                    return Pair(false, 0)
                }
                if(right != null && right.color == Color.RED){
                    return Pair(false, 0)
                }
            }

            var correctness = true
            var blackHeight = 1

            if (left != null){
                correctness = left.verifyRB().first
                blackHeight = left.verifyRB().second
            }

            if (right != null){
                correctness = (right.verifyRB().first && correctness)
                if (blackHeight != right.verifyRB().second) {
                    return Pair(false,0)
                }
            }
            else {
                if (blackHeight != 1) {
                    return Pair(false,0)
                }
            }

            return Pair(correctness, blackHeight + if(this.color == Color.RED) 0 else 1)
        }
    }

    override fun createNode(value: T, key: K, parent: Node?): RBNode {
        return RBNode(key, value, parent)
    }

    override fun innerInsert(key: K, value: T) : RBNode{
        if (this.root == null) {
            this.root = createNode(value, key, null)
            (this.root as RBNode).color = Color.BLACK
            size++
            return this.root as RBNode
        }

        var curNode: Node? = this.root
        val newNode = createNode(value, key, null)
        while (curNode != null) {
            if (newNode.key > curNode.key) {
                if (curNode.right != null) curNode = curNode.right
                else {
                    newNode.parent = curNode
                    curNode.right = newNode
                    break
                }
            } else if (newNode.key < curNode.key) {
                if (curNode.left != null) curNode = curNode.left
                else {
                    newNode.parent = curNode
                    curNode.left = newNode
                    break
                }
            }
        }
        size++
        newNode.balanceNode()
        return newNode
    }

    internal fun isRBTree(): Boolean {
        return root == null  || (root as RBNode).color == Color.BLACK  && (root as RBNode).verifyRB().first
    }
}