abstract class Node<K : Comparable<K>, V, NodeType : Node<K, V, NodeType>>(val key: K, var value: V) {
    var left: NodeType? = null
        internal set
    var right: NodeType? = null
        internal set
    var parent: NodeType? = null
        internal set
    val grandparent: NodeType?
        get() = this.parent?.parent
    val uncle: NodeType?
        get() = if (grandparent?.left == parent) grandparent?.right else grandparent?.left
}