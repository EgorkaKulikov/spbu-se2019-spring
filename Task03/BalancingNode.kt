abstract class BalancingNode<K: Comparable<K>, V, NT: BalancingNode<K, V, NT>>: Node<K, V, NT>() {
    abstract fun balancing()
    protected open fun rotateLeft() {
        val father = parent!!
        val leftSon = left
        this.setFather(father.parent, father.type)
        father.setSon(leftSon, TypeSon.RightSon)
        this.setSon(father, TypeSon.LeftSon)
    }
    protected open fun rotateRight() {
        val father = parent!!
        val rightSon = right
        this.setFather(father.parent, father.type)
        father.setSon(rightSon, TypeSon.LeftSon)
        this.setSon(father, TypeSon.RightSon)
    }
    fun rotate() {
        when (type) {
            TypeSon.LeftSon -> rotateRight()
            TypeSon.RightSon -> rotateLeft()
            TypeSon.Root -> {}
        }
    }
    private fun rotateLeftBig() {
        val leftSon = left!!
        leftSon.rotateRight()
        leftSon.rotateLeft()
    }
    private fun rotateRightBig() {
        val rightSon = right!!
        rightSon.rotateLeft()
        rightSon.rotateRight()
    }
    fun rotateBig() {
        when (type) {
            TypeSon.LeftSon -> rotateRightBig()
            TypeSon.RightSon -> rotateLeftBig()
            TypeSon.Root -> {}
        }
    }
}
