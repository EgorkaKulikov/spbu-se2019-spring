package binarySearchTree

import INode

class Node<K : Comparable<K>, V> (var key   : K,
                                  var value : V,
                                  override var parent: Node<K, V>? = null
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
                this.safeParent() == other.safeParent()

    override fun hashCode(): Int
    {
        var result = key.hashCode()
        result = 31 * result + (value?.hashCode() ?: 0)
        result = 31 * result + (leftChild?.hashCode() ?: 0)
        result = 31 * result + (rightChild?.hashCode() ?: 0)
        result = 31 * result + (safeParent()?.hashCode() ?: 0)
        return result
    }
}