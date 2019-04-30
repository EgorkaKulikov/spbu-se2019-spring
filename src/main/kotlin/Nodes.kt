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




class BinaryNode<K: Comparable<K>, V>(key: K, value: V) :
        Node<K, V, BinaryNode<K, V>>(key, value) {

    override fun equals(other: Any?): Boolean {
        return (other is BinaryNode<*, *>
                && left == other.left
                && right == other.right
                && parent?.key == other.parent?.key
                && key == other.key
                && value == other.value)
    }

    internal fun addLeftSon(key: K, value: V) {
        val newSon = BinaryNode(key, value)
        this.left = newSon
        newSon.parent = this
    }

    internal fun addRightSon(key: K, value: V) {
        val newSon = BinaryNode(key, value)
        this.right = newSon
        newSon.parent = this
    }
}




enum class Color {
    Red, Black
}

class RedBlackNode<K: Comparable<K>, V>(key: K, value: V, var color: Color) :
        Node<K, V, RedBlackNode<K, V>>(key, value) {

    override fun equals(other: Any?): Boolean {
        return (other is RedBlackNode<*, *>
                && left == other.left
                && right == other.right
                && parent?.key == other.parent?.key
                && parent?.color == other.parent?.color
                && key == other.key
                && value == other.value
                && color == other.color)
    }

    internal fun addLeftSon(key: K, value: V, color: Color) {
        val newSon = RedBlackNode(key, value, color)
        this.left = newSon
        newSon.parent = this
    }

    internal fun addRightSon(key: K, value: V, color: Color) {
        val newSon = RedBlackNode(key, value, color)
        this.right = newSon
        newSon.parent = this
    }

    // Using Strategy pattern
    private val balancer = NodeBalancer<K, V, RedBlackNode<K, V>>()

    internal fun leftRotate() {
        balancer.leftRotate(this)
    }

    internal fun rightRotate() {
        balancer.rightRotate(this)
    }
}




class AVLNode<K: Comparable<K>, V>(key: K, value: V) :
        Node<K, V, AVLNode<K, V>>(key, value) {

    var height: Int = 1
        private set

    var deltaHeight: Int = 0
        private set
        get() = (left?.height ?: 0) - (right?.height ?: 0)

    internal fun updateHeight() {
        height = kotlin.math.max((left?.height ?: 0), (right?.height ?: 0)) + 1
    }

    internal fun addLeftSon(key: K, value: V) {
        val newSon = AVLNode(key, value)
        this.left = newSon
        newSon.parent = this
    }

    internal fun addRightSon(key: K, value: V) {
        val newSon = AVLNode(key, value)
        this.right = newSon
        newSon.parent = this
    }

    override fun equals(other: Any?): Boolean {
        return (other is AVLNode<*, *>
                && left == other.left
                && right == other.right
                && parent?.key == other.parent?.key
                && key == other.key
                && value == other.value)
    }

    // Using Strategy pattern
    private val balancer = NodeBalancer<K, V, AVLNode<K, V>>()

    internal fun leftRotate() {
        balancer.leftRotate(this)
    }

    internal fun rightRotate() {
        balancer.rightRotate(this)
    }

    internal fun bigLeftRotate() {
        balancer.bigLeftRotate(this)
    }

    internal fun bigRightRotate() {
        balancer.bigRightRotate(this)
    }
}