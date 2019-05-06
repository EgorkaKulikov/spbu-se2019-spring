import com.sun.org.apache.xpath.internal.operations.Bool

open class BinarySearchTree<K : Comparable<K>, V> {
    public open inner class Node(var key: K, var value: V, var parent: Node?) {
        var left: Node? = null
        var right: Node? = null

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

        fun verifySearch(): Boolean {
            return ((this.left == null) || (this.left!!.key <= this.key
                                            && this.left!!.verifySearch()))
                    && ((this.right == null) || (this.key <= this.right!!.key
                                            && this.right!!.verifySearch()))
        }

        fun verifyParents(): Boolean {
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

    open fun find(key: K): V? {
        var curNode: Node? = this.root

        while (curNode != null) {
            when {
                (curNode.key == key) -> return curNode.value
                (curNode.key > key) -> curNode = curNode.left
                else -> curNode = curNode.right
            }
        }

        return null
    }

    protected open fun createNode(key: K, value: V, parent: Node?): Node {
        return Node(key, value, parent)
    }

    protected open fun innerInsert(key: K, value: V): Node {

        if (this.root == null) {
            this.root = createNode(key, value, null)
            size++
            return this.root!!
        }

        var curNode = this.root!!

        while (true) {
            when {
                (curNode.key == key) -> {
                    curNode.value = value
                    return curNode
                }

                (curNode.key > key) -> {
                    if (curNode.left == null) {
                        curNode.left = createNode(key, value, curNode)
                        size++
                        return curNode.left!!
                    }

                    curNode = curNode.left!!
                }
                (curNode.key < key) -> {
                    if (curNode.right == null) {
                        curNode.right = createNode(key, value, curNode)
                        size++
                        return curNode.right!!
                    }

                    curNode = curNode.right!!
                }
            }
        }
    }

    fun insert(key: K, value: V) {
        innerInsert(key, value)
    }

    open fun print() {
        root?.print(0, 0)
    }

    fun isBinarySearchTree(): Boolean {
        return root == null || root!!.verifySearch()
    }

    fun parentsCorrectness(): Boolean {
        return root == null || (root!!.parent == null && root!!.verifyParents())
    }
}