package trees

abstract class BalancedSearchTree<K : Comparable<K>, V> : BinarySearchTree<K, V>() {
    open inner class BalancedNode(key: K, value: V, parent: Node?) : Node(key, value, parent) {
        internal fun rotateLeft() {
            val temporary = right
            right = temporary?.left
            temporary?.parent = parent

            if (temporary?.left != null) {
                temporary.left?.parent = this
            }

            when {
                parent == null -> root = temporary
                this == parent?.left -> parent?.left = temporary
                else -> parent?.right = temporary
            }

            temporary?.left = this
            parent = temporary
        }

        internal fun rotateRight() {
            val temporary = left
            left = temporary?.right
            temporary?.parent = parent

            if (temporary?.right != null) {
                temporary.right?.parent = this
            }

            when {
                parent == null -> root = temporary
                this == parent?.left -> parent?.left = temporary
                else -> parent?.right = temporary
            }

            temporary?.right = this
            parent = temporary
        }
    }
}