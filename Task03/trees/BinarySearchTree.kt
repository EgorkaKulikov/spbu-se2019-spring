package trees

open class BinarySearchTree<K: Comparable<K>, V> {
    open inner class Node(internal val key: K, internal var value: V, internal var parent: Node?) {
        internal var left: Node? = null
        internal var right: Node? = null

        internal fun verifyKeysOrder(): Boolean {
            return ((this.left == null) || (this.left!!.key <= this.key
                    && this.left!!.verifyKeysOrder()))
                    && ((this.right == null) || (this.key <= this.right!!.key
                    && this.right!!.verifyKeysOrder()))
        }

        internal fun verifyParents(): Boolean {
            var result = true
            this.left?.let { result = result
                    && this.left!!.parent == this
                    && this.left!!.verifyParents() }

            this.right?.let { result = result
                    && this.right!!.parent == this
                    && this.right!!.verifyParents() }

            return result
        }
    }

    var size = 0
        protected set

    protected var root: Node? = null

    protected open fun createNode(key: K, value: V, parent: Node?): Node {
        return Node(key, value, parent)
    }

    fun find(key: K): V? {
        var tempNode = this.root

        while (tempNode != null) {
            when {
                key == tempNode.key -> return tempNode.value
                key < tempNode.key -> tempNode = tempNode.left
                key > tempNode.key -> tempNode = tempNode.right
            }
        }

        return null
    }

    internal open fun innerInsert(key: K, value: V): Node {
        if (this.root == null) {
            this.root = createNode(key, value, null)
            size++
            return this.root!!
        }

        var tempNode = this.root
        var parent: Node? = null
        var isRightSon = false
        var isLeftSon = false

        while (tempNode != null) {
            parent = tempNode
            when {
                key < tempNode.key -> {
                    tempNode = tempNode.left
                    if (tempNode == null) isLeftSon = true
                }
                key > tempNode.key -> {
                    tempNode = tempNode.right
                    if (tempNode == null) isRightSon = true
                }
                key == tempNode.key -> {
                    tempNode.value = value
                    return tempNode
                }
            }
        }

        val newNode = createNode(key, value, parent)
        if (isRightSon)
            parent!!.right = newNode
        if (isLeftSon)
            parent!!.left = newNode

        size++
        return newNode
    }

    fun insert(key: K, value: V) {
        innerInsert(key, value)
    }

    internal fun isBinarySearchTree(): Boolean {
        return if (root != null) {
            root!!.verifyKeysOrder()
        } else {
            true
        }
    }

    internal fun parentsCorrectness(): Boolean {
        return if (root != null) {
            root!!.parent == null && root!!.verifyParents()
        } else {
            true
        }
    }
}
