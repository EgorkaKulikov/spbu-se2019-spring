import kotlin.math.max

class AVLNode<K: Comparable<K>, V>(override var key: K, override var value: V):
    BalancingNode<K, V, AVLNode<K, V>>() {
    override fun createNode(key: K, value: V): AVLNode<K, V> = AVLNode(key, value)
    override fun balancing() {
        updateHeight()
        when (deltaHeight) {
            -2 -> {
                if (right!!.deltaHeight == 1)
                    right!!.rotateBig()
                else
                    right!!.rotate()
            }
            2 -> {
                if (left!!.deltaHeight == -1)
                    left!!.rotateBig()
                else
                    left!!.rotate()
            }
        }
    }
    var height: Int = 1
    fun updateHeight() {
        height = max(left?.height ?: 0, right?.height ?: 0) + 1
    }
    val deltaHeight: Int
        get() = (left?.height ?: 0) - (right?.height ?: 0)
    override fun rotateLeft() {
        super.rotateLeft()
        left!!.updateHeight()
        this.updateHeight()
    }
    override fun rotateRight() {
        super.rotateRight()
        right!!.updateHeight()
        this.updateHeight()
    }
    override fun toString(): String = "($key to $value, height: $height)"
}
