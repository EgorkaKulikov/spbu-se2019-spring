import kotlin.math.max

class AVLNode<K: Comparable<K>, V>(override val key: K, override var value: V):
    BalanceNode<K, V, AVLNode<K, V>>() {
    protected override fun createNode(key: K, value: V): AVLNode<K, V> = AVLNode(key, value)

    public override fun balancing() {
        updateHeight()
        // перебалансировка происходит тогда и только тогда, когда
        // разница между высотами левого и правого поддерева = 2
        when (deltaHeight) {
            -2 -> {
                if (right!!.deltaHeight == 1)
                    right!!.bigRotate()
                else
                    right!!.rotate()
            }
            2 -> {
                if (left!!.deltaHeight == -1)
                    left!!.bigRotate()
                else
                    left!!.rotate()
            }
        }
    }

    var height: Int = 1
        private set

    public fun updateHeight() {
        height = max(left?.height ?: 0, right?.height ?: 0) + 1
    }

    private val deltaHeight: Int
        get() = (left?.height ?: 0) - (right?.height ?: 0)

    public override fun leftRotate() {
        super.leftRotate()
        left!!.updateHeight()
        this.updateHeight()
    }

    public override fun rightRotate() {
        super.rightRotate()
        right!!.updateHeight()
        this.updateHeight()
    }

    public override fun copy() = AVLNode(key, value)

    override fun equals(other: Any?): Boolean =
        (other is AVLNode<*, *> &&
                other.left == left &&
                other.right == right &&
                other.key == key &&
                other.value == value)
}
