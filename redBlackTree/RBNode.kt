package redBlackTree

enum class Color{
    Red, Black
}

class RBNode<K : Comparable<K>, V>(
    val key: K,
    var value: V,
    var color: Color = Color.Red,
    var parent: RBNode<K, V>? = null
) {
    var left: RBNode<K, V>? = null
    var right: RBNode<K, V>? = null


    override fun equals(other: Any?): Boolean =
        other is RBNode<*, *>
                && key == other.key
                && value == other.value
                && left == other.left
                && right == other.right
                && color == other.color

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + (value?.hashCode() ?: 0)
        result = 31 * result + color.hashCode()
        result = 31 * result + (left?.hashCode() ?: 0)
        result = 31 * result + (right?.hashCode() ?: 0)
        return result
    }


    fun grandparent(): RBNode<K, V>? = parent?.parent


    fun uncle(): RBNode<K, V>? = when (parent) {

        this.grandparent()?.left -> this.grandparent()!!.right
        else -> this.grandparent()?.left

    }


    private fun swapColors(cur: RBNode<K, V>?){
        val tmp = cur?.color ?: return
        cur.color = this.color
        color = tmp
    }


    fun leftRotate(){
        val rightChild = this.right ?: throw Exception("Right child for node does no exist")
        val parent = parent
        swapColors(rightChild)

        rightChild.left?.parent = this
        this.right = rightChild.left
        rightChild.left = this

        when (this) {
            parent?.right -> parent.right = rightChild
            parent?.left -> parent.left = rightChild
        }

        this.parent = rightChild
        rightChild.parent = parent
    }


    fun rightRotate(){
        val leftChild = this.left ?: throw Exception("Left child for node does no exist")
        val parent = parent
        swapColors(leftChild)

        leftChild.right?.parent = this
        this.left = leftChild.right
        leftChild.right = this

        when (this) {
            parent?.left -> parent.left = leftChild
            parent?.right -> parent.right = leftChild
        }

        this.parent = leftChild
        leftChild.parent = parent
    }

}
