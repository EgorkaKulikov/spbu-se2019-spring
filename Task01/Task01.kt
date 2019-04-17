val MAX_DISTANCE = 101
val MIN_DISTANCE = 1
val MAX_NUM_VRTX = 1000
val MAX_NUM_EDGE = 10000
val INF = MAX_DISTANCE * MAX_NUM_VRTX


fun createGraph(numVrtx: Int, numEdge: Int): Array<Array<Int>> {
    var randVrtx1: Int
    var randVrtx2: Int
    val graph: Array<Array<Int>> = Array(numVrtx, { Array(numVrtx, {0}) })
    var edgeCreated = false

    if(numVrtx>1 && numEdge>0) {
        for (i in 0 until numEdge) {
            while (edgeCreated == false) {
                randVrtx1 = (0..(numVrtx - 2)).random()
                randVrtx2 = ((randVrtx1 + 1)..(numVrtx - 1)).random()

                if (graph[randVrtx1][randVrtx2] == 0) {
                    graph[randVrtx1][randVrtx2] = (MIN_DISTANCE..(MAX_DISTANCE-1)).random()
                    graph[randVrtx2][randVrtx1] = graph[randVrtx1][randVrtx2]
                    edgeCreated = true
                }
            }
            edgeCreated = false
        }
    }

    return graph
}

fun stats(numVrtx: Int, numEdge: Int, graph: Array<Array<Int>>) {
    var min = MAX_DISTANCE
    var max = MIN_DISTANCE

    for (i in 0 until numVrtx) {
        for (j in i until numVrtx) {
            if (graph[i][j] != 0) {
                if (graph[i][j] < min) {
                    min = graph[i][j]
                }
                if (graph[i][j] > max) {
                    max = graph[i][j]
                }
            }
        }
    }

    println("В графе:")
    println("$numVrtx вершин")
    if (numEdge > 0) {
        println("$numEdge рёбер")
        println("$min минимальное ребро")
        println("$max максимальное ребро")
    } else {
        println("Рёбер нет")
    }
}

fun algoDijkstra(start: Int, graph: Array<Array<Int>>) : Array<Int>{
    val n = graph.size
    val distance = Array(n){INF}
    val used = Array(n){false}
    distance[start] = 0
    for (i in 0 until n) {
        var curVrtx = -1
        for (j in 0 until n) {
            if (used[j] == false && (curVrtx == -1 || distance[j] < distance[curVrtx])) {
                    curVrtx = j
                }
        }
        if (distance[curVrtx] == INF) {
            break
        }
        used[curVrtx] = true
        for (j in 0 until n) {
            if (graph[curVrtx][j] != 0) {
                if(distance[j] > (distance[curVrtx] + graph[curVrtx][j])){
                    distance[j] = distance[curVrtx] + graph[curVrtx][j]
                }
            }
        }
    }
    return distance
}

fun  algoFordBellman(start: Int, numEdge: Int, graph: Array<Array<Int>>) : Array<Int>{
    val distance: Array<Int> = Array(graph.size){INF}
    data class Edge(val start: Int, val end: Int, val dist: Int)
    val queue: Array<Edge> = Array(numEdge){Edge(0,0,0)}
    distance[start] = 0
    var stop : Boolean
    var iter = 0

    for(i in 0 until graph.size) {
        for(j in i until graph.size) {
            if(graph[i][j] != 0){
                queue[iter] = Edge(i, j, graph[i][j])
                iter++
            }
        }
    }

    do{
        stop = false
        for(i in 0 until numEdge) {
            if (distance[queue[i].end] > distance[queue[i].start] + queue[i].dist) {
                distance[queue[i].end] = distance[queue[i].start] + queue[i].dist
                stop = true
            }
            if (distance[queue[i].start] > distance[queue[i].end] + queue[i].dist) {
                distance[queue[i].start] = distance[queue[i].end] + queue[i].dist
                stop = true
            }
        }

    }while(stop)

    return distance
}


fun main() {
    println("Enter number of vertexes and number of edges")
    val data = readLine().toString()
    val numVrtx = data.substringBefore(' ').toInt()
    val numEdge = data.substringAfter(' ').toInt()

    if (numVrtx>MAX_NUM_VRTX || numEdge>MAX_NUM_EDGE || numVrtx<=0 || numEdge<0){
        println("Data is incorrect")
        return
    }

    val graph = createGraph(numVrtx, numEdge)
    stats(numVrtx, numEdge, graph)

    var k = 0
    println("Enter vertex")
    try {
        k = readLine()!!.toInt()
    }
    catch (e: NumberFormatException){
        println("Data is incorrect")
    }
    k--

    if (k<0 || k>=numVrtx){
        println("Data is incorrect")
        return
    }

    val distDijkstra = algoDijkstra(k, graph)
    val distFordBellman = algoFordBellman(k, numEdge, graph)

    if( distDijkstra contentEquals distFordBellman) {
        println("Algorithms are equal")
    }
    else{
        println("There is mistake somewhere...")
    }
}