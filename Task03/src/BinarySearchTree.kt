package binaryTrees

open class BinarySearchTree<K: Comparable<K>, V> {

    open inner class Node(internal var key: K, internal var value: V, internal var parent: Node?) {
        internal var left: Node? = null
        internal var right: Node? = null

        internal fun isLeftSon(): Boolean {
            return parent?.left == this
        }

        internal fun isRightSon(): Boolean {
            return parent?.right == this
        }

        internal fun verifySearch(): Boolean {
            return ((this.left == null) || (this.left!!.key < this.key
                                            && this.left!!.verifySearch()))
                    && ((this.right == null) || (this.right!!.key > this.key
                                            && this.right!!.verifySearch()))
        }

        internal fun verifyParents(): Boolean {
            var areCorrect = true

            if (this.left != null) {
                areCorrect = areCorrect && this.left!!.parent == this
                        && this.left!!.verifyParents()
            }

            if (this.right != null) {
                areCorrect = areCorrect && this.right!!.parent == this
                        && this.right!!.verifyParents()
            }

            return areCorrect
        }
    }

    protected var root: Node? = null
    var size = 0
        protected set

    protected open fun createNode(key: K, value: V, parent: Node?): Node {
        return Node(key, value, parent)
    }

    internal fun isBinarySearchTree() = root == null || root!!.verifySearch()

    internal fun parentsCorrectness() = root == null
                                    || (root!!.parent == null && root!!.verifyParents())

    fun find(key: K) : V? {
        var current = root

        while (current != null) {
            when {
                current.key == key -> return current.value
                current.key < key -> current = current.right
                else -> current = current.left
            }
        }

        return null
    }

    internal open fun innerInsert(key: K, value: V): Node {
        if (root == null) {
            root = createNode(key, value, null)
            size++
            return root!!
        }

        var current = root!!

        while(true) {
            when {
                current.key == key -> {
                    current.value = value
                    return current
                }
                current.key > key -> {
                    if (current.left == null) {
                        current.left = createNode(key, value, current)
                        size++
                        return current.left!!
                    }
                    current = current.left!!
                }
                else -> {
                    if (current.right == null) {
                        current.right = createNode(key, value, current)
                        size++
                        return current.right!!
                    }
                    current = current.right!!
                }
            }
        }
    }

    fun insert(key: K, value: V) {
        innerInsert(key, value)
    }
}
