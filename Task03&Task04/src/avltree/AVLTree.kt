package avltree

import tree.Tree

class AVLTree<Key: Comparable<Key>, Data>: Tree<Key, Data>, Iterable<Pair<Key, Data>> {

    internal var root: AVLTreeNode<Key, Data>? = null

    override fun find(key: Key): Pair<Key, Data>? {

        var currentNode = root

        while(currentNode != null) {
            when {
                currentNode.key > key -> currentNode = currentNode.leftChild
                currentNode.key < key -> currentNode = currentNode.rightChild
                currentNode.key == key -> return Pair(currentNode.key, currentNode.data)
            }
        }

        return null

    }

    override fun insert(key: Key, data: Data) {

        var parent: AVLTreeNode<Key, Data>? = null
        var currentNode: AVLTreeNode<Key, Data>? = root

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
            root = AVLTreeNode(key, data, parent)
            return
        }

        if (key < parent.key) {
            parent.leftChild = AVLTreeNode(key, data, parent)
            balance(parent.leftChild!!)
        } else {
            parent.rightChild = AVLTreeNode(key, data, parent)
            balance(parent.rightChild!!)
        }
    }

    private fun balance(node: AVLTreeNode<Key, Data>) {

        node.updateHeight()

        if (node.getHeight(node.rightChild) - node.getHeight(node.leftChild) == 2) {
            if (node.getHeight(node.rightChild!!.rightChild) >= node.getHeight(node.rightChild!!.leftChild)) {
                node.leftRotate()
            } else {
                node.bigLeftRotate()
            }
        } else if (node.getHeight(node.rightChild) - node.getHeight(node.leftChild) == -2) {
            if (node.getHeight(node.leftChild!!.leftChild) >= node.getHeight(node.leftChild!!.rightChild)) {
                node.rightRotate()
            } else {
                node.bigRightRotate()
            }
        }

        if (node.parent != null) balance(node.parent!!) else root = node

    }

    override fun iterator(): Iterator<Pair<Key, Data>> {

        var nextNode: AVLTreeNode<Key, Data>? = root

        while (nextNode?.leftChild != null) {
            nextNode = nextNode.leftChild
        }

        return (object : Iterator<Pair<Key, Data>> {

            override fun next(): Pair<Key, Data> {

                val nextN: AVLTreeNode<Key, Data>? = nextNode

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
