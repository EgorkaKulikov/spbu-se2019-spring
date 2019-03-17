fun buildGraph(numberOfVertexes: Int, numberOfEdges: Int): Array<IntArray> {

    val adjacencyMatrix = Array(numberOfVertexes) { IntArray(numberOfVertexes) }
    val edges: HashSet<Pair<Int, Int>> = HashSet()

    while (edges.size != numberOfEdges) {
        val begin = (1 until numberOfVertexes).random()
        val end = (begin..numberOfVertexes).random()
        if (begin != end) {
            if (edges.add(Pair(begin, end))) {
                val weight = (1..100).random()
                adjacencyMatrix[begin - 1][end - 1] = weight
                adjacencyMatrix[end - 1][begin - 1] = weight
            }
        }
    }

    return adjacencyMatrix

}

fun statistic(adjacencyMatrix: Array<IntArray>, N: Int, M: Int) {

    println("There are $N vertexes and $M edges in graph")

    var minEdgeWeight = Int.MAX_VALUE
    var maxEdgeWeight = Int.MIN_VALUE

    for (i in 0 until N) {
        for (j in i until N) {
            if (adjacencyMatrix[i][j] > maxEdgeWeight)
                maxEdgeWeight = adjacencyMatrix[i][j]
            if (adjacencyMatrix[i][j] < minEdgeWeight && adjacencyMatrix[i][j] != 0)
                minEdgeWeight = adjacencyMatrix[i][j]
        }
    }

    println("The lightest edge is $minEdgeWeight and the heaviest is $maxEdgeWeight")

}

fun dijkstra(adjacencyMatrix: Array<IntArray>, N: Int, K: Int): Array<Int> {

    val distances: Array<Int> = Array(N) { Int.MAX_VALUE }
    val used: Array<Boolean> = Array(N) { false }

    distances[K] = 0

    for (i in 0 until N) {
        var indexMinWeight = -1
        for (j in 0 until N) {
            if (!used[j] && (indexMinWeight == -1 || distances[j] < distances[indexMinWeight])) {
                indexMinWeight = j
            }
        }
        if (distances[indexMinWeight] == Int.MAX_VALUE) break
        used[indexMinWeight] = true
        for (j in 0 until N) {
            if (adjacencyMatrix[indexMinWeight][j] == 0) continue
            if (distances[indexMinWeight] + adjacencyMatrix[indexMinWeight][j] < distances[j]) {
                distances[j] = distances[indexMinWeight] + adjacencyMatrix[indexMinWeight][j]
            }
        }
    }

    for (i in 0 until N) {
        if (distances[i] == Int.MAX_VALUE)
            println("There's no way from vertex number ${K + 1} to vertex number ${i + 1}")
        else
            println("From ${K + 1} to ${i + 1}: ${distances[i]}")
    }

    return distances

}

fun fordBellman(adjacencyMatrix: Array<IntArray>, N: Int, K: Int): Array<Int> {

    data class Edge(val from: Int, val to: Int, val weight: Int)

    var edges: Array<Edge> = emptyArray()

    for (i in 0 until N) {
        for (j in 0 until N) {
            edges = edges.plus(Edge(i, j, adjacencyMatrix[i][j]))
            edges = edges.plus(Edge(j, i, adjacencyMatrix[i][j]))
        }
    }

    val distances: Array<Int> = Array(N) { Int.MAX_VALUE }
    distances[K] = 0

    while (true) {
        var any = false
        for (edge in edges) {
            if (distances[edge.from] < Int.MAX_VALUE) {
                if (distances[edge.to] > distances[edge.from] + edge.weight) {
                    distances[edge.to] = distances[edge.from] + edge.weight
                    any = true
                }
            }
        }
        if (!any) break
    }

    for (i in 0 until N) {
        if (distances[i] == Int.MAX_VALUE)
            println("There's no way from vertex number ${K + 1} to vertex number ${i + 1}")
        else
            println("From ${K + 1} to ${i + 1}: ${distances[i]}")
    }

    return distances

}

fun check(adjacencyMatrix: Array<IntArray>, N: Int, K: Int) {

    val fordBellmanArray: Array<Int> = fordBellman(adjacencyMatrix, N, K)
    val dijkstraArray: Array<Int> = dijkstra(adjacencyMatrix, N, K)
    var correct = true

    if (dijkstraArray.size != fordBellmanArray.size) {
        println("Algorithms are incorrect")
        correct = false
    } else {
        for (i in 0 until dijkstraArray.size) {
            if (dijkstraArray[i] != fordBellmanArray[i]) {
                println("Algorithms are incorrect")
                correct = false
                break
            }
        }
    }

    if (correct) {
        println("Algorithms are correct")
    }

}

fun main() {

    val (N, M) = readLine()!!.split(' ').map(String::toInt)
    val adjacencyMatrix: Array<IntArray> = buildGraph(N, M)

    for (row in adjacencyMatrix) {
        for (element in row) {
            print(element)
            print(" ")
        }
        println()
    }

    statistic(adjacencyMatrix, N, M)
    println("Please, enter the number of vertex to find all distances from:")

    val k = readLine()!!.toInt()
    check(adjacencyMatrix, N, k - 1)

}
