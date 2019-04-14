import java.lang.Exception
import java.util.Stack

enum class InfoAboutNode {
    NodeCreate, NodeUpdate, NodeDelete, NodeNotFounded
}

abstract class Tree<K: Comparable<K>, V, NT: Node<K, V, NT>>: Iterable<Node<K, V, NT>> {
    var root: NT? = null
        protected set(value) {
            field = value
        }
    protected abstract fun createRoot(key: K, value: V): NT
    operator fun get(key: K): V? {
        if (root == null)
            return null
        var node = root!!
        while (node.nextNode(key) != null)
            node = node.nextNode(key)!!
        if (node.key == key)
            return node.value
        return null
    }
    protected open fun setWithInfo(key: K, value: V?): InfoAboutNode {
        if (root == null) {
            if (value == null)
                return InfoAboutNode.NodeNotFounded
            root = createRoot(key, value)
            return InfoAboutNode.NodeCreate
        }
        var node = root!!
        while (node.nextNode(key) != null)
            node = node.nextNode(key)!!
        if (value != null) {
            if (node.key == key) {
                node.value = value
                return InfoAboutNode.NodeUpdate
            }
            node.createSon(key, value, node.findKey(key))
            return InfoAboutNode.NodeCreate
        }
        if (node.key != key)
            return InfoAboutNode.NodeNotFounded
        if (node.left == null && node.right == null) {
            when (node.type) {
                TypeSon.LeftSon -> node.parent!!.left = null
                TypeSon.RightSon -> node.parent!!.right = null
                TypeSon.Root -> root = null
            }
            return InfoAboutNode.NodeDelete
        }
        if (node.right == null) {
            if (node.type == TypeSon.Root)
                root = node.left!!
            node.left!!.setFather(node.parent, node.type)
            return InfoAboutNode.NodeDelete
        }
        var nodeWithNextKey = node.right!!
        while (nodeWithNextKey.left != null)
            nodeWithNextKey = nodeWithNextKey.left!!
        node.key = nodeWithNextKey.key
        node.value = nodeWithNextKey.value
        nodeWithNextKey.parent!!.setSon(nodeWithNextKey.right, nodeWithNextKey.type)
        return InfoAboutNode.NodeDelete
    }
    operator fun set(key: K, value: V?) {
        setWithInfo(key, value)
    }
    fun insert(size: Int, init: (Int) -> Pair<K, V>) {
        for (i in 0 until size) {
            when (setWithInfo(init(i).first, init(i).second)) {
                InfoAboutNode.NodeUpdate -> throw Exception("Function insert don't update nodes in tree")
                InfoAboutNode.NodeDelete -> throw Exception("Function insert don't delete nodes in tree")
                else -> {}
            }
        }
    }
    fun insert(vararg elements: Pair<K, V>) {
        insert(elements.size) {elements[it]}
    }
    fun erase(size: Int, init: (Int) -> K) {
        for (i in 0 until size) {
            if (setWithInfo(init(i), null) == InfoAboutNode.NodeNotFounded)
                throw Exception("Function erase can't delete absent node")
        }
    }
    fun erase(vararg elements: K) {
        erase(elements.size) {elements[it]}
    }
    override fun iterator(): Iterator<NT> = (object: Iterator<NT> {
        val path = Stack<NT>()
        override fun next(): NT = path.peek()
        override fun hasNext(): Boolean {
            if (path.empty()) {
                var node = root
                while (node != null) {
                    path.push(node)
                    node = node.left
                }
                return ! path.empty()
            }
            else if (path.peek().right != null) {
                var node = path.peek().right
                while (node != null) {
                    path.push(node)
                    node = node.left
                }
                return true
            }
            else {
                var node = path.peek()
                while (node.type == TypeSon.RightSon) {
                    node = node.parent!!
                    path.pop()
                }
                if (node.type == TypeSon.Root)
                    return false
                path.pop()
                return true
            }
        }
    })
}
