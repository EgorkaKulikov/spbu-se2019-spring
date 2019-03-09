import java.util.*

const val INF = Int.MAX_VALUE

class MyGraph(private val N: Int, private val M: Int) {
    var graphMatrix : Array<Array<Int>> = emptyArray()

    data class Edge(val from: Int, val to: Int, val cost: Int)
    var minEdge = Edge(-1, -1, INF)
    var maxEdge = Edge(-1, -1, -INF)
    val edges : MutableList<Edge> = MutableList(0)  { Edge(0, 0, 0) }

    init {
        graphMatrix = Array(N + 1) { Array(N + 1) { 0 } }
        val setOfEdges : HashSet<Pair<Int, Int>> = HashSet()
        while (setOfEdges.size != M) {
            var beginVertex = (1..N).random()
            var endVertex = (1..N).random()
            if (beginVertex > endVertex) {
                val tmp = beginVertex
                beginVertex = endVertex
                endVertex = tmp
            }
            if (beginVertex != endVertex) {
                if (setOfEdges.add(Pair(beginVertex, endVertex))) {
                    val edgeLength = (1..100).random()
                    graphMatrix[beginVertex][endVertex] = edgeLength
                    graphMatrix[endVertex][beginVertex] = edgeLength
                    edges.add(Edge(beginVertex, endVertex, edgeLength))
                    edges.add(Edge(endVertex, beginVertex, edgeLength))
                }
            }
        }
        minEdge = edges.minBy { it.cost }!!
        maxEdge = edges.maxBy { it.cost }!!
    }

    fun getDijkstraShortestPath(K: Int)
            : Pair<Array<Int>, Array<Int>> {
        val len : Array<Int> = Array(N + 1) { INF }
        val parent : Array<Int> = Array(N + 1) { 0 }
        val used : Array<Boolean> = Array(N + 1) { false }
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
                    parent[j] = v
                }
            }
        }
        return Pair(len, parent)
    }

    fun getFordBellmanShortestPath(K: Int)
            : Pair<Array<Int>, Array<Int>> {
        val len : Array<Int> = Array(N + 1) { INF }
        val parent : Array<Int> = Array(N + 1) { 0 }
        len[K] = 0
        while (true) {
            var proceed = false
            for (edge in edges) {
                if (len[edge.from] < INF) {
                    if (len[edge.to] > len[edge.from] + edge.cost) {
                        len[edge.to] = len[edge.from] + edge.cost
                        parent[edge.to] = edge.from
                        proceed = true
                    }
                }
            }
            if (!proceed) break
        }
        return Pair(len, parent)
    }

    fun getLevitShortestPath(K: Int)
            : Pair<Array<Int>, Array<Int>> {
        val len : Array<Int> = Array(N + 1) { INF }
        val parent : Array<Int> = Array(N + 1) { 0 }
        val id : Array<Int> = Array(N + 1) { 0 }
        val view : Deque<Int> = LinkedList()
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
                    parent[j] = v
                    when (id[j]) {
                        0 -> view.addLast(j)
                        1 -> view.addFirst(j)
                    }
                    id[j] = 1
                }
            }
        }
        return Pair(len, parent)
    }

    fun printGraphStatistics(K : Int = 0) {
        println("Do you want to know the number of vertices of the graph? yes/no")
        var answer = getAnswer()
        if (answer == "yes"){
            println("There are $N vertexes in the graph.")
        }
        println("Do you want to know the number of edges of the graph? yes/no")
        answer = getAnswer()
        if (answer == "yes") {
            println("There are $M edges in the graph.")
        }
        println("Do you want to look at the easiest edge in the graph? yes/no")
        answer = getAnswer()
        if (answer == "yes") {
            println("The lightest edge in graph is ( ${minEdge.from}, ${minEdge.to} ) and it's weight is ${minEdge.cost}.")
        }
        println("Do you want to look at the heaviest edge in the graph? yes/no")
        answer = getAnswer()
        if (answer == "yes") {
            println("The heaviest edge in graph is ( ${maxEdge.from}, ${maxEdge.to} ) and it's weight is ${maxEdge.cost}.")
        }
        if (K != 0) {
            println("Would you like to see the shortest paths from $K? yes/no")
            answer = getAnswer()
            if (answer == "yes") {
                val (len, parent) = getLevitShortestPath(K)
                fun getPath(parent: Array<Int>, K: Int, V: Int): MutableList<Int> {
                    val path: MutableList<Int> = MutableList(0) { 0 }
                    var cur = V
                    while (cur != K) {
                        path.add(cur)
                        cur = parent[cur]
                    }
                    path.add(K)
                    path.reverse()
                    return path
                }
                for (v in 1..N) {
                    if (v == K) continue
                    if (len[v] == INF) {
                        println("Where is no way from $K to $v")
                        continue
                    }
                    val path = getPath(parent, K, v)
                    println("Shortest path from $K to $v is:")
                    for (j in path.indices)
                        print("${path[j]}${if (j == path.size - 1) "\n" else " - "}")
                    println("The length of path is: ${len[v]}")
                }
            }
        }
    }
}

fun getAnswer() : String {
    val input : String? = readLine()
    return when (input) {
        "yes", "no" -> input
        else -> {
            println("Please enter \'yes\' or \'no\': ")
            getAnswer()
        }
    }
}

fun main() {
    println("Input number of vertices and number of edges of graph:")
    val (N, M) = readLine()!!.split(' ').map(String::toInt)
    val graph = MyGraph(N, M)
    println("Would you like to see Graph Adjacency Matrix? yes/no")
    var answer = getAnswer()
    if (answer == "yes") {
        for (i in 1..N) {
            for (j in 1..N) {
                print("${graph.graphMatrix[i][j]} ")
            }
            println()
        }
    }
    println("Enter the number of the graph vertex you are interested in: ")
    val K = readLine()!!.toInt()
    println("Would you like to see Graph Statistics? yes/no")
    answer = getAnswer()
    if (answer == "yes") {
        graph.printGraphStatistics(K)
    }
}