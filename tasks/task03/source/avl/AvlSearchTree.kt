package avl

import binary.BinarySearchTree
import binary.SearchData

class AvlSearchData<Key, Value>(
    override val key: Key,
    override var value: Value,
    state: BalanceFactor
) : AvlData(state), SearchData<Key, Value>

class AvlSearchTree<Key : Comparable<Key>, Value> : BinarySearchTree<Key, Value, AvlSearchData<Key, Value>>(
    createAvlTreeBalancer(), { key: Key, value: Value -> AvlSearchData(key, value, BalanceFactor.BALANCED) }
)
