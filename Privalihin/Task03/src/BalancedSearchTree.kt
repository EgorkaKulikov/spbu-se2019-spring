abstract class BalancedSearchTree<T, K : Comparable<K>> : BinarySearchTree<T, K>() {

    protected fun Node.grandparent(): Node? {
        return this.parent?.parent
    }

    protected  fun Node.sibling(): Node? {
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

    protected  fun Node.uncle(): Node? {
        return this.parent?.sibling()
    }

    protected fun Node.rotateLeft() {
        val tmp = this.right?: return
        this.right = tmp.left
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

    protected fun Node.rotateRight() {
        val tmp = this.left?: return
        this.left = tmp.right
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