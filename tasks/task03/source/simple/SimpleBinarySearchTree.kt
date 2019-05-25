package simple

import binary.BinarySearchTree
import binary.RotatableBinaryNode
import binary.SearchData

data class SimpleSearchData<Key, Value>(
    override val key: Key,
    override var value: Value
) : SearchData<Key, Value>

class SimpleBinarySearchTree<Key : Comparable<Key>, Value> :
    BinarySearchTree<Key, Value, SearchData<Key, Value>, SimpleSearchData<Key, Value>>() {

    override fun createData(key: Key, value: Value) = SimpleSearchData(key, value)

    override fun balance(inserted: RotatableBinaryNode<SimpleSearchData<Key, Value>>) {}
}
