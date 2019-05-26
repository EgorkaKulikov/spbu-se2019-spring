package simple

import binary.BinarySearchTree
import binary.BinaryTreeNode
import binary.Copyable
import binary.SearchData

data class SimpleSearchData<Key, Value>(
    override val key: Key,
    override var value: Value
) : SearchData<Key, Value>, Copyable<SimpleSearchData<Key, Value>> {

    override fun copy() = SimpleSearchData(key, value)
}

class SimpleBinarySearchTree<Key : Comparable<Key>, Value> :
    BinarySearchTree<Key, Value, SimpleSearchData<Key, Value>>() {

    override fun createData(key: Key, value: Value) = SimpleSearchData(key, value)

    override fun balance(inserted: BinaryTreeNode<SimpleSearchData<Key, Value>>) {}
}
