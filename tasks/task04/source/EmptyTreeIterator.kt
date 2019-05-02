class EmptyTreeIterator<V> : Iterator<V> {

    override fun hasNext() = false

    override fun next() = null!!
}
