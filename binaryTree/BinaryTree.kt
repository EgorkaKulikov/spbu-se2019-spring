package binaryTree

import Tree
import java.util.*


class BinaryTree<K : Comparable<K>, V> : Tree<K, V>, Iterable<BSTNode<K, V>> {
    var root: BSTNode<K, V>? = null

    private fun prefind(key: K, BSTNode: BSTNode<K, V>? = root): BSTNode<K, V>? = when {
        BSTNode == null || BSTNode.key == key -> BSTNode
        key < BSTNode.key -> prefind(key, BSTNode.left)
        else -> prefind(key, BSTNode.right)
    }

    override fun find(key: K): Pair<K, V>? {
        val res = prefind(key) ?: return null
        return Pair(res.key, res.value)
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


    override fun iterator() : Iterator<BSTNode<K, V>> =
        (object : Iterator<BSTNode<K, V>>{
            var cur = root
            val helpDeq: Deque<BSTNode<K,V>> = ArrayDeque()
            val turn: Deque<BSTNode<K,V>> = ArrayDeque()

            override fun next(): BSTNode<K, V> = helpDeq.poll()


            override fun hasNext(): Boolean {
                    if (helpDeq.isEmpty() && cur?.left == null && cur?.right == null) {
                        return !helpDeq.isEmpty()
                    }
                        else if (helpDeq.isEmpty()) {
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
