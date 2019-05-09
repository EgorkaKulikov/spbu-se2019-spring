package zipviewer


/*
  "All slashes MUST be forward slashes '/'"
  4.4.17.1 of .ZIP File Format Specification
*/
const val slash = '/'
const val indent = "    "


fun tree(zip: Zipfile)
{
  var dirname = ""
  var offset = 0

  while (zip.hasNext() == true)
  {
    zip.next()
    val filename = zip.name()

    while (filename.startsWith(dirname) == false)
    {
      offset--

      if (dirname.last().equals(slash))
      {
        dirname = dirname.dropLast(1)
      }
      
      dirname = dirname.dropLastWhile{!it.equals(slash)}
    }
    
    print(indent.repeat(offset))
    println(filename.drop(dirname.length))

    if (filename.endsWith(slash))
    {
      offset++
      dirname = filename
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
    println("Size of directory '$name' is $summarySize B.")
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

      val hour = zip.hour()
      val minute = zip.minute()
      val second = zip.second()
      
      print("'$filename' was lastly edited ")
      println("$day.$month.$year at $hour:$minute:$second.")
    }
  }

  if (existing == false)
  {
    println("No such file or directory.")
  }
}