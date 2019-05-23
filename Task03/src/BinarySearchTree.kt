open class BinarySearchTree<T, K : Comparable<K>> {
    open inner class Node(val key: K, var value: T, open var parent: Node?) {
        open var left: Node? = null
        open var right: Node? = null
    }

    internal var size: Int = 0
    protected var root: Node? = null

    open fun find(key: K): T? {
        var curNode: Node? = this.root
        while (curNode != null) {
            if(curNode.key == key) return curNode.value
            else if(curNode.key > key) curNode = curNode.left
            else curNode = curNode.right
        }
        return null
    }

    protected open fun createNode(value: T, key: K, parent: Node?): Node {
        return Node(key, value, parent)
    }

    fun insert(key: K, value: T) {
        innerInsert(key, value)
    }

    open fun innerInsert(key: K, value: T) : Node{
        if (this.root == null) {
            this.root = createNode(value, key, null)
            size++
            return this.root!!
        }

        var curNode: Node? = this.root
        val newNode = createNode(value, key, null)
        while (curNode != null) {
            if (newNode.key > curNode.key) {
                if (curNode.right != null) curNode = curNode.right
                else {
                    newNode.parent = curNode
                    curNode.right = newNode
                    break
                }
            }
            else if (newNode.key < curNode.key) {
                if (curNode.left != null) curNode = curNode.left
                else {
                    newNode.parent = curNode
                    curNode.left = newNode
                    break
                }
            }
            else{
                curNode.value = value
                return curNode
            }
        }
        size++
        return newNode
    }

    private fun verifySearch(curNode: Node): Boolean{
        return (curNode.left == null || curNode.key >= curNode.left!!.key && verifySearch(curNode.left!!)) &&
                (curNode.right == null || curNode.key <= curNode.right!!.key && verifySearch(curNode.right!!))
    }

    internal fun isBinarySearchTree(): Boolean{
        return this.root == null || verifySearch(this.root!!)
    }
}