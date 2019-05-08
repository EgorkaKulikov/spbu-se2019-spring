package crypt


fun haming(_input: Long): Long
{
  var bits: Array<Int> = Array(21){0}
  var input = _input
  
  for (i in 0..20)
  {
    if (!(i in listOf(0, 1, 3, 7, 15)))
    {
      bits[i] = (input % 2).toInt()
      input /= 2
    }
  }

  var i = 1
  for (j in 0..4)
  {
    var k = i
    while (k <= 20)
    {
      if (k and i != 0)
      {
        bits[i - 1] += bits[k - 1]
      }
      k++
    }

    bits[i - 1] %= 2
    i *= 2
  }

  var res = 0L
  for (j in 20 downTo 0)
  {
    res *= 2L
    res += bits[j].toLong()
  }

  return res
}

fun unhaming(input: Long): Long
{
  var bits: Array<Int> = Array(16){0}
  var buf = input
  var offset = 0
  
  for (i in 0..20)
  {
    if (!(i in listOf(0, 1, 3, 7, 15)))
    {
      bits[i - offset] = (buf % 2).toInt()
    }
    else
    {
      offset++
    }

    buf /= 2L
  }

  buf = 0L
  for (i in 15 downTo 0)
  {
    buf *= 2L
    buf += bits[i].toLong()
  }

  return buf 
}

fun dehaming(input: Long): Long
{
  var repared = haming(unhaming(input))

  if (repared != input)
  {
    var position = 0
    var err: Long = repared xor input

    for (i in 1..21)
    {
      position += (err % 2).toInt() * i
      err /= 2
    }
    
    if (position <= 21)
    {
      err = 1L
      
      for (i in 2..position)
      {
        err *= 2L
      }

      if ((err and input) == 0L)
      {
        repared = input + err
      }
      else
      {
        repared = input - err
      }
    }
  }

  return repared
}