val INF : Int = 1000000 // distance to nodes does not exceed INF

fun createGraph(nodes : Int, edges : Int) : Array<Array<Int>>
{
    val graph = Array(nodes) { Array(nodes) { 0 } }
    var line: Int
    var column: Int
    val maxWeight : Int = 100
    for (i in 1..edges)
    {
        var newEdgeCreated : Boolean = false
        while (!newEdgeCreated)
        {
            line = (0..(nodes - 1)).random()
            column = (0..(nodes - 1)).random()
            if (line != column && graph[line][column] == 0)
            {
                val edgeWeight : Int = (1..maxWeight).random()
                graph[line][column] = edgeWeight
                graph[column][line] = edgeWeight
                newEdgeCreated = true
            }
        }

    }
    return graph
}

fun graphStatistic(graph : Array<Array<Int>> )
{
    var edges : Int = 0
    var minWeight : Int = 101
    var maxWeight : Int = -1
    for (i in 0..(graph.size - 1))
        for (j in i..(graph.size - 1))
        {
            if (graph[i][j] != 0)
            {
                edges++
                if (minWeight > graph[i][j])
                    minWeight = graph[i][j]
                if (maxWeight < graph[i][j])
                    maxWeight = graph[i][j]
            }
        }
    if (edges > 0)
    {
        println("Number of nodes: ${graph.size}")
        println("Number of edges: $edges")
        println("max weight of edge: $maxWeight")
        println("min weight of edge: $minWeight")
    }
    else
        println("graph is empty")

}

fun dijkstra(graph : Array<Array<Int>>, startNode : Int) : Array<Int>
{
    val distance = Array (graph.size) {INF}
    distance[startNode] = 0
    val visited = Array (graph.size) {false}
    for (i in 0..(graph.size-1))
    {
        var minDistanceNode: Int = -1
        for (j in 0..(graph.size - 1))
            if (!visited[j]) {
                minDistanceNode = j // first not visited
                break
            }

        for (j in 0..(graph.size - 1))
            if (!visited[j] && distance[j] < distance[minDistanceNode])
                minDistanceNode = j

        if (distance[minDistanceNode] == INF)
            break
        visited[minDistanceNode] = true

        for (someNode in 0..(graph.size - 1))
            if (!visited[someNode] && graph[minDistanceNode][someNode] != 0
                && distance[minDistanceNode] + graph[minDistanceNode][someNode] < distance[someNode]
            ) {
                distance[someNode] = distance[minDistanceNode] + graph[minDistanceNode][someNode]
            }
    }
    return distance
}

fun fordBellman(graph : Array<Array<Int>>, startNode : Int) : Array<Int>
{
    val distance = Array (graph.size) {INF}
    distance[startNode] = 0

    for(k in 1..(graph.size - 1))
        for(i in 0..(graph.size - 1))
            for(j in 0..(graph.size - 1))
                if(graph[i][j] != 0 && graph[i][j] + distance[i] < distance[j])
                    distance[j] = graph[i][j] + distance[i]

    return distance
}

fun main () {
    val input = java.util.Scanner(System.`in`)
    val nodes = input.nextInt()
    val edges = input.nextInt()
    val startNode = input.nextInt()
    val graph = createGraph(nodes, edges)
    graphStatistic(graph)
    val dijkstraDistance = dijkstra(graph, startNode)
    val fordBellmanDistance = fordBellman(graph, startNode)
    if (dijkstraDistance contentEquals fordBellmanDistance)
        println("Dijkstra and FordBellman algorithms gave the same answers")
    else
        println("Dijkstra and FordBellman algorithms gave not the same answers. One of them has mistake")
}