import java.lang.Exception
import java.lang.NumberFormatException
import java.util.*
import kotlin.system.exitProcess

const val EDGE_MIN_VALUE = 1
const val EDGE_MAX_VALUE = 100
const val INFTY = Int.MAX_VALUE

class Graph(private val nodesNumber : Int, private val edgesNumber: Int)
{
    private var matrix : Array<Array<Int>> = emptyArray()
    private var edges  : Array<Triple<Int, Int, Int>> = Array(edgesNumber) { Triple(0, 0, 0) }

    init
    {
        if (nodesNumber < 1) throw Exception("Number of nodes must be more than zero!")
        if (edgesNumber < 0) throw Exception("Number of edges must pe positive!")
        val maxEdgesNumber = (nodesNumber * nodesNumber - nodesNumber) / 2
        if (edgesNumber > maxEdgesNumber) throw Exception("Number of edges must be <= $maxEdgesNumber")

        matrix = Array(nodesNumber) { Array(nodesNumber) {0} }

        for (i in 0 until edgesNumber)
        {
            var lineIndex : Int
            var cellIndex : Int

            do {
                lineIndex  = randomUntil(nodesNumber)
                cellIndex  = randomUntil(nodesNumber)
            } while (matrix[lineIndex][cellIndex] != 0 || lineIndex == cellIndex)

            val edgeCost = (EDGE_MIN_VALUE..EDGE_MAX_VALUE).random()
            matrix[lineIndex][cellIndex] = edgeCost
            matrix[cellIndex][lineIndex] = edgeCost
            edges[i] = Triple(lineIndex, cellIndex, edgeCost)
        }
    }

    private fun getDijkstraPath(k : Int) : Array<Int>
    {
        val pathLength = Array(nodesNumber) { INFTY }
        pathLength[k] = 0

        val visited = Array(nodesNumber) { false }

        for (i in 0 until nodesNumber)
        {
            var min = INFTY
            for (j in 0 until nodesNumber)
                if (!visited[j] && (min == INFTY || pathLength[j] < pathLength[min]))
                    min = j

            if (pathLength[min] == INFTY)
                break

            visited[min] = true

            for (j in 0 until nodesNumber)
            {
                if (matrix[min][j] != 0 && pathLength[min] + matrix[min][j] < pathLength[j])
                {
                    pathLength[j] = pathLength[min] + matrix[min][j]
                }
            }
        }

        return pathLength
    }

    private fun getFordBellmanPath(k: Int) : Array<Int>
    {
        val len: Array<Int> = Array(nodesNumber) { INFTY }
        len[k] = 0

        for(i in 0 until nodesNumber)
        {
            if (matrix[k][i] != 0 && len[i] > matrix[k][i])
            {
                len[i] = matrix[k][i]
                recurFB(i, len)
            }
        }

        return len
    }

    private fun recurFB(ind : Int, len : Array<Int>)
    {

        for (i in 0 until nodesNumber)
        {
            if (matrix[ind][i] != 0 && len[i] > len[ind] + matrix[ind][i])
            {
                len[i] = len[ind] + matrix[ind][i]
                recurFB(i, len)
            }
        }
    }

    fun printShortestPathsFrom(k : Int)
    {
        if (k !in 0 until nodesNumber) throw Exception("Node $k does not exist!")

        val D = getDijkstraPath(k)
        val FB = getFordBellmanPath(k)

        for (i in 0 until nodesNumber)
            if (D[i] != FB[i])
                throw Exception("Dijkstra and Ford-Bellsman algorithms provided different results!")


        println("Shortest paths from node $k to other:")
        for (i in 0 until nodesNumber)
            if (D[i] != INFTY)
                println("   to node $i: " + D[i])
            else
                println("   to node $i: path does not exist")
    }

    fun printStatistics()
    {
        for (row in matrix) {
            for (cell in row) {
                print("$cell \t")
            }
            println()
        }

        val max = findMax()
        val min = findMin()

        println("Number of nodes is $nodesNumber")
        println("Number of edges is $edgesNumber")
        println("Maximum edge value is $max")
        println("Minimum edge value is $min")
    }

    private fun findMax() : Int
    {
        var max : Int = EDGE_MIN_VALUE

        for (row in matrix) {

            val rowMax = row.max() as Int

            if (max < rowMax)
                max = rowMax
        }
        return max
    }

    private fun findMin() : Int
    {
        var min : Int = EDGE_MAX_VALUE

        for (row in matrix) {
            for (cell in row)
                if (min > cell && cell > 0)
                    min = cell
        }
        return  min
    }
}

fun main()
{
    println("Enter three numbers separated by ' ' \n" +
            "N - number of nodes \n" +
            "M - number of edges \n" +
            "K - number of node (from 0 to N-1) from witch paths will be found")

    val input = Scanner(System.`in`)

    val nodesNumber = input.nextInt()
    val edgesNumber = input.nextInt()
    val k = input.nextInt()

    val graph : Graph

    try {
        graph = Graph(nodesNumber, edgesNumber)
        graph.printStatistics()
        graph.printShortestPathsFrom(k)
    }
    catch (e : Exception)
    {
        println(e.message)
        exitProcess(1)
    }
}

fun randomUntil(number : Int) =
    if (number == 0) 0
    else (0 until number).random()