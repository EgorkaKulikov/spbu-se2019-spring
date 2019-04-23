open class BinarySearchTree<T, K : Comparable<K>> {
    public open inner class Node(_value: T, _key: K, _parent: Node?) {
        var left: Node? = null
        var right: Node? = null
        var parent: Node? = _parent
        var value = _value
        val key = _key
    }

    var size = 0
        protected set

    protected open var root: Node? = null

    public open fun find(key: K): T? {
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

    protected open fun createNode(value: T, key: K, parent: Node?): Node {
        return Node(value, key, parent)
    }

    public open fun insert(value: T, key: K): Node {

        if (this.root == null) {
            this.root = createNode(value, key, null)
            size++
            return this.root!!
        }

        var curNode = this.root!!

        while (true) {
            if (curNode.key == key) {
                curNode.value = value
                return curNode
            }

            if (curNode.key > key) {
                if (curNode.left == null) {
                    curNode.left = createNode(value, key, curNode)
                    curNode.left!!.parent = curNode
                    size++
                    return curNode.left!!
                }

                curNode = curNode.left!!
            } else {
                if (curNode.right == null) {
                    curNode.right = createNode(value, key, curNode)
                    size++
                    curNode.right!!.parent = curNode
                    return curNode.right!!
                }

                curNode = curNode.right!!
            }
        }

    }
}