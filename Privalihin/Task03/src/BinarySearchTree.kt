open class BinarySearchTree<K : Comparable<K>, V> {
    public open inner class Node(var key: K, var value: V, var parent: Node?) {
        var left: Node? = null
        var right: Node? = null

        open fun print(indentation: Int, side: Int) {
            this.left?.print(indentation + 1, -1)

            for (i in 1..indentation) {
                print(" ")
            }

            when(side) {
                -1 -> print("/")
                1 -> print("\\")
            }
            println("$key $value")

            this.right?.print(indentation + 1, 1)
        }
    }

    var size = 0
        protected set

    protected var root: Node? = null

    open fun find(key: K): V? {
        var curNode: Node? = this.root

        while (curNode != null) {
            when {
                (curNode.key == key) -> return curNode.value
                (curNode.key > key) -> curNode = curNode.left
                else -> curNode = curNode.right
            }
        }

        return null
    }

    protected open fun createNode(key: K, value: V, parent: Node?): Node {
        return Node(key, value, parent)
    }

    open fun insert(key: K, value: V): Node {

        if (this.root == null) {
            this.root = createNode(key, value, null)
            size++
            return this.root!!
        }

        var curNode = this.root!!

        while (true) {
            when {
                (curNode.key == key) -> {
                    curNode.value = value
                    return curNode
                }

                (curNode.key > key) -> {
                    if (curNode.left == null) {
                        curNode.left = createNode(key, value, curNode)
                        size++
                        return curNode.left!!
                    }

                    curNode = curNode.left!!
                }
                (curNode.key < key) -> {
                    if (curNode.right == null) {
                        curNode.right = createNode(key, value, curNode)
                        size++
                        return curNode.right!!
                    }

                    curNode = curNode.right!!
                }
            }
        }
    }

    open fun print() {
        root?.print(0, 0)
    }
}