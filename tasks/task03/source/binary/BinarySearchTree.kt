package binary

abstract class BinarySearchTree<Key : Comparable<Key>, Value, Info>(
    private val balancer: BinaryTreeBalancer<Info>,
    private val defaultInfo: Info
) : Iterable<Pair<Key, Value>> {

    private var root: BinarySearchTreeNode<Key, Value, Info>? = null

    var size = 0
        private set

    override fun iterator() = BinarySearchTreeIterator(root)

    operator fun get(key: Key): Value? {
        var node = root

        while (node != null) {
            val comparisonResult = key.compareTo(node.key)

            node = when {
                comparisonResult < 0 -> node.left
                comparisonResult > 0 -> node.right
                else -> return node.value
            }
        }

        return null
    }

    operator fun set(key: Key, value: Value): Value? {
        var wasInserted = false
        val root = root ?: BinarySearchTreeNode(key, value, defaultInfo).also {
            root = it
            wasInserted = true
        }

        var node = root

        while (!wasInserted) {
            val comparisonResult = key.compareTo(node.key)

            node = when {
                comparisonResult < 0 -> node.left ?: BinarySearchTreeNode(key, value, defaultInfo).also {
                    node.left = it
                    wasInserted = true
                }
                comparisonResult > 0 -> node.right ?: BinarySearchTreeNode(key, value, defaultInfo).also {
                    node.right = it
                    wasInserted = true
                }
                else -> return node.value.also { node.value = value }
            }
        }

        size++
        balancer.balance(node)

        while (this.root!!.parent != null) {
            this.root = this.root!!.parent
        }

        return null
    }
}
