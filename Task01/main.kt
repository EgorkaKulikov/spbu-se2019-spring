import graph.*


fun main(args: Array<String>)
{
  val (N, M, K) = extractArgs(args)
  val matrix = createMatrix(N, M)
  
  analyseMatrix(matrix)

  val res1: Array<Int> = algDijkstra(matrix, K)
  val res2: Array<Int> = algFordBellman(matrix, K)
  
  val equality: Boolean = res1.contentEquals(res2)
  print("It`s $equality that ")
  println("Dijkstra`s and Ford-Bellman`s algorithms give the same result.")
}