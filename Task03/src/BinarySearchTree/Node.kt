package BinarySearchTree

internal class Node<K : Comparable<K>, V>(
        var key: K,
        var value: V,
        var parent: Node<K, V>? = null
) {

    var left: Node<K, V>? = null
    var right: Node<K, V>? = null

    private fun pullParent(): Node<K, V>? = if (this.parent == null) null else {
        Node(this.parent!!.key, this.parent!!.value, null)
    }

    private fun pullLeft(): Node<K, V>? = if (this.left == null) null else {
        Node(this.left!!.key, this.left!!.value, null)
    }

    private fun pullRight(): Node<K, V>? = if (this.right == null) null else {
        Node(this.right!!.key, this.right!!.value, null)
    }

    override fun equals(other: Any?): Boolean {

        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Node<*, *>

        return when {
            key != other.key -> false
            value != other.value -> false
            this.pullLeft() != other.pullLeft() -> false
            this.pullRight() != other.pullRight() -> false
            this.pullParent() != other.pullParent() -> false
            else -> true
        }

    }

    override fun hashCode(): Int {

        var result = key.hashCode()

        result = 31 * result + (value?.hashCode() ?: 0)
        result = 31 * result + (this.pullParent()?.hashCode() ?: 0)
        result = 31 * result + (this.pullLeft()?.hashCode() ?: 0)
        result = 31 * result + (this.pullRight()?.hashCode() ?: 0)

        return result

    }

}