import java.util.*
import kotlin.math.min
import kotlin.math.max
import kotlin.random.Random

const val MAX_WEIGHT = 100
const val INF = Int.MAX_VALUE / 2

class Graph (n: Int, m: Int){
    val verticesNum : Int = n
    val edgesNum: Int = m
    val adjMatrix : Array<Array<Int>> = Array (n){ Array(n){0}}

    init {
        if (verticesNum * (verticesNum-1) /2 < edgesNum){
            throw ExceptionInInitializerError("Not enough vertices for given amount of edges")
        }
        if (verticesNum == 0){
            throw ExceptionInInitializerError("Empty graph is not supported")
        }
        for (i in 1..edgesNum) {
            var u = Random.nextInt(verticesNum)
            var v = Random.nextInt(verticesNum)
            while ((u == v) || (adjMatrix[u][v] != 0)){
                u = Random.nextInt(verticesNum)
                v = Random.nextInt(verticesNum)
            }
            val weight = Random.nextInt(1,MAX_WEIGHT)
            adjMatrix[u][v] = weight
            adjMatrix[v][u] = weight
        }
    }

    fun print_stats() {
        println("Number of vertices: $verticesNum")
        println("Number of edges: $edgesNum")
        if (edgesNum > 0) {
            var maxGraphWeight = 0
            var minGraphWeight = MAX_WEIGHT
            for (i in 0 until verticesNum) {
                for (j in 0 until verticesNum) {
                    if (adjMatrix[i][j] > 0)
                        minGraphWeight = min(minGraphWeight, adjMatrix[i][j])
                }
                maxGraphWeight = max(maxGraphWeight, adjMatrix[i].max() ?: 0)
            }
            println("Min edge weight: $minGraphWeight")
            println("Max edge weight: $maxGraphWeight")
        }
    }

    data class vertexDistPair(val vertex: Int, val distance: Int): Comparable<vertexDistPair> {
        override fun compareTo(other: vertexDistPair): Int {
            if (this.distance == other.distance)
                return this.vertex - other.vertex
            return this.distance - other.distance //else
        }
    }

    fun dijsktra(k:Int):Array<Int>{
        val distances : Array <Int> = Array(verticesNum) {INF}
        distances[k] = 0
        val queue = PriorityQueue<vertexDistPair>()
        queue.add(vertexDistPair(k,0))
        while  (queue.isNotEmpty()) {
            val minV = queue.first().vertex
            queue.remove(queue.first())
            adjMatrix[minV].forEachIndexed { nextVertex, weightOfEdge ->
                if (weightOfEdge != 0) {
                    if (distances[minV] + weightOfEdge < distances[nextVertex]) {
                        queue.remove(vertexDistPair(nextVertex,distances[nextVertex]))
                        distances[nextVertex] = distances[minV] + weightOfEdge
                        queue.add(vertexDistPair(nextVertex,distances[nextVertex]))
                    }
                }
            }
        }
        return distances
    }

    fun ford_bellman(k:Int):Array<Int>{
        val distances : Array <Int> = Array(verticesNum) {INF}
        distances[k] = 0
        var i = 1
        while (i in 1..verticesNum) {
                var any = false
                    adjMatrix.forEachIndexed{ vertex, row ->
                        row.forEachIndexed{ column, weight ->
                            if ((weight > 0) && (distances[column] > distances[vertex] + weight)) {
                                distances[column] = distances[vertex] + weight
                                any = true
                            }
                        }
                    }
                if (!any) {
                    break
                }
                i++
            }
        if (i == verticesNum)
            throw Exception("Negative cycle found: result is not correct")
        return distances
    }

    fun checkDistances(k: Int):Boolean {
        if (k > verticesNum){
            throw ArrayIndexOutOfBoundsException("No such vertex")
        }
        var result = false
        try {
          result = dijsktra(k).contentEquals(ford_bellman(k))
        }
        catch (e : Exception) {
            println(e.message)
        }
        return result
    }
}

fun main(){
    val input = Scanner(System.`in`)
    val n = input.nextInt()
    val m = input.nextInt()
    try {
        val test = Graph(n, m)
        test.print_stats()
        val k = input.nextInt()
            try {
                println(test.checkDistances(k))
            }
            catch (e : ArrayIndexOutOfBoundsException) {
                println(e.message)
            }
        }
    catch (e : ExceptionInInitializerError) {
        println( e.message )
    }
}
