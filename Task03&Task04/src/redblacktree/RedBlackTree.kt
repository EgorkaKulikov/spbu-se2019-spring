package redblacktree

import tree.Tree

class RedBlackTree<Key: Comparable<Key>, Data> : Tree<Key, Data>, Iterable<Pair<Key, Data>> {

    internal var root: RedBlackTreeNode<Key, Data>? = null

    override fun insert(key: Key, data: Data) {

        var parent: RedBlackTreeNode<Key, Data>? = null
        var currentNode: RedBlackTreeNode<Key, Data>? = root

        while(currentNode != null) {

            parent = currentNode

            when {
                key < currentNode.key -> currentNode = currentNode.leftChild
                key > currentNode.key -> currentNode =  currentNode.rightChild
                key == currentNode.key -> {
                    currentNode.data = data
                    return
                }
            }

        }

        if (parent == null) {
            root = RedBlackTreeNode(key, data, parent,Color.BLACK)
            return
        }

        if (key < parent.key) {
            parent.leftChild = RedBlackTreeNode(key, data, parent)
            balance(parent.leftChild)
        } else {
            parent.rightChild = RedBlackTreeNode(key, data, parent)
            balance(parent.rightChild)
        }

    }

    private fun balance(node: RedBlackTreeNode<Key, Data>?){

        var currentNode = node

        if (currentNode == root) {
            currentNode?.color = Color.BLACK
            return
        }

        while(currentNode?.parent?.color == Color.RED) {

            if (currentNode.parent == currentNode.parent?.parent?.leftChild) {

                val uncleNode = currentNode.parent?.parent?.rightChild

                if (uncleNode?.color == Color.RED) {
                    uncleNode.color = Color.BLACK
                    currentNode.parent?.color = Color.BLACK
                    currentNode.parent?.parent?.color = Color.RED
                    currentNode = currentNode.parent?.parent!!
                } else {
                    if (currentNode == currentNode.parent?.rightChild) {
                        currentNode = currentNode.parent!!
                        currentNode.leftRotate()
                    }
                    currentNode.parent?.color = Color.BLACK
                    currentNode.parent?.parent?.color = Color.RED
                    currentNode.parent?.parent?.rightRotate()
                }

            } else {

                val uncleNode = currentNode.parent?.parent?.leftChild

                if (uncleNode?.color == Color.RED) {
                    uncleNode.color = Color.BLACK
                    currentNode.parent?.color = Color.BLACK
                    currentNode.parent?.parent?.color = Color.RED
                    currentNode = currentNode.parent?.parent ?: break
                } else {
                    if(currentNode == currentNode.parent?.leftChild) {
                        currentNode = currentNode.parent!!
                        currentNode.rightRotate()
                    }
                    currentNode.parent?.color = Color.BLACK
                    currentNode.parent?.parent?.color = Color.RED
                    currentNode.parent?.parent?.leftRotate()
                }

            }

        }

        while (root?.parent != null) root = root?.parent

        root?.color = Color.BLACK

    }

    override fun find(key: Key) : Pair<Key, Data>? {

        var currentNode: RedBlackTreeNode<Key, Data>? = root

        while(currentNode != null) {
            when {
                currentNode.key > key -> currentNode = currentNode.leftChild
                currentNode.key < key -> currentNode = currentNode.rightChild
                currentNode.key == key -> return Pair(currentNode.key, currentNode.data)
            }
        }

        return null

    }

    override fun iterator(): Iterator<Pair<Key, Data>> {

        var nextNode: RedBlackTreeNode<Key, Data>? = root

        while (nextNode?.leftChild != null) {
            nextNode = nextNode.leftChild
        }

        return (object : Iterator<Pair<Key, Data>> {

            override fun next(): Pair<Key, Data> {

                val nextN: RedBlackTreeNode<Key, Data>? = nextNode

                if (nextNode?.rightChild != null) {
                    nextNode = nextNode?.rightChild
                    while (nextNode?.leftChild != null) {
                        nextNode = nextNode?.leftChild
                    }
                    return Pair(nextN!!.key, nextN.data)
                }

                while (true) {

                    if (nextNode?.parent == null) {
                        nextNode = null
                        return Pair(nextN!!.key, nextN.data)
                    }

                    if (nextNode?.parent?.leftChild == nextNode) {
                        nextNode = nextNode?.parent
                        return Pair(nextN!!.key, nextN.data)
                    }

                    nextNode = nextNode?.parent
                }
            }

            override fun hasNext(): Boolean {
                return nextNode != null
            }

        })

    }

}
