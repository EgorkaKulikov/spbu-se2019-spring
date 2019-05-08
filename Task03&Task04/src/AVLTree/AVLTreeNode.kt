package AVLTree

class AVLTreeNode<Key: Comparable<Key>, Data>(var key: Key, var data: Data, var parent: AVLTreeNode<Key, Data>? = null){

    var height : Int = 1
    var leftChild: AVLTreeNode<Key, Data>? = null
    var rightChild: AVLTreeNode<Key, Data>? = null

    override fun equals(other: Any?): Boolean {
        if (other is AVLTreeNode<*, *>?) {

            if (other == null)
                return false

            if (this.key == other.key &&
                this.data == other.data &&
                this.height == other.height &&
                this.leftChild?.key == other.leftChild?.key &&
                this.rightChild?.key == other.rightChild?.key &&
                this.leftChild?.data == other.leftChild?.data &&
                this.rightChild?.data == other.rightChild?.data &&
                this.parent?.key == other.parent?.key &&
                this.parent?.height == other.parent?.height) {

                return true
            }

            return false
        }

        return false
    }

    fun leftRotate() {
        val rightChild = this.rightChild ?: return

        this.rightChild = rightChild.leftChild
        rightChild.leftChild?.parent = this
        rightChild.leftChild = this
        rightChild.parent = this.parent
        this.parent = rightChild

        when (this) {
            rightChild.parent?.leftChild -> rightChild.parent?.leftChild = rightChild
            rightChild.parent?.rightChild -> rightChild.parent?.rightChild = rightChild
        }

        this.updateHeight()
        rightChild.updateHeight()
    }

    fun rightRotate() {
        val leftChild = this.leftChild ?: return

        this.leftChild = leftChild.rightChild
        leftChild.rightChild?.parent = this
        leftChild.rightChild = this
        leftChild.parent = this.parent
        this.parent = leftChild

        when (this) {
            leftChild.parent?.rightChild -> leftChild.parent?.rightChild = leftChild
            leftChild.parent?.leftChild -> leftChild.parent?.leftChild = leftChild
        }

        this.updateHeight()
        leftChild.updateHeight()
    }

    fun bigLeftRotate() {
        this.rightChild?.rightRotate()
        this.leftRotate()
    }

    fun bigRightRotate() {

        this.leftChild?.leftRotate()
        this.rightRotate()
    }

    internal fun updateHeight() {
        height = kotlin.math.max(leftChild?.height ?: 0, rightChild?.height ?: 0) + 1
    }

    fun getHeight(node: AVLTreeNode<Key, Data>?): Int = node?.height ?: 0
}