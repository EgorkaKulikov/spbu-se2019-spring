package AVLTree

import kotlin.math.max

class AVLNode<K: Comparable<K>, V>(
    var key: K,
    var value: V,
    var parent:AVLNode<K, V>? = null) {
    var left: AVLNode<K, V>? = null
    var right: AVLNode<K, V>? = null
    var height: Int = 1

    override fun equals(other: Any?): Boolean =
        other is AVLNode<*, *>
                && key == other.key
                && value == other.value
                && left == other.left
                && right == other.right
                && height == other.height

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + (value?.hashCode() ?: 0)
        result = 31 * result + (parent?.hashCode() ?: 0)
        result = 31 * result + (left?.hashCode() ?: 0)
        result = 31 * result + (right?.hashCode() ?: 0)
        return result
    }

    fun getHeight(node: AVLNode<K, V>?) = node?.height ?: 0

    fun leftRotate(){
        val rightChild = this.right ?: throw Exception("Null node")
        val parent = this.parent

        rightChild.left?.parent = this
        this.right = rightChild.left
        rightChild.left = this

        when (this) {
            parent?.right -> parent.right = rightChild
            parent?.left -> parent.left = rightChild
        }

        this.parent = rightChild
        rightChild.parent = parent

        // Update heights
        this.height = max(getHeight(this.left), getHeight(this.right)) + 1
        rightChild.height = max(getHeight(rightChild.left), getHeight(rightChild.right)) + 1
    }

    fun rightRotate(){
        val leftChild = this.left ?: throw Exception("Null node")
        val parent = this.parent

        leftChild.right?.parent = this
        this.left = leftChild.right
        leftChild.right = this

        when (this) {
            parent?.left -> parent.left = leftChild
            parent?.right -> parent.right = leftChild
        }

        this.parent = leftChild
        leftChild.parent = parent

        //update heights
        this.height = max(getHeight(this.left), getHeight(this.right)) + 1
        leftChild.height = max(getHeight(leftChild.left), getHeight(leftChild.right)) + 1
    }

    internal fun getBalance() = getHeight(this.left) - getHeight(this.right)

    internal fun correctHeight() {
        this.height = max(getHeight(this.left), getHeight(this.right)) + 1
    }



}
