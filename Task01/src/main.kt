import java.util.*

const val INF = Int.MAX_VALUE

class MyGraph(private val N: Int, private val M: Int) {
    var graphMatrix: Array<Array<Int>> = emptyArray()

    data class Edge(val from: Int, val to: Int, val cost: Int)

    var minEdge = Edge(-1, -1, INF)
    var maxEdge = Edge(-1, -1, -INF)
    val edges: MutableList<Edge> = MutableList(0) { Edge(0, 0, 0) }

    init {
        graphMatrix = Array(N + 1) { Array(N + 1) { 0 } }
        val setOfEdges: HashSet<Pair<Int, Int>> = HashSet()
        while (setOfEdges.size != M) {
            val beginVertex = (1 until N).random()
            val endVertex = ((beginVertex + 1)..N).random()
            if (setOfEdges.add(Pair(beginVertex, endVertex))) {
                val edgeLength = (1..100).random()
                graphMatrix[beginVertex][endVertex] = edgeLength
                graphMatrix[endVertex][beginVertex] = edgeLength
                edges.add(Edge(beginVertex, endVertex, edgeLength))
                edges.add(Edge(endVertex, beginVertex, edgeLength))
            }
        }
        minEdge = edges.minBy { it.cost } ?: minEdge
        maxEdge = edges.maxBy { it.cost } ?: maxEdge
    }

    fun getDijkstraShortestPath(K: Int): Array<Int> {
        val len: Array<Int> = Array(N + 1) { INF }
        val used: Array<Boolean> = Array(N + 1) { false }
        len[K] = 0
        for (i in 1..N) {
            var v = -1
            for (j in 1..N) {
                if (!used[j] && (v == -1 || len[j] < len[v])) {
                    v = j
                }
            }
            if (len[v] == INF) break
            used[v] = true
            for (j in 1..N) {
                if (graphMatrix[v][j] == 0) continue
                if (len[v] + graphMatrix[v][j] < len[j]) {
                    len[j] = len[v] + graphMatrix[v][j]
                }
            }
        }
        return len
    }

    fun getFordBellmanShortestPath(K: Int): Array<Int> {
        val len: Array<Int> = Array(N + 1) { INF }
        len[K] = 0
        while (true) {
            var proceed = false
            for (edge in edges) {
                if (len[edge.from] < INF) {
                    if (len[edge.to] > len[edge.from] + edge.cost) {
                        len[edge.to] = len[edge.from] + edge.cost
                        proceed = true
                    }
                }
            }
            if (!proceed) break
        }
        return len
    }

    fun getLevitShortestPath(K: Int): Array<Int> {
        val len: Array<Int> = Array(N + 1) { INF }
        val id: Array<Int> = Array(N + 1) { 0 }
        val view: Deque<Int> = LinkedList()
        len[K] = 0
        view.addLast(K)
        while (view.isNotEmpty()) {
            val v = view.first
            view.removeFirst()
            id[v] = 1
            for (j in 1..N) {
                if (graphMatrix[v][j] == 0) continue
                if (len[j] > len[v] + graphMatrix[v][j]) {
                    len[j] = len[v] + graphMatrix[v][j]
                    when (id[j]) {
                        0 -> view.addLast(j)
                        1 -> view.addFirst(j)
                    }
                    id[j] = 1
                }
            }
        }
        return len
    }

    fun printGraphStatistics(K: Int) {
        println("There are $N vertexes in the graph.")
        println("There are $M edges in the graph.")
        println("The lightest edge in graph is ( ${minEdge.from}, ${minEdge.to} ) and it's weight is ${minEdge.cost}.")
        println("The heaviest edge in graph is ( ${maxEdge.from}, ${maxEdge.to} ) and it's weight is ${maxEdge.cost}.")
        println("Shortest paths from $K:")
        val len = getLevitShortestPath(K)
        for (v in 1..N) {
            if (v == K) continue
            if (len[v] == INF) {
                println("Where is no way from $K to $v")
                continue
            }
            println("The length of shortest path from $K to $v is ${len[v]}")
        }
    }
}

fun main() {
    println("Input number of vertices and number of edges of graph:")
    val (N, M) = readLine()!!.split(' ').map(String::toInt)
    val graph = MyGraph(N, M)
    for (i in 1..N) {
        for (j in 1..N) {
            print("${graph.graphMatrix[i][j]} ")
        }
        println()
    }
    println("Enter the number of the graph vertex you are interested in: ")
    val k = readLine()!!.toInt()
    graph.printGraphStatistics(k)
}