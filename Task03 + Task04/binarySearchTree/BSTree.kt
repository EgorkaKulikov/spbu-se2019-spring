package binarySearchTree

import AbstractTree
import java.util.*

class BSTree<K : Comparable<K>, V> : AbstractTree<K, V, Node<K, V>>()
{
    private fun findNode(key : K, current : Node<K, V>? = root ) : Node<K, V>?
    {
        return when
        {
            current == null -> null
            current.key == key -> current
            key > current.key -> findNode(key, current.rightChild)
            key < current.key -> findNode(key, current.leftChild)
            else -> null
        }
    }

    override fun buildDeque(current : Node<K, V>?, deque : Deque<Node<K, V>>)
    {
        if (current != null)
            deque.addLast(current)
        else return

        buildDeque(current.leftChild, deque)
        buildDeque(current.rightChild, deque)
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

        if (key < parent!!.key) parent.leftChild  = Node(key, value, parent)
        else parent.rightChild = Node(key, value, parent)
    }
}