package AVLTree

import Tree

class AVLTree<K : Comparable<K>, V> : Tree<K, V>, Iterable<Pair<K, V>> {

    internal var root: Node<K, V>? = null

    override fun find(key: K): Pair<K, V>? {

        val result = findNode(key) ?: return null

        return Pair(result.key, result.value)

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
            root = Node(key, value, parent)
            return
        }

        if (key < parent.key) {
            parent.left = Node(key, value, parent)
            rebalance(parent.left!!)
        } else {
            parent.right = Node(key, value, parent)
            rebalance(parent.right!!)
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

        val key = node.key

        var cur: Node<K, V>? = root
        var parent: Node<K, V>? = root
        var delNode: Node<K, V>? = null
        var child: Node<K, V>? = root

        while (child != null) {
            parent = cur
            cur = child
            child = if (key >= cur.key) cur.right else cur.left
            if (key == cur.key) delNode = cur
        }

        delNode!!.key = cur!!.key
        delNode.value = cur.value

        child = if (cur.left != null) cur.left else cur.right

        if (root!!.key == key) {
            root = child
            child?.parent = null
            return
        } else {
            if (parent!!.left == cur) {
                parent.left = child
                child?.parent = parent
            } else {
                parent.right = child
                child?.parent = parent
            }
            rebalance(parent)
        }

    }

    private fun rebalance(node: Node<K, V>) {

        node.fixHeight()

        if (node.balanceFactor(node) == -2) {
            if (node.height(node.left!!.left) >= node.height(node.left!!.right)) {
                node.rotateRight()
            } else {
                node.rotateLeftThenRight()
            }
        } else if (node.balanceFactor(node) == 2) {
            if (node.height(node.right!!.right) >= node.height(node.right!!.left)) {
                node.rotateLeft()
            } else {
                node.rotateRightThenLeft()
            }
        }
        if (node.parent != null) {
            rebalance(node.parent!!)
        } else {
            root = node
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