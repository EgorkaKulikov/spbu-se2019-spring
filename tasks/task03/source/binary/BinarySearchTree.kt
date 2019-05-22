package binary

interface SearchData<Key, Value> {
    val key: Key
    var value: Value
}

class BinarySearchTree<Key : Comparable<Key>, Value, Data : SearchData<Key, Value>>(
    private val balancer: BinaryTreeBalancer<Data>,
    private val creator: (Key, Value) -> Data
) : Iterable<SearchData<Key, Value>> {

    private var root: BinaryNode<Data>? = null

    var size = 0
        private set

    fun getRunner() = BinaryTreeRunner(root ?: throw IllegalStateException("Tree is empty"))

    override fun iterator() = BinaryTreeIterator(root) { it }

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

    operator fun set(key: Key, value: Value): Value? {
        var wasInserted = false
        val root = root ?: BinaryNode(creator(key, value)).also {
            root = it
            wasInserted = true
        }

        var node = root

        while (!wasInserted) {
            val data = node.data
            val comparisonResult = key.compareTo(data.key)

            node = when {
                comparisonResult < 0 -> node.left ?: let {
                    BinaryNode(creator(key, value)).also {
                        node.left = it
                        wasInserted = true
                    }
                }
                comparisonResult > 0 -> node.right ?: let {
                    BinaryNode(creator(key, value)).also {
                        node.right = it
                        wasInserted = true
                    }
                }
                else -> return data.value.also { data.value = value }
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
