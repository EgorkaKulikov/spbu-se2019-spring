import java.lang.Exception
import kotlin.math.min as min
import kotlin.math.max as max

const val MAX_BRANCH_LENGTH = 100
const val MAX_N = 2000
const val INF = MAX_BRANCH_LENGTH * MAX_N

fun Int.length() : Int = this.toString().length

class SymMatrix(val N : Int) {
    private val table : MutableMap<Int, Int> = mutableMapOf()
    operator fun get(i: Int, j: Int) : Int? {
        val key = this.N * min(i, j) + max(i, j)
        return if (key in this.table.keys) table[key] else null
    }
    operator fun set(i : Int, j : Int, value : Int) {
        val key = this.N * min(i, j) + max(i, j)
        this.table[key] = value
    }
    override fun toString() : String {
        var result = ""
        val maxIntLength = this.table.maxBy {it.value.length()}!!.value.length()
        for (i in 1..this.N) {
            for (j in 1..this.N) {
                result += " ".repeat(maxIntLength - (this[i, j] ?: 0).length()) + "${this[i, j] ?: 0} "
            }
            result += '\n'
        }
        return result
    }
}

class ArrayFrom1(val N : Int, private val funGenerator : (Int) -> Int?) {
    private var table = Array(N) {funGenerator(it + 1)}
    operator fun get(i: Int) : Int? = this.table[i - 1]
    operator fun set(i : Int, value : Int) {this.table[i - 1] = value}
    override operator fun equals(other : Any?) : Boolean {
        if (other !is ArrayFrom1) return false
        if (this.N != other.N) return false
        for (i in 1..this.N) {
            if (this[i] != other[i]) return false
        }
        return true
    }
    override fun hashCode(): Int {
        var hash = this.N * 1313
        for (i in 1..this.N) {
            hash = (hash xor (this[i] ?: 131313 * (i + 13))) shl 1
        }
        return hash
    }
    override fun toString() : String {
        var result = ""
        for (i in 1..this.N) result += "$i: ${this[i]}\n"
        return result
    }
}

class Heap {
    private val table = sortedSetOf<Pair<Int, Int>>(Comparator { it, other ->
        if (it.first == other.first)
            it.second - other.second
        else
            it.first - other.first})
    fun push(key : Int, value : Int) {this.table.add(Pair(key, value))}
    fun pop() : Int {
        val result = this.table.first()!!
        this.table.remove(result)
        return result.second
    }
    fun remove(key : Int, value : Int) {this.table.remove(Pair(key, value))}
    fun size() : Int = this.table.size
}

fun autoCreateGraph(N : Int, M : Int) : SymMatrix? {
    if (N * (N - 1) < M * 2)
        return null
    val resGraph = SymMatrix(N)
    val pairs = mutableListOf<Pair<Int, Int>>()
    var k = 0
    for (i in 1 ..N) {
        for (j in i + 1..N) {
            pairs.add((0..k++).random(), Pair(i, j))
        }
    }
    for (i in 0 until M) {
        val (x, y) = pairs[i]
        resGraph[x, y] = (1..MAX_BRANCH_LENGTH).random()
    }
    return resGraph
}

fun statisticGraph(graph : SymMatrix) {
    println("Статистика графа:")
    var minBranch = MAX_BRANCH_LENGTH
    var maxBranch = 0
    var numBranch = 0
    for (i in 1..graph.N) {
        for (j in i + 1..graph.N) {
            val value = graph[i, j]
            if (value != null) {
                minBranch = min(minBranch, value)
                maxBranch = max(maxBranch, value)
                numBranch++
            }
        }
    }
    println("Граф состоит из ${graph.N} ${if (graph.N == 1) "вершины" else "вершин"} " +
            "и $numBranch ${if (numBranch == 1) "ребра" else "рёбер"}.")
    if (numBranch != 0)
    {
        println("Минимальная длина ребра = $minBranch")
        println("Максимальная длина ребра = $maxBranch")
    }
}

fun algoDijkstra(graph : SymMatrix, start_vertex : Int) : ArrayFrom1 {
    val length = ArrayFrom1(graph.N) {if (it == start_vertex) 0 else null}
    val p = ArrayFrom1(graph.N) {null}
    val heap = Heap()
    heap.push(0, start_vertex)
    while (heap.size() > 0) {
        val v = heap.pop()
        for (to in 1..graph.N) {
            if (graph[v, to] != null) {
                if (length[to] == null) {
                    length[to] = length[v]!! + graph[v, to]!!
                    p[to] = v
                    heap.push(length[to]!!, to)
                }
                else if (length[to]!! > length[v]!! + graph[v, to]!!) {
                    heap.remove(length[to]!!, to)
                    length[to] = min(length[to]!!, length[v]!! + graph[v, to]!!)
                    p[to] = v
                    heap.push(length[to]!!, to)
                }
            }
        }
    }
    return length
}

fun algoBellmanFord(graph : SymMatrix, start_vertex : Int) : ArrayFrom1 {
    val length = ArrayFrom1(graph.N) {if (it == start_vertex) 0 else null}
    val p = ArrayFrom1(graph.N) {null}
    var lengthUpdate: Boolean
    do {
        lengthUpdate = false
        for (v in 1..graph.N) {
            for (u in v + 1..graph.N) {
                if (graph[v, u] != null) {
                    if (length[v] != null && length[v]!! + graph[v, u]!! < length[u] ?: INF) {
                        length[u] = length[v]!! + graph[v, u]!!
                        p[u] = v
                        lengthUpdate = true
                    }
                    if (length[u] != null && length[u]!! + graph[v, u]!! < length[v] ?: INF) {
                        length[v] = length[u]!! + graph[v, u]!!
                        p[v] = u
                        lengthUpdate = true
                    }
                }
            }
        }
    } while (lengthUpdate)
    return length
}

fun main(args: Array<String>) {
    var correct_input = false
    var n: Int? = null
    var m: Int? = null
    while (!correct_input) {
        println("Введите желаемое количество вершин и рёбер графа: ")
        val string_input = readLine()
        if (string_input == null) {
            println("Вы ничего не ввели!")
            continue
        }
        val params_input = string_input.split(' ')
        if (params_input.size != 2) {
            println("Вы ввели неверное количество параметров!")
            continue
        }
        try {
            n = params_input[0].toInt()
        }
        catch (NumberFormatException: Exception) {
            println("Первый параметр - не число!")
            continue
        }
        if (n < 1 || n > MAX_N) {
            println("Первый параметр некорректный! (меньше 0 или больше $MAX_N)")
            continue
        }
        try {
            m = params_input[1].toInt()
        }
        catch (NumberFormatException: Exception) {
            println("Второй параметр - не число!")
            continue
        }
        if (m < 0 || m > n * (n - 1) / 2) {
            println("Второй параметр некорректный! (меньше 0 или больше ${n * (n - 1) / 2})")
            continue
        }
        correct_input = true
    }
    //val (n, m) = readLine()!!.split(' ').map(String::toInt)
    val graph = autoCreateGraph(n!!, m!!)
    if (graph == null) {
        println("НЕ удалось создать граф из $n ${if (n == 1) "вершины" else "вершин"} " +
                "и $m ${if (m == 1) "ребра" else "рёбер"}.")
        return
    }
    print("Граф:\n$graph")
    statisticGraph(graph)
    correct_input = false
    var start_vertex: Int? = null
    while (!correct_input) {
        println("Введите номер стартовой вершины:")
        val string_input = readLine()
        if (string_input == null) {
            println("Вы ничего не ввели!")
            continue
        }
        try {
            start_vertex = string_input.toInt()
        }
        catch (NumberFormatException: Exception) {
            println("Вы ввели не число!")
            continue
        }
        if (start_vertex < 1 || start_vertex > n) {
            println("Неверный номер вершины! (должен быть от 1 до $n)")
            continue
        }
        correct_input = true
    }
    val lengthD = algoDijkstra(graph, start_vertex!!)
    val lengthBF = algoBellmanFord(graph, start_vertex)
    println("Ответы двух алгоритмов ${if (lengthD == lengthBF) "совпали." else "НЕ СОВПАЛИ!!!"}")
    print("Длины путей от вершины с номером $start_vertex до всех остальных:\n$lengthD")
}

/*  10 45
    Граф состоит из 10 вершин и 45 рёбер.
    Минимальная длина ребра = 2
    Максимальная длина ребра = 97
    0 54 87 86 49 68 49 22 14 46
    54 0 20 25 90 7 95 96 54 97
    87 20 0 45 14 82 83 82 33 54
    86 25 45 0 74 71 9 28 21 69
    49 90 14 74 0 7 97 54 35 86
    68 7 82 71 7 0 84 54 29 2
    49 95 83 9 97 84 0 24 5 8
    22 96 82 28 54 54 24 0 27 31
    14 54 33 21 35 29 5 27 0 92
    46 97 54 69 86 2 8 31 92 0
    7
    ArrayFrom1: 19 17 37 9 17 10 0 24 5 8
    ArrayFrom1: 19 17 31 9 17 10 0 24 5 8
    Ответы двух алгоритмов НЕ совпали!!!*/
