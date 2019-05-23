package binaryTree

class BSTNode<K : Comparable<K>, V>(
    var key: K,
    var value: V,
    var parent: BSTNode<K, V>? = null
) {
    var left: BSTNode<K, V>? = null
    var right: BSTNode<K, V>? = null


    override fun equals(other: Any?): Boolean =
        other is BSTNode<*, *>
                && key == other.key
                && value == other.value
                && left == other.left
                && right == other.right


    override fun hashCode(): Int {
        var result = key.hashCode()
        result = 31 * result + (value?.hashCode() ?: 0)
        result = 31 * result + (left?.hashCode() ?: 0)
        result = 31 * result + (right?.hashCode() ?: 0)
        return result
    }

}
