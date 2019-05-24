package rb

import binary.BinarySearchTree
import binary.SearchData

class RedBlackSearchData<Key, Value>(
    override val key: Key,
    override var value: Value,
    color: Color
) : RedBlackData(color), SearchData<Key, Value>

class RedBlackSearchTree<Key: Comparable<Key>, Value> : BinarySearchTree<Key, Value, RedBlackSearchData<Key, Value>>(
    createRedBlackTreeBalancer(), { key: Key, value: Value -> RedBlackSearchData(key, value, Color.Red) }
)
