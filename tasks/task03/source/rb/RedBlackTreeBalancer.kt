package rb

import binary.BinaryTreeBalancer
import binary.BinaryTreeCorrector
import rb.Color.*

enum class Color {
    Red, Black
}

interface RedBlackData {
    var color: Color
}

class RedBlackTreeBalancer<Data : RedBlackData> : BinaryTreeBalancer<Data>() {

    override fun balance(corrector: BinaryTreeCorrector<Data>) = with(corrector) {
        currentData.color = Red

        while (true) {
            if (!currentHasParent) {
                currentData.color = Black
                break
            }

            if (parentData.color == Black) {
                break
            }

            if (currentHasUncle && uncleData.color == Red) {
                uncleData.color = Black
                parentData.color = Black
                grandparentData.color = Red
                moveToGrandparent()
            } else {
                if (currentIsRightChild) {
                    if (parentIsLeftChild) {
                        rotateParentToLeft()
                        moveToLeftChild()
                    }
                } else if (parentIsRightChild) {
                    rotateParentToRight()
                    moveToRightChild()
                }

                grandparentData.color = Red
                parentData.color = Black

                if (currentIsLeftChild) {
                    rotateGrandparentToRight()
                } else {
                    rotateGrandparentToLeft()
                }

                break
            }
        }
    }
}
