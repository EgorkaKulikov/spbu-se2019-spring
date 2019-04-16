val MAX_DISTANCE = 101
val MIN_DISTANCE = 1
val INF = MAX_DISTANCE * 1001


fun createGraph(n: Int, m: Int): Array<Array<Int>> {
    var randVrtx1: Int
    var randVrtx2: Int
    val graph: Array<Array<Int>> = Array(n, { Array(n, {0}) })
    var edgeCreated = false

    if(n>1 && m>0) {
        for (i in 0 until m) {
            while (edgeCreated == false) {
                randVrtx1 = (0..(n - 2)).random()
                randVrtx2 = ((randVrtx1 + 1)..(n - 1)).random()

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

fun stats(n: Int, m: Int, graph: Array<Array<Int>>) {
    var min = MAX_DISTANCE
    var max = MIN_DISTANCE

    for (i in 0 until n) {
        for (j in i until n) {
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
    println("$n вершин")
    if (m > 0) {
        println("$m рёбер")
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

fun  algoFordBellman(start: Int, graph: Array<Array<Int>>) : Array<Int>{
    val distance: Array<Int> = Array(graph.size){INF}
    distance[start] = 0
    var stop: Boolean

    do{
        stop = true
        for(i in 0 until graph.size) {
            for(j in 0 until graph.size){
                if(graph[i][j] != 0 && distance[j] > distance[i] + graph[i][j]){
                    distance[j] = distance[i] + graph[i][j]
                    stop = false
                }
            }
        }
    }while(!stop)

    return distance
}


fun main() {
    println("Enter number of vertexes and number of edges")
    val data = readLine().toString()
    val n = data.substringBefore(' ').toInt()
    val m = data.substringAfter(' ').toInt()

    if (n>1000 || m>100000 || n<=0 || m<0){
        println("Data is incorrect")
        return
    }

    val graph = createGraph(n, m)
    stats(n, m, graph)


    println("Enter vertex")
    var k = readLine()!!.toInt()
    k--

    if (k<0 || k>=n){
        println("Data is incorrect")
        return
    }

    val distDijkstra = algoDijkstra(k, graph)
    val distFordBellman = algoFordBellman(k, graph)

    if( distDijkstra contentEquals distFordBellman) {
        println("Algorithms are equal")
    }
    else{
        println("There is mistake somewhere...")
    }
}