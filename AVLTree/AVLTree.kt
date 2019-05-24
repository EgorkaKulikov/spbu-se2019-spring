package AVLTree

import Tree
import java.lang.Math.abs
import java.util.*

class AVLTree<K : Comparable<K>, V> : Tree<K, V>, Iterable<V> {
    internal var root: AVLNode<K, V>? = null

    override fun find(key: K): Pair<K, V>? {
        var cur: AVLNode<K, V>? = root ?: return null
        var result: AVLNode<K, V>?

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

        var parent: AVLNode<K, V>? = null
        var cur: AVLNode<K, V>? = root

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
            root = AVLNode(key, value)
            return
        }

        if (key < parent.key) {
            parent.left = AVLNode(key, value, parent)
            balanceTree(parent.left)
        } else {
            parent.right = AVLNode(key, value, parent)
            balanceTree(parent.right)
        }

    }

    private fun balanceTree(node: AVLNode<K, V>?) {
        correctHeight(node)
        var noNullCur = node ?: return
        while (abs(noNullCur.getBalance()) < 2) {
            noNullCur = noNullCur.parent ?: return
        }
        when (noNullCur.getBalance()) {
            2 -> when (noNullCur.left!!.getBalance()) {
                //Left Left Case
                1 -> noNullCur.rightRotate()
                //Left Right Case
                -1 -> {
                    noNullCur.left!!.leftRotate()
                    noNullCur.rightRotate()
                }
            }
            -2 -> when (noNullCur.right!!.getBalance()) {
                //Right Right Case
                -1 -> noNullCur.leftRotate()
                //Right Left case
                1 -> {
                    noNullCur.right!!.rightRotate()
                    noNullCur.leftRotate()
                }
            }
        }
        while (root?.parent != null) {
            root = root?.parent
        }
    correctHeight(node)
    }

    private fun correctHeight(node : AVLNode<K, V>?){
        var cur = node
        while (cur != null) {
            cur.correctHeight()
            cur = cur.parent
        }
    }


    override fun iterator() : Iterator<V> =
        (object : Iterator<V>{


            private fun createDeq(): Deque<AVLNode<K, V>>{
                val turn: Deque<AVLNode<K,V>> = ArrayDeque()
                val deque: Deque<AVLNode<K,V>> = ArrayDeque()
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