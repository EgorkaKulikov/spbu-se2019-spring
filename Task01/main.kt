import java.util.Scanner


fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)

    val n = scanner.nextInt()
    val m = scanner.nextInt()
    val graph = Graph(n, m)
    graph.info()

    val k = scanner.nextInt()
    val distDijkstra = graph.getMinimalDistFrom(k, algo="dijkstra")
    val distFord = graph.getMinimalDistFrom(k, algo="ford-bellman")
    println(distDijkstra == distFord)
}