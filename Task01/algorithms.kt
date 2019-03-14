package graph


fun algDijkstra(matrix: Array<Array<Int>>, start: Int): Array<Int>
{
  val size = matrix.size
  val map: Array<Int> = Array(size) {Int.MAX_VALUE}
  val visited: Array<Boolean> = Array(size) {false}
  
  var current: Int = start
  var min: Int
  
  map[start] = 0

  for (i in 0 until size)
  {
    if (matrix[i].all {it -> it == 0})
    {
      visited[i] = true
    }
  }
  
  while (visited.any {it -> it == false})
  {
    min = Int.MAX_VALUE
    
    for (i in 0 until size)
    {
      if (visited[i] == false
        && map[i] <= min)
      {
        min = map[i]
        current = i
      }
    }
    
    visited[current] = true
    
    for (i in 0 until size)
    {
      if (visited[i] == false
        && matrix[current][i] != 0
        && map[i] - matrix[current][i] > map[current])
      {
        map[i] = map[current] + matrix[current][i]
      }
    }
  }
  
  return map
}


fun algFordBellman(matrix: Array<Array<Int>>, start: Int): Array<Int>
{
  val size = matrix.size
  val map: Array<Int> = Array(size) {Int.MAX_VALUE}
  
  map[start] = 0

  for (i in 1 until size)
  {
    for (j in 0 until size)
    {
      for (k in 0 until size)
      {
        if (map[k] - map[j] > matrix[j][k]
          && matrix[j][k] != 0)
        {
          map[k] = map[j] + matrix[j][k]
        }
      }
    }
  }

  return map
}