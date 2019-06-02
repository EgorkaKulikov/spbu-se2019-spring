package trees

abstract class BalancedSearchTree<K : Comparable<K>, V> : BinarySearchTree<K, V>() {
    open inner class BalancedNode(_key: K, _value: V, _parent: Node?) : Node(_key, _value, _parent) {
        internal var grandparent: Node? = null
            get() {
                return this.parent?.parent
            }
        internal var uncle: Node? = null
            get() {
                return if (this.grandparent != null) {
                    if (this.parent == this.grandparent?.left) {
                        this.grandparent?.right
                    }
                    else {
                        this.grandparent?.left
                    }

                }
                else {
                    null
                }
            }

        internal fun rotateLeft() {
            val rightSon = this.right
            if (this.parent == null) {
                rightSon?.parent = null
                root = rightSon
            }
            else {
                rightSon?.parent = this.parent
                if (this.parent?.left == this) {
                    this.parent?.left = rightSon
                }
                else {
                    this.parent?.right = rightSon
                }
            }

            this.right = rightSon?.left
            if (rightSon?.left != null) {
                rightSon.left?.parent = this
            }
            this.parent = rightSon
            rightSon?.left = this
        }

        internal fun rotateRight() {
            val leftSon = this.left
            if (this.parent == null) {
                leftSon?.parent = null
                root = leftSon
            }
            else {
                leftSon?.parent = this.parent
                if (this.parent?.left == this) {
                    this.parent?.left = leftSon
                }
                else {
                    this.parent?.right = leftSon
                }
            }

            this.left = leftSon?.right
            if (leftSon?.right != null) {
                leftSon.right?.parent = this
            }
            this.parent = leftSon
            leftSon?.right = this
        }
    }
}