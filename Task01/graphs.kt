import java.util.Scanner

const val MAX_WEIGHT = 100
const val NO_EDGE = -1
const val INF = 100001

class Graph(numVertex: Int, numEdge: Int) {
    private var matrVert: Array<Array<Int>> = Array(numVertex){ _ -> Array(numVertex){ NO_EDGE } }
    private var heaviestEdge: Int = NO_EDGE
    private var lightestEdge: Int = INF
    private var numEdge: Int = numEdge
    var size: Int = numVertex

    init {
        // "minOf" is just to make sure I wrote correct [numEdge]
        for (i in 1..minOf(numVertex * (numVertex - 1) / 2, numEdge)) {
            var firstV: Int
            var secondV: Int
            var edgeWeight = (1..MAX_WEIGHT).random()

            do {
                firstV = (0 until numVertex - 1).random()
                secondV = (firstV + 1 until numVertex).random()
            } while (matrVert[firstV][secondV] != NO_EDGE) // searching for new edge
            matrVert[firstV][secondV] = edgeWeight
            matrVert[secondV][firstV] = edgeWeight
            heaviestEdge = maxOf(heaviestEdge, edgeWeight)
            lightestEdge = minOf(lightestEdge, edgeWeight)
        }
    }

    fun printStat() {
        println("The graph has ${size} vertexes and $numEdge edges")
        println("the lightest edge is $lightestEdge")
        println("and the heaviest - $heaviestEdge")
        println()
    }

    fun getDistByDijkstra(start: Int) : Array<Int> {
        /** @param[start] should be in range [0, graph size)
         * returns -1 for vertex if there's no way to it
         * "V" is a contradiction to "Vertex"
         */

        val distTo = Array(size, { INF })
        val usedV = Array(size, { false })
        distTo[start] = 0

        for (i in 0 until size) {
            var curV = -1
            var minDist = INF

            for (v in 0 until size) {
                if (!usedV[v] && minDist > distTo[v]) {
                    curV = v
                    minDist = distTo[v]
                }
            }
            if (minDist == INF) // there are no more vertexes we could move from
                break

            usedV[curV] = true
            for (nextV in 0 until size) {
                if (matrVert[curV][nextV] != NO_EDGE && distTo[nextV] > distTo[curV] + matrVert[curV][nextV]) {
                    distTo[nextV] = distTo[curV] + matrVert[curV][nextV]
                }
            }
        }

        for (i in 0 until size) { // changing [INF] value to [NO_EDGE] for easier reading
            if (distTo[i] == INF)
                distTo[i] = NO_EDGE
        }
        return distTo
    }

    fun getDistByFordBellman(start: Int) : Array<Int> {
        /** @param[start] should be in range [0, size)
         * returns -1 for vertex if there's no way to it
         */

        val distTo = Array(size, { INF })
        distTo[start] = 0

        for (i in 0 until size) {
            for (from in 0 until size) {
                for (to in 0 until size) {
                    if (matrVert[from][to] != NO_EDGE)
                        distTo[to] = minOf(distTo[to], distTo[from] + matrVert[from][to])
                }
            }
        }

        for (i in 0 until size) {
            if (distTo[i] == INF)
                distTo[i] = NO_EDGE
        }
        return distTo
    }
}

fun main() {
    val input = Scanner(System.`in`)
    val numVertex = input.nextInt()
    val numEdge = input.nextInt()

    val myGraph = Graph(numVertex, numEdge)
    myGraph.printStat()

    val start = input.nextInt()
    if (myGraph.getDistByDijkstra(start) contentEquals myGraph.getDistByFordBellman(start)) {
        println("Both algorithms finished successfully and returned the same result!")
    }
    else {
        println("Sadly, something went wrong and results are not equal.")
        println("Check for mistakes in the code.")
    }
}