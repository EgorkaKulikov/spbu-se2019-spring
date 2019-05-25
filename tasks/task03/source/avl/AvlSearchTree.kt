package avl

import binary.BinarySearchTree
import binary.SearchData

interface AvlVisibleSearchData<Key, Value> : SearchData<Key, Value> {
    val state: BalanceFactor
}

class AvlSearchData<Key, Value>(
    override val key: Key,
    override var value: Value,
    override var state: BalanceFactor
) : AvlData, AvlVisibleSearchData<Key, Value>

class AvlSearchTree<Key : Comparable<Key>, Value>
    : BinarySearchTree<Key, Value, AvlVisibleSearchData<Key, Value>, AvlSearchData<Key, Value>>(

    createAvlTreeBalancer(),
    { key: Key, value: Value -> AvlSearchData(key, value, BalanceFactor.BALANCED) }
)
