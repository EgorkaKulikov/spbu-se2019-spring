fun build(N: Int, M: Int) : Array<IntArray> {
    val adjacencyMatrix = Array(N) { IntArray(N) }
    val edges : HashSet<Pair<Int, Int>> = HashSet()
    while (edges.size != M) {
        var begin = (1..N).random()
        var end = (1..N).random()
        if (begin > end) {
            begin = end.also { end = begin }
        }
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

fun statistic(adjacencyMatrix : Array<IntArray>, N : Int, M: Int) {
    println("There are $N vertexes and $M edges in graph")
    var minEdge = 101
    var maxEdge = 0
    for(i in 0..(N - 1)) {
        for(j in i..(N - 1)) {
            if(adjacencyMatrix[i][j] > maxEdge)
                maxEdge = adjacencyMatrix[i][j]
            if (adjacencyMatrix[i][j] < minEdge && adjacencyMatrix[i][j] != 0)
                minEdge = adjacencyMatrix[i][j]
        }
    }
    println("The lightest edge is $minEdge and the heaviest is $maxEdge")
}

fun dijkstra(adjacencyMatrix: Array<IntArray>, N: Int, K: Int) : Array<Int> {
    val distances: Array<Int> = Array(N) { Int.MAX_VALUE }
    val used : Array<Boolean> = Array(N) { false }
    distances[K] = 0
    for(i in 0 until N) {
        var v = -1
        for(j in 0 until N) {
            if (!used[j] && (v == -1 || distances[j] < distances[v])) {
                v = j
            }
        }
        if (distances[v] == Int.MAX_VALUE) break
        used[v] = true
        for(j in 0 until N) {
            if (adjacencyMatrix[v][j] == 0) continue
            if (distances[v] + adjacencyMatrix[v][j] < distances[j]) {
                distances[j] = distances[v] + adjacencyMatrix[v][j]
            }
        }
    }
    for(i in 0 until N) {
        if(distances[i] == Int.MAX_VALUE)
            println("There's no way from vertex number ${K + 1} to vertex number ${i + 1}")
        else
            println("From ${K + 1} to ${i + 1}: ${distances[i]}")
    }
    return distances
}

fun fordBellman(adjacencyMatrix: Array<IntArray>, N: Int, K: Int) : Array<Int> {
    data class Edge(val from: Int, val to: Int, val weight: Int)
    var edges: Array<Edge> = emptyArray()
    for(i in 0 until N) {
        for(j in 0 until N) {
            edges = edges.plus(Edge(i, j, adjacencyMatrix[i][j]))
            edges = edges.plus(Edge(j, i, adjacencyMatrix[i][j]))
        }
    }
    val distances : Array<Int> = Array(N) { Int.MAX_VALUE }
    distances[K] = 0
    while(true) {
        var any = false
        for (edge in edges) {
            if(distances[edge.from] < Int.MAX_VALUE) {
                if (distances[edge.to] > distances[edge.from] + edge.weight) {
                    distances[edge.to] = distances[edge.from] + edge.weight
                    any = true
                }
            }
        }
        if(!any) break
    }
    for(i in 0 until N) {
        if(distances[i] == Int.MAX_VALUE)
            println("There's no way from vertex number ${K + 1} to vertex number ${i + 1}")
        else
            println("From ${K + 1} to ${i + 1}: ${distances[i]}")
    }
    return distances
}

fun check(adjacencyMatrix: Array<IntArray>, N : Int, K: Int) {
    val fordBellmanArray : Array<Int> = fordBellman(adjacencyMatrix, N, K)
    val dijkstraArray : Array<Int> = dijkstra(adjacencyMatrix, N, K)
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
    if(correct) {
        println("Algorithms are correct")
    }
}

fun main() {
    val (N, M) = readLine()!!.split(' ').map(String::toInt)
    val adjacencyMatrix : Array<IntArray> = build(N, M)
    for (row in adjacencyMatrix) {
        for (element in row) {
            print(element)
            print (" ")
        }
        println()
    }
    statistic(adjacencyMatrix, N, M)
    println("Please, enter the number of vertex to find all distances from:")
    val k = readLine()!!.toInt()
    check(adjacencyMatrix, N, k - 1)
}
