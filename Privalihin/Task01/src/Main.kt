import java.util.*

const val INF = 10000001

class Graph(N: Int) {
    private var adjMat: Array<Array<Int>> = Array(N){ _ -> Array(N) { 0 } }
    private var minWeight: Int = 0
    private var maxWeight: Int = 0
    private var numberOfEdges: Int = 0


    fun addRandomEdges(M: Int) {
        var freeEdges = Array<Array<Pair<Int, Int>>>(adjMat.size)
            { i -> Array(adjMat.size - i - 1) { j -> Pair(i, i + j + 1) } }
                .flatMap { v -> v.asList() }
                .filter { p -> adjMat[p.first][p.second] == 0 }
                .shuffled()
        val randGen = Random()

        for (i in 0.until(M)) {
            val weight = randGen.nextInt(100) + 1

            if (maxWeight < weight) {
                maxWeight = weight
            }

            if (minWeight == 0 || minWeight > weight) {
                minWeight = weight
            }

            numberOfEdges++
            adjMat[freeEdges[i].first][freeEdges[i].second] = weight
            adjMat[freeEdges[i].second][freeEdges[i].first] = weight
        }
    }

    fun printStats() {
        println(adjMat.size)
        println(numberOfEdges)
        println(minWeight)
        println(maxWeight)
    }

    fun Dijkstra(start: Int): Array<Int> {
        var distances = Array<Int>(adjMat.size) {i -> if(i == start) 0 else INF}
        var visited = Array<Boolean>(adjMat.size) { _ -> false }

        for (i in 0.until(adjMat.size)) {
            var v: Int = 0.until(adjMat.size).filter { j: Int -> !visited[j] }.minBy { j: Int -> distances[j] }!!

            if (distances[v] == INF) {
                break
            }

            visited[v] = true

            for (j in 0.until(adjMat.size)) {
                if (adjMat[v][j] != 0 && distances[v] + adjMat[v][j] < distances[j]) {
                    distances[j] = distances[v] + adjMat[v][j]
                }
            }
        }

        return distances
    }

    fun FordBellman(start: Int): Array<Int> {
        var distances = Array<Int>(adjMat.size) { v -> if (v == start) 0 else INF }
        val edges = Array<Array<Pair<Int, Int>>>(adjMat.size) { i -> Array(adjMat.size) { j -> Pair(i, j) } }
                .flatMap { v -> v.asList() }
                .filter { p -> adjMat[p.first][p.second] != 0 }

        for (k in 0.until(adjMat.size)) {
            var changed = false

            for (e in edges) {
                if (distances[e.second] > distances[e.first] + adjMat[e.first][e.second]) {
                    changed = true
                    distances[e.second] = distances[e.first] + adjMat[e.first][e.second]
                }
            }

            if (!changed) {
                break
            }
        }

        return distances
    }
}

fun createRandomGraph(N: Int, M: Int): Graph {
    val result = Graph(N)
    result.addRandomEdges(M)
    return result
}

fun main(Args: Array<String>) {
    with(Scanner(System.`in`)) {
        val N: Int = nextInt()
        val M: Int = nextInt()
        var graph: Graph = createRandomGraph(N, M)
        graph.printStats()
        val k = nextInt() - 1
        println((graph.Dijkstra(k) contentEquals graph.FordBellman(k)))
    }
}