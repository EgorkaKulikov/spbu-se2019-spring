val MAX_LENGTH = 101
val MIN_LENGTH = 1


fun Create(n: Int, m: Int): Array<Array<Int>> {
    var rand1: Int
    var rand2: Int
    val graph: Array<Array<Int>> = Array(n, { Array(n, {0}) })
    var trigger = false

    if(n>1 && m>0) {
        for (i in 0 until m) {
            while (trigger == false) {
                rand1 = (0..(n - 2)).random()
                rand2 = ((rand1 + 1)..(n - 1)).random()

                if (graph[rand1][rand2] == 0) {
                    graph[rand1][rand2] = (MIN_LENGTH..(MAX_LENGTH-1)).random()
                    graph[rand2][rand1] = graph[rand1][rand2]
                    trigger = true
                }
            }
            trigger = false
        }
    }

    return graph
}

fun Stats(n: Int, m: Int, graf: Array<Array<Int>>) {
    var min = MAX_LENGTH
    var max = MIN_LENGTH

    for (i in 0 until n) {
        for (j in i until n) {
            if (graf[i][j] != 0) {
                if (graf[i][j] < min) {
                    min = graf[i][j]
                }
                if (graf[i][j] > max) {
                    max = graf[i][j]
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
    val distance = Array(n){MAX_LENGTH*1001}
    val off = Array(n){false}
    distance[start] = 0
    for (i in 0 until n) {
        var curVrtx = -1
        for (j in 0 until n) {
            if (off[j] == false && (curVrtx == -1 || distance[j] < distance[curVrtx])) {
                    curVrtx = j
                }
            }
            if (distance[curVrtx] == MAX_LENGTH + 1) {
                break
            }
            off[curVrtx] = true
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
    val distance: Array<Int> = Array(graph.size){MAX_LENGTH*1001}
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

    val graph = Create(n, m)
    Stats(n, m, graph)


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