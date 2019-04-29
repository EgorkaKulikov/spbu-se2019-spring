abstract class NodeWithBalancing<K: Comparable<K>, V, NT: NodeWithBalancing<K, V, NT>>: Node<K, V, NT>() {
    public abstract fun balancing()
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
    public fun rotate() {
        when (type) {
            TypeSon.LeftSon -> rotateRight()
            TypeSon.RightSon -> rotateLeft()
            TypeSon.Root -> {}
        }
    }
    private fun bigRotateLeft() {
        val leftSon = left!!
        leftSon.rotateRight()
        leftSon.rotateLeft()
    }
    private fun bigRotateRight() {
        val rightSon = right!!
        rightSon.rotateLeft()
        rightSon.rotateRight()
    }
    public fun bigRotate() {
        when (type) {
            TypeSon.LeftSon -> bigRotateRight()
            TypeSon.RightSon -> bigRotateLeft()
            TypeSon.Root -> {}
        }
    }
}
