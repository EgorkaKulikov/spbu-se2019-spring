internal class NodeRotator<K : Comparable<K>, V, NodeType : Node<K, V, NodeType>> {
    internal fun leftRotate(node: NodeType): NodeType {
        val newNode = node.right ?: return node
        node.right = newNode.left
        node.right?.parent = node
        newNode.left = node
        newNode.parent = node.parent
        node.parent = newNode
        return newNode
    }

    internal fun rightRotate(node: NodeType): NodeType {
        val newNode = node.left ?: return node
        node.left = newNode.right
        node.left?.parent = node
        newNode.right = node
        newNode.parent = node.parent
        node.parent = newNode
        return newNode
    }
}