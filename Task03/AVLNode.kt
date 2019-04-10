import kotlin.math.max
import kotlin.math.abs

class AVLNode<K: Comparable<K>, V>(override var key: K, override var value: V):
    BalancingNode<K, V, AVLNode<K, V>>() {
    override fun createNode(key: K, value: V): AVLNode<K, V> = AVLNode(key, value)
    override fun balancing() {
        updateHeight()
        if (abs(deltaHeight) != 2)
            return
        if (deltaHeight == 2) {
            if (left!!.deltaHeight == -1)
                left!!.rotateRightBig()
            else
                left!!.rotateRight()
        }
        else {
            if (right!!.deltaHeight == 1)
                right!!.rotateLeftBig()
            else
                right!!.rotateLeft()
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
    fun rotateLeftBig() {
        val leftSon = left!!
        leftSon.rotateRight()
        leftSon.rotateLeft()
    }
    override fun rotateRight() {
        super.rotateRight()
        right!!.updateHeight()
        this.updateHeight()
    }
    fun rotateRightBig() {
        val rightSon = right!!
        rightSon.rotateLeft()
        rightSon.rotateRight()
    }
    override fun toString(): String = "($key to $value, height: $height)"
}
