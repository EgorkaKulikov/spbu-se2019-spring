import java.lang.Exception
import java.util.LinkedList

class IterableSearchTree<K: Comparable<K>, V>: Iterable<V> {


    inner class Node(_key: K, _value :V) {
        var key = _key
        var value = _value
        var left: Node? = null
        var right: Node? = null

    }
    private var root : Node? = null

     fun find(key: K): V? {
         var current = root
         while (current != null) {
             when {
                 current.key == key -> return current.value
                 current.key > key -> current = current.left
                 current.key < key -> current = current.right
             }
         }
         return null
     }

    fun insert(key: K, value: V){
        if(root == null){
            root = Node(key , value)
        }
        else{
            var current = root
            while(current != null){
                when{
                    current.key == key -> {
                        current.value = value
                        return
                    }
                    current.key < key -> {
                        if(current.right == null){
                            current.right = Node(key , value)
                            return
                        }
                        current = current.right
                    }
                    current.key > key ->{
                        if(current.left == null){
                            current.left = Node(key , value)
                            return
                        }
                        current = current.left
                    }


                }
            }
        }

    }

    inner class TreeIterator: Iterator<V> {
        private val nodeList = makeNodeList()
        private fun makeNodeList():LinkedList<Node>{
            val nodeQueue = LinkedList<Node>()
            val listOfNodes = LinkedList<Node>()
            if(root != null){
                nodeQueue.add(root!!)
            }
            while(!nodeQueue.isEmpty()){
                val current = nodeQueue.remove()

            listOfNodes.add(current)
                if (current.left != null){
                    nodeQueue.add(current.left!!)
                }
                if (current.right != null){
                    nodeQueue.add(current.right!!)
                }
            }
            return listOfNodes
        }

        override fun next(): V {
            if(!hasNext()){
                throw Exception("There are no more elements")
            }
            return nodeList.remove().value
        }

        override fun hasNext(): Boolean {
           return !nodeList.isEmpty()
        }
    }

    override fun iterator(): TreeIterator = TreeIterator()

}
