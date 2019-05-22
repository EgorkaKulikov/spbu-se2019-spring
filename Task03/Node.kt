abstract class Node<K: Comparable<K>, V, NodeType: Node<K, V, NodeType>>(internal var key: K, internal var value: V) {
    var left: NodeType? = null
        internal set
    var right: NodeType? = null
        internal set
    var parent: NodeType? = null
        internal set

    fun getKey() : K {
        return key
    }

    fun getValue() : V {
        return value
    }

    val sibling: NodeType?
        get() = if (this.parent?.left == this)
            this.parent?.right
        else
            this.parent?.left
    val uncle: NodeType?
        get() = this.parent?.sibling
    val grandparent: NodeType?
        get() = this.parent?.parent

    open fun getInfo() : String {
        return "{$key -> $value}"
    }
}

internal class NodeBalancer<K: Comparable<K>, V, NodeType: Node<K, V, NodeType>> {
    fun rightRotate(node: NodeType) {
        val newNode = node.left
        if (newNode == null)
            return

        node.left = newNode.right
        newNode.right = node

        newNode.parent = node.parent
        node.parent = newNode
        node.left?.parent = node

        val parent = newNode.parent
        if (parent != null) {
            if (parent.left == node)
                parent.left = newNode
            else
                parent.right = newNode
        }
    }

    fun leftRotate(node: NodeType) {
        val newNode = node.right
        if (newNode == null)
            return

        node.right = newNode.left
        newNode.left = node

        newNode.parent = node.parent
        node.parent = newNode
        node.right?.parent = node

        val parent = newNode.parent
        if (parent != null) {
            if (parent.left == node)
                parent.left = newNode
            else
                parent.right = newNode
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


class BinaryNode<K: Comparable<K>, V>(key: K, value: V) :
        Node<K, V, BinaryNode<K, V>>(key, value) {
    internal fun addRightSon(key: K, value: V) {
        val newNode = BinaryNode(key, value)
        this.right = newNode
        newNode.parent = this
    }

    internal fun addLeftSon(key: K, value: V) {
        val newNode = BinaryNode(key, value)
        this.left = newNode
        newNode.parent = this
    }

    override fun equals(other: Any?): Boolean {
        return (other is BinaryNode<*, *>
                && parent?.key == other.parent?.key
                && key == other.key
                && value == other.value
                && left == other.left
                && right == other.right)
    }

    override fun hashCode(): Int {
        return key.hashCode() + value.hashCode()
    }
}


enum class Color {
    Red, Black
}


class RedBlackNode<K: Comparable<K>, V>(key: K, value: V, internal var color: Color) :
        Node<K, V, RedBlackNode<K, V>>(key, value) {
    private val balancer = NodeBalancer<K, V, RedBlackNode<K, V>>()
    internal fun leftRotate() {
        balancer.leftRotate(this)
    }
    internal fun rightRotate() {
        balancer.rightRotate(this)
    }

    override fun getInfo(): String {
        return super.getInfo() + ": ${if (color == Color.Black) "black" else "Red"}"
    }

    internal fun addRightSon(key: K, value: V, color: Color) {
        val newNode = RedBlackNode(key, value, color)
        this.right = newNode
        newNode.parent = this
    }

    internal fun addLeftSon(key: K, value: V, color: Color) {
        val newNode = RedBlackNode(key, value, color)
        this.left = newNode
        newNode.parent = this
    }

    override fun equals(other: Any?): Boolean {
        return (other is RedBlackNode<*, *>
                && parent?.key == other.parent?.key
                && parent?.color == other.parent?.color
                && key == other.key
                && value == other.value
                && color == other.color
                && left == other.left
                && right == other.right)
    }

    override fun hashCode(): Int {
        return key.hashCode() + value.hashCode()
    }
}


class AVLNode<K: Comparable<K>, V>(key: K, value: V) :
        Node<K, V, AVLNode<K, V>>(key, value) {
    private var height: Int = 1
    var deltaHeight: Int = 0
        private set
        get() = (left?.height ?: 0) - (right?.height ?: 0)

    internal fun updateHeight() {
        height = maxOf((right?.height ?: 0), (left?.height ?: 0)) + 1
    }

    private val balancer = NodeBalancer<K, V, AVLNode<K, V>>()
    internal fun leftRotate() {
        balancer.leftRotate(this)
        updateHeight()
        parent?.updateHeight()
    }
    internal fun rightRotate() {
        balancer.rightRotate(this)
        updateHeight()
        parent?.updateHeight()
    }
    internal fun bigLeftRotate() {
        balancer.bigLeftRotate(this)
        updateHeight()
        parent?.updateHeight()
        sibling?.updateHeight()
    }
    internal fun bigRightRotate() {
        balancer.bigRightRotate(this)
        updateHeight()
        parent?.updateHeight()
        sibling?.updateHeight()
    }

    override fun getInfo(): String {
        return super.getInfo() + " at $height with delta $deltaHeight"
    }

    internal fun addRightSon(key: K, value: V) {
        val newNode = AVLNode(key, value)
        this.right = newNode
        newNode.parent = this
    }

    internal fun addLeftSon(key: K, value: V) {
        val newNode = AVLNode(key, value)
        this.left = newNode
        newNode.parent = this
    }

    override fun equals(other: Any?): Boolean {
        return (other is AVLNode<*, *>
                && parent?.key == other.parent?.key
                && key == other.key
                && value == other.value
                && left == other.left
                && right == other.right)
    }

    override fun hashCode(): Int {
        return key.hashCode() + value.hashCode()
    }
}