package binaryTrees

abstract class BalancedSearchTree<K: Comparable<K>, V> : BinarySearchTree<K, V>() {

    open inner class BalancedNode(key: K, value: V, parent: Node?) : Node(key, value, parent) {
        internal fun getBrother(): Node? {
            when {
                this.parent == null -> return null
                isLeftSon() -> return parent?.right
                else -> return parent?.left
            }
        }

        internal fun getUncle() = (parent as BalancedNode?)?.getBrother()

        internal fun getGrandparent(): Node? = parent?.parent

        internal fun rotateLeft() {
            if (this.right == null) {
                return
            }

            val replacement = this.right!!
            this.right = replacement.left

            if (replacement.left != null) {
                replacement.left!!.parent = this
            }

            replacement.left = this
            replacement.parent = this.parent
            this.parent = replacement

            if (replacement.parent == null) {
                root = replacement
            } else {
                if (replacement.parent?.left == this) {
                    replacement.parent!!.left = replacement
                } else {
                    replacement.parent!!.right = replacement
                }
            }
        }

        internal fun rotateRight() {
            if (this.left == null) {
                return
            }

            val replacement = this.left!!
            this.left = replacement.right

            if (replacement.right != null) {
                replacement.right!!.parent = this
            }

            replacement.right = this
            replacement.parent = this.parent
            this.parent = replacement

            if (replacement.parent == null) {
                root = replacement
            } else {
                if (replacement.parent?.left == this) {
                    replacement.parent!!.left = replacement
                } else {
                    replacement.parent!!.right = replacement
                }
            }
        }
    }

    internal abstract fun balance(node: Node)

    override fun innerInsert(key: K, value: V): Node {
        val sizeBeforeInsertion = size
        val insertedNode = super.innerInsert(key, value)

        if (size != sizeBeforeInsertion) {
            balance(insertedNode)
        }

        return insertedNode
    }
}
