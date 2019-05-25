package simple

import binary.BinaryTreeBalancer
import binary.RotatableBinaryNode

class SimpleBinaryTreeBalancer<Key, Value> : BinaryTreeBalancer<SimpleSearchData<Key, Value>> {

    override fun invoke(inserted: RotatableBinaryNode<SimpleSearchData<Key, Value>>) {}
}