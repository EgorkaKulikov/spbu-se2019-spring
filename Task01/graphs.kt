import java.util.Scanner

const val MAX_WEIGHT = 100
const val NO_EDGE = -1
const val INF = 100001

fun min(a: Int, b: Int) : Int {
    if (a < b)
        return a
    return b
}

fun max(a: Int, b: Int) : Int {
    if (a > b)
        return a
    return b
}

fun createGraph(numVertex: Int, numEdge: Int) : Array<Array<Int>> {
    var graph = Array(numVertex, { Array(numVertex, { NO_EDGE }) })

    for (i in 1..min(numVertex * (numVertex - 1) / 2, numEdge)) { // just to make sure I wrote correct [numEdge]
        var firstV: Int
        var secondV: Int
        var edgeWeight = (1..MAX_WEIGHT).random()

        do {
            firstV = (0..numVertex - 2).random()
            secondV = (firstV + 1..numVertex - 1).random()
        } while (graph[firstV][secondV] != NO_EDGE) // searching for new edge
        graph[firstV][secondV] = edgeWeight
        graph[secondV][firstV] = edgeWeight
    }

    return graph
}

fun printGraph(graph: Array<Array<Int>>) {
    for (i in 0 until graph.size) {
        for (j in 0 until graph.size) {
            print("${graph[i][j]} ")
        }
        println()
    }
}

fun printGraphStat(graph: Array<Array<Int>>) {
    var numEdge = 0
    var lightestEdge = MAX_WEIGHT
    var heaviestEdge = -1

    for (i in 0 until graph.size) {
        for (j in 0 until graph.size) {
            if (graph[i][j] != NO_EDGE) {
                numEdge++
                lightestEdge = min(lightestEdge, graph[i][j])
                heaviestEdge = max(heaviestEdge, graph[i][j])
            }
        }
    }
    numEdge /= 2 // we have disoriented graph so we counted each edge twice

    println("Given graph has ${graph.size} vertexes and $numEdge edges")
    println("the lightest edge is $lightestEdge")
    println("and the heaviest - $heaviestEdge")
    println()
}

fun dijkstra(start: Int, graph: Array<Array<Int>>) : Array<Int> {
    /* [start] should be in range [0, graph.size)
       returns -1 for vertex if there's no way to it
       "V" is a contradiction to "Vertex"
    */

    val distTo = Array(graph.size, { INF })
    val usedV = Array(graph.size, { false })
    distTo[start] = 0

    for (i in 0 until graph.size) {
        var curV = -1
        var minDist = INF

        for (v in 0 until graph.size) {
            if (!usedV[v] && minDist > distTo[v]) {
                curV = v
                minDist = distTo[v]
            }
        }
        if (minDist == INF) // there are no more vertexes we could move from
            break

        usedV[curV] = true
        for (nextV in 0 until graph.size) {
            if (graph[curV][nextV] != NO_EDGE && distTo[nextV] > distTo[curV] + graph[curV][nextV]) {
                distTo[nextV] = distTo[curV] + graph[curV][nextV]
            }
        }
    }

    for (i in 0 until graph.size) { // changing [INF] value to [NO_EDGE] for easier reading
        if (distTo[i] == INF)
            distTo[i] = NO_EDGE
    }
    return distTo
}

fun fordBellman(start: Int, graph: Array<Array<Int>>) : Array<Int> {
    // see [dijkstra] comment

    val distTo = Array(graph.size, { INF })
    distTo[start] = 0

    for (i in 0 until graph.size) {
        for (from in 0 until graph.size) {
            for (to in 0 until graph.size) {
                if (graph[from][to] != NO_EDGE)
                    distTo[to] = min(distTo[to], distTo[from] + graph[from][to])
            }
        }
    }

    for (i in 0 until graph.size) {
        if (distTo[i] == INF)
            distTo[i] = NO_EDGE
    }
    return distTo
}

fun main() {
    val input = Scanner(System.`in`)
    val numVertex = input.nextInt()
    val numEdge = input.nextInt()

    val myGraph = createGraph(numVertex, numEdge)
    printGraphStat(myGraph)

    val start = input.nextInt()
    if (dijkstra(start, myGraph) contentEquals fordBellman(start, myGraph)) {
        println("Both algorithms finished successfully and returned the same result!")
    }
    else {
        println("Sadly, something went wrong and results are not equal.")
        println("Check for mistakes in the code.")
    }
}