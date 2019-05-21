package trees

abstract class BalancedSearchTree<K : Comparable<K>, T> : BinarySearchTree<K, T>() {

    open inner class BalancedNode(key_: K, value_: T, parent_: Node?) : Node(key_, value_, parent_) {

        internal var grandparent: Node? = null
            private set
            get() = this.parent?.parent

        internal var uncle: Node? = null
            private set
            get() = when {
                grandparent == null -> null
                grandparent?.left == this.parent -> grandparent?.right
                else -> grandparent?.left
            }

        internal var sibling: Node? = null
            private set
            get() = if (parent?.left == this) parent?.right else parent?.left

        fun rotateLeft() {
            if (this.right == null) {
                return
            }
            val rightNode = this.right

            if (rightNode?.left != null) {
                rightNode.left!!.parent = this
            }
            this.right = rightNode?.left

            val parentOfCurrNode: Node? = this.parent
            if (parentOfCurrNode == null) {
                root = rightNode
            } else {
                if (parentOfCurrNode?.left == this) {
                    parentOfCurrNode.left = rightNode
                } else {
                    parentOfCurrNode!!.right = rightNode
                }
            }

            rightNode?.parent = parentOfCurrNode
            this.parent = rightNode

            rightNode!!.left = this
        }

        fun rotateRight() {
            if (this.left == null) {
                return
            }

            val leftNode = this.left

            if (leftNode?.right != null) {
                leftNode.right!!.parent = this
            }
            this.left = leftNode?.right

            val parentOfCurrNode: Node? = this.parent
            if (parentOfCurrNode == null) {
                root = leftNode
            } else {
                if (parentOfCurrNode?.left == this) {
                    parentOfCurrNode.left = leftNode
                } else {
                    parentOfCurrNode!!.right = leftNode
                }
            }

            leftNode?.parent = parentOfCurrNode
            this.parent = leftNode

            leftNode!!.right = this
        }
    }

    protected abstract fun balance(node: Node)

    override fun innerInsert(key: K, value: T): Node {
        val sizeBeforeIns = size
        val insertedNode = super.innerInsert(key, value)

        if (sizeBeforeIns != size) {
            balance(insertedNode)
        }
        // If size of the tree is the same
        // before and after insertion
        // it means we only change value
        // one of the nodes without really insertion
        return insertedNode
    }

}