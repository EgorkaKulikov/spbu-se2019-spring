enum class Color {
    Black,
    Red,
}

class RedBlackNode<K: Comparable<K>, V>(override val key: K, override var value: V, var color: Color):
    NodeWithBalancing<K, V, RedBlackNode<K, V>>() {
    protected override fun createNode(key: K, value: V): RedBlackNode<K, V> = RedBlackNode(key, value, Color.Red)
    public override fun balancing() {
        if (color == Color.Black || parent!!.color == Color.Black)
            return
        val father = parent!!
        val grandfather = father.parent!!
        val uncle = father.brother
        if (uncle == null || uncle.color == Color.Black) {
            if (father.type == this.type)
                father.rotate()
            else
                father.bigRotate()
        }
        else {
            father.color = Color.Black
            uncle.color = Color.Black
            if (grandfather.type != TypeSon.Root)
                grandfather.color = Color.Red
        }
    }
    protected override fun rotateLeft() {
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
    protected override fun rotateRight() {
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
    public override fun copy() = RedBlackNode(key, value, color)
    override fun equals(other: Any?): Boolean =
        (other is RedBlackNode<*, *> &&
                other.left == left &&
                other.right == right &&
                other.key == key &&
                other.value == value &&
                other.color == color)
}
