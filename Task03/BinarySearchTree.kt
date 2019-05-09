package binaryTrees

open class BinarySearchTree<K : Comparable<K>, V> {
    public open inner class Node(internal var key: K, internal var value: V, internal var parent: Node?) {
        internal var left: Node? = null
        internal var right: Node? = null

        open fun print(indentation: Int, side: Int) {
            this.right?.print(indentation + 1, -1)

            for (i in 1..indentation) {
                print(" ")
            }

            when(side) {
                -1 -> print("/")
                1 -> print("\\")
            }

            println("$key $value")

            this.left?.print(indentation + 1, 1)
        }

        internal fun verifySearch(): Boolean {
            val localLeftNode = this.left ?: return true
            val localRightNode = this.right ?: return true

            return localLeftNode.verifySearch() && localRightNode.verifySearch()
        }

        internal fun verifyParents(): Boolean {
            val leftChild = this.left
            val rightChild = this.right

            return leftChild?.parent == null || leftChild.parent == this
                    && leftChild.verifyParents()
                    && rightChild?.parent == null || rightChild?.parent == this
                    && rightChild.verifyParents()
        }
    }

    var size = 0
        protected set

    protected var root: Node? = null

    fun find(key: K): V? {
        var currentNode: Node? = this.root

        while (currentNode != null) {
            when {
                currentNode.key == key -> return currentNode.value
                currentNode.key > key -> currentNode = currentNode.left
                currentNode.key < key -> currentNode = currentNode.right
            }
        }

        return null
    }

    protected open fun createNode(key: K, value: V, parent: Node?): Node {
        size++
        return Node(key, value, parent)
    }

    internal open fun innerInsert(key: K, value: V): Node {
        var localRoot = this.root //local copy of root, is used to perform null checks to avoid !!
        if (localRoot == null) {
            localRoot = createNode(key, value, null)
            this.root = localRoot
            return localRoot
        }
        var currentNode: Node = localRoot

        while (currentNode.key != key) {
            var localCurrentNode: Node? //local copy, is used to perform null checks to avoid !!

            if (currentNode.key > key) {
                localCurrentNode = currentNode.left
                if (localCurrentNode == null) {
                    localCurrentNode = createNode(key, value, currentNode)
                }
                currentNode.left = localCurrentNode

            } else {
                localCurrentNode = currentNode.right
                if (localCurrentNode == null) {
                    localCurrentNode = createNode(key, value, currentNode)
                }
                currentNode.right = localCurrentNode
            }

            currentNode = localCurrentNode

            if (currentNode.key == key) {
                currentNode.value = value
            }
        }
        return currentNode
    }

    fun insert(key: K, value: V) {
        innerInsert(key, value)
    }

    open fun print() {
        root?.print(0, 0)
    }

    internal fun isBinarySearchTree(): Boolean {
        val localRoot = root ?: return true
        return localRoot.verifySearch()
    }

    internal fun parentsCorrectness(): Boolean {
        val localRoot = root ?: return true
        return localRoot.parent == null && localRoot.verifyParents()
    }
}