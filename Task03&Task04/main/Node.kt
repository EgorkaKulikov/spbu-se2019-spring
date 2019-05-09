abstract class Node<K : Comparable<K>, V, NodeType : Node<K, V, NodeType>>(val key: K, var value: V) {
    var left: NodeType? = null
    var right: NodeType? = null
    var parent: NodeType? = null
    val grandparent: NodeType?
        get() = this.parent?.parent
    val uncle: NodeType?
        get() = if (grandparent?.left == parent) grandparent?.right else grandparent?.left

    protected fun isLeaf(): Boolean {
        return (this.left == null && this.right == null)
    }
}