package rb

import binary.BinarySearchTree

class RedBlackSearchTree<Key : Comparable<Key>, Value> : BinarySearchTree<Key, Value, Color>(
    RedBlackTreeBalancer,
    Color.Red
)
