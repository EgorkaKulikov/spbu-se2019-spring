const val infinity = java.lang.Integer.MAX_VALUE
const val edgeLim = 100

var maxWeight = 0
var minWeight = edgeLim + 1

fun createGraph(nodes: Int, edges: Int): Array<Array<Int>>{
    val graph = Array(nodes) {Array(nodes) {0} }
    var amountOfFilledEdges = 0

    while (amountOfFilledEdges < edges){
        val currNode1 = (0 until nodes).random()
        val currNode2 = (0 until nodes).random()
        val edgeWeight = (1..edgeLim).random()
        if ((graph[currNode1][currNode2] == 0) && (currNode1 != currNode2)){
            graph[currNode1][currNode2] = edgeWeight
            graph[currNode2][currNode1] = edgeWeight

            if (edgeWeight >= maxWeight)
                maxWeight = edgeWeight
            if (edgeWeight <= minWeight)
                minWeight = edgeWeight

            amountOfFilledEdges++
        }
    }

    return graph
}

fun printStatistics(graph: Array<Array<Int>>, edges: Int){
    val nodes = graph.size
    println("Nodes Number: $nodes")
    println("Edges Number: $edges")
    println("Max Weight: $maxWeight")
    println("Min Weight: $minWeight")
}

fun algDijkstra(graph:Array<Array<Int>>, amountOfNodes: Int, givenNode: Int): Array<Int>{
    val distances = Array(amountOfNodes) {infinity}
    val used = Array(amountOfNodes) {false}
    var last = givenNode

    distances[givenNode] = 0
    used[givenNode] = true

    for (i in 0 until amountOfNodes - 1){
        for (j in 0 until amountOfNodes)
            if (graph[last][j] != 0 && !used[j])
                distances[j] = kotlin.math.min(distances[j], distances[last] + graph[last][j])

        var minDistance = infinity
        for (j in 0 until amountOfNodes)
            if (minDistance > distances[j] && !used[j]){
                minDistance = distances[j]
                last = j
            }
        used[last] = true
    }

    return distances
}

fun algFordBellman(graph:Array<Array<Int>>, amountOfNodes: Int, givenNode: Int): Array<Int>{
    val distances = Array(amountOfNodes) {infinity}
    distances[givenNode] = 0

    for (i in 1 until amountOfNodes)
        for (j in 0 until amountOfNodes)
            for (k in 0 until amountOfNodes)
                if (distances[j] + graph[k][j] < distances[k] &&
                    graph[j][k] != 0 &&
                    distances[j] != infinity
                ) {
                    distances[k] = distances[j] + graph[j][k]
                }

    return distances
}

fun main() {
    val scan = java.util.Scanner(System.`in`)
    val n = scan.nextInt()
    val m = scan.nextInt()
    val graph = createGraph(n, m)

    printStatistics(graph, m)

    val k = scan.nextInt()
    val distancesDijkstra = algDijkstra(graph, n, k)
    val distancesFordBellman = algFordBellman(graph, n, k)

    if (distancesDijkstra contentEquals distancesFordBellman)
        println("The results of algorithms match.")
    else
        println("The results of algorithms do not match.")
}