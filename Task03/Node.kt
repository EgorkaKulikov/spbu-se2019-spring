enum class TypeSon {
    LeftSon, RightSon, Root
}

abstract class Node<K: Comparable<K>, V, NT: Node<K, V, NT>> {
    var left: NT? = null
    var right: NT? = null
    var parent: NT? = null
    val type: TypeSon
        get() {
            if (parent == null)
                return TypeSon.Root
            return if (parent!!.left == this) TypeSon.LeftSon else TypeSon.RightSon
        }
    abstract var key: K
    abstract var value: V
    abstract fun createNode(key: K, value: V): NT
    open fun createSon(key: K, value: V, typeNewSon: TypeSon) {
        setSon(createNode(key, value), typeNewSon)
    }
    fun setSon(newSon: NT?, typeNewSon: TypeSon) {
        if (typeNewSon == TypeSon.LeftSon)
            left = newSon
        else
            right = newSon
        newSon?.parent = this as NT
    }
    fun setFather(newFather: NT?, typeThisNode: TypeSon) {
        if (typeThisNode == TypeSon.LeftSon)
            newFather?.left = this as NT
        else
            newFather?.right = this as NT
        parent = newFather
    }
    fun findKey(key: K): TypeSon {
        if (this.key == key)
            return TypeSon.Root
        if (this.key > key)
            return TypeSon.LeftSon
        return TypeSon.RightSon
    }
    fun nextNode(key: K): NT? = when (findKey(key)) {
        TypeSon.Root -> null
        TypeSon.LeftSon -> left
        TypeSon.RightSon -> right
    }
    override fun toString(): String = "($key to $value)"
    fun subtreeToString(): String = "$this" +
                (if (left != null)
                    " left: {${left!!.subtreeToString()}}"
                else "") +
                (if (right != null)
                    " right: {${right!!.subtreeToString()}}"
                else "")
}
