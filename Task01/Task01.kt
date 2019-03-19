const val INFINITY = 1000001
const val MAX_WEIGHT = 100

fun addGraph(size: Int, numOfEdges: Int): Array<Array<Int>>{

    val matrix: Array<Array<Int>> = Array(size) {Array(size) {0}}
    var counterOfCreatedEdges = 0

    while(counterOfCreatedEdges != numOfEdges) {
        val rnd1 = (0 until size).random()
        val rnd2 = (0 until size).random()
        val value = (1 until MAX_WEIGHT).random()
        if ((matrix[rnd1][rnd2] == 0) && (rnd1 != rnd2)) {
            matrix[rnd1][rnd2] = value
            matrix[rnd2][rnd1] = value
            counterOfCreatedEdges++
        }
    }

    return matrix
}

//if graph hasn't any edges, max and min weights of edges is zeros
fun printInformationAboutGraph(matrix: Array<Array<Int>>){

    println("Num of vertices = ${matrix.size}")

    var numOfEdges = 0
    for(i in 0 until matrix.size){
        for(j in (i + 1) until matrix.size)
            if(matrix[i][j] != 0)
                numOfEdges++
    }
    println("Num of edges = $numOfEdges")

    var maxWeight = 0
    var minWeight = INFINITY
    for(i in 0..(matrix.size - 1)){
        for(j in 0..(matrix.size - 1)){
            if(matrix[i][j] >= maxWeight)
                maxWeight = matrix[i][j]
            if(matrix[i][j] <= minWeight && matrix[i][j] != 0)
                minWeight = matrix[i][j]
        }
    }
    println("Max weight = $maxWeight")

    if(minWeight == INFINITY)
        println("Min weight = 0")
    else
        println("Min weight = $minWeight")
}

fun dijkstra(matrix: Array<Array<Int>>, start: Int): Array<Int>{

    val lengths: Array<Int> = Array(matrix.size) {INFINITY}
    lengths[start] = 0
    val visited = Array(matrix.size) {false}

    for(i in 0..(matrix.size - 1)){
        var v = -1
        for(j in 0..(matrix.size - 1))
            if(!visited[j] && (v == -1 || lengths[j] < lengths[v]))
                v = j
        if(lengths[v] == INFINITY)
            break
        visited[v] = true
        for(j in 0..(matrix.size - 1)){
            if(matrix[v][j] == 0)
                continue
            if(lengths[v] + matrix[v][j] < lengths[j])
                lengths[j] = lengths[v] + matrix[v][j]
        }
    }

    return lengths
}

fun fordBellman(matrix: Array<Array<Int>>, start: Int): Array<Int>{

    val lengths: Array<Int> = Array(matrix.size) {INFINITY}
    lengths[start] = 0

    for(k in 1..(matrix.size - 1))
        for(i in 0..(matrix.size - 1))
            for(j in 0..(matrix.size - 1))
                if((matrix[i][j] + lengths[i] < lengths[j]) && (matrix[i][j] != 0))
                    lengths[j] = lengths[i] + matrix[i][j]

    return lengths
}

fun areTheAlgorithmsEquivalent(matrix: Array<Array<Int>>, start: Int): Boolean{

    val dijkstraResult = dijkstra(matrix, start)
    val fordBellmanResult = fordBellman(matrix, start)

    return dijkstraResult contentEquals fordBellmanResult
}

fun main(args: Array<String>){

    val scan = java.util.Scanner(System.`in`)

    val N: Int = scan.nextInt()
    val M = scan.nextInt()
    val k = scan.nextInt()
    val graph: Array<Array<Int>> = addGraph(N, M)

    printInformationAboutGraph(graph)
    print(areTheAlgorithmsEquivalent(graph,k - 1))
}