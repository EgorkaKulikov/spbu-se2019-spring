fun main() {
    val numbers = (readLine() ?: "0 0").split(' ')
    if (numbers.size < 2)
        return

    val graphSize = numbers[0].toIntOrNull() ?: 0
    val pathsCount = numbers[1].toIntOrNull() ?: 0

    val graph = Graph.createWithRandomPaths(graphSize, pathsCount)
    graph.printInfo()

    val vertex = (readLine() ?: "").toIntOrNull() ?: -1

    val arrayWithDijkstra = graph.getMinimumLengthsWithDijkstra(vertex)
    val arrayWithBellmanFord = graph.getMinimumLengthsWithBellmanFord(vertex)

    if (arrayWithDijkstra.contentEquals(arrayWithBellmanFord))
        println("Algorithms do not work correctly")
    else
        println("Algorithms probably work correctly")
}
