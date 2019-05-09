package redBlackTree

import java.util.*
import AbstractTree

class RBTree<K : Comparable<K>, V> : AbstractTree<K, V, Node<K, V>>()
{
    private fun makeBlack(node : Node<K, V>?)
    {
        if (node?.isBlack() == false)
            node.invertColor()
    }

    private fun makeRed(node : Node<K, V>?)
    {
        if (node?.isRed() == false)
            node.invertColor()
    }

    private fun findNode(key: K, current: Node<K, V>? = root): Node<K, V>?
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

    override fun buildDeque(current: Node<K, V>?, deque: Deque<Node<K, V>>)
    {
        if (current != null)
            deque.addLast(current)
        else return

        buildDeque(current.leftChild,  deque)
        buildDeque(current.rightChild, deque)
    }

    override fun find(key: K): Pair<K, V>?
    {
        val res = findNode(key) ?: return null
        return Pair(res.key, res.value)
    }

    override fun insert(key: K, value: V) {

        if (root == null)
        {
            root = Node(key, value, null, true)
            return
        }

        var parent: Node<K, V>? = null
        var current: Node<K, V>? = root

        while (current != null) {
            parent = current
            when {
                key < current.key -> current = current.leftChild
                key > current.key -> current = current.rightChild
                key == current.key -> {
                    current.value = value
                    return
                }
            }
        }

        if (key < parent!!.key) {
            parent.leftChild = Node(key, value, parent, false)
            balance(parent.leftChild)
        } else {
            parent.rightChild = Node(key, value, parent, false)
            balance(parent.rightChild)
        }

    }

    private fun balance(node: Node<K, V>?)
    {
        if (node == null)
            return

        var parent: Node<K, V>? = node.parent

        while (parent?.isBlack() == false) {
            if (node.uncle()?.isRed() == true) {
                makeBlack(node.uncle())
                makeBlack(parent)
                makeRed(node.grandparent())
                balance(node.grandparent())
            }
            else when{
                node.grandparent()?.leftChild == parent ->
                {
                    if (node == parent.leftChild)
                    {
                        parent = parent.parent
                        parent?.rotateRight()
                        parent = parent?.parent
                    }
                    if (node == parent!!.rightChild)
                    {
                        parent.rotateLeft()
                        parent = node.parent
                        parent?.rotateRight()
                        parent = node.parent
                    }
                }

                node.grandparent()?.rightChild == parent ->
                {
                    if (node == parent.rightChild)
                    {
                        parent = parent.parent
                        parent?.rotateLeft()
                        parent = parent?.parent
                    }
                    if (node == parent!!.leftChild)
                    {
                        parent.rotateRight()
                        parent = node.parent
                        parent?.rotateLeft()
                        parent = node.parent
                    }
                }
            }
        }

        while (root?.parent != null) {
            root = root?.parent
        }
        makeBlack(root)
    }
}