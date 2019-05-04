package AVLTree

import Tree
import java.lang.Math.abs
import java.util.*

class AVLTree<K : Comparable<K>, V> : Tree<K, V>, Iterable<AVLNode<K, V>> {
    var root: AVLNode<K, V>? = null

    private fun prefind(key: K, AVLNode: AVLNode<K, V>? = root): AVLNode<K, V>? = when {
        AVLNode == null || AVLNode.key == key -> AVLNode
        key < AVLNode.key -> prefind(key, AVLNode.left)
        else -> prefind(key, AVLNode.right)
    }

    override fun find(key: K): Pair<K, V>? {
        val res = prefind(key) ?: return null
        return Pair(res.key, res.value)
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
            treeBalance(parent.left)
        } else {
            parent.right = AVLNode(key, value, parent)
            treeBalance(parent.right)
        }

    }

    private fun treeBalance(node: AVLNode<K, V>?) {
        var cur = node
        while (cur != null) {
            cur.correctHeight()
            cur = cur.parent
        }
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
    }


    override fun iterator(): Iterator<AVLNode<K, V>> =
        (object : Iterator<AVLNode<K, V>> {
            var cur = root
            val helpDeq: Deque<AVLNode<K, V>> = ArrayDeque()
            val turn: Deque<AVLNode<K, V>> = ArrayDeque()

            override fun next(): AVLNode<K, V> = helpDeq.poll()


            override fun hasNext(): Boolean {
                if (helpDeq.isEmpty() && cur?.left == null && cur?.right == null) {
                    return !helpDeq.isEmpty()
                } else if (helpDeq.isEmpty()) {
                    helpDeq.add(cur)
                    turn.add(cur)
                    while (!turn.isEmpty()) {
                        val res = turn.poll()
                        if (res.left != null) {
                            helpDeq.add(res.left)
                            turn.add(res.left)
                        }
                        if (res.right != null) {
                            helpDeq.add(res.right)
                            turn.add(res.right)
                        }
                        cur = res
                    }

                }

                return !helpDeq.isEmpty()


            }


        })
}