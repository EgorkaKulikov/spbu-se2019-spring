class RBNode<K : Comparable<K>, V>(
    var key : K,
    var value : V,
    var parent : RBNode<K, V>? = null
){
    internal var left : RBNode<K, V>? = null
    internal var right : RBNode<K, V>? = null
    internal var isRed : Boolean = true

    fun rotateLeft(){
        val curRight = right ?: throw Exception("Couldn't rotateLeft as right child == null")
        right = curRight.left
        curRight.left?.parent = this

        curRight.parent = parent
        when (this) {
            parent?.left -> parent?.left = curRight
            else -> parent?.right = curRight
        }
        curRight.left = this
        parent = curRight
    }

    fun rotateRight(){
        val curLeft = left ?: throw Exception("Couldn't rotateRight as left child == null")
        left = curLeft.right
        curLeft.right?.parent = this

        curLeft.parent = parent
        when (this) {
            parent?.left -> parent?.left = curLeft
            else -> parent?.right = curLeft
        }
        curLeft.right = this
        parent = curLeft
    }

    fun uncle() : RBNode<K, V>? = when (this.parent){
        this.parent?.parent?.left -> this.parent?.parent?.right
        this.parent?.parent?.right -> this.parent?.parent?.left
        else -> null
    }
}