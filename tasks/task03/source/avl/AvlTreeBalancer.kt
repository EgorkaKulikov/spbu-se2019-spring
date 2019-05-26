package avl

import avl.BalanceFactor.*
import binary.BinaryTreeBalancer
import binary.BinaryTreeNode

enum class BalanceFactor {
    LEFT_HEAVY,
    BALANCED,
    RIGHT_HEAVY,
    ;

    val increased
        get() = when (this) {
            LEFT_HEAVY -> BALANCED
            BALANCED -> RIGHT_HEAVY
            else -> throw IllegalStateException("This is maximum value")
        }

    val decreased
        get() = when (this) {
            RIGHT_HEAVY -> BALANCED
            BALANCED -> LEFT_HEAVY
            else -> throw IllegalStateException("This is minimum value")
        }
}

open class AvlData(var state: BalanceFactor)

interface AvlTreeBalancer<Data : AvlData> : BinaryTreeBalancer<Data> {

    override fun balance(inserted: BinaryTreeNode<Data>) {
        val parent = inserted.parent ?: return

        if (inserted === parent.right) {
            if (parent.data.state == RIGHT_HEAVY) {
                if (inserted.data.state == LEFT_HEAVY) {
                    val left = inserted.left ?: throw IllegalArgumentException("Tree was changed without balancer")

                    when (left.data.state) {
                        RIGHT_HEAVY -> {
                            parent.data.state = LEFT_HEAVY
                            inserted.data.state = BALANCED
                        }
                        BALANCED -> {
                            parent.data.state = BALANCED
                            inserted.data.state = BALANCED
                        }
                        else -> {
                            parent.data.state = BALANCED
                            inserted.data.state = RIGHT_HEAVY
                        }
                    }

                    left.data.state = BALANCED

                    inserted.rotateRight()
                    parent.rotateLeft()
                } else {
                    inserted.data.state = BALANCED
                    parent.data.state = BALANCED
                    parent.rotateLeft()
                }

                return
            }

            parent.data.state = parent.data.state.increased
        } else {
            if (parent.data.state == LEFT_HEAVY) {
                if (inserted.data.state == RIGHT_HEAVY) {
                    val right = inserted.right ?: throw IllegalArgumentException("Tree was changed without balancer")

                    when (right.data.state) {
                        LEFT_HEAVY -> {
                            parent.data.state = RIGHT_HEAVY
                            inserted.data.state = BALANCED
                        }
                        BALANCED -> {
                            parent.data.state = BALANCED
                            inserted.data.state = BALANCED
                        }
                        else -> {
                            parent.data.state = BALANCED
                            inserted.data.state = LEFT_HEAVY
                        }
                    }

                    right.data.state = BALANCED

                    inserted.rotateLeft()
                    parent.rotateRight()
                } else {
                    parent.data.state = BALANCED
                    inserted.data.state = BALANCED
                    parent.rotateRight()
                }

                return
            }

            parent.data.state = parent.data.state.decreased
        }

        if (parent.data.state != BALANCED) {
            balance(parent)
        }
    }
}
