package rb

import binary.BinarySearchTree
import binary.SearchData

interface RedBlackVisibleSearchData<Key, Value> : SearchData<Key, Value> {
    val color: Color
}

class RedBlackSearchData<Key, Value>(
    override val key: Key,
    override var value: Value,
    override var color: Color
) : RedBlackData, RedBlackVisibleSearchData<Key, Value>

class RedBlackSearchTree<Key : Comparable<Key>, Value>
    : BinarySearchTree<Key, Value, RedBlackVisibleSearchData<Key, Value>, RedBlackSearchData<Key, Value>>(

    createRedBlackTreeBalancer(),
    { key: Key, value: Value -> RedBlackSearchData(key, value, Color.Red) }
)
