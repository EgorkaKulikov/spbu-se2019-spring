package simple

import binary.BinarySearchTree
import binary.SearchData

class SimpleSearchData<Key, Value>(
    override val key: Key,
    override var value: Value
) : SearchData<Key, Value>

class SimpleBinarySearchTree<Key : Comparable<Key>, Value>
    : BinarySearchTree<Key, Value, SearchData<Key, Value>, SimpleSearchData<Key, Value>>(

    {},
    { key: Key, value: Value -> SimpleSearchData(key, value) }
)
