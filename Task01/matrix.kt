package graph

import kotlin.random.Random
import kotlin.system.exitProcess


fun extractArgs(args: Array<String>): Triple<Int, Int, Int>
{
  if (args.size != 3)
  {
    println("Wrong number of arguments!")
    println("Pass 3 arguments: N, M & K.")
    exitProcess(1)
  }

  if (args[0].toIntOrNull() == null ||
      args[1].toIntOrNull() == null ||
      args[2].toIntOrNull() == null)
  {
    println("Wrong type of arguments!")
    println("Arguments must be integer.")
    exitProcess(1)
  }

  if (args[2].toInt() !in 0..(args[0].toInt() - 1))
  {
    println("Wrong value!")
    println("Variable K have to be in 0..(N-1).")
    exitProcess(1)
  }
  
  return Triple(args[0].toInt(), args[1].toInt(), args[2].toInt())
}


fun createMatrix(N: Int, M: Int): Array<Array<Int>>
{
  var nodes: Int = 0
  var zeroes: Int = if (N*(N - 1)/2 > M) {N*(N - 1)/2 - M} else 0
  
  val matrix: Array<Array<Int>> = Array(N, {Array(N, {0})})
  
  for (i in 0..(N-1))
  {
    for (j in 0..(N-1))
    {
      when
      {
        (j < i) ->
        {
          matrix[i][j] = matrix[j][i]
        }

        (zeroes != 0) && (nodes < M) &&
        (j > i) && (Random.nextInt(0, 2) == 0) ->
        {
          matrix[i][j] = 0
          zeroes--
        }

        (nodes < M) && (j > i) ->
        {
          matrix[i][j] = Random.nextInt(1, 101)
          nodes++
        }

        else ->
        {
          matrix[i][j] = 0
        }
      }
    }
  }

  return matrix
}


fun analyseMatrix(matrix : Array<Array<Int>>)
{
  val size = matrix.size
  
  var routs = 0
  var min = 101
  var max = 0
  var node: Int
  
  for (i in 0..(size - 1))
  {
    inner@ for (j in i..(size - 1))
    {
      node = matrix[i][j]
      
      when (node)
      {
        0           -> continue@inner
        in 1..100   ->
        {
          routs++
          
          if (node in 1..min)
          {
            min = node
          }

          if (node in max..100)
          {
            max = node
          }
        }
      }
    }
  }

  if (min == 101)
  {
    min = 0
  }

  println("Matrix size is $size.")
  println("It includes $routs routs.")
  println("Minimal rout is $min.")
  println("Maximal rout is $max.")
}