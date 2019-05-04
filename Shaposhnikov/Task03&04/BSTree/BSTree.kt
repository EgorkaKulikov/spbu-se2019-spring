import java.util.Stack

class BSTree<K : Comparable<K>, V> : Tree<K, V>, Iterable<BSTNode<K, V>> {

    var root : BSTNode<K, V>? = null

    override fun insert(key : K, value: V) {
        var current = root
        var parent : BSTNode<K, V>? = null
        if (current == null) {
            root = BSTNode(key, value)
                  return
        }

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
            key < parent!!.key -> parent.left = BSTNode(key, value, parent)
            key > parent.key -> parent.right = BSTNode(key, value, parent)
        }
    }

    override fun find(key: K): Pair<K, V>? {
        val node : BSTNode<K, V>? = findNode(key) ?: return null
        return Pair(node!!.key, node.value)
    }

    override fun iterator(): Iterator<BSTNode<K, V>> {
        return (object : Iterator<BSTNode<K, V>> {
            private var stack = Stack<BSTNode<K, V>?>()
            private var current : BSTNode<K, V>? = root

            override fun hasNext(): Boolean = (!stack.isEmpty() || current != null)

            override fun next(): BSTNode<K, V> {
                while (current != null)
                {
                    stack.push(current)
                    current = current!!.left
                }
                current = stack.pop()
                val node = current
                current = current?.right
                return node!!
            }
        })
    }

    private fun findNode(key: K, current : BSTNode<K, V>? = root): BSTNode<K, V>? = when {
        current == null || key == current.key -> current
        key < current.key -> findNode(key, current.left)
        else -> findNode(key, current.right)
        }
}