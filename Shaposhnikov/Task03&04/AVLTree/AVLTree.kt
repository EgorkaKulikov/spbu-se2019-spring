import java.util.*
import kotlin.math.abs

class AVLTree<K : Comparable<K>, V> : Tree<K, V>, Iterable<AVLNode<K, V>> {

    var root : AVLNode<K, V>? = null

    override fun insert(key : K, value: V) {
        var current = root
        var parent : AVLNode<K, V>? = null
        if (current == null) {
            root = AVLNode(key, value)
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
            key < parent!!.key -> {
                                    parent.left = AVLNode(key, value, parent)
                                    fixHeights(parent.left)
                                    balance(parent.left)
                                    fixRoot(parent)
                                  }
            key > parent.key -> {
                                    parent.right = AVLNode(key, value, parent)
                                    fixHeights(parent.right)
                                    balance(parent.right)
                                    fixRoot(parent)
                                }
        }

    }

    override fun find(key: K): Pair<K, V>? {
        val node : AVLNode<K, V>? = findNode(key) ?: return null
        return Pair(node!!.key, node.value)
    }

    override fun iterator(): Iterator<AVLNode<K, V>> {
        return (object : Iterator<AVLNode<K, V>> {
            private var stack = Stack<AVLNode<K, V>?>()
            private var current : AVLNode<K, V>? = root

            override fun hasNext(): Boolean = (!stack.isEmpty() || current != null)

            override fun next(): AVLNode<K, V> {
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

    private fun findNode(key: K, current : AVLNode<K, V>? = root): AVLNode<K, V>? = when {
        current == null || key == current.key -> current
        key < current.key -> findNode(key, current.left)
        else -> findNode(key, current.right)
    }

    private fun balance(node : AVLNode<K, V>?){
        var unbalanced = node ?: return
        while (abs(unbalanced.heightDif()) < 2){
            unbalanced = unbalanced.parent ?: return
        }
        when (unbalanced.heightDif()){
            2 -> when (unbalanced.left!!.heightDif()){
                                                1 -> unbalanced.rotateRight() //left-left case
                                                -1 -> { //left-right case
                                                    unbalanced.left!!.rotateLeft()
                                                    unbalanced.rotateRight()
                                                }
                                            }
            -2 -> when (unbalanced.right!!.heightDif()){
                                                1 -> { //right-left case
                                                    unbalanced.right!!.rotateRight()
                                                    unbalanced.rotateLeft()
                                                }
                                                -1 -> unbalanced.rotateLeft() //right-right case
                                            }
        }
        fixHeights(unbalanced)
    }

    private fun fixHeights(node : AVLNode<K, V>?){
        var current = node
        while (current != null) {
            current.height = Integer.max(current.left?.height ?: 0, current.right?.height ?: 0) + 1
            current = current.parent
        }
    }

    private fun fixRoot(node : AVLNode<K, V>) {
        if (root?.parent == null) return // to avoid excess iterations
        var cur = node
        while (cur.parent != null)
            cur = cur.parent!!
        root = cur
    }
}




