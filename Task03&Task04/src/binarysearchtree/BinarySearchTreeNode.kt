package binarysearchtree

class BinarySearchTreeNode<Key, Data> (var key: Key, var data: Data)
{
    internal var parent: BinarySearchTreeNode<Key, Data>? = null
    internal var leftChild: BinarySearchTreeNode<Key, Data>? = null
    internal var rightChild: BinarySearchTreeNode<Key, Data>? = null

    override fun equals(other: Any?): Boolean {

        if (other is BinarySearchTreeNode<*, *>?) {

            if (other == null) return false

            if (this.key == other.key &&
                this.data == other.data &&
                this.leftChild?.key == other.leftChild?.key &&
                this.rightChild?.key == other.rightChild?.key &&
                this.leftChild?.data == other.leftChild?.data &&
                this.rightChild?.data == other.rightChild?.data &&
                this.parent?.key == other.parent?.key) return true

            return false

        }

        return false

    }

    override fun hashCode(): Int {

        var result = key.hashCode()

        result = 31 * result + (key.hashCode())
        result = 31 * result + (this.leftChild?.hashCode() ?: 0)
        result = 31 * result + (this.rightChild?.hashCode() ?: 0)
        result = 31 * result + (this.parent?.hashCode() ?: 0)

        return result

    }
}
