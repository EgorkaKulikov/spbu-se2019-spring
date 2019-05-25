package rb

import binary.BinarySearchTree
import binary.SearchData
import binary.SearchDataCreator

interface RedBlackVisibleSearchData<Key, Value> : SearchData<Key, Value> {
    val color: Color
}

class RedBlackSearchData<Key, Value>(
    override val key: Key,
    override var value: Value,
    override var color: Color
) : RedBlackData, RedBlackVisibleSearchData<Key, Value>

class RedBlackSearchDataCreator<Key, Value> : SearchDataCreator<Key, Value, RedBlackSearchData<Key, Value>> {

    override fun invoke(key: Key, value: Value) = RedBlackSearchData(key, value, Color.Red)
}

class RedBlackSearchTree<Key : Comparable<Key>, Value>
    : BinarySearchTree<Key, Value, RedBlackVisibleSearchData<Key, Value>, RedBlackSearchData<Key, Value>>(

    RedBlackTreeBalancer(),
    RedBlackSearchDataCreator()
)
