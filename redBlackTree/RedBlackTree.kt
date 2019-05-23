package redBlackTree

import Tree
import java.util.*


class RedBlackTree<K : Comparable<K>, V> : Tree<K, V>, Iterable<RBNode<K, V>> {
    var root: RBNode<K, V>? = null


    override fun find(key: K): Pair<K, V>? {
        var cur: RBNode<K, V>? = root ?: return null
        var result: RBNode<K, V>?

        while (cur != null) {
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
            root?.color = Color.Black
            return
        }

        if (key < parent.key) {
            parent.left = RBNode(key, value, Color.Red, parent)
            balanceTree(parent.left)
        } else {
            parent.right = RBNode(key, value, Color.Red, parent)
            balanceTree(parent.right)
        }

    }


    private fun balanceTree(RBNode: RBNode<K, V>?) {
        var cur: RBNode<K, V> = RBNode ?: throw Exception("Incorrect RBNode's construction")
        var parent: RBNode<K, V>? = cur.parent

        while (parent?.color == Color.Red) {
            //uncle is red
            if (cur.uncle()?.color == Color.Red) {
                cur.uncle()?.color = Color.Black
                parent.color = Color.Black
                cur.grandparent()?.color = Color.Red
                cur = cur.grandparent() ?: throw Exception("Insert error")
                parent = cur.parent
            }
            //uncle is black or leaf
            else when {
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

        root?.color = Color.Black

    }


    override fun iterator() : Iterator<RBNode<K, V>> =
        (object : Iterator<RBNode<K, V>>{


            private fun createDeq(): Deque<RBNode<K, V>>{
                val turn: Deque<RBNode<K,V>> = ArrayDeque()
                val deque: Deque<RBNode<K,V>> = ArrayDeque()
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


            override fun next(): RBNode<K, V> = iterationList.poll()

        })

}
