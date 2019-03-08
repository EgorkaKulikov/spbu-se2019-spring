import com.sun.org.apache.xml.internal.security.Init
import com.sun.org.apache.xpath.internal.operations.Bool
import org.omg.CORBA.INTERNAL
import kotlin.math.max
import kotlin.random.Random


fun buildMatrix(graph: Array<Array<Int>>, n: Int, m: Int, max: Array<Int>, min: Array<Int>) {
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

fun DijkstraAlgo(graph: Array<Array<Int>>, n:Int) : Array<Int> {
    println("Number of vertex from 0 to ${n - 1} for DijkstraAlgo")
    val k = readLine()!!.toInt()
    println()


    val visited = Array(n) { false }

    val d = Array(n) { 1000 }


    d[k] = 0

    for (i in 0 until n) {

        var v = -1

        for (j in 0 until n) {
            if (!visited[j]  && (v == -1 || d[j] < d[v]))
                v = j
        }

        if (d[v] == 1000) break
        visited[v] = true

        //create temp list
        var listv : List<Int> = emptyList()
        for (j in 0 until n){
            if (graph[v][j] > 0){
                listv = listv.plus(j)
            }
        }

        for (j in listv) {
            val len = graph[v][j]
            if (d[v] + len < d[j]) {
                d[j] = d[v] + len
            }

        }

    }

    return d
}

fun Ford_bellmanAlgo(graph: Array<Array<Int>>, n:Int) : Array<Int> {
    println("Number of vertex from 0 to ${n - 1} for Ford_bellmanAlgo")
    val k = readLine()!!.toInt()


    val d = Array(n) { 1000 }


    d[k] = 0

    for(m in 0 until n){
    for (i in 0 until n) {
        for (j in 0 until n) {
            if (graph[i][j] != 0) {
                if (d[j] > d[i] + graph[i][j])
                    d[j] = d[i] + graph[i][j]
            }
        }
    }
    }
    return d
}


fun main() {

    val n = try {
        print("Print n: ")
        val temp = readLine()!!.toInt()
        if (temp <= 0 || temp >= 1000) throw ExceptionInInitializerError()
        else temp
    }catch (e: ExceptionInInitializerError){
        println("Initializer error, allowable range [1; 1000)")
        return
    }



    val m =  try {
        print("Print m: ")
        val tmp = readLine()!!.toInt()
        if (tmp > n * (n - 1) / 2 || (tmp <= 0 || tmp >= 100000)) throw ExceptionInInitializerError()
        else tmp
    }
    catch(e: ExceptionInInitializerError) {
        println("Initializer error, m must be <= ${n * (n - 1) / 2} or m should be contained in range from 0 to 100000")
        return
    }

    val graph : Array<Array<Int>> = Array(n){Array(n){0}}

    val max = Array(1) {0}

    val min = Array(1) {0}


    buildMatrix(graph, n, m, max, min)


    printStats(n, m, max, min)



    val dDj = DijkstraAlgo(graph, n).forEach {
        print("$it ")
    }

    println()
    val dFb = Ford_bellmanAlgo(graph, n).forEach { print("$it ") }
    println()


    if(dDj == dFb) println("Algorithms are equel")
    else {
        println("Error, algorithms are not equel")
        return

    }
}