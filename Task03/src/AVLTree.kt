import kotlin.math.max

class AVLTree<T, K : Comparable<K>> : BalancedSearchTree<T, K>() {
    inner class AVLNode(_key: K, _value: T, _parent: Node?) : BalancedNode(_key, _value, _parent) {
        private var height = 1

        internal fun balanceFactor(): Int {
            var right = 0
            if(this.right != null){
                right = (this.right as AVLNode).height
            }
            var left = 0
            if(this.left != null){
                left = (this.left as AVLNode).height
            }
            return right - left
        }

        private fun fixHeight(){
            var right = 0
            if(this.right != null){
                right = (this.right as AVLNode).height
            }
            var left = 0
            if(this.left != null){
                left = (this.left as AVLNode).height
            }
            this.height = max(right, left) + 1
        }

        internal fun balanceNode(){
            this.fixHeight()
            if(this.balanceFactor() == 2){
                if((this.right as AVLNode).balanceFactor() < 0){
                    (this.right as BalancedNode).rotateRight()
                }
                (this.right as BalancedNode).rotateLeft()
            }
            if(this.balanceFactor() == -2){
                if((this.left as AVLNode).balanceFactor() > 0){
                    (this.left as BalancedNode).rotateLeft()
                }
                (this.left as BalancedNode).rotateRight()
            }
        }
    }


    override fun createNode(value: T, key: K, parent: Node?): AVLNode {
        return AVLNode(key, value, parent)
    }

    override fun innerInsert(key: K, value: T) : AVLNode{
        if (this.root == null) {
            this.root = createNode(value, key, null)
            size++
            return this.root as AVLNode
        }

        var curNode: Node? = this.root
        val newNode = createNode(value, key, null)
        while (curNode != null) {
            if (newNode.key > curNode.key) {
                if (curNode.right != null) curNode = curNode.right
                else {
                    newNode.parent = curNode
                    curNode.right = newNode
                    break
                }
            }
            else if (newNode.key < curNode.key) {
                if (curNode.left != null) curNode = curNode.left
                else {
                    newNode.parent = curNode
                    curNode.left = newNode
                    break
                }
            }
        }
        size++
        newNode.balanceNode()
        return newNode
    }

    private fun verifyAVL(curNode: AVLNode): Boolean{
        if(curNode.balanceFactor() >= -1 && curNode.balanceFactor() <= 1){
            if(curNode.left != null){
                if(!verifyAVL(curNode.left as AVLNode)) return false
            }
            if(curNode.right != null){
                if(!verifyAVL(curNode.right as AVLNode)) return false
            }
            return true
        }
        return false
    }

    internal fun isAVLTree(): Boolean {
        return root == null || verifyAVL(root as AVLNode)
    }
}