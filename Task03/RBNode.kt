enum class Color {
    Black, Red
}

class RBNode<K: Comparable<K>, V>(override var key: K, override var value: V, var color: Color):
    BalancingNode<K, V, RBNode<K, V>>() {
    override fun createNode(key: K, value: V): RBNode<K, V> = RBNode(key, value, Color.Red)
    override fun balancing() {
        if (color == Color.Black || parent!!.color == Color.Black)
            return
        val father = parent!!
        val grandfather = father.parent!!
        val uncle = father.brother
        if (uncle == null || uncle.color == Color.Black) {
            if (father.type == this.type)
                father.rotate()
            else
                father.rotateBig()
        }
        else {
            father.color = Color.Black
            uncle.color = Color.Black
            if (grandfather.type != TypeSon.Root)
                grandfather.color = Color.Red
        }
    }
    override fun rotateLeft() {
        super.rotateLeft()
        if (left!!.color == Color.Red) {
            left!!.color = this.color
            this.color = Color.Red
        }
        else {
            left!!.color = this.color
            this.color = Color.Black
        }
    }
    override fun rotateRight() {
        super.rotateRight()
        if (right!!.color == Color.Red) {
            right!!.color = this.color
            this.color = Color.Red
        }
        else {
            right!!.color = this.color
            this.color = Color.Black
        }
    }
    override fun toString(): String = "($key to $value, color: $color)"
}
