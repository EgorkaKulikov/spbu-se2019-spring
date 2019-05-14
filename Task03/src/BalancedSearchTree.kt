abstract class BalancedSearchTree<T, K : Comparable<K>> : BinarySearchTree<T, K>() {
    open inner class BalancedNode(_key: K, _value: T, _parent: Node?)  : Node(_key, _value, _parent)  {
        fun grandparent(): Node? {
            return this.parent?.parent
        }

        fun uncle(): Node? {
            if(this.grandparent() != null){
                if(this.grandparent()?.left == this.parent) return this.grandparent()?.right
                else return this.grandparent()?.left
            }
            else return null
        }

        fun rotateLeft() {
            val tmpNode = this.right ?: return

            if (this.parent != null) {
                if (this == this.parent!!.left) {
                    this.parent!!.left = tmpNode
                }
                else {
                    this.parent!!.right = tmpNode
                }
            }
            else {
                root = tmpNode
            }

            tmpNode.parent = this.parent
            this.parent = tmpNode
            this.right = tmpNode.left
            tmpNode.left = this
            this.right?.parent = this
        }

        fun rotateRight() {
            val tmpNode = this.left ?: return

            if (this.parent != null) {
                if (this == this.parent!!.left) {
                    this.parent!!.left = tmpNode
                }
                else {
                    this.parent!!.right = tmpNode
                }
            }
            else {
                root = tmpNode
            }

            tmpNode.parent = this.parent
            this.parent = tmpNode
            this.left = tmpNode.right
            tmpNode.right = this
            this.left?.parent = this
        }

        fun verifyParents(): Boolean{
            if(this.left != null){
                return ((this.left as BalancedNode).parent == this) && ((this.left as BalancedNode).verifyParents())
            }
            if(this.right != null){
                return ((this.right as BalancedNode).parent == this) && ((this.right as BalancedNode).verifyParents())
            }
            return true
        }
    }

    internal fun parentsCorrectness(): Boolean {
        return root == null || (root!!.parent == null && (root as BalancedNode).verifyParents())
    }
}