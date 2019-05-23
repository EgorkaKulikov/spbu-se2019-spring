import java.util.*

class RBTree<K : Comparable<K>, V> : Tree<K, V>, Iterable<RBNode<K, V>> {

    internal var root : RBNode<K, V>? = null

    override fun insert(key : K, value: V) {
        var parent : RBNode<K, V>? = null
        if (root == null) {
            root = RBNode(key, value)
            return
        }

        var current = root
        while (current != null) {
            parent = current
            when {
                key < current.key -> current = current.left
                key > current.key -> current = current.right
                key == current.key -> {
                    current.value = value
                    return
                }
            }
        }
        when {
            key < parent!!.key -> {
                parent.left = RBNode(key, value, parent)
                balance(parent.left)
                fixRoot(parent)
            }
            key > parent.key -> {
                parent.right = RBNode(key, value, parent)
                balance(parent.right)
                fixRoot(parent)
            }
        }
    }

    override fun find(key: K): Pair<K, V>? {
        val node : RBNode<K, V>? = findNode(key) ?: return null
        return Pair(node!!.key, node.value)
    }

    override fun iterator(): Iterator<RBNode<K, V>> {
        return (object : Iterator<RBNode<K, V>> {
            private var stack = Stack<RBNode<K, V>?>()
            private var current : RBNode<K, V>? = root

            override fun hasNext(): Boolean = (!stack.isEmpty() || current != null)

            override fun next(): RBNode<K, V> {
                while (current != null)
                {
                    stack.push(current)
                    current = current!!.left
                }
                try {
                    current = stack.pop()
                }
                catch(e : Exception){
                    throw kotlin.NoSuchElementException()
                }
                val node = current
                current = current?.right
                return node!!
            }
        })
    }

    private fun findNode(key: K, current : RBNode<K, V>? = root): RBNode<K, V>? = when {
        current == null || key == current.key -> current
        key < current.key -> findNode(key, current.left)
        else -> findNode(key, current.right)
    }

    private fun balance(node : RBNode<K, V>?){
        if (node == null) return
        val grandParent = node.parent?.parent
        val parent = node.parent
        val uncle = node.uncle()

        if (node == root){
            node.isRed = false
            return
        }
        if (parent?.isRed != true) return

        if (uncle?.isRed == true) {
            parent.isRed = false
            uncle.isRed = false
            grandParent?.isRed = true
            balance(grandParent)
        }
        else {
            when (parent){
                grandParent?.left -> when (node) {
                    parent.left -> {  // left-left case
                        grandParent.rotateRight()
                        grandParent.isRed = true
                        parent.isRed = false
                        if (grandParent.parent?.isRed  == true)
                            balance(grandParent)
                    }
                    parent.right -> { // left-right case
                        parent.rotateLeft()
                        grandParent.rotateRight()
                        grandParent.isRed = true
                        node.isRed = false
                        if (grandParent.parent?.isRed  == true)
                            balance(grandParent)
                    }
                }
                grandParent?.right -> when (node) {
                    parent.right -> { // right-right case
                        grandParent.rotateLeft()
                        grandParent.isRed = true
                        parent.isRed = false
                        if (grandParent.parent?.isRed  == true)
                            balance(grandParent)
                    }
                    parent.left -> { // right-left case
                        parent.rotateRight()
                        grandParent.rotateLeft()
                        grandParent.isRed = true
                        node.isRed = false
                        if (grandParent.parent?.isRed  == true)
                            balance(grandParent)
                    }
                }
            }
        }

    }

    private fun fixRoot(node : RBNode<K, V>) {
        if (root?.parent == null) return // to avoid excess iterations
        var cur = node
        while (cur.parent != null)
            cur = cur.parent!!
        root = cur
    }
}


