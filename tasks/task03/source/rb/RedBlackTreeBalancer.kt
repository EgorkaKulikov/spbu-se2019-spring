package rb

import binary.BinaryTreeBalancer
import binary.RotatableBinaryNode
import rb.Color.Black
import rb.Color.Red

enum class Color {
    Red,
    Black
}

interface RedBlackData {
    var color: Color
}

class RedBlackTreeBalancer<Data : RedBlackData> : BinaryTreeBalancer<Data> {

    override fun invoke(inserted: RotatableBinaryNode<Data>) {
        var current = inserted.apply { data.color = Red }

        while (true) {
            val parent = current.parent

            if (parent == null) {
                current.data.color = Black
                break
            }

            if (parent.data.color == Black) {
                break
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
                current = grandparent
            } else {
                fun update(current: RotatableBinaryNode<Data>, parent: RotatableBinaryNode<Data>) {
                    grandparent.data.color = Red
                    parent.data.color = Black

                    if (current === parent.left) {
                        grandparent.rotateRight()
                    } else {
                        grandparent.rotateLeft()
                    }
                }

                if (current === parent.right && parent === grandparent.left) {
                    parent.rotateLeft()
                    update(parent, current)
                } else if (current === parent.left && parent === grandparent.right) {
                    parent.rotateRight()
                    update(parent, current)
                } else {
                    update(current, parent)
                }

                break
            }
        }
    }
}
