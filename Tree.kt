interface Tree<K : Comparable<K>, V> {
    fun find(key : K) : Pair<K, V>?
    fun insert(key : K, value : V)
}
