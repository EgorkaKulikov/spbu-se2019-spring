package redBlackTree

import INode

class Node<K : Comparable<K>, V> (var key   : K,
                                  var value : V,
                                  override var parent : Node<K, V>? = null,
                                  private var isBlack : Boolean = false
                                 ) : INode<Node<K, V>>
{
    override var leftChild  : Node<K, V>? = null
    override var rightChild : Node<K, V>? = null

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
                isBlack == other.isBlack &&
                this.safeParent() == other.safeParent()

    override fun hashCode(): Int
    {
        var result = key.hashCode()
        result = 31 * result + (isBlack.hashCode())
        result = 31 * result + (value?.hashCode() ?: 0)
        result = 31 * result + (leftChild?.hashCode() ?: 0)
        result = 31 * result + (rightChild?.hashCode() ?: 0)
        result = 31 * result + (safeParent()?.hashCode() ?: 0)
        return result
    }

    private fun swapColorsWith(node : Node<K, V>?)
    {
        val currentColor = isBlack
        if (node != null)
        {
            isBlack = node.isBlack
            node.isBlack = currentColor
        }
    }

    fun rotateLeft()
    {

        val currentRight = this.rightChild ?: return
        val parent = this.parent
        this.swapColorsWith(currentRight)

        currentRight.leftChild?.parent = this
        this.rightChild = currentRight.leftChild
        currentRight.leftChild = this

        when (this) {
            parent?.rightChild -> parent.rightChild = currentRight
            parent?.leftChild  -> parent.leftChild  = currentRight
        }

        this.parent = currentRight
        currentRight.parent = parent
    }

    fun rotateRight()
    {
        val currentLeft = this.leftChild ?: return
        val parent = this.parent
        this.swapColorsWith(currentLeft)

        currentLeft.rightChild?.parent = this
        this.leftChild = currentLeft.rightChild
        currentLeft.rightChild = this

        when (this) {
            parent?.leftChild  -> parent.leftChild  = currentLeft
            parent?.rightChild -> parent.rightChild = currentLeft
        }

        this.parent = currentLeft
        currentLeft.parent = parent
    }

    fun uncle() : Node<K, V>?
    {
        when (parent)
        {
            parent?.parent?.leftChild  -> return parent?.parent?.rightChild
            parent?.parent?.rightChild -> return parent?.parent?.leftChild
        }
        return null
    }

    fun invertColor()
    {
        isBlack = !isBlack
    }

    fun grandparent() = parent?.parent

    fun isRed()   = !isBlack

    fun isBlack() =  isBlack
}