class SortedVertex(val vertex: Int, val length: Int): Comparable<SortedVertex> {
    override operator fun compareTo(other: SortedVertex): Int {
        if (this.length == other.length)
            return this.vertex - other.vertex
        return this.length - other.length
    }
}
