package avl

import binary.BinaryTreeBalancer
import binary.BinaryTreeCorrector

enum class BalanceFactor(val value: Int) {
    LEFT_HEAVY(-1), BALANCED(0), RIGHT_HEAVY(1);

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

class AvlTreeBalancer<Data : AvlData> : BinaryTreeBalancer<Data>() {

    override fun balance(corrector: BinaryTreeCorrector<Data>) = with(corrector) {
        currentData.state = BalanceFactor.BALANCED

        while (currentHasParent) {
            if (currentIsRightChild) {
                if (parentData.state == BalanceFactor.RIGHT_HEAVY) {
                    if (currentData.state == BalanceFactor.LEFT_HEAVY) {
                        when (leftChildData.state) {
                            BalanceFactor.RIGHT_HEAVY -> {
                                parentData.state = BalanceFactor.LEFT_HEAVY
                                currentData.state = BalanceFactor.BALANCED
                            }
                            BalanceFactor.BALANCED -> {
                                parentData.state = BalanceFactor.BALANCED
                                currentData.state = BalanceFactor.BALANCED
                            }
                            else -> {
                                parentData.state = BalanceFactor.BALANCED
                                currentData.state = BalanceFactor.RIGHT_HEAVY
                            }
                        }

                        leftChildData.state = BalanceFactor.BALANCED

                        rotateCurrentToRight()
                        rotateGrandparentToLeft()
                    } else {
                        currentData.state = BalanceFactor.BALANCED
                        parentData.state = BalanceFactor.BALANCED
                        rotateParentToLeft()
                    }

                    break
                } else {
                    parentData.state = parentData.state.increased
                }
            } else {
                if (parentData.state == BalanceFactor.LEFT_HEAVY) {
                    if (currentData.state == BalanceFactor.RIGHT_HEAVY) {
                        when (rightChildData.state) {
                            BalanceFactor.LEFT_HEAVY -> {
                                parentData.state = BalanceFactor.RIGHT_HEAVY
                                currentData.state = BalanceFactor.BALANCED
                            }
                            BalanceFactor.BALANCED -> {
                                parentData.state = BalanceFactor.BALANCED
                                currentData.state = BalanceFactor.BALANCED
                            }
                            else -> {
                                parentData.state = BalanceFactor.BALANCED
                                currentData.state = BalanceFactor.LEFT_HEAVY
                            }
                        }

                        rightChildData.state = BalanceFactor.BALANCED

                        rotateCurrentToLeft()
                        rotateGrandparentToRight()
                    } else {
                        parentData.state = BalanceFactor.BALANCED
                        currentData.state = BalanceFactor.BALANCED
                        rotateParentToRight()
                    }

                    break
                } else {
                    parentData.state = parentData.state.decreased
                }
            }

            if (parentData.state == BalanceFactor.BALANCED) {
                break
            }

            moveToParent()
        }
    }
}
