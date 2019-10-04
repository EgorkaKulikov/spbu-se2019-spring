package avlTree

import AbstractTree
import java.util.*

class AVLTree<K : Comparable<K>, V> : AbstractTree<K, V, Node<K, V>>()
{
    private fun findNode(key : K, current : Node<K, V>? = root ) : Node<K, V>? =
        when
        {
            current == null -> null
            current.key == key -> current
            key > current.key -> findNode(key, current.rightChild)
            key < current.key -> findNode(key, current.leftChild)
            else -> null
        }

    override fun buildDeque(current : Node<K, V>?, deque : Deque<Node<K, V>>)
    {
        if (current != null)
            deque.addLast(current)
        else return

        buildDeque(current.leftChild, deque)
        buildDeque(current.rightChild, deque)
    }

    private fun balance(currentNode: Node<K, V>)
    {
        var node = currentNode

        while (node.getChildrenHeightDifference() !=  2 || node.getChildrenHeightDifference() != -2)
            node = node.parent ?: return

        when
        {
            node.getChildrenHeightDifference() == -2 ->
            {
                if (node.leftChild!!.getChildrenHeightDifference() > 0)
                    node.rotateRight()
                else
                {
                    node.leftChild?.rotateLeft()
                    node.rotateRight()
                }
            }

            node.getChildrenHeightDifference() == 2  ->
            {
                if (node.rightChild!!.getChildrenHeightDifference() > 0)
                    node.rotateLeft()
                else
                {
                    node.rightChild?.rotateRight()
                    node.rotateLeft()
                }
            }
        }

        var current : Node<K, V>? = node
        while (current != null)
        {
            current.fixHeight()
            current = current.parent
        }
    }

    override fun find(key : K) : Pair<K, V>?
    {
        val foundNode = findNode(key)
        return if (foundNode == null) null
               else Pair(foundNode.key, foundNode.value)
    }

    override fun insert(key: K, value: V)
    {
        if (root == null)
        {
            root = Node(key, value)
            return
        }

        var current = root
        var parent : Node<K, V>? = null

        while (current != null)
        {
            parent = current

            when
            {
                key < current.key -> current = current.leftChild
                key > current.key -> current = current.rightChild
                key == current.key ->
                {
                    current.value = value
                    return
                }
            }
        }

        if (key < parent!!.key)
        {
            parent.leftChild = Node(key, value, parent)
            balance(parent.leftChild!!)
        }
        else
        {
            parent.rightChild = Node(key, value, parent)
            balance(parent.rightChild!!)
        }
    }
}