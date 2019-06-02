package avl

import avl.BalanceFactor.*
import binary.BinarySearchTreeNode
import binary.BinaryTreeBalancer

object AvlTreeBalancer : BinaryTreeBalancer<BalanceFactor> {

    override fun balance(inserted: BinarySearchTreeNode<*, *, BalanceFactor>) {
        val parent = inserted.parent ?: return

        if (inserted === parent.right) {
            if (parent.info == RIGHT_HEAVY) {
                if (inserted.info == LEFT_HEAVY) {
                    val left = inserted.left ?: throw IllegalArgumentException("Tree was changed without balancer")

                    when (left.info) {
                        RIGHT_HEAVY -> {
                            parent.info = LEFT_HEAVY
                            inserted.info = BALANCED
                        }
                        BALANCED -> {
                            parent.info = BALANCED
                            inserted.info = BALANCED
                        }
                        else -> {
                            parent.info = BALANCED
                            inserted.info = RIGHT_HEAVY
                        }
                    }

                    left.info = BALANCED

                    inserted.rotateRight()
                    parent.rotateLeft()
                } else {
                    inserted.info = BALANCED
                    parent.info = BALANCED
                    parent.rotateLeft()
                }

                return
            }

            parent.info = parent.info.increased
        } else {
            if (parent.info == LEFT_HEAVY) {
                if (inserted.info == RIGHT_HEAVY) {
                    val right = inserted.right ?: throw IllegalArgumentException("Tree was changed without balancer")

                    when (right.info) {
                        LEFT_HEAVY -> {
                            parent.info = RIGHT_HEAVY
                            inserted.info = BALANCED
                        }
                        BALANCED -> {
                            parent.info = BALANCED
                            inserted.info = BALANCED
                        }
                        else -> {
                            parent.info = BALANCED
                            inserted.info = LEFT_HEAVY
                        }
                    }

                    right.info = BALANCED

                    inserted.rotateLeft()
                    parent.rotateRight()
                } else {
                    parent.info = BALANCED
                    inserted.info = BALANCED
                    parent.rotateRight()
                }

                return
            }

            parent.info = parent.info.decreased
        }

        if (parent.info != BALANCED) {
            balance(parent)
        }
    }
}
