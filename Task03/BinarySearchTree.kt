package trees

open class BinarySearchTree<K : Comparable<K>, V> {
    open inner class Node(internal var key: K, internal var value: V, internal var parent: Node?) {
        internal var left: Node? = null
        internal var right: Node? = null

        internal fun verifySearch(): Boolean {
            return ((this.left == null) || (this.left!!.key <= this.key
                    && this.left!!.verifySearch()))
                    && ((this.right == null) || (this.key <= this.right!!.key
                    && this.right!!.verifySearch()))
        }

        internal fun verifyParents(): Boolean {
            var result = true

            if (this.left != null) {
                result = result
                        && this.left!!.parent == this
                        && this.left!!.verifyParents()
            }

            if (this.right != null) {
                result = result
                        && this.right!!.parent == this
                        && this.right!!.verifyParents()
            }

            return result
        }
    }

    protected var root: Node? = null
    var numberOfNodes = 0

    fun find(key: K): V? {
        var tempNode = root
        while(tempNode != null) {
            when {
                tempNode.key == key -> return tempNode.value
                tempNode.key > key -> tempNode = tempNode.left
                tempNode.key < key -> tempNode = tempNode.right
            }
        }

        return null
    }

    protected open fun createNode(key: K, value: V, parent: Node?): Node {
        return Node(key, value, parent)
    }

    internal open fun innerInsert(key: K, value: V): Node {
        if (root == null) {
            root = createNode(key, value, null)
            numberOfNodes++
            return root!!
        }

        var tempNode = root!!
        while (true) {
            if (tempNode.key > key) {
                if (tempNode.left == null) {
                    val newNode = createNode(key, value, tempNode)
                    tempNode.left = newNode
                    numberOfNodes++
                    return newNode
                }
                else {
                    tempNode = tempNode.left!!
                }
            }
            if (tempNode.key < key) {
                if (tempNode.right == null) {
                    val newNode = createNode(key, value, tempNode)
                    tempNode.right = newNode
                    numberOfNodes++
                    return newNode
                }
                else {
                    tempNode = tempNode.right!!
                }
            }
            if (tempNode.key == key) {
                tempNode.value = value
                return tempNode
            }
        }
    }

    fun insert(key: K, value: V) {
        innerInsert(key, value)
    }

    internal fun isBinarySearchTree(): Boolean {
        return if (root == null) {
            true
        }
        else {
            root!!.verifySearch()
        }

    }

    internal fun parentsCorrectness(): Boolean {
        return if (root == null) {
            true
        }
        else {
            (root!!.parent == null && root!!.verifyParents())
        }

    }
}