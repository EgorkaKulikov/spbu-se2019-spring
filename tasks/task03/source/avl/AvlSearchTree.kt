package avl

import binary.BinarySearchTree

class AvlSearchTree<Key : Comparable<Key>, Value> : BinarySearchTree<Key, Value, BalanceFactor>(
    AvlTreeBalancer,
    BalanceFactor.BALANCED
)
