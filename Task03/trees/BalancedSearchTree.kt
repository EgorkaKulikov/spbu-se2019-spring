package trees

abstract class BalancedSearchTree<K: Comparable<K>, V>: BinarySearchTree<K, V>() {
    open inner class BalancedNode(key: K, value: V, parent: Node?) : Node(key, value, parent) {
        internal fun rotateLeft() {
            val tempNode = this.right
            this.right = tempNode?.left

            if (tempNode?.left != null)
                tempNode.left?.parent = this

            tempNode?.parent = this.parent

            when {
                this.parent == null -> root = tempNode
                this == this.parent?.left -> this.parent?.left = tempNode
                else -> this.parent?.right = tempNode
            }

            tempNode?.left = this
            this.parent = tempNode
        }

        internal fun rotateRight() {
            val tempNode = this.left
            this.left = tempNode?.right

            if (tempNode?.right != null)
                tempNode.right?.parent = this

            tempNode?.parent = this.parent

            when {
                this.parent == null -> root = tempNode
                this == this.parent?.left -> this.parent?.left = tempNode
                else -> this.parent?.right = tempNode
            }

            tempNode?.right = this
            this.parent = tempNode
        }
    }
}