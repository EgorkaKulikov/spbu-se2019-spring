package redBlackTree

import Tree
import java.util.*


class RedBlackTree<K : Comparable<K>, V> : Tree<K, V>, Iterable<RBNode<K, V>> {
    var root: RBNode<K, V>? = null

    private fun prefind(key: K, RBNode: RBNode<K, V>? = root): RBNode<K, V>? = when {
        RBNode == null || RBNode.key == key -> RBNode
        key < RBNode.key -> prefind(key, RBNode.left)
        else -> prefind(key, RBNode.right)
    }

    override fun find(key: K): Pair<K, V>? {
        val res = prefind(key) ?: return null
        return Pair(res.key, res.value)
    }

    override fun insert(key: K, value: V) {

        var parent: RBNode<K, V>? = null
        var cur: RBNode<K, V>? = root

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
            root = RBNode(key, value)
            root?.isBlack = true
            return
        }

        if (key < parent.key) {
            parent.left = RBNode(key, value, false, parent)
            treeBalance(parent.left ?: throw Exception("Incorrect BSTNode's construction"))
        } else {
            parent.right = RBNode(key, value, false, parent)
            treeBalance(parent.right ?: throw Exception("Incorrect BSTNode's construction"))
        }

    }

    private fun treeBalance(RBNode: RBNode<K, V>) {
        var cur: RBNode<K, V> = RBNode
        var parent: RBNode<K, V>? = cur.parent

        while (parent?.isBlack == false) {
            //uncle is red
            if (cur.uncle()?.isBlack == false) {
                cur.uncle()?.isBlack = true
                parent.isBlack = true
                cur.grandparent()?.isBlack = false
                cur = cur.grandparent() ?: throw Exception("Insert error")
                parent = cur.parent
            }
            //uncle is black or leaf
            else when{
                //Left Left Case
                cur.grandparent()?.left == parent && parent.left == cur -> {
                    parent = parent.parent
                    parent?.rightRotate()
                    parent = parent?.parent
                }
                //Left Right Case
                cur.grandparent()?.left == parent && parent.right == cur -> {
                    parent.leftRotate()
                    parent = cur.parent
                    parent?.rightRotate()
                    parent = cur.parent
                }
                //Right Right Case
                cur.grandparent()?.right == parent && parent.right == cur -> {
                    parent = parent.parent
                    parent?.leftRotate()
                    parent = parent?.parent
                }
                //Right Left Case
                else -> {
                    parent.rightRotate()
                    parent = cur.parent
                    parent?.leftRotate()
                    parent = cur.parent
                }
            }


        }

        while (root?.parent != null) {
            root = root?.parent
        }
        root?.isBlack = true
    }


    override fun iterator() : Iterator<RBNode<K, V>> =
        (object : Iterator<RBNode<K, V>>{
            var cur = root
            val helpDeq: Deque<RBNode<K, V>> = ArrayDeque()
            val turn: Deque<RBNode<K, V>> = ArrayDeque()

            override fun next(): RBNode<K, V> = helpDeq.poll()


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
