package simple

import binary.BinarySearchTree
import binary.SearchData
import binary.SearchDataCreator

class SimpleSearchData<Key, Value>(
    override val key: Key,
    override var value: Value
) : SearchData<Key, Value>

class SimpleSearchDataCreator<Key, Value> : SearchDataCreator<Key, Value, SimpleSearchData<Key, Value>> {

    override fun invoke(key: Key, value: Value) = SimpleSearchData(key, value)
}

class SimpleBinarySearchTree<Key : Comparable<Key>, Value>
    : BinarySearchTree<Key, Value, SearchData<Key, Value>, SimpleSearchData<Key, Value>>(

    SimpleBinaryTreeBalancer(),
    SimpleSearchDataCreator()
)
