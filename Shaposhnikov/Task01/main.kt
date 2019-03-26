const val MaxWeight = 100
const val MaxEdgesNumber = 100000
const val MaxNodesNumber = 1000
const val MaxDistance = MaxWeight*MaxNodesNumber
var Min = MaxWeight+1
var Max = -1

fun main ()
{
    val scan = java.util.Scanner(System.`in`)

    val nodes = scan.nextInt()
    if (nodes > MaxNodesNumber) throw Exception("Too large number for nodes")
    val edges = scan.nextInt()
    if (edges > nodes * (nodes - 1) / 2)
        throw Exception("Too large number for edges")
    val peak = scan.nextInt()
    if (peak !in 0 until nodes) throw Exception("Incorrect number for peak")
    val graph: Array<Array<Int>> = generateGraph(nodes, edges)

    printStats(nodes, edges)
    if (Dijkstra(graph, peak).contentEquals(BellmanFord(graph, peak)))
        println("True ")
    else println("False ")
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
        graph[node1][node2] = (1..MaxWeight).random()
        graph[node2][node1] = graph[node1][node2]
        if (graph[node1][node2] < Min) Min = graph[node1][node2]
        if (graph[node1][node2] > Max) Max = graph[node1][node2]
    }
    return graph
}

fun printStats (nodes : Int, edges : Int) {
    println("Nodes amount is $nodes")
    println("Edges amount is $edges")
    println("Minimum weight is $Min")
    println("Maximum weight is $Max")
}

fun Dijkstra (graph: Array<Array<Int>>, node : Int) : Array<Int>{
    val distance = Array(graph.size) {MaxDistance}
    var index = node //it needs to be initialized
    var min: Int
    var current: Int
    val visited : Array<Boolean> = Array(graph.size) {false}
    distance[node] = 0
    val size = graph.size

    for (j in 0 until(size)){
        min = MaxDistance
        for (i in 0 until(size))
            if (!visited[i] && distance[i] <= min){
                index = i
                min = distance[i]
            }
        current = index
        visited[current] = true
        for (i in 0 until(size))
            if (!visited[i] && graph[current][i] != 0
                && distance[current] != MaxDistance
                && distance[current] + graph[current][i] < distance[i]
            ) {
                distance[i] = distance[current] + graph[current][i]
            }
    }
    return distance
}

fun BellmanFord (graph: Array<Array<Int>>, node : Int) : Array<Int>{
    val distance = Array(graph.size) {MaxDistance}
    distance[node] = 0
    val size = graph.size

    for (i in 1 until(size))
        for (u in 0 until(size))
            for (v in 0 until(size))
                if (distance[v] > distance[u] + graph[u][v] && graph[u][v] != 0
                ) {
                    distance[v] = distance[u] + graph[u][v]
                }
    return distance
}
