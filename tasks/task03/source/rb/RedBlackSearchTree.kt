package rb

import binary.BinarySearchTree
import binary.Copyable
import binary.SearchData

data class RedBlackSearchTreeData<Key, Value>(
    override val key: Key,
    override var value: Value,
    override var color: Color
) : RedBlackData, SearchData<Key, Value>, Copyable<RedBlackSearchTreeData<Key, Value>> {

    override fun copy() = RedBlackSearchTreeData(key, value, color)
}

class RedBlackSearchTree<Key : Comparable<Key>, Value> :
    BinarySearchTree<Key, Value, RedBlackSearchTreeData<Key, Value>>(),
    RedBlackTreeBalancer<RedBlackSearchTreeData<Key, Value>> {

    override fun createData(key: Key, value: Value) = RedBlackSearchTreeData(key, value, Color.Red)
}

