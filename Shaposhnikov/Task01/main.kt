import kotlin.math.max

const val maxWeight = 100
const val maxDistance = 1000

fun main ()
{
    val nodes = readLine()!!.toInt()
    val edges = readLine()!!.toInt()
    val graph: Array<Array<Int>> = generateGraph(nodes, edges)

    //statistics(graph)
    printStats(graph)
    val peak = readLine()!!.toInt()

    if (Dijkstra(graph, peak).contentEquals(BellmanFord(graph, peak)))
        println("True ")
    else println("False ")
    //Dijkstra(graph, peak).forEach { print("$it ") }
    //println()
    //BellmanFord(graph, peak).forEach { print("$it ") }
}

fun generateGraph (vertices : Int, edges : Int) : Array<Array<Int>>
{
    val graph = Array(vertices, {Array(vertices, {0})})

    for (i in 1..edges) {
        var node1: Int
        var node2: Int
        do {
            node1 = (1 until(vertices)).random()
            node2 = (0 until(node1)).random()
        }  while (graph[node1][node2] != 0)
        graph[node1][node2] = (1..maxWeight).random()
        graph[node2][node1] = graph[node1][node2]
    }
    return graph
}

fun statistics(array : Array<Array<Int>>) {
    for (i in 0 until(array.size)) {
        for (j in 0 until(array.size))
            print(java.lang.String.format("%4d", array[i][j]))
        println("\n")
    }
}

fun printStats (array : Array<Array<Int>>) {
    println("Nodes amount is ${array.size}")
    var num = 0
    var min = 100
    var max = 0

    for (i in 0 until(array.size))
        for (j in 0..i)
            if (array[i][j] !=0){
                ++num
                when (array[i][j]) {
                    max(max, array[i][j]) -> max = array[i][j]
                    kotlin.math.min(min, array[i][j]) -> min = array[i][j]
                }
            }

    println("Edges amount is $num")
    println("Minimum weight is $min")
    println("Maximum weight is $max")
}

fun Dijkstra (graph: Array<Array<Int>>, node : Int) : Array<Int>{
    val distance = Array(graph.size, {maxDistance})
    var index = node //it needs to be initialized
    var min: Int
    var current: Int
    val visited : Array<Boolean> = Array(graph.size, {false})
    distance[node] = 0

    for (j in 0 until(graph.size)){
        min = maxDistance
        for (i in 0 until(graph.size))
            if ((!visited[i]) && (distance[i] <= min)){
                index = i
                min = distance[i]
            }
        current = index
        visited[current] = true
        for (i in 0 until(graph.size))
            if ((!visited[i]) && (graph[current][i] != 0)
                && (distance[current] != maxDistance)
                && (distance[current] + graph[current][i] < distance[i]))
                distance[i] = distance[current] + graph[current][i]
    }
    return distance
}

fun BellmanFord (graph: Array<Array<Int>>, node : Int) : Array<Int>{
    val distance = Array(graph.size, {maxDistance})
    distance[node] = 0

    for (i in 1 until(graph.size))
        for (u in 0 until(graph.size))
            for (v in 0 until(graph.size))
                if ((distance[v] > distance[u] + graph[u][v]) && (graph[u][v] != 0))
                    distance[v] = distance[u] + graph[u][v]
    return distance
}
