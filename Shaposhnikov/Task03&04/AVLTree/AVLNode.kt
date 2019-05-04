import java.lang.Integer.max

class AVLNode<K : Comparable<K>, V>(
    var key : K,
    var value : V,
    var parent : AVLNode<K, V>? = null,
    var height : Int = 1
){
    var left : AVLNode<K, V>? = null
    var right : AVLNode<K, V>? = null

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

        this.height = max(this.left?.height ?: 0, this.right?.height ?: 0) + 1
        curRight.height = max(curRight.left?.height ?: 0, curRight.right?.height ?: 0) + 1
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

        this.height = max(this.left?.height ?: 0, this.right?.height ?: 0) + 1
        curLeft.height = max(curLeft.left?.height ?: 0, curLeft.right?.height ?: 0) + 1
    }

    fun heightDif(): Int = (this.left?.height ?: 0) - (this.right?.height ?: 0)
}