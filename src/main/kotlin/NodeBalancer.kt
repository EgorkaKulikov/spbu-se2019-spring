internal class NodeBalancer<K: Comparable<K>, V, NodeType: Node<K, V, NodeType>> {

    fun leftRotate(node: NodeType) {
        val newNode = node.right
        val ancestor = node.parent
        if (newNode == null)
            return

        // Rotation
        node.right = newNode.left
        newNode.left = node

        // Redefining parents
        node.parent = newNode
        node.right?.parent = node
        newNode.parent = ancestor
        if (ancestor != null) {
            if (node == ancestor.left)
                ancestor.left = newNode
            else
                ancestor.right = newNode
        }
    }

    fun rightRotate(node: NodeType) {
        val newNode = node.left
        val ancestor = node.parent
        if (newNode == null)
            return

        // Rotation
        node.left = newNode.right
        newNode.right = node

        // Redefining parents
        node.parent = newNode
        node.left?.parent = node
        newNode.parent = ancestor
        if (ancestor != null) {
            if (node == ancestor.left)
                ancestor.left = newNode
            else
                ancestor.right = newNode
        }
    }

    fun bigLeftRotate(node: NodeType) {
        if (node.right == null)
            return
        rightRotate(node.right!!)
        leftRotate(node)
    }

    fun bigRightRotate(node: NodeType) {
        if (node.left == null)
            return
        leftRotate(node.left!!)
        rightRotate(node)
    }

}