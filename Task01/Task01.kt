val scan = java.util.Scanner(System.`in`)

const val INF = 1000001

fun addGraph(size: Int, numOfEdges: Int): Array<Array<Int>>{

    val matrix: Array<Array<Int>> = Array(size) {Array(size) {0}}
    var i = 0

    while(i != numOfEdges) {
        val rnd1 = (0..(size - 1)).random()
        val rnd2 = (0..(size - 1)).random()
        val value = (1..99).random()
        if ((matrix[rnd1][rnd2] == 0) && (rnd1 != rnd2)) {
            matrix[rnd1][rnd2] = value
            matrix[rnd2][rnd1] = value
            i++
        }
    }

    return matrix
}

fun printInformationAboutGraph(matrix: Array<Array<Int>>){

    println("Num of vertices = ${matrix.size}")

    var numOfEdges = 0
    for(i in 0..(matrix.size - 1)){
        for(j in 0..(matrix.size - 1))
            if(matrix[i][j] != 0)
                numOfEdges++
    }
    println("Num of edges = ${numOfEdges / 2}")

    var maxWeight = 0
    var minWeight = 101
    for(i in 0..(matrix.size - 1)){
        for(j in 0..(matrix.size - 1)){
            if(matrix[i][j] >= maxWeight)
                maxWeight = matrix[i][j]
            if(matrix[i][j] <= minWeight && matrix[i][j] != 0)
                minWeight = matrix[i][j]
        }
    }
    println("Max weight = $maxWeight")

    if(minWeight == 101)
        println(0)
    else
        println("Min weight = $minWeight")
}

fun dijkstra(matrix: Array<Array<Int>>, start: Int): Array<Int>{

    val lengths: Array<Int> = Array(matrix.size) {INF}
    lengths[start] = 0
    val visited = Array(matrix.size) {false}

    for(i in 0..(matrix.size - 1)){
        var v = -1
        for(j in 0..(matrix.size - 1))
            if(!visited[j] && (v == -1 || lengths[j] < lengths[v]))
                v = j
        if(lengths[v] == INF)
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

    val lengths: Array<Int> = Array(matrix.size) {INF}
    lengths[start] = 0

    for(k in 1..(matrix.size - 1))
        for(i in 0..(matrix.size - 1))
            for(j in 0..(matrix.size - 1))
                if((matrix[i][j] + lengths[i] < lengths[j]) && (matrix[i][j] != 0))
                    lengths[j] = lengths[i] + matrix[i][j]

    return lengths
}

fun areTheAlgorithmsEquivalent(matrix: Array<Array<Int>>, start: Int): Boolean{

    val djkstr = dijkstra(matrix, start - 1)
    val frdBllmn = fordBellman(matrix, start - 1)
    var i = 0

    while(i != matrix.size - 1){
        if(djkstr[i] != frdBllmn[i])
            break
        i++
    }

    return i == matrix.size - 1
}

fun main(args: Array<String>){

    val N: Int = scan.nextInt()
    val M = scan.nextInt()
    val k = scan.nextInt()
    val graph: Array<Array<Int>> = addGraph(N, M)

    printInformationAboutGraph(graph)
    print(areTheAlgorithmsEquivalent(graph,k))
}