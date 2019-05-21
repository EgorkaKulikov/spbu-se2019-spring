package trees

open class BinarySearchTree<K:Comparable<K>, T> {
    open inner class Node(internal var key: K, internal var value: T, internal var parent: Node?) {
        internal var left: Node? = null
        internal var right: Node? = null

        //part of the team tests
        internal fun verifySearch(): Boolean {
            return ((this.left == null) || (this.left!!.key <= this.key
                    && this.left!!.verifySearch()))
                    && ((this.right == null) || (this.key <= this.right!!.key
                    && this.right!!.verifySearch()))
        }

        //part of the team tests
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

    var numEdges = 0
        protected set

    protected var root: Node? = null

    protected open fun createNode(key: K, value: T, parent: Node?): Node {
        return Node(key, value, parent)
    }

    fun find(key: K): T? {
        var currNode: Node? = root

        while (currNode != null) {
            when {
                currNode.key == key -> return currNode.value
                currNode.key > key -> currNode = currNode.left
                currNode.key < key -> currNode = currNode.right
            }
        }

        return null
    }

    fun insert(key: K, value: T) {
        innerInsert(key, value)
    }

    internal open fun innerInsert(key: K, value: T): Node {

        if (root == null) {
            root = createNode(key, value, null)
            numEdges++
            return root!!
        }

        var currNode = root!!

        while (true) {
            when {
                currNode.key > key -> {
                    if (currNode.left == null) {
                        currNode.left = createNode(key, value, currNode)
                        numEdges++
                        return currNode.left!!
                    } else {
                        currNode = currNode.left!!  //We checked it's not null
                    }
                }
                currNode.key < key -> {
                    if (currNode.right == null) {
                        currNode.right = createNode(key, value, currNode)
                        numEdges++
                        return currNode.right!!
                    } else {
                        currNode = currNode.right!! //We checked it's not null
                    }
                }
                else -> {
                    currNode.value = value
                    return currNode
                }
            }
        }
    }

    //part of the team tests
    internal fun isBinarySearchTree(): Boolean {
        return if (root != null) {
            root!!.verifySearch()
        } else {
            true
        }
    }

    //part of the team tests
    internal fun parentsCorrectness(): Boolean {
        return if (root != null) {
            root!!.parent == null && root!!.verifyParents()
        } else true
    }
}