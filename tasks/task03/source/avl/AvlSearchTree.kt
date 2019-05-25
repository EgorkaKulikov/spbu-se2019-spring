package avl

import binary.BinarySearchTree
import binary.SearchData
import binary.SearchDataCreator

interface AvlVisibleSearchData<Key, Value> : SearchData<Key, Value> {
    val state: BalanceFactor
}

class AvlSearchData<Key, Value>(
    override val key: Key,
    override var value: Value,
    override var state: BalanceFactor
) : AvlData, AvlVisibleSearchData<Key, Value>

class AvlSearchDataCreator<Key, Value> : SearchDataCreator<Key, Value, AvlSearchData<Key, Value>> {

    override fun invoke(key: Key, value: Value) = AvlSearchData(key, value, BalanceFactor.BALANCED)
}

class AvlSearchTree<Key : Comparable<Key>, Value>
    : BinarySearchTree<Key, Value, AvlVisibleSearchData<Key, Value>, AvlSearchData<Key, Value>>(

    AvlTreeBalancer(),
    AvlSearchDataCreator()
)
