import java.lang.Math.max

enum class Color {
    Red,
    Black
}

class BinaryNode<K : Comparable<K>, V>(key: K, value: V) : Node<K, V, BinaryNode<K, V>>(key, value) {
    override fun equals(other: Any?): Boolean {
        return (other is BinaryNode<*, *>
                && left == other.left
                && right == other.right
                && parent?.key == other.parent?.key
                && key == other.key
                && value == other.value)
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }

    internal fun addLeftSon(key: K, value: V) {
        val son = BinaryNode(key, value)
        this.left = son
        son.parent = this
    }

    internal fun addRightSon(key: K, value: V) {
        val son = BinaryNode(key, value)
        this.right = son
        son.parent = this
    }
}

class RBNode<K : Comparable<K>, V>(key: K, value: V, col: Color) : Node<K, V, RBNode<K, V>>(key, value) {
    var color = col
        internal set

    internal fun leftRotate(): RBNode<K, V> {
        return NodeRotator.leftRotate(this)

    }

    internal fun rightRotate(): RBNode<K, V> {
        return NodeRotator.rightRotate(this)
    }

    internal fun swapColors(other: RBNode<K, V>) {
        val buf = this.color
        this.color = other.color
        other.color = buf
    }

    override fun equals(other: Any?): Boolean {
        return (other is RBNode<*, *>
                && left == other.left
                && right == other.right
                && parent?.key == other.parent?.key
                && parent?.color == other.parent?.color
                && key == other.key
                && value == other.value
                && color == other.color)
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }

    internal fun addLeftSon(key: K, value: V, color: Color) {
        val son = RBNode(key, value, color)
        this.left = son
        son.parent = this
    }

    internal fun addRightSon(key: K, value: V, color: Color) {
        val son = RBNode(key, value, color)
        this.right = son
        son.parent = this
    }
}

class AVLNode<K : Comparable<K>, V>(key: K, value: V) : Node<K, V, AVLNode<K, V>>(key, value) {
    var height: Int = 1
        private set

    internal fun leftRotate(): AVLNode<K, V> {
        return NodeRotator.leftRotate(this)
    }

    internal fun rightRotate(): AVLNode<K, V> {
        return NodeRotator.rightRotate(this)
    }

    internal fun updateHeight() {
        height = max(left?.height ?: 0, right?.height ?: 0) + 1
    }

    override fun equals(other: Any?): Boolean {
        return (other is AVLNode<*, *>
                && left == other.left
                && right == other.right
                && parent?.key == other.parent?.key
                && key == other.key
                && value == other.value)
    }

    override fun hashCode(): Int {
        return key.hashCode()
    }

    internal fun addLeftSon(key: K, value: V) {
        val son = AVLNode(key, value)
        this.left = son
        son.parent = this
    }

    internal fun addRightSon(key: K, value: V) {
        val son = AVLNode(key, value)
        this.right = son
        son.parent = this
    }
}