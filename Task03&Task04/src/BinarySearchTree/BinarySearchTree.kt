package BinarySearchTree

import tree.Tree


class BinarySearchTree<Key: Comparable<Key>, Data> : Tree<Key, Data>, Iterable<Pair<Key, Data>> {

    var root: BinarySearchTreeNode<Key, Data>? = null

    override fun find(key: Key): Pair<Key, Data>? {

        var currentNode: BinarySearchTreeNode<Key, Data>? = root

        while(currentNode != null) {
            if (currentNode.key == key) {
                return Pair(currentNode.key, currentNode.data)
            }
            when {
                currentNode.key > key -> currentNode = currentNode.leftChild
                currentNode.key < key -> currentNode = currentNode.rightChild
            }
        }

        return null

    }

    override fun insert(key: Key, data: Data) {

        val newNode: BinarySearchTreeNode<Key, Data> = BinarySearchTreeNode(key, data)

        if (root == null) {
            root = newNode
            return
        }

        var currentNode: BinarySearchTreeNode<Key, Data>? = root

        while (currentNode != null) {
            if (currentNode.key > newNode.key) {
                if (currentNode.leftChild == null) {
                    currentNode.leftChild = newNode
                    newNode.parent = currentNode
                    return
                } else {
                    currentNode = currentNode.leftChild
                }
            } else  if (currentNode.key < newNode.key)  {

                if (currentNode.rightChild == null) {
                    currentNode.rightChild = newNode
                    newNode.parent = currentNode
                    return
                } else {
                    currentNode = currentNode.rightChild
                }
            } else if (currentNode.key == newNode.key) {
                currentNode.data = newNode.data
                return
            }
        }

        return

    }

    override fun iterator(): Iterator<Pair<Key, Data>> {

        var nextNode: BinarySearchTreeNode<Key, Data>? = root

        while(nextNode?.leftChild != null) nextNode = nextNode.leftChild

        return (object : Iterator<Pair<Key, Data>> {

            override fun next(): Pair<Key, Data> {

                val nextN: BinarySearchTreeNode<Key, Data>? = nextNode

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
