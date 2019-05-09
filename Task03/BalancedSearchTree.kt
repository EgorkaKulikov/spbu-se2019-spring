package binaryTrees

abstract class BalancedSearchTree<K : Comparable<K>, V> : BinarySearchTree<K, V>() {
    open inner class BalancedNode(_key: K, _value: V, _parent: Node?) : Node(_key, _value, _parent) {
        internal fun grandparent(): Node? {
            return this.parent?.parent
        }

        private fun sibling(): Node? {
            val localParent = this.parent ?: return null //local copy of parent to avoid using !!

            return if (this == localParent.left) {
                localParent.right
            }
            else {
                localParent.left
            }
        }

        internal fun uncle(): Node? {
            return (this.parent as BalancedNode?)?.sibling()
        }

        internal fun rotateLeft() {
            val rightChild = this.right ?: return
            val parent = this.parent
            val grandchildLeft = rightChild.left

            this.right = grandchildLeft
            grandchildLeft?.parent = this
            rightChild.left = this
            rightChild.parent = this.parent
            this.parent = rightChild

            if (parent != null) {
                if (this == parent.left) {
                    parent.left = rightChild
                } else {
                    parent.right = rightChild
                }
            } else {
                root = rightChild
            }
        }

        internal fun rotateRight() {
            val leftChild = this.left ?: return
            val parent = this.parent
            val grandchildRight = leftChild.right

            this.left = grandchildRight
            grandchildRight?.parent = this
            leftChild.right = this
            leftChild.parent = this.parent
            this.parent = leftChild

            if (parent != null) {
                if (this == parent.left) {
                    parent.left = leftChild
                } else {
                    parent.right = leftChild
                }
            } else {
                root = leftChild
            }
        }
    }
}