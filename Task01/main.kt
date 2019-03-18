const val MAX_DISTANCE = 10000000 // so-called infinity for the Dijkstra's and Bellman-Ford's algorithms

fun createGraph(numberNodes: Int, numberEdges: Int): Array<Array<Int>>
{
    val graph = Array(numberNodes) { Array(numberNodes) { 0 } }
    var rowInd: Int
    var cellInd: Int

    for (i in numberEdges downTo 1)
    {
        rowInd = (0 until numberNodes).random()
        cellInd = (0 until numberNodes).random()

        while(graph[rowInd][cellInd] != 0 || rowInd == cellInd)
        {
            rowInd = (0 until numberNodes).random()
            cellInd = (0 until numberNodes).random()
        }

        graph[rowInd][cellInd] = (1..100).random() // to 100 because it is max value of one edge
        graph[cellInd][rowInd] = graph[rowInd][cellInd]
    }

    return graph
}

fun getStats(numberNodes : Int, numberEdges : Int, graphRel: Array<Array<Int>>)
{
    var maxElem = -1
    var minElem = -1

    for(row in graphRel)
    {
        for(cell in row)
        {
            minElem = if ((minElem > cell || minElem == -1) && cell != 0) cell else minElem
            maxElem = if (maxElem < cell) cell else maxElem
        }
    }

    println("Number of nodes: $numberNodes")
    println("Number of edges: $numberEdges")
    if (minElem == -1)
    {
        println("Graph is empty, so max and min weight are 0")
    }
    else
    {
        println("Max weight: $maxElem")
        println("Min weight: $minElem")
    }
}

fun dijkstra(graphRel: Array<Array<Int>>, numberNodes: Int, initNode: Int): Array<Int>
{
    val distance = Array(numberNodes) {MAX_DISTANCE}
    distance[initNode] = 0
    val isChecked = Array(numberNodes) {false}
    var indexOfCurrentNode = 0

    for (j in 0 until numberNodes-1)
    {
        var currentMin = MAX_DISTANCE

        for (i in 0 until numberNodes)
        {
            if (!isChecked[i] && distance[i] <= currentMin)
            {
                currentMin = distance[i]
                indexOfCurrentNode = i
            }
        }

        isChecked[indexOfCurrentNode] = true

        for (anotherNode in 0 until numberNodes)
        {
            if (!isChecked[anotherNode]
                && graphRel[indexOfCurrentNode][anotherNode] != 0
                && distance[indexOfCurrentNode] != MAX_DISTANCE
                && distance[indexOfCurrentNode]
                + graphRel[indexOfCurrentNode][anotherNode] < distance[anotherNode])
            {
                distance[anotherNode] = distance[indexOfCurrentNode] + graphRel[indexOfCurrentNode][anotherNode]
            }
        }
    }

    return distance
}

fun bellmanRecursive(rowInd: Int, dist : Array<Int>, numberNodes: Int, graphRel: Array<Array<Int>>)
{
    for (i in 0 until numberNodes)
    {
        if (graphRel[rowInd][i] != 0 && dist[i] > dist[rowInd] + graphRel[rowInd][i])
        {
            dist[i] = dist[rowInd] + graphRel[rowInd][i]
            bellmanRecursive(i, dist, numberNodes, graphRel)
        }
    }
}

fun bellmanFord(graphRel: Array<Array<Int>>, numberNodes: Int, initNode: Int): Array<Int>
{
    val distance = Array(numberNodes) {MAX_DISTANCE}
    distance[initNode] = 0

    for(i in 0 until numberNodes)
    {
        if (graphRel[initNode][i] != 0 && distance[i] > graphRel[initNode][i])
        {
            distance[i] = graphRel[initNode][i]
            bellmanRecursive(i, distance, numberNodes, graphRel)
        }
    }

    return distance
}

fun main()
{
    val scan = java.util.Scanner(System.`in`)
    val numberNodes = scan.nextInt()
    val numberEdges = scan.nextInt()
    val k = scan.nextInt()

    val graphRel: Array<Array<Int>> = createGraph(numberNodes, numberEdges)
    val dijkstraRes = dijkstra(graphRel, numberNodes, k)
    val bellmanRes = bellmanFord(graphRel, numberNodes, k)

    getStats(numberNodes, numberEdges, graphRel)
    println(dijkstraRes contentEquals bellmanRes)
}