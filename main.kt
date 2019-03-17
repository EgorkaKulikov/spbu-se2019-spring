const val maxWeight = 100
const val infinity = -1

fun createGraph(numberOfNodes: Int, numberOfEdges: Int): Array<IntArray> {
    var graphMatrix = Array(numberOfNodes) { IntArray(numberOfNodes) { 0 } }

    for (i in 0 until numberOfEdges) {
        var node1: Int
        var node2: Int

        do {
            node1 = (1 until numberOfNodes).random()
            node2 = (0 until node1).random()
        } while (graphMatrix[node1][node2] != 0)

        val weight = (1..maxWeight).random()
        graphMatrix[node1][node2] = weight
        graphMatrix[node2][node1] = weight
    }
    return graphMatrix
}

fun printStats(graphMatrix: Array<IntArray>) {
    var min = maxWeight
    var max = 0

    for (i in 0 until graphMatrix.size) {
        for (j in 0 until i) {
            if (graphMatrix[i][j] < min && graphMatrix[i][j] != 0)
                min = graphMatrix[i][j]
            if (graphMatrix[i][j] > max)
                max = graphMatrix[i][j]
        }
    }

    println("Number of nodes: ${graphMatrix.size}")
    println("Minimal edge weight value: $min\nMaximal edge weight value: $max")
}

fun dijkstra(graphMatrix: Array<IntArray>, nodeIndex: Int): IntArray {
    var pathLengths = IntArray(graphMatrix.size) { infinity }
    var isVisited = Array(graphMatrix.size) { false }
    pathLengths[nodeIndex] = 0

    for (i in 0 until graphMatrix.size) {
        var min = infinity
        for (j in 0 until graphMatrix.size) {
            if (!isVisited[j] && (min == infinity || pathLengths[j] < pathLengths[min]))
                min = j
        }
        if (pathLengths[min] == infinity) {
            break
        }
        isVisited[min] = true
        for (j in 0 until graphMatrix.size) {
            if (graphMatrix[min][j] != 0
                && pathLengths[min] + graphMatrix[min][j] < pathLengths[j]
            ) {
                pathLengths[j] = pathLengths[min] + graphMatrix[min][j]
            }
        }
    }
    return pathLengths
}

fun fordBellman(graphMatrix: Array<IntArray>, nodeIndex: Int): IntArray {
    var pathLengths = IntArray(graphMatrix.size) { infinity }
    pathLengths[nodeIndex] = 0

    for (i in 1 until graphMatrix.size) {
        for (node1 in 0 until graphMatrix.size)
            for (node2 in 0 until graphMatrix.size)
                if (pathLengths[node1] != infinity
                    && pathLengths[node2] > pathLengths[node1] + graphMatrix[node1][node2]
                    && graphMatrix[node1][node2] != 0
                ) {
                    pathLengths[node2] = pathLengths[node1] + graphMatrix[node1][node2]
                }
    }
    return pathLengths
}

fun main() {
    println("Input number of nodes..")
    val numberOfNodes = readLine()!!.toInt()
    println("Input number of edges..")
    val numberOfEdges = readLine()!!.toInt()

    val graph = createGraph(numberOfNodes, numberOfEdges)
    printStats(graph)

    println("Input node index for Dijkstra and Ford Bellman algorithms..")
    val nodeIndex = readLine()!!.toInt()
    if (dijkstra(graph, nodeIndex) contentEquals fordBellman(graph, nodeIndex)) {
        println("Algorithms provided identical results.")
    }
    else {
        println("Error! Algorithms provided different results.")
    }
}