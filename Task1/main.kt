import kotlin.math.min

const val MAX_BRANCH_LENGTH = 100
const val MAX_N = 2000
const val INF = MAX_BRANCH_LENGTH * MAX_N
const val NULL_BRANCH = "*"

val Int?.length: Int
    get() = if (this == null)
        NULL_BRANCH.length
    else
        toString().length

fun arrayBeginningFrom1Of(size: Int, init: (Int) -> Int?): Array<Int?> = Array(size + 1, init)

fun equalsElements(firstArray: Array<Int?>, secondArray: Array<Int?>): Boolean {
    if (firstArray.size != secondArray.size)
        return false
    for (i in 0 until firstArray.size)
        if (firstArray[i] != secondArray[i])
            return false
    return true
}

fun autoCreateGraph(N : Int, M : Int) : SymMatrix {
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

fun statisticGraph(graph: SymMatrix) {
    println("Статистика графа:")
    print("Граф состоит из ${graph.N} ")
    if (graph.N == 1)
        print("вершины")
    else
        print("вершин")
    print(" и ${graph.branches.size} ")
    if (graph.branches.size == 1)
        print("ребра")
    else
        print("рёбер")
    println()
    if (!graph.branches.isEmpty())
    {
        println("Минимальная длина ребра = ${graph.branches.minBy { it.value }!!.value}")
        println("Максимальная длина ребра = ${graph.branches.maxBy { it.value }!!.value}")
    }
}

fun algoDijkstra(graph: SymMatrix, start_vertex: Int): Array<Int?> {
    val length = arrayBeginningFrom1Of(graph.N) {if (it == start_vertex) 0 else null}
    val heap = sortedSetOf<SortedVertex>()
    heap.add(SortedVertex(start_vertex, 0))
    while (!heap.isEmpty()) {
        val v = heap.first().vertex
        heap.remove(heap.first())
        for (to in graph.adjacentVertexes(v)) {
            if (length[to] == null) {
                length[to] = length[v]!! + graph[v, to]!!
                heap.add(SortedVertex(to, length[to]!!))
            }
            else if (length[to]!! > length[v]!! + graph[v, to]!!) {
                heap.remove(SortedVertex(to, length[to]!!))
                length[to] = min(length[to]!!, length[v]!! + graph[v, to]!!)
                heap.add(SortedVertex(to, length[to]!!))
            }
        }
    }
    return length
}

fun algoBellmanFord(graph: SymMatrix, start_vertex: Int): Array<Int?> {
    val length = arrayBeginningFrom1Of(graph.N) {if (it == start_vertex) 0 else null}
    var lengthUpdate: Boolean
    do {
        lengthUpdate = false
        for (branch in graph.branches) {
            val (v, u) = branch.key
            val branchLength = branch.value
            if (length[v] != null && length[v]!! + branchLength < length[u] ?: INF) {
                length[u] = length[v]!! + branchLength
                lengthUpdate = true
            }
            if (length[u] != null && length[u]!! + branchLength < length[v] ?: INF) {
                length[v] = length[u]!! + branchLength
                lengthUpdate = true
            }
        }
    } while (lengthUpdate)
    return length
}

fun main(args: Array<String>) {
    var correctInput = false
    var n: Int? = null
    var m: Int? = null
    while (!correctInput) {
        println("Введите желаемое количество вершин и рёбер графа: ")
        val stringInput = readLine()
        if (stringInput == null) {
            println("Вы ничего не ввели!")
            continue
        }
        val paramsInput = stringInput.split(' ')
        if (paramsInput.size != 2) {
            println("Вы ввели неверное количество параметров!")
            continue
        }
        try {
            n = paramsInput[0].toInt()
        }
        catch (e: NumberFormatException) {
            println("Первый параметр - не число!")
            continue
        }
        if (n < 1 || n > MAX_N) {
            println("Первый параметр некорректный! (меньше 1 или больше $MAX_N)")
            continue
        }
        try {
            m = paramsInput[1].toInt()
        }
        catch (e: NumberFormatException) {
            println("Второй параметр - не число!")
            continue
        }
        if (m < 0 || m > n * (n - 1) / 2) {
            println("Второй параметр некорректный! (меньше 0 или больше ${n * (n - 1) / 2})")
            continue
        }
        correctInput = true
    }
    val graph = autoCreateGraph(n!!, m!!)
    print("Граф:\n$graph")
    statisticGraph(graph)
    correctInput = false
    var startVertex: Int? = null
    while (!correctInput) {
        println("Введите номер стартовой вершины:")
        val stringInput = readLine()
        if (stringInput == null) {
            println("Вы ничего не ввели!")
            continue
        }
        try {
            startVertex = stringInput.toInt()
        }
        catch (e: NumberFormatException) {
            println("Вы ввели не число!")
            continue
        }
        if (startVertex < 1 || startVertex > n) {
            println("Неверный номер вершины! (должен быть от 1 до $n)")
            continue
        }
        correctInput = true
    }
    val lengthD = algoDijkstra(graph, startVertex!!)
    val lengthBF = algoBellmanFord(graph, startVertex)
    println("Ответы двух алгоритмов ${if (equalsElements(lengthD, lengthBF)) "совпали." else "НЕ СОВПАЛИ!!!"}")
    println("Длины путей от вершины с номером $startVertex:")
    for (i in 1..graph.N)
        if (i != startVertex && lengthD[i] != null)
            println("($startVertex -> $i) = ${lengthD[i]}")
}
