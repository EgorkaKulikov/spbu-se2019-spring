package RedBlackTree

enum class Color {
    RED,
    BLACK
}

class RedBlackTreeNode<Key, Data>(var key: Key, var data: Data,
                                  var parent: RedBlackTreeNode<Key, Data>? = null, var color: Color = Color.RED) {

    var leftChild: RedBlackTreeNode<Key, Data>? = null
    var rightChild: RedBlackTreeNode<Key, Data>? = null

    override fun equals(other: Any?): Boolean {

        if (other is RedBlackTreeNode<*, *>?) {
            if (other == null)
                return false

            if (this.key == other.key &&
                this.data == other.data &&
                this.color == other.color &&
                this.leftChild?.key == other.leftChild?.key &&
                this.rightChild?.key == other.rightChild?.key &&
                this.leftChild?.data == other.leftChild?.data &&
                this.rightChild?.data == other.rightChild?.data &&
                this.parent?.key == other.parent?.key &&
                this.parent?.color == other.parent?.color) {

                return true
            }

            return false
        }

        return false
    }

    internal fun leftRotate() {

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

    }

    internal fun rightRotate() {

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

    }

    internal fun isLeaf():Boolean = this.leftChild == null && this.rightChild == null //function only for tests

}