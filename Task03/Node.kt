import java.lang.Exception

enum class TypeSon {
    LeftSon,
    RightSon,
    Root,
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
    val brother: NT?
        get() = when (type) {
            TypeSon.LeftSon -> parent!!.right
            TypeSon.RightSon -> parent!!.left
            TypeSon.Root -> null
        }
    abstract val key: K
    abstract var value: V
    protected abstract fun createNode(key: K, value: V): NT
    fun createSon(key: K, value: V, typeNewSon: TypeSon) {
        setSon(createNode(key, value), typeNewSon)
    }
    fun setSon(newSon: NT?, typeNewSon: TypeSon) {
        when (typeNewSon) {
            TypeSon.LeftSon -> {
                if (left != null)
                    left!!.parent = null
                left = newSon
            }
            TypeSon.RightSon -> {
                if (right != null)
                    right!!.parent = null
                right = newSon
            }
            TypeSon.Root -> throw Exception("Function setSon don't expect that" +
                    "typeNewSon can be TypeSon.Root")
        }
        if (newSon != null) {
            if (newSon.type == TypeSon.LeftSon)
                newSon.parent!!.left = null
            else if (newSon.type == TypeSon.RightSon)
                newSon.parent!!.right = null
            newSon.parent = this as NT
        }
    }
    fun setFather(newFather: NT?, typeThisNode: TypeSon) {
        if (newFather != null) {
            when (typeThisNode) {
                TypeSon.LeftSon -> {
                    if (newFather.left != null)
                        newFather.left!!.parent = null
                    newFather.left = this as NT
                }
                TypeSon.RightSon -> {
                    if (newFather.right != null)
                        newFather.right!!.parent = null
                    newFather.right = this as NT
                }
                TypeSon.Root -> throw Exception("Function setFather don't expect that" +
                        "typeThisNode can be TypeSon.Root")
            }
        }
        if (type == TypeSon.LeftSon)
            parent!!.left = null
        else if (type == TypeSon.RightSon)
            parent!!.right = null
        parent = newFather
    }
    fun moveOnNewPlace(newPlace: NT) {
        setFather(newPlace.parent, newPlace.type)
        setSon(newPlace.left, TypeSon.LeftSon)
        setSon(newPlace.right, TypeSon.RightSon)
    }
    fun findKey(key: K): TypeSon =
        if (this.key == key)
            TypeSon.Root
        else if (this.key > key)
            TypeSon.LeftSon
        else
            TypeSon.RightSon
    fun nextNode(key: K): NT? = when (findKey(key)) {
        TypeSon.Root -> null
        TypeSon.LeftSon -> left
        TypeSon.RightSon -> right
    }
    open fun addLeftSon(key: K, value: V) {
        createSon(key, value, TypeSon.LeftSon)
    }
    open fun addRightSon(key: K, value: V) {
        createSon(key, value, TypeSon.RightSon)
    }
    abstract fun copy(): NT
}
