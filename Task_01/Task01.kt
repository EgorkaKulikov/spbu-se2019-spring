import kotlin.math.min

const val Max_Weight = 101
const val Min_Weight = 1

fun createGraph(countOfTops: Int , countOfEdges: Int): Array<Array<Int>> {
    val graph = Array(countOfTops){Array(countOfTops) {0}}
    var graphEdgesCount:Int = 0;
    while(graphEdgesCount < countOfEdges){
        val from = (0 until countOfTops - 1).random()
        val to = (from + 1 until countOfTops).random()
        if(graph[from][to] == 0){
            graph[from][to]=(Min_Weight until Max_Weight).random()
            graph[to][from]=graph[from][to]
            graphEdgesCount++
        }
    }

    return graph
}

fun printGraphStats(graph:Array<Array<Int>>){
    var maxWeight:Int = 0
    var minWeight:Int = 101
    var edgesCount: Int = 0
    var topsCount: Int = 0
    for(i in graph){
        for(k in i)
            if(k!=0) {
                if(k>maxWeight)
                    maxWeight = k
                if(k<minWeight)
                    minWeight = k
                edgesCount++
            }
        topsCount++
    }
    edgesCount/=2
    println("Count of edges: $edgesCount \n" + "Count of tops: $topsCount \n" + "Max weight: $maxWeight \n" + "Min weight: $minWeight \n")
}

fun dxtra(graph: Array<Array<Int>>, startTop:Int):Array<Int> {
    val countOfTops = graph.size
    val marked = Array(countOfTops) { false }
    val ranges = Array(countOfTops) {Max_Weight}
    ranges[startTop]=0

    for (i in 0 until countOfTops) {
        var viewingTop = -1
        for(j in 0 until countOfTops) {
            if (!marked[j] and (viewingTop == -1 || ranges[j] < ranges[viewingTop]))
                viewingTop = j
        }
            if(ranges[viewingTop] == Max_Weight+1)
                break
            marked[viewingTop] = true
        for(j in 0 until countOfTops){
            if(graph[viewingTop][j]!=0) {
                ranges[j] = min(ranges[j], ranges[viewingTop] + graph[viewingTop][j])
            }
        }

    }
    return ranges
}

fun fordBellman(graph: Array<Array<Int>>, startTop: Int):Array<Int>{
    val countOfTops = graph.size
    val ranges = Array(countOfTops) {Max_Weight}
    ranges[startTop]=0

    for(i in 0 until countOfTops-1){
        for (k in 0 until countOfTops){
            for(j in 0 until countOfTops){
                if(graph[k][j]!=0) {
                    ranges[j] = min(ranges[j], ranges[k] + graph[k][j])

                }
            }
        }
    }
    return ranges
}

fun printGraph(gr:Array<Array<Int>>){
    for(l in gr){
        for (s in l)
            print("$s ")
        print("\n")
    }

}
fun main(){
    print("Enter quantity of tops and number of edges:")
    val (n,m)= readLine()!!.split(' ').map{it.toInt()}

    val graph=createGraph(n,m)

    printGraph(graph)
    printGraphStats(graph)

    print("Enter a top to count from:")
     val k= readLine()!!.toInt()

    val dxtraRange=dxtra(graph,k)
    val fordBellmanRange=fordBellman(graph,k)
    var isSameResult = true
    for(i in 0 until m){
        if(dxtraRange[i]!=fordBellmanRange[i]) {
            isSameResult = false
        }
    }
    if(isSameResult){
        print("Algorithms has same result")
    }
    else{
        print("Algorithms has different result")
    }
}


