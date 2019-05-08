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

        curRight.parent = parent
        when (this) {
            parent?.left -> parent?.left = curRight
            else -> parent?.right = curRight
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

        curLeft.parent = parent
        when (this) {
            parent?.left -> parent?.left = curLeft
            else -> parent?.right = curLeft
        }
        curLeft.right = this
        parent = curLeftОпять забываешь, что потом тестировщики пришлют тебе вот такой эксепшен из логов и попросят быстренько починить. Не пожалеешь ли ты, что не написал "Right child for node ... does not exist"?



        this.height = max(this.left?.height ?: 0, this.right?.height ?: 0) + 1
        curLeft.height = max(curLeft.left?.height ?: 0, curLeft.right?.height ?: 0) + 1
    }

    fun heightDif(): Int = (this.left?.height ?: 0) - (this.right?.height ?: 0)
}