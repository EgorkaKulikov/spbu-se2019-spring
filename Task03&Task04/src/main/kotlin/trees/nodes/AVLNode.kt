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

    internal fun leftRotate() {
        NodeRotator.leftRotate(this)
    }

    internal fun rightRotate() {
        NodeRotator.rightRotate(this)
    }

    internal fun bigLeftRotate() {
        NodeRotator.bigLeftRotate(this)
    }

    internal fun bigRightRotate() {
        NodeRotator.bigRightRotate(this)
    }
}