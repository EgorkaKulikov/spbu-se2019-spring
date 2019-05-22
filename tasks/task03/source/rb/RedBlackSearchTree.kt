package rb

import binary.BinarySearchTree
import binary.SearchData

data class RedBlackSearchData<Key, Value>(
    override val key: Key,
    override var value: Value,
    override var color: Color
) : SearchData<Key, Value>, RedBlackData

typealias RedBlackSearchTree<Key, Value> = BinarySearchTree<Key, Value, RedBlackSearchData<Key, Value>>

fun <Key: Comparable<Key>, Value> RedBlackSearchTree(): RedBlackSearchTree<Key, Value> {
    return BinarySearchTree(RedBlackTreeBalancer()) { key: Key, value: Value ->
        RedBlackSearchData(key, value, Color.Red)
    }
}
