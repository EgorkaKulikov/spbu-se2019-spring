import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class AVLTree<T, K : Comparable<K>> : BalancedSearchTree<T, K>() {
    public inner class AVLNode(_value: T, _key: K, _parent: Node?) : Node(_value, _key, _parent) {
        var flag = 0

    }

    protected override fun createNode(value: T, key: K, parent: Node?): Node {
        return AVLNode(value, key, parent)
    }

    override fun insert(value: T, key: K): Node {
        val oldSize = size
        val inserted = super<BalancedSearchTree>.insert(value, key)

        if (size == oldSize) {
            return inserted
        }

        var curNode = inserted as AVLNode
        var son = curNode
        while (curNode.parent != null) {
            val dad = curNode.parent as AVLNode

            if (dad.left == curNode) {
                dad.flag++
            }
            else {
                dad.flag--
            }

            if (dad.flag == 0) {
                break
            }

            if (abs(dad.flag) > 1) {
                if (curNode == dad.left) {
                    if (son == curNode.right) {
                        curNode.rotateLeft()
                        curNode.flag += min(0, son.flag) - 1
                        son.flag += max(0, curNode.flag) + 1
                    }

                    dad.rotateRight()
                    dad.flag += min(0, curNode.flag) - 1
                    curNode.flag += max(0, dad.flag) + 1
                }
                else {
                    if (son == curNode.left) {
                        curNode.rotateRight()
                        curNode.flag += max(0, son.flag) + 1
                        son.flag += min(0, curNode.flag) - 1
                    }

                    dad.rotateRight()
                    dad.flag += max(0, curNode.flag) + 1
                    curNode.flag += min(0, dad.flag) - 1
                }

                break
            }

            son = curNode
            curNode = dad
        }

        return inserted
    }

    public fun AVLNode.print() {
        print("(")
        this.left?.let { (this.left as AVLNode).print() }
        print(")")
        print("<$key $value $flag>")
        print("(")
        this.right?.let { (this.right as AVLNode).print() }
        print(")")
    }

    public fun print() {
        (root as AVLNode).print()
    }
}