abstract class BalanceNode<K: Comparable<K>, V, NT: BalanceNode<K, V, NT>>: Node<K, V, NT>() {
    public abstract fun balancing()

    public open fun leftRotate() {
        val father = parent!!
        val leftSon = left
        this.setFather(father.parent, father.type)
        father.setSon(leftSon, SonType.RightSon)
        this.setSon(father, SonType.LeftSon)
    }

    public open fun rightRotate() {
        val father = parent!!
        val rightSon = right
        this.setFather(father.parent, father.type)
        father.setSon(rightSon, SonType.LeftSon)
        this.setSon(father, SonType.RightSon)
    }

    public fun rotate() {
        when (type) {
            SonType.LeftSon -> rightRotate()
            SonType.RightSon -> leftRotate()
            SonType.Root -> {}
        }
    }

    public fun bigLeftRotate() {
        val leftSon = left!!
        leftSon.rightRotate()
        leftSon.leftRotate()
    }

    public fun bigRightRotate() {
        val rightSon = right!!
        rightSon.leftRotate()
        rightSon.rightRotate()
    }

    public fun bigRotate() {
        when (type) {
            SonType.LeftSon -> bigRightRotate()
            SonType.RightSon -> bigLeftRotate()
            SonType.Root -> {}
        }
    }
}
