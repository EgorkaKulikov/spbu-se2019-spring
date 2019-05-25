package rb

import binary.BinarySearchTree
import binary.SearchData

interface RedBlackVisibleSearchData<Key, Value> : SearchData<Key, Value> {
    val color: Color
}

data class RedBlackSearchTreeData<Key, Value>(
    override val key: Key,
    override var value: Value,
    override var color: Color
) : RedBlackData, RedBlackVisibleSearchData<Key, Value>

class RedBlackSearchTree<Key : Comparable<Key>, Value> :
    BinarySearchTree<Key, Value, RedBlackVisibleSearchData<Key, Value>, RedBlackSearchTreeData<Key, Value>>(),
    RedBlackTreeBalancer<RedBlackSearchTreeData<Key, Value>> {

    override fun createData(key: Key, value: Value) = RedBlackSearchTreeData(key, value, Color.Red)
}

