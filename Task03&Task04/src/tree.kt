package tree

interface Tree<Key : Comparable<Key>, Data> {

    fun find(key : Key): Pair<Key, Data>?

    fun insert(key: Key, data: Data)

}