import kotlin.math.abs
import kotlin.math.max

class AVLTree<K : Comparable<K>, V> : BalancedSearchTree<K, V>() {
    inner class AVLNode(_key: K, _value: V, _parent: Node?) : Node(_key, _value, _parent) {
        var height = 1

        fun updateHeight() {
            var tmp = 0
            this.right?.let { tmp = (this.right as AVLNode).height }
            this.left?.let { tmp = max(tmp, (this.left as AVLNode).height) }
            height = tmp + 1
        }

        override fun print(indentation: Int, side: Int) {
            this.right?.print(indentation + 1, -1)

            for (i in 1..indentation) {
                print(" ")
            }

            when(side) {
                -1 -> print("/")
                1 -> print("\\")
            }
            println("$key $value $height")

            this.left?.print(indentation + 1, 1)
        }

        fun verifyAVL(): Pair<Boolean, Int> {
            var leftCorrectness = true
            var leftHeight = 0

            if (this.left != null) {
                val tmp = (this.left as AVLNode).verifyAVL()
                leftCorrectness = tmp.first
                leftHeight = tmp.second
            }

            var rightCorrectness = true
            var rightHeight = 0

            if (this.right != null) {
                val tmp = (this.right as AVLNode).verifyAVL()
                rightCorrectness = tmp.first
                rightHeight = tmp.second
            }

            return Pair(leftCorrectness
                    && rightCorrectness
                    && abs(rightHeight - leftHeight) < 2
                    , max(rightHeight, leftHeight) + 1)
        }
    }

    override fun createNode(key: K, value: V, parent: Node?): Node {
        return AVLNode(key, value, parent)
    }

    override fun innerInsert(key: K, value: V): Node {
        val oldSize = size
        val inserted = super.innerInsert(key, value)

        if (size == oldSize || size == 1) {
            return inserted
        }

        var curNode = inserted as AVLNode
        curNode.parent?.let { (curNode.parent as AVLNode).updateHeight() }
        var son = curNode

        while (curNode.parent != null) {
            val dad = curNode.parent as AVLNode
            dad.updateHeight()
            val subtreeHeightDifference = abs(
                    (if (dad.right != null) (dad.right as AVLNode).height else 0)
                            - (if (dad.left != null) (dad.left as AVLNode).height else 0))

            var next = dad
            var nextSon = curNode

            if (subtreeHeightDifference > 1) {
                if (curNode == dad.left) {
                    next = curNode
                    nextSon = son

                    if (son == curNode.right) {
                        curNode.rotateLeft()
                        next = son
                        nextSon = curNode
                    }

                    dad.rotateRight()
                    dad.updateHeight()
                    curNode.updateHeight()
                    son.updateHeight()
                }
                else {
                    next = curNode
                    nextSon = son

                    if (son == curNode.left) {
                        curNode.rotateRight()
                        next = son
                        nextSon = curNode
                    }

                    dad.rotateLeft()
                    dad.updateHeight()
                    curNode.updateHeight()
                    son.updateHeight()
                }
            }

            son = nextSon
            curNode = next
        }

        return inserted
    }

    override fun print() {
        root?.let { (root as AVLNode).print(0, 0) }
    }

    fun isAVLTree(): Boolean {
        return root == null || (root as AVLNode).verifyAVL().first
    }
}