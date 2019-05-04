class IterableSearchTree<T, K: Comparable<K>>: Iterable<T> {
    inner class Node(var key : K, var value : T,  var parent : Node?) {
        var left : Node? = null
        var right : Node? = null
    }

    private var root : Node? = null

    private fun createNode(key: K, value : T, parent: Node?) : Node {
        return Node(value, key, parent)
    }

    fun find(key: K): T? {
        var currNode = this.root

        while(currNode != null) {
            when {
                currNode.key == key -> return currNode.value
                currNode.key > key -> currNode = currNode.left
                currNode.key < key -> currNode = currNode.right
            }
        }

        return null
    }

    fun insert(value : T, key: K): Node{
        var currNode = this.root

        if (currNode == null) {
            currNode = createNode(key, value, null)
            return currNode
        }

        while (currNode!!.key != key) {
            when {
                currNode.key > key -> {
                    if (currNode.left == null) {
                        val insertedNode = createNode(key, value, currNode)
                        currNode.left = insertedNode
                        return insertedNode
                    }
                    else {
                        currNode = currNode.left
                    }
                }
                currNode.key < key -> {
                    if (currNode.right == null) {
                        val insertedNode = createNode(key, value, currNode)
                        currNode.right = insertedNode
                        return insertedNode
                    }
                    else {
                        currNode = currNode.right
                    }
                }
            }
        }

        currNode.value = value
        return currNode

    }
    private fun findMinNode(startNode: Node?): Node?{
        var currLeftNode = startNode
        while (currLeftNode?.left != null){
            currLeftNode = currLeftNode.left
        }
        return currLeftNode
    }

    // Infix-traverse
    inner class SearchTreeIterator : Iterator<T>{

        // We find first needed node - node with min key
        private var currNode = findMinNode(root)

        // So we will find next node and return previous node's value
        override fun next(): T {

            var nextNode = currNode
            // We sure about safe currNode because it checks in hasNext()
            // Even if we have empty tree findMinNode(root) will return
            // null to the first node and it will be checked in hasNext()
            val value = currNode!!.value

            if (nextNode?.right != null) {
                nextNode = findMinNode(nextNode.right)
            } else {
                while(nextNode?.parent != null){
                    if (nextNode.parent?.right == nextNode){
                        nextNode = nextNode.parent
                    } else {
                        break
                    }
                }
                if (nextNode?.parent != null){
                    nextNode = nextNode.parent
                } else {
                    nextNode = null
                }
            }

            currNode = nextNode
            return value
        }

        override fun hasNext(): Boolean {
            return currNode != null
        }
    }
    
    override fun iterator(): Iterator<T>{
        return SearchTreeIterator()
    }
}