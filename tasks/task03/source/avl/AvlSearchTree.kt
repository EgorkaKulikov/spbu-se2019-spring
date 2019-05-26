package avl

import binary.BinarySearchTree
import binary.Copyable
import binary.SearchData

data class AvlSearchTreeData<Key, Value>(
    override val key: Key,
    override var value: Value,
    override var state: BalanceFactor
) : AvlData, SearchData<Key, Value>, Copyable<AvlSearchTreeData<Key, Value>> {

    override fun copy() = AvlSearchTreeData(key, value, state)
}

class AvlSearchTree<Key : Comparable<Key>, Value> :
    BinarySearchTree<Key, Value, AvlSearchTreeData<Key, Value>>(),
    AvlTreeBalancer<AvlSearchTreeData<Key, Value>> {

    override fun createData(key: Key, value: Value) = AvlSearchTreeData(key, value, BalanceFactor.BALANCED)
}
