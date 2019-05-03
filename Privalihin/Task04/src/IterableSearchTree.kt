class IterableSearchTree<T, K: Comparable<K>>: Iterable<T> {
    inner class Node(_value: T, _key: K) {
        var left: Node? = null
        var right: Node? = null
        var value = _value
        val key = _key
        var parent: Node? = null

        constructor(_value: T, _key: K, _parent: Node): this(_value, _key) {
            parent = _parent
        }

        fun print(indentation: Int) {
            this.left?.print(indentation + 1)

            for (i in 1..indentation) {
                print(" ")
            }

            println("$key $value")

            this.right?.print(indentation + 1)
        }
    }

    fun print() {
        root?.print(0)
    }

    private var root: Node? = null

    inner class SearchTreeIterator: Iterator<T> {
        private fun leftMost(node: Node?): Node? {
            if (node?.left == null) {
                return node
            }

            return leftMost(node.left)
        }

        private var curNode = leftMost(root)

        override fun next(): T {
            if (curNode == null) {
                throw IndexOutOfBoundsException()
            }

            var current = curNode!!
            val returnValue = current.value

            if (current.right != null) {
                curNode = leftMost(current.right)
            }
            else {

                while (current.parent != null) {
                    if (current.parent!!.left == current) {
                        curNode = current.parent
                        return returnValue
                    }

                    current = current.parent!!
                }

                curNode = null
            }

            return returnValue
        }

        override fun hasNext(): Boolean {
            return curNode != null
        }
    }

    override fun iterator(): Iterator<T> {
        return SearchTreeIterator()
    }

    fun find(key: K): T? {
        var curNode: Node? = this.root

        while (curNode != null) {
            when (curNode.key) {
                key -> return curNode.value
                (curNode.key > key) -> curNode = curNode.left
                else -> curNode = curNode.right
            }
        }

        return null
    }

    fun insert(value: T, key: K) {

        if (this.root == null) {
            this.root = Node(value, key)
            return
        }
        var curNode: Node = this.root!!

        while (true) {
            if (curNode.key == key) {
                curNode.value = value
                return
            }

            if (curNode.key > key) {
                if (curNode.left == null) {
                    val tmp = Node(value, key, curNode)
                    curNode.left = tmp
                    return
                }

                curNode = curNode.left!!
            }
            else {
                if (curNode.right == null) {
                    val tmp = Node(value, key, curNode)
                    curNode.right = tmp
                    return
                }

                curNode = curNode.right!!
            }
        }
    }
}