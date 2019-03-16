import java.util.Random
import java.util.TreeSet
import kotlin.math.max
import kotlin.math.min

const val MAX_WEIGHT = 100

class Graph(N: Int, M: Int) {

    private val verticesNumber: Int = N
    private val edgesNumber: Int = M
    private val matrix: Array<Array<Int>> = Array(N) { Array(N) {0} }

    init {

        if (edgesNumber >  verticesNumber * (verticesNumber - 1) / 2) {
            throw ExceptionInInitializerError("Too large number of edges")
        }
        if (verticesNumber == 0) {
            throw ExceptionInInitializerError("Graph can not be empty")
        }

        val random = Random()
        val randomEdges = List(edgesNumber) {
            Pair(random.nextInt(verticesNumber), random.nextInt(verticesNumber))
        }
        randomEdges.forEach {
            var u = it.first
            var v = it.second
            while (matrix[u][v] != 0 || u == v) {    // Trying to re-generate edge, if it isn't valid
                u = random.nextInt(verticesNumber)
                v = random.nextInt(verticesNumber)
            }
            val weight = random.nextInt(MAX_WEIGHT) + 1
            matrix[u][v] = weight
            matrix[v][u] = weight
        }
    }

    fun info() {
        var maxWeight = 0
        var minWeight = MAX_WEIGHT
        matrix.forEach { row ->
            row.forEach {
                if (it != 0) {
                    maxWeight = max(maxWeight, it)
                    minWeight = min(minWeight, it)
                }
            }
        }
        println("Number of vertices: $verticesNumber")
        println("Number of edges: $edgesNumber")
        if (edgesNumber > 0) {
            println("The heaviest edge: $maxWeight")
            println("The lightest edge: $minWeight")
        }
    }

    fun getMinimalDistFrom(K: Int, algo: String = "dijkstra") : List<Int> {

        val startVertex = K - 1
        if (startVertex - 1 >= verticesNumber) {
            throw Exception("Number of start vertex must be less than N")
        }

        val dist: Array<Int> = Array(verticesNumber) { Int.MAX_VALUE }
        dist[startVertex] = 0

        when (algo) {
            "dijkstra" -> {

                data class AdvancedVertex(val distance: Int, val vertex: Int)
                val comparator = compareBy<AdvancedVertex> ({ it.distance }, { it.vertex })
                val queue: TreeSet<AdvancedVertex> = sortedSetOf(
                        comparator,
                        AdvancedVertex(dist[startVertex], startVertex)
                )

                while (!queue.isEmpty()) {
                    val queueFront = queue.first()
                    val currentVertex = queueFront.vertex
                    queue.remove(queueFront)
                    matrix[currentVertex].forEachIndexed { nextVertex, weightOfEdge ->
                        if (weightOfEdge != 0) {
                            if (dist[currentVertex] + weightOfEdge < dist[nextVertex]) {
                                queue.remove(AdvancedVertex(dist[nextVertex], nextVertex))
                                dist[nextVertex] = dist[currentVertex] + weightOfEdge
                                queue.add(AdvancedVertex(dist[nextVertex], nextVertex))
                            }
                        }
                    }
                }

                return dist.toList()
            }

            "ford-bellman" -> {

                for (phase in 0 until (verticesNumber - 1)) {
                    matrix.forEachIndexed { currentVertex, row ->
                        row.forEachIndexed { nextVertex, weightOfEdge ->

                            if (weightOfEdge != 0 && dist[currentVertex] < Int.MAX_VALUE) {
                                dist[nextVertex] = min(
                                        dist[nextVertex],
                                        dist[currentVertex] + weightOfEdge
                                )
                            }

                        }
                    }
                }

                return dist.toList()
            }

            else -> {
                throw Exception("Wrong algorithm's name")
            }
        }
    }

}