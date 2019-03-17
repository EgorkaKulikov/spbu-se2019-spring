import kotlin.random.Random


fun buildMatrix(
    graph: Array<Array<Int>>, n: Int, m: Int,
    max: Array<Int>, min: Array<Int>
) {
    println("Adjacency matrix of graph n x n:")
    var max1 = 0
    var min1 = 100
    var countofedges = 0
    while (countofedges != m) {
        val i = Random.nextInt(n)
        val j = Random.nextInt(n)
        if (graph[i][j] == 0 && i != j) {
            graph[i][j] = Random.nextInt(100)
            graph[j][i] = graph[i][j]
            max1 = maxOf(max1, graph[i][j])
            min1 = minOf(min1, graph[i][j])
            countofedges++
        }

    }
    for (i in 0 until n) {
        for (j in 0 until n)
            print("${graph[i][j]} \t")
        println()
    }
    max[0] = max1
    min[0] = min1
    println()
}


fun printStats(n: Int, m: Int, max: Array<Int>, min: Array<Int>) {
    println("Stats:")
    println("Counts of vertices: $n")
    println("Counts of edges: $m")
    println("Weight of most heavy edge: ${max[0]}")
    println("Weight of most light edge: ${min[0]}")
    println()
}

fun DijkstraAlgo(graph: Array<Array<Int>>, n: Int, k: Int): Array<Int> {

    val visited = Array(n) { false }
    val distance = Array(n) { Int.MAX_VALUE }
    distance[k] = 0

    for (i in 0 until n) {

        var vertex = -1

        for (j in 0 until n) {
            if (!visited[j] && (vertex == -1 || distance[j] < distance[vertex]))
                vertex = j
        }

        if (distance[vertex] == Int.MAX_VALUE) break
        visited[vertex] = true

        //create temp list
        var listv: List<Int> = emptyList()
        for (j in 0 until n) {
            if (graph[vertex][j] > 0) {
                listv = listv.plus(j)
            }
        }

        for (j in listv) {
            val len = graph[vertex][j]
            if (distance[vertex] + len < distance[j]) {
                distance[j] = distance[vertex] + len
            }

        }

    }

    return distance
}

fun algoFord_Bellman(graph: Array<Array<Int>>, n: Int, k: Int): Array<Int> {
    val distance = Array(n) { Int.MAX_VALUE }
    distance[k] = 0
    for (m in 0 until n) {
        for (i in 0 until n) {
            for (j in 0 until n) {
                if (graph[i][j] != 0) {
                    if (distance[j].toLong() > distance[i].toLong() + graph[i][j].toLong())
                        distance[j] = distance[i] + graph[i][j]
                }
            }
        }
    }

    return distance
}

fun main() {

    val n = try {
        print("Print n: ")
        val temp = readLine()!!.toInt()
        if (temp in 0 until 1000) temp
        else throw Exception()
    } catch (e: Exception) {
        println("Initializer error, allowable range [1; 1000)")
        return
    }

    val m = try {
        print("Print m: ")
        val tmp = readLine()!!.toInt()
        if ((tmp <= n * (n - 1) / 2) && (tmp in 0 until 100000)) tmp
        else throw Exception()
    } catch (e: Exception) {
        println(
            "Initializer error, m must be <= ${n * (n - 1) / 2} " +
                    "or m should be contained in range from 0 to 100000"
        )
        return
    }

    val graph: Array<Array<Int>> = Array(n) { Array(n) { 0 } }
    val max = Array(1) { 0 }
    val min = Array(1) { 0 }

    buildMatrix(graph, n, m, max, min)
    printStats(n, m, max, min)

    println("Number of vertex from 0 to ${n - 1}")
    val k = readLine()!!.toInt()
    val distDijkstra: Array<Int> = DijkstraAlgo(graph, n, k)
    distDijkstra.forEach { print("$it ") }
    println()
    val distFord_Bellman: Array<Int> = algoFord_Bellman(graph, n, k)
    distFord_Bellman.forEach { print("$it ") }
    println()
    if (distDijkstra.contentEquals(distFord_Bellman)) {
        println("Both algorithms work the same way.")
    } else {
        println("Error, algorithms give different results.")
        return
    }

}