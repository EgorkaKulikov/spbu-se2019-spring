package rb

import binary.BinaryTreeBalancer
import binary.BinarySearchTreeNode
import rb.Color.Black
import rb.Color.Red

object RedBlackTreeBalancer : BinaryTreeBalancer<Color> {

    override fun balance(inserted: BinarySearchTreeNode<*, *, Color>) {
        val parent = inserted.parent

        if (parent == null) {
            inserted.info = Black
            return
        }

        if (parent.info == Black) {
            return
        }

        val grandparent = parent.parent ?: throw IllegalArgumentException("Tree was changed without balancer")
        val uncle = when {
            parent === grandparent.left -> grandparent.right
            else -> grandparent.left
        }

        if (uncle != null && uncle.info == Red) {
            uncle.info = Black
            parent.info = Black
            grandparent.info = Red
            balance(grandparent)
        } else {
            fun update(current: BinarySearchTreeNode<*, *, Color>, parent: BinarySearchTreeNode<*, *, Color>) {
                grandparent.info = Red
                parent.info = Black

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
