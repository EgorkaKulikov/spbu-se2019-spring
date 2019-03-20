import kotlin.math.max
import kotlin.math.min

const val MAX_WEIGHT = 101
const val MIN_WEIGHT = 1

fun createGraph(countOfVertices: Int, countOfEdges: Int): Array<IntArray> {
    val graph = Array(countOfVertices) { IntArray(countOfVertices) { 0 } }
    var countOfAddedEdges = 0
    while (countOfAddedEdges < countOfEdges) {
        val from = (0 until countOfVertices - 1).random()
        val to = (from + 1 until countOfVertices).random()
        if (graph[from][to] == 0) {
            val weight = (MIN_WEIGHT until MAX_WEIGHT).random()
            graph[from][to] = weight
            graph[to][from] = weight
            ++countOfAddedEdges
        }
    }
    return graph
}

fun printStatistic(graph: Array<IntArray>, countOfEdges: Int) {
    val countOfVertices = graph.size
    var minWeightOfEdge = MAX_WEIGHT
    var maxWeightOfEdge = MIN_WEIGHT
    for (i in 0 until countOfVertices - 1) {
        for (j in i + 1 until countOfVertices) {
            if (graph[i][j] != 0) {
                minWeightOfEdge = min(graph[i][j], minWeightOfEdge)
                maxWeightOfEdge = max(graph[i][j], maxWeightOfEdge)
            }
        }
    }
    print("Count of vertices: $countOfVertices\n" +
            "Count of edges: $countOfEdges\n" +
            "Max weight: $maxWeightOfEdge\n" +
            "Min weight: $minWeightOfEdge\n")
}

fun printGraph(graph: Array<IntArray>, n:Int = graph.size) {
    for (i in 0 until n) {
        for (j in 0 until n) {
            print(graph[i][j])
            print(' ')
        }
        print('\n')
    }
}

fun fordBellman(graph: Array<IntArray>, start: Int): IntArray {
    val n = graph.size
    var distance = IntArray(n) { MAX_WEIGHT }
    distance[start] = 0
    for (k in 0 until n - 1) {
        for (i in 0 until n) {
            for (j in 0 until n) {
                if (graph[i][j] != 0) {
                    distance[j] = min(distance[j], distance[i] + graph[i][j])
                }
            }
        }
    }
    return distance
}

fun dijkstra(graph: Array<IntArray>, start: Int): Array<Int> {
    val n = graph.size
    val distance = Array(n) { MAX_WEIGHT }
    val used = Array(n) { false }
    distance[start] = 0
    for (i in 0 until n) {
        var curVrtx = -1
        for (v in 0 until n) {
            if (!used[v] && (curVrtx == -1 || distance[v] < distance[curVrtx])) {
                curVrtx = v
            }
        }
        if (distance[curVrtx] == MAX_WEIGHT + 1) {
            break
        }
        used[curVrtx] = true
        for (v in 0 until n) {
            if (graph[curVrtx][v] != 0) {
                distance[v] = min(distance[v], distance[curVrtx] + graph[curVrtx][v])
            }
        }
    }
    return distance
}

fun main () {
    val (n, m) = readLine()!!.split(' ').map { it.toInt() }
    val graph = createGraph(n, m)
    var algorithmsAreEqual = true

    printGraph(graph)
    printStatistic(graph, m)
    print("Enter a vertex count from 1..$n\n")

    val k = readLine()!!.toInt()
    val distDijkstra = dijkstra(graph, k - 1)
    val distFordBellman = fordBellman(graph, k - 1)

    for (i in 0 until n) {
        if (distDijkstra[i] != distFordBellman[i]) {
            algorithmsAreEqual = false
            print("Lengths of path from $k to ${i + 1} are different!")
            break
        }
        if (distDijkstra[i] != MAX_WEIGHT && distDijkstra[i] != 0) {
            print("The shortest distance from $k to ${i + 1} is ${distDijkstra[i]}\n")
        } else {
            print("There's no path from $k to ${i + 1}\n")
        }
    }

    if (algorithmsAreEqual) {
        print("\nAlgorithms seem to be equal\n")
    } else {
        print("\nThere's a mistake in one of the algorithms!\n")
    }
}
