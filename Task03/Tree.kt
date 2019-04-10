import java.util.Stack

abstract class Tree<K: Comparable<K>, V, NT: Node<K, V, NT>>: Iterable<Node<K, V, NT>> {
    protected var root: NT? = null
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
    open operator fun set(key: K, value: V) {
        if (root == null) {
            root = createRoot(key, value)
            return
        }
        var node = root!!
        while (node.nextNode(key) != null)
            node = node.nextNode(key)!!
        if (node.key == key)
            node.value = value
        else
            node.createSon(key, value, node.findKey(key))
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
            else if (path.last().right != null) {
                var node = path.last().right
                path.push(node!!)
                while (node != null) {
                    path.push(node)
                    node = node.left
                }
                return true
            }
            else {
                var node = path.last()
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
