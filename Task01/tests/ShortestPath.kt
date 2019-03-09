import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class TestMyGraph {
    @Test
    fun ShortestPath() {
        for (N in 2..20) {
            for (M in 1..(N * (N - 1) / 2)) {
                val graph = MyGraph(N, M)
                for (K in 1..N) {
                    val lenDijkstra: Array<Int> = graph.getDijkstraShortestPath(K).first
                    val lenFordBellman: Array<Int> = graph.getFordBellmanShortestPath(K).first
                    val lenLevit: Array<Int> = graph.getLevitShortestPath(K).first
                    for (i in 1..N) {
                        assertEquals(lenDijkstra[i], lenLevit[i])
                        assertEquals(lenLevit[i], lenFordBellman[i])
                        assertEquals(lenFordBellman[i], lenDijkstra[i])
                    }
                }
            }
        }
    }

    @Test
    fun testGetMinEdge() {
        for (N in 2..20) {
            for (M in 1..(N * (N - 1) / 2)) {
                val graph = MyGraph(N, M)
                val res = graph.edges.minBy { it.cost }!!.cost
                assertEquals(res, graph.minEdge.cost)
            }
        }
    }

    @Test
    fun testGetMaxEdge() {
        for (N in 2..20) {
            for (M in 1..(N * (N - 1) / 2)) {
                val graph = MyGraph(N, M)
                val res = graph.edges.maxBy { it.cost }!!.cost
                assertEquals(res, graph.maxEdge.cost)
            }
        }
    }
}