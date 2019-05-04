package redBlackTree

class RBNode<K : Comparable<K>, V>(
    var key: K,
    var value: V,
    var isBlack: Boolean = false,
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
                && isBlack == other.isBlack

    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + (value?.hashCode() ?: 0)
        result = 31 * result + (parent?.hashCode() ?: 0)
        result = 31 * result + (left?.hashCode() ?: 0)
        result = 31 * result + (right?.hashCode() ?: 0)
        return result
    }




    fun grandparent(): RBNode<K, V>? = this.parent?.parent

    fun uncle(): RBNode<K, V>? = when (this.parent) {

        this.grandparent()?.left -> this.grandparent()!!.right
        else -> this.grandparent()?.left

    }
    private fun swapColors(ABLRBNode: RBNode<K, V>?){
        val tmp = ABLRBNode?.isBlack ?: return
        ABLRBNode.isBlack = this.isBlack
        this.isBlack = tmp
    }

    fun leftRotate(){
        //this == parent
        val rightChild = this.right ?: throw Exception("Null node")
        val parent = this.parent
        this.swapColors(rightChild)

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
        val leftChild = this.left ?: throw Exception("Null node")
        val parent = this.parent
        this.swapColors(leftChild)

        //leftChild?.right = T3
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