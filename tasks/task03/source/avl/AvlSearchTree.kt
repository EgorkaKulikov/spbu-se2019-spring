package avl

import binary.BinarySearchTree
import binary.SearchData

data class AvlSearchData<Key, Value>(
    override val key: Key,
    override var value: Value,
    override var state: BalanceFactor
) : SearchData<Key, Value>, AvlData

typealias AvlSearchTree<Key, Value> = BinarySearchTree<Key, Value, AvlSearchData<Key, Value>>

fun <Key : Comparable<Key>, Value> AvlSearchTree(): AvlSearchTree<Key, Value> {
    return BinarySearchTree(AvlTreeBalancer()) { key: Key, value: Value ->
        AvlSearchData(key, value, BalanceFactor.BALANCED)
    }
}
