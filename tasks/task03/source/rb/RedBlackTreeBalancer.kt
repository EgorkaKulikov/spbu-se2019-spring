package rb

import binary.BinaryTreeBalancer
import binary.BinaryTreeNode
import rb.Color.Black
import rb.Color.Red

enum class Color {
    Red,
    Black,
}

open class RedBlackData(var color: Color)

interface RedBlackTreeBalancer<Data : RedBlackData> : BinaryTreeBalancer<Data> {

    override fun balance(inserted: BinaryTreeNode<Data>) {
        val parent = inserted.parent

        if (parent == null) {
            inserted.data.color = Black
            return
        }

        if (parent.data.color == Black) {
            return
        }

        val grandparent = parent.parent ?: throw IllegalArgumentException("Tree was changed without balancer")
        val uncle = when {
            parent === grandparent.left -> grandparent.right
            else -> grandparent.left
        }

        if (uncle != null && uncle.data.color == Red) {
            uncle.data.color = Black
            parent.data.color = Black
            grandparent.data.color = Red
            balance(grandparent)
        } else {
            fun update(current: BinaryTreeNode<Data>, parent: BinaryTreeNode<Data>) {
                grandparent.data.color = Red
                parent.data.color = Black

                if (current === parent.left) {
                    grandparent.rotateRight()
                } else {
                    grandparent.rotateLeft()
                }
            }

            if (inserted === parent.right && parent === grandparent.left) {
                parent.rotateLeft()
                update(parent, inserted)
            } else if (inserted === parent.left && parent === grandparent.right) {
                parent.rotateRight()
                update(parent, inserted)
            } else {
                update(inserted, parent)
            }
        }
    }
}
