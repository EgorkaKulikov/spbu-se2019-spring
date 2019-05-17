class IterableSearchTree<T, K: Comparable<K>>: Iterable<T> {
    open inner class Node(val key: K, var value: T, open var parent: Node?) {
        open var left: Node? = null
        open var right: Node? = null
    }

    private var root: Node? = null

    fun find(key: K): T? {
        var curNode: Node? = this.root
        while (curNode != null) {
            if(curNode.key == key) return curNode.value
            else if(curNode.key > key) curNode = curNode.left
            else curNode = curNode.right
        }
        return null
    }

    private fun createNode(value: T, key: K, parent: Node?): Node {
        return Node(key, value, parent)
    }


    fun insert(key: K, value: T) {
        if (this.root == null) {
            this.root = createNode(value, key, null)
            return
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
                return
            }
        }
        return
    }

    inner class SearchTreeIterator: Iterator<T> {
        private fun findLeast(curNode: Node?): Node?{
            if (curNode?.left != null){
                return findLeast(curNode.left)
            }
            else{
                return curNode
            }
        }

        private var curNode = findLeast(root)

        override fun hasNext(): Boolean{
            return curNode != null
        }

        override fun next(): T{
            if(!hasNext()){
                throw IndexOutOfBoundsException()
            }
            var cur = curNode!!
            val curValue = cur.value

            if(cur.right != null) {
                cur = findLeast(cur.right)!!
            }
            else{
                while (cur.parent != null) {
                    if (cur.parent!!.left == cur) {
                        curNode = cur.parent
                        return curValue
                    }

                    cur = cur.parent!!
                }

                curNode = null
            }
            return curValue
        }
    }

    override fun iterator(): Iterator<T> {
        return SearchTreeIterator()
    }
}