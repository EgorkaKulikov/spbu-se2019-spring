class BSTNode<K : Comparable<K>, V>(
    var key : K,
    var value : V,
    var parent : BSTNode<K, V>? = null
)
{
    var left : BSTNode<K, V>? = null
    var right : BSTNode<K, V>? = null
}