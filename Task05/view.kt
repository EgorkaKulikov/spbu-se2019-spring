package zipviewer


import java.time.format.DateTimeFormatter
import java.time.LocalDate
import java.time.LocalTime


/*
  "All slashes MUST be forward slashes '/'"
  4.4.17.1 of .ZIP File Format Specification
*/
const val slash = '/'
const val indent = "  "
const val multiple = "KMGTPEZY"

fun tree(zip: Zipfile)
{
  var dirname = ""
  var offset = 0
  val structure = zip.struct()

  for (record in structure)
  {
    while (record.startsWith(dirname) == false)
    {
      offset--

      if (dirname.last().equals(slash))
      {
        dirname = dirname.dropLast(1)
      }
      
      dirname = dirname.dropLastWhile{!it.equals(slash)}
    }
    
    print(indent.repeat(offset))
    println(record.drop(dirname.length))

    if (record.endsWith(slash))
    {
      offset++
      dirname = record
    }
  }
}


fun size(zip: Zipfile, name: String)
{
  var summarySize = 0L
  var existing = false
  
  while (zip.hasNext() == true)
  {
    zip.next()
    val filename = zip.name()
    
    if (filename.startsWith(name.plus(slash)) 
      || filename.contains(slash.plus(name).plus(slash)))
    {
      existing = true
      summarySize += zip.filesize()
    } 
  }

  if (existing == true)
  {
    var exponent = -1
    var rest = 0L

    while (summarySize >= 1024L)
    {
      exponent++
      rest = summarySize % 1024L
      summarySize /= 1024L
    }

    rest /= 100L
    var toDisplay = (summarySize + (rest / 10) ).toString()

    if (rest in 1..9)
    {
      toDisplay += ".$rest "
    }
    if (exponent >= 0)
    {
      toDisplay += "${multiple[exponent]}iB"
    }
    else
    {
      toDisplay += "B"
    }

    println("Size of directory '$name' is $toDisplay.")
  }
  else
  {
    println("Directory '$name' doesn`t exist.")
  }
}


fun time(zip: Zipfile, name: String)
{
  var existing = false

  while (zip.hasNext())
  {
    zip.next()
    val filename = zip.name()
    
    if (filename.endsWith(slash.plus(name)) 
      || filename.contentEquals(name))
    {
      existing = true
            
      val year = zip.year()
      val month = zip.month()
      val day = zip.day()

      val date = LocalDate.of(year, month, day)
      val formatedDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE)

      val hour = zip.hour()
      val minute = zip.minute()
      val second = zip.second()

      val time = LocalTime.of(hour, minute, second)
      val formatedTime = time.format(DateTimeFormatter.ISO_LOCAL_TIME)

      print("'$filename' was lastly edited ")
      println("on $formatedDate, at $formatedTime.")
    }
  }

  if (existing == false)
  {
    println("No such file or directory.")
  }
}
