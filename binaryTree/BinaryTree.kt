package binaryTree

import Tree
import java.util.*


class BinaryTree<K : Comparable<K>, V> : Tree<K, V>, Iterable<V> {
    internal var root: BSTNode<K, V>? = null


    override fun find(key: K): Pair<K, V>? {
        var cur: BSTNode<K, V>? = root ?: return null
        var result: BSTNode<K, V>?

        while (cur != null){
            result = cur
            when {
                key < cur.key -> cur = cur.left
                key > cur.key -> cur = cur.right
                key == cur.key -> return Pair(result.key, result.value)
            }
        }

        return null
    }


    override fun insert(key: K, value: V) {
        var parent: BSTNode<K, V>? = null
        var cur: BSTNode<K, V>? = root

        while (cur != null) {
            parent = cur
            when {
                key < cur.key -> cur = cur.left
                key > cur.key -> cur = cur.right
                key == cur.key -> {
                    cur.value = value
                    return
                }
            }
        }

        if (parent == null) {
            root = BSTNode(key, value)
            return
        }

        if (key < parent.key) {
            parent.left = BSTNode(key, value, parent)
        } else {
            parent.right = BSTNode(key, value, parent)
        }

    }


    override fun iterator() : Iterator<V> =
        (object : Iterator<V>{


            private fun createDeq(): Deque<BSTNode<K, V>>{
                val turn: Deque<BSTNode<K,V>> = ArrayDeque()
                val deque: Deque<BSTNode<K,V>> = ArrayDeque()
                val cur = root ?: return deque

                deque.add(cur)
                turn.add(cur)

                while (!turn.isEmpty()){
                    val res = turn.poll()

                    if (res.left != null) {
                        deque.add(res.left)
                        turn.add(res.left)
                    }

                    if (res.right != null) {
                        deque.add(res.right)
                        turn.add(res.right)
                    }

                }

                return deque
            }

            val iterationList = createDeq()


            override fun hasNext() = !iterationList.isEmpty()


            override fun next(): V = iterationList.poll().value

        })

}
