package BinarySearchTree

import Tree

class BinarySearchTree<K : Comparable<K>, V> : Tree<K, V>, Iterable<Pair<K, V>> {

    internal var root: Node<K, V>? = null

    override fun find(key: K): Pair<K, V>? {

        val res = findNode(key)

        return if (res != null) Pair(res.key, res.value) else null

    }

    override fun insert(key: K, value: V) {

        var parent: Node<K, V>? = null
        var cur: Node<K, V>? = root

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
            root = Node(key, value)
            return
        }

        if (key < parent.key) {
            parent.left = Node(key, value, parent)
        } else {
            parent.right = Node(key, value, parent)
        }

    }

    override fun delete(key: K) {

        val node = findNode(key) ?: return

        deleteNode(node)

    }

    override fun iterator(): Iterator<Pair<K, V>> {

        return (object : Iterator<Pair<K, V>> {

            var cur: Node<K, V>? = min(root)
            var prev: Node<K, V>? = min(root)
            var last: Node<K, V>? = max(root)

            override fun hasNext(): Boolean = cur != null && cur!!.key <= last!!.key

            override fun next(): Pair<K, V> {

                prev = cur
                cur = next(cur)

                return Pair(prev!!.key, prev!!.value)

            }

        })

    }

    private fun findNode(key: K): Node<K, V>? {

        var cur = root

        while (cur != null ) {
            if (key == cur.key) {
                return cur
            }
            cur = if (key < cur.key) cur.left else cur.right
        }

        return null

    }

    private fun deleteNode(node: Node<K, V>) {

        val cur: Node<K, V> = node
        val parent: Node<K, V>? = cur.parent

        if (cur.left == null && cur.right == null) {
            if (parent == null) {
                root = null
                return
            }
            if (cur == parent.left) {
                parent.left = null
            }
            if (cur == parent.right) {
                parent.right = null
            }
        } else if (cur.left == null || cur.right == null) {
            if (cur.left == null) {
                if (parent == null) {
                    root = cur.right
                    cur.right!!.parent = null
                    return
                }
                if (cur == parent.left) {
                    parent.left = cur.right
                } else {
                    parent.right = cur.right
                }
                cur.right?.parent = parent
            } else {
                if (parent == null) {
                    root = cur.left
                    cur.left!!.parent = null
                    return
                }
                if (cur == parent.left) {
                    parent.left = cur.left
                } else {
                    parent.right = cur.left
                }
                cur.left?.parent = parent
            }
        } else {
            val successor: Node<K, V> = min(cur.right)!!
            cur.key = successor.key
            cur.value = successor.value
            if (successor == successor.parent?.left) {
                successor.parent?.left = successor.right
                if (successor.right != null) {
                    successor.right!!.parent = successor.parent
                }
            } else {
                successor.parent?.right = successor.right
                if (successor.right != null)
                    successor.right!!.parent = successor.parent
            }
        }

    }

    private fun min(cur: Node<K, V>?): Node<K, V>? = if (cur?.left == null) cur else min(cur.left)

    private fun max(cur: Node<K, V>?): Node<K, V>? = if (cur?.right == null) cur else max(cur.right)

    private fun next(cur: Node<K, V>?): Node<K, V>? {

        var next = cur ?: return null

        if (next.right != null) {
            return min(next.right!!)
        } else if (next == next.parent?.right) {
            while (next == next.parent?.right) {
                next = next.parent!!
            }
        }

        return next.parent

    }

}