package binaryTrees

abstract class BalancedSearchTree<K : Comparable<K>, V> : BinarySearchTree<K, V>() {
    open inner class BalancedNode(_key: K, _value: V, _parent: Node?) : Node(_key, _value, _parent) {
        internal fun grandparent(): Node? {
            return this.parent?.parent
        }

        internal fun sibling(): Node? {
            if (this.parent == null) {
                return null
            }

            return if (this == this.parent!!.left) {
                this.parent!!.right
            }
            else {
                this.parent!!.left
            }
        }

        internal fun uncle(): Node? {
            return (this.parent as BalancedNode?)?.sibling()
        }

        internal fun rotateLeft() {
            val tmp = this.right?: return
            this.right = tmp.left
            this.right?.let { this.right!!.parent = this }
            tmp.left = this
            tmp.parent = this.parent
            this.parent = tmp

            if (tmp.parent != null) {
                if (this == tmp.parent!!.left) {
                    tmp.parent!!.left = tmp
                }
                else {
                    tmp.parent!!.right = tmp
                }
            }
            else {
                root = tmp
            }
        }

        internal fun rotateRight() {
            val tmp = this.left?: return
            this.left = tmp.right
            this.left?.let { this.left!!.parent = this }
            tmp.right = this
            tmp.parent = this.parent
            this.parent = tmp

            if (tmp.parent != null) {
                if (this == tmp.parent!!.left) {
                    tmp.parent!!.left = tmp
                }
                else {
                    tmp.parent!!.right = tmp
                }
            }
            else {
                root = tmp
            }
        }
    }
}