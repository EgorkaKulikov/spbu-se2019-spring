package trees

open class BinarySearchTree<K : Comparable<K>, V> {
    public open inner class Node
        (
        internal val key: K,
        internal var value: V,
        internal var parent: Node?
    ) {
        internal var left: Node? = null
        internal var right: Node? = null

        internal fun verifyBST(): Boolean {
            var correctOrderLeft = true
            var correctOrderRight = true

            if (left != null && left!!.key < key) {
                correctOrderLeft = left!!.verifyBST()
            }

            if (right != null && right!!.key > key) {
                correctOrderLeft = right!!.verifyBST()
            }

            return (correctOrderLeft && correctOrderRight)
        }

        internal fun verifyParents(): Boolean {
            var correctParentLeft = true
            var correctParentRight = true

            if (left != null) {
                correctParentLeft = (left!!.parent == this) && (left!!.verifyParents())
            }

            if (right != null) {
                correctParentRight = (right!!.parent == this) && (right!!.verifyParents())
            }

            return (correctParentLeft && correctParentRight)
        }
    }

    var size = 0
        protected set

    internal var root: Node? = null

    internal fun isBinarySearchTree() = 
        (root?.verifyBST() ?: true)

    internal fun parentsCorrectness() =
        (root == null)
        || (root!!.parent == null 
            && root!!.verifyParents())

    protected open fun createNode(key: K, value: V, parent: Node?) =
        Node(key, value, parent)

    fun find(key: K): V? {
        var current = root

        while (current != null && current.key != key) {
            if (current.key < key) {
                current = current.right
            } else {
                current = current.left
            }
        }

        return current?.value
    }

    fun insert(key: K, value: V) {
        innerInsert(key, value)
    }

    internal open fun innerInsert(key: K, value: V): Node {
        var previous: Node? = null
        var current = root

        while (current != null && current.key != key) {
            previous = current

            if (current.key > key) {
                current = current.left
            } else {
                current = current.right
            }
        }

        if (current != null) {
            current.value = value
        } else {
            current = createNode(key, value, previous)
            size++

            when {
                root == null -> root = current
                previous!!.key < key -> previous.right = current
                else -> previous.left = current
            }
        }

        return current
    }
}