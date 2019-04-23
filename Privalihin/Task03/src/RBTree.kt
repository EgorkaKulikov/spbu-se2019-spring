class RBTree<T, K : Comparable<K>> : BalancedSearchTree<T, K>() {
    public inner class RBNode(_value: T, _key: K, _parent: Node?) : Node(_value, _key, _parent) {
        var color = 1 // 1 is red, 0 is black.
        // Maybe I should replace it with an enum class
    }

    protected override fun createNode(value: T, key: K, parent: Node?): Node {
        return RBNode(value, key, parent)
    }

    override fun insert(value: T, key: K): Node {
        var node = super<BalancedSearchTree>.insert(value, key) as RBNode//!!!!
        val inserted = node
        while (node.parent != null) {
            val dad = node.parent as RBNode
            val u = node.uncle() as RBNode?

            when {

                (dad.color == 0) -> {
                    return inserted
                }

                (u != null && u.color == 1) -> {
                    dad.color = 0
                    u.color = 0
                    val grandDad = dad.parent as RBNode // grandparent exists because uncle exists
                    grandDad.color = 1
                    node = grandDad
                }

                else -> {
                    val grandDad = dad.parent as RBNode // grandparent exists because parent is red
                    // and therefore is not root

                    if (node ==  dad.right && dad == grandDad.left) {
                        dad.rotateLeft()
                        node = node.left as RBNode // safe because we just rotated the tree
                    }
                    else if (node ==  dad.left && dad == grandDad.right) {
                        dad.rotateRight()
                        node = node.right as RBNode // safe because we just rotated the tree
                    }

                    val newDad = node.parent as RBNode
                    val newGrandDad = node.grandparent() as RBNode
                    if (node == newDad.left) {
                        newGrandDad.rotateRight()
                    }
                    else {
                        newGrandDad.rotateLeft()
                    }
                    newDad.color = 0
                    newGrandDad.color = 1
                    return inserted
                }
            }
        }

        node.color = 0
        return inserted
    }

    public fun RBNode.print() {
        print("(")
        this.left?.let { (this.left as RBNode).print() }
        print(")")
        print("<$key $value $color>")
        print("(")
        this.right?.let { (this.right as RBNode).print() }
        print(")")
    }

    public fun print() {
        (root as RBNode).print()
    }
}