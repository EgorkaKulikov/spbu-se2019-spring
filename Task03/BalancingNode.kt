abstract class BalancingNode<K: Comparable<K>, V, NT: BalancingNode<K, V, NT>>: Node<K, V, NT>() {
    override fun createSon(key: K, value: V, typeNewSon: TypeSon) {
        super.createSon(key, value, typeNewSon)
        if (typeNewSon == TypeSon.LeftSon)
            left!!.balancing()
        else
            right!!.balancing()
    }
    abstract fun balancing()
    open fun rotateLeft() {
        val father = parent!!
        val leftSon = left
        this.setFather(father.parent, father.type)
        father.setSon(leftSon, TypeSon.RightSon)
        this.setSon(father, TypeSon.LeftSon)
    }
    open fun rotateRight() {
        val father = parent!!
        val rightSon = right
        this.setFather(father.parent, father.type)
        father.setSon(rightSon, TypeSon.LeftSon)
        this.setSon(father, TypeSon.RightSon)
    }
}
