interface Tree<K : Comparable<K>, V>{
    fun insert(key : K, value : V)
    fun find(key : K) : Pair<K, V>?
}