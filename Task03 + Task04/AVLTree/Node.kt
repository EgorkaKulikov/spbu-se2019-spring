package AVLTree

import INode

class Node<K : Comparable<K>, V> (var key   : K,
                                  var value : V,
                                  override var parent : Node<K, V>? = null
                                 ) : INode<Node<K, V>>
{
    override var leftChild  : Node<K, V>? = null
    override var rightChild : Node<K, V>? = null

    var height : Int = 1

    private fun safeParent() : Node<K, V>?
    {
        if (parent == null)
            return null
        else
            return Node(parent!!.key, parent!!.value)
    }

    override fun equals(other: Any?): Boolean =
        other is Node<*, *> &&
                key == other.key &&
                value == other.value &&
                leftChild == other.leftChild &&
                rightChild == other.rightChild &&
                height == other.height &&
                this.safeParent() == other.safeParent()

    override fun hashCode(): Int
    {
        var result = key.hashCode()
        result = 31 * result + (height.hashCode())
        result = 31 * result + (value?.hashCode() ?: 0)
        result = 31 * result + (leftChild?.hashCode() ?: 0)
        result = 31 * result + (rightChild?.hashCode() ?: 0)
        result = 31 * result + (safeParent()?.hashCode() ?: 0)
        return result
    }

    fun rotateLeft()
    {
        val currentRight = this.rightChild ?: return
        val parent = this.parent

        currentRight.leftChild?.parent = this
        this.rightChild = currentRight.leftChild
        currentRight.leftChild = this

        when (this) {
            parent?.leftChild -> parent.leftChild = currentRight
            parent?.rightChild -> parent.rightChild = currentRight
        }

        this.parent = currentRight
        currentRight.parent = parent

        this.fixHeight()
        currentRight.fixHeight()
        currentRight.parent?.fixHeight()
    }

    fun rotateRight()
    {
        val currentLeft = this.leftChild ?: return
        val parent = this.parent

        currentLeft.rightChild?.parent = this
        this.leftChild = currentLeft.rightChild
        currentLeft.rightChild = this

        when (this) {
            parent?.leftChild -> parent.leftChild = currentLeft
            parent?.rightChild -> parent.rightChild = currentLeft
        }

        this.parent = currentLeft
        currentLeft.parent = parent

        this.fixHeight()
        currentLeft.fixHeight()
        currentLeft.parent?.fixHeight()
    }

    fun getChildrenHeightDifference() : Int =
        (rightChild?.height ?: 0) - (leftChild?.height ?: 0)

    fun fixHeight()
    {
        height = Math.max(leftChild?.height ?: 0, rightChild?.height ?: 0) + 1
    }
}