package avl

import binary.BinarySearchTree
import binary.SearchData

interface AvlVisibleSearchData<Key, Value> : SearchData<Key, Value> {
    val state: BalanceFactor
}

data class AvlSearchTreeData<Key, Value>(
    override val key: Key,
    override var value: Value,
    override var state: BalanceFactor
) : AvlData, AvlVisibleSearchData<Key, Value>

class AvlSearchTree<Key : Comparable<Key>, Value> :
    BinarySearchTree<Key, Value, AvlVisibleSearchData<Key, Value>, AvlSearchTreeData<Key, Value>>(),
    AvlTreeBalancer<AvlSearchTreeData<Key, Value>> {

    override fun createData(key: Key, value: Value) = AvlSearchTreeData(key, value, BalanceFactor.BALANCED)
}
