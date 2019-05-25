package binary

interface SearchData<Key, Value> {
    val key: Key
    var value: Value
}

open class BinarySearchTree<Key : Comparable<Key>, Value, VisibleData : SearchData<Key, Value>, Data : VisibleData>(
    private val balancer: (RotatableBinaryNode<Data>) -> Unit,
    private val creator: (Key, Value) -> Data
) : Iterable<VisibleData> {

    private var root: BinaryTreeNode<VisibleData, Data>? = null

    val openRoot: BinaryNode<VisibleData>? get() = root

    var size = 0
        private set

    override fun iterator() = BinaryTreeIterator(root)

    operator fun get(key: Key): Value? {
        var node = root

        while (node != null) {
            val data = node.data
            val comparisonResult = key.compareTo(data.key)

            node = when {
                comparisonResult < 0 -> node.left
                comparisonResult > 0 -> node.right
                else -> return data.value
            }
        }

        return null
    }

    private fun createNode(key: Key, value: Value) = BinaryTreeNode<VisibleData, Data>(creator(key, value))

    operator fun set(key: Key, value: Value): Value? {
        var wasInserted = false
        val root = root ?: createNode(key, value).also {
            root = it
            wasInserted = true
        }

        var node = root

        while (!wasInserted) {
            val data = node.data
            val comparisonResult = key.compareTo(data.key)

            node = when {
                comparisonResult < 0 -> node.left ?: createNode(key, value).also {
                    node.left = it
                    wasInserted = true
                }
                comparisonResult > 0 -> node.right ?: createNode(key, value).also {
                    node.right = it
                    wasInserted = true
                }
                else -> return data.value.also { data.value = value }
            }
        }

        size++
        balancer(node)

        while (this.root!!.parent != null) {
            this.root = this.root!!.parent
        }

        return null
    }
}
