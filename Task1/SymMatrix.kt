import kotlin.math.max
import kotlin.math.min

class SymMatrix(val N : Int) {
    private val table: MutableMap<Int, Int> = mutableMapOf()
    private fun getKey(i: Int, j: Int): Int = N * min(i, j) + max(i, j)
    operator fun get(i: Int, j: Int) : Int? = table[getKey(i, j)]
    operator fun set(i : Int, j : Int, value : Int) { table[getKey(i, j)] = value }
    override fun toString() : String {
        var result = ""
        val maxIntLength = if (table.isEmpty())
            NULL_BRANCH.length
        else
            max(this.table.maxBy {it.value.length}!!.value.length, NULL_BRANCH.length)
        for (i in 1..N) {
            for (j in 1..N) {
                result += " ".repeat(maxIntLength - this[i, j].length) + "${this[i, j] ?: NULL_BRANCH} "
            }
            result += '\n'
        }
        return result
    }
    val branches: Map<Pair<Int, Int>, Int>
        get() {
            val branches = mutableMapOf<Pair<Int, Int>, Int>()
            for (i in 1..N) {
                for (j in i..N) {
                    if (getKey(i, j) in table.keys)
                        branches[Pair(i, j)] = table[getKey(i, j)]!!
                }
            }
            return branches.toMap()
        }
    fun adjacentVertexes(vertex: Int): Set<Int>
    {
        val adjacentVertexes = mutableSetOf<Int>()
        for (pairVertexes in branches.filter { it.key.second == vertex}.keys)
            adjacentVertexes.add(pairVertexes.first)
        for (pairVertexes in branches.filter { it.key.first == vertex}.keys)
            adjacentVertexes.add(pairVertexes.second)
        return adjacentVertexes.toSet()
    }
}
