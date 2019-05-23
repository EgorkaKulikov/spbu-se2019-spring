import java.lang.Integer.max

class AVLNode<K : Comparable<K>, V>(
    var key : K,
    var value : V,
    var parent : AVLNode<K, V>? = null
){
    internal var left : AVLNode<K, V>? = null
    internal var right : AVLNode<K, V>? = null
    internal var height : Int = 1

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

        height = max(left?.height ?: 0, right?.height ?: 0) + 1
        curRight.height = max(curRight.left?.height ?: 0, curRight.right?.height ?: 0) + 1
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

        height = max(left?.height ?: 0, right?.height ?: 0) + 1
        curLeft.height = max(curLeft.left?.height ?: 0, curLeft.right?.height ?: 0) + 1
    }

    fun heightDif(): Int = (left?.height ?: 0) - (right?.height ?: 0)
}