import java.lang.Exception

enum class SonType {
    LeftSon,
    RightSon,
    Root,
}

abstract class Node<K: Comparable<K>, V, NT: Node<K, V, NT>> {
    var left: NT? = null
    var right: NT? = null
    var parent: NT? = null
    val type: SonType
        get() {
            if (parent == null)
                return SonType.Root
            return if (parent!!.left == this) SonType.LeftSon else SonType.RightSon
        }
    val brother: NT?
        get() = when (type) {
            SonType.LeftSon -> parent!!.right
            SonType.RightSon -> parent!!.left
            SonType.Root -> null
        }
    abstract val key: K
    abstract var value: V

    protected abstract fun createNode(key: K, value: V): NT

    fun createSon(key: K, value: V, typeNewSon: SonType) {
        setSon(createNode(key, value), typeNewSon)
    }

    fun setSon(newSon: NT?, typeNewSon: SonType) {
        when (typeNewSon) {
            SonType.LeftSon -> {
                if (left != null)
                    left!!.parent = null
                left = newSon
            }
            SonType.RightSon -> {
                if (right != null)
                    right!!.parent = null
                right = newSon
            }
            SonType.Root -> throw Exception("Function setSon don't expect that" +
                    "typeNewSon can be SonType.Root")
        }
        if (newSon != null) {
            if (newSon.type == SonType.LeftSon)
                newSon.parent!!.left = null
            else if (newSon.type == SonType.RightSon)
                newSon.parent!!.right = null
            newSon.parent = this as NT
        }
    }

    fun setFather(newFather: NT?, typeThisNode: SonType) {
        if (newFather != null) {
            when (typeThisNode) {
                SonType.LeftSon -> {
                    if (newFather.left != null)
                        newFather.left!!.parent = null
                    newFather.left = this as NT
                }
                SonType.RightSon -> {
                    if (newFather.right != null)
                        newFather.right!!.parent = null
                    newFather.right = this as NT
                }
                SonType.Root -> throw Exception("Function setFather don't expect that" +
                        "typeThisNode can be SonType.Root")
            }
        }
        if (type == SonType.LeftSon)
            parent!!.left = null
        else if (type == SonType.RightSon)
            parent!!.right = null
        parent = newFather
    }

    fun moveOn(newPlace: NT) {
        setFather(newPlace.parent, newPlace.type)
        setSon(newPlace.left, SonType.LeftSon)
        setSon(newPlace.right, SonType.RightSon)
    }

    fun findKeyType(key: K): SonType =
        if (this.key == key)
            SonType.Root
        else if (this.key > key)
            SonType.LeftSon
        else
            SonType.RightSon

    fun nextNode(key: K): NT? = when (findKeyType(key)) {
        SonType.Root -> null
        SonType.LeftSon -> left
        SonType.RightSon -> right
    }

    open fun addLeftSon(key: K, value: V) {
        createSon(key, value, SonType.LeftSon)
    }

    open fun addRightSon(key: K, value: V) {
        createSon(key, value, SonType.RightSon)
    }

    abstract fun copy(): NT
}
