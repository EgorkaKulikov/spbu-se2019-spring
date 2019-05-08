class RBNode<K : Comparable<K>, V>(
    var key : K,
    var value : V,
    var parent : RBNode<K, V>? = null,
    var isRed : Boolean = true
){
    var left : RBNode<K, V>? = null
    var right : RBNode<K, V>? = null

    fun rotateLeft(){
        val curRight = this.right ?: return
        this.right = curRight.left
        curRight.left?.parent = this

        curRight.parent = this.parent
        when (this) {
            parent?.left -> parent?.left = curRight
            else -> parent?.right = curRight
        }
        curRight.left = this
        parent = curRight
    }

    fun rotateRight(){
        val curLeft = this.left ?: return
        this.left = curLeft.right
        curLeft.right?.parent = this

        curLeft.parent = this.parent
        when (this) {
            parent?.left -> parent?.left = curLeft
            else -> parent?.right = curLeft
        }
        curLeft.right = this
        parent = curLeft
    }

    fun uncle() : RBNode<K, V>? = when (parent){
            parent?.parent?.left -> parent?.parent?.right
            parent?.parent?.right -> parent?.parent?.left
            else -> null
    }
}