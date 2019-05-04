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
            this.parent?.left -> this.parent?.left = curRight
            else -> this.parent?.right = curRight
        }
        curRight.left = this
        this.parent = curRight
    }

    fun rotateRight(){
        val curLeft = this.left ?: return
        this.left = curLeft.right
        curLeft.right?.parent = this

        curLeft.parent = this.parent
        when (this) {
            this.parent?.left -> this.parent?.left = curLeft
            else -> this.parent?.right = curLeft
        }
        curLeft.right = this
        this.parent = curLeft
    }

    fun uncle() : RBNode<K, V>? = when (this.parent){
            this.parent?.parent?.left -> this.parent?.parent?.right
            this.parent?.parent?.right -> this.parent?.parent?.left
            else -> null
    }
}