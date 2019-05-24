package AVLTree

import kotlin.math.max

class AVLNode<K : Comparable<K>, V>(
    val key: K,
    var value: V,
    var parent: AVLNode<K, V>? = null
) {
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
        result = 31 * result + (left?.hashCode() ?: 0)
        result = 31 * result + (right?.hashCode() ?: 0)
        result = 31 * result + height
        return result
    }


    private fun getLeafHeight(node: AVLNode<K, V>?): Pair<Int, Int> {
        when {
            node?.height == null -> throw Exception("Error, null node hasn't leaf")
            else -> return Pair(node.left?.height ?: 0, node.right?.height ?: 0)
        }
    }

    fun leftRotate() {
        val rightChild = this.right ?: throw Exception("Right child for node does no exist")
        val parent = parent

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
        height = max(getLeafHeight(this).first, getLeafHeight(this).second) + 1
        rightChild.height = max(getLeafHeight(rightChild).first, getLeafHeight(rightChild).second) + 1
    }


    fun rightRotate() {
        val leftChild = this.left ?: throw Exception("Left child for node does no exist")
        val parent = parent

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
        height = max(getLeafHeight(this).first, getLeafHeight(this).second) + 1
        leftChild.height = max(getLeafHeight(leftChild).first, getLeafHeight(leftChild).second) + 1
    }


    internal fun getBalance() = getLeafHeight(this).first - getLeafHeight(this).second


    internal fun correctHeight() {
        height = max(getLeafHeight(this).first, getLeafHeight(this).second) + 1
    }

}
