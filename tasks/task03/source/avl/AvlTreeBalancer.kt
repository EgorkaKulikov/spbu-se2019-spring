package avl

import avl.BalanceFactor.*
import binary.BinaryTreeBalancer
import binary.RotatableBinaryNode

enum class BalanceFactor {
    LEFT_HEAVY,
    BALANCED,
    RIGHT_HEAVY;

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

interface AvlData {
    var state: BalanceFactor
}

class AvlTreeBalancer<Data : AvlData> : BinaryTreeBalancer<Data> {

    override fun invoke(inserted: RotatableBinaryNode<Data>) {
        var current = inserted.apply { data.state = BALANCED }

        while (true) {
            val parent = current.parent ?: break

            if (current === parent.right) {
                if (parent.data.state == RIGHT_HEAVY) {
                    if (current.data.state == LEFT_HEAVY) {
                        val left = current.left ?: throw IllegalArgumentException("Tree was changed without balancer")

                        when (left.data.state) {
                            RIGHT_HEAVY -> {
                                parent.data.state = LEFT_HEAVY
                                current.data.state = BALANCED
                            }
                            BALANCED -> {
                                parent.data.state = BALANCED
                                current.data.state = BALANCED
                            }
                            else -> {
                                parent.data.state = BALANCED
                                current.data.state = RIGHT_HEAVY
                            }
                        }

                        left.data.state = BALANCED

                        current.rotateRight()
                        parent.rotateLeft()
                    } else {
                        current.data.state = BALANCED
                        parent.data.state = BALANCED
                        parent.rotateLeft()
                    }

                    break
                }

                parent.data.state = parent.data.state.increased
            } else {
                if (parent.data.state == LEFT_HEAVY) {
                    if (current.data.state == RIGHT_HEAVY) {
                        val right = current.right ?: throw IllegalArgumentException("Tree was changed without balancer")

                        when (right.data.state) {
                            LEFT_HEAVY -> {
                                parent.data.state = RIGHT_HEAVY
                                current.data.state = BALANCED
                            }
                            BALANCED -> {
                                parent.data.state = BALANCED
                                current.data.state = BALANCED
                            }
                            else -> {
                                parent.data.state = BALANCED
                                current.data.state = LEFT_HEAVY
                            }
                        }

                        right.data.state = BALANCED

                        current.rotateLeft()
                        parent.rotateRight()
                    } else {
                        parent.data.state = BALANCED
                        current.data.state = BALANCED
                        parent.rotateRight()
                    }

                    break
                }

                parent.data.state = parent.data.state.decreased
            }

            if (parent.data.state == BALANCED) {
                break
            }

            current = parent
        }
    }
}
