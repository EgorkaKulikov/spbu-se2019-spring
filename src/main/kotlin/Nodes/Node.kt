abstract class Node<K: Comparable<K>, V, NodeType: Node<K, V, NodeType>>(var key: K, var value: V) {
    var left: NodeType? = null
    var right: NodeType? = null
    var parent: NodeType? = null

    val grandparent: NodeType?
        get() = this.parent?.parent
    val sibling: NodeType?
        get() = if (this.parent?.left == this) this.parent?.right else this.parent?.left
    val uncle: NodeType?
        get() = this.parent?.sibling

}