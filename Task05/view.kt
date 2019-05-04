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


/*
  Using MS-DOS date & time format.
  http://www.vsft.com/hal/dostime.htm
*/
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
      val date = zip.date()
      val time = zip.time()
      
      val year = 1980 + (date and 0xFE00) / 0x200
      val month = (date and 0x1E0) / 0x20
      val day = (date and 0x1F)
      
      val hour = time / 0x800
      val minute = (time and 0x7E0) / 0x20
      val second = (time and 0x1F) * 2
      
      print("'$filename' was lastly edited ")
      println("$day.$month.$year at $hour:$minute:$second.")
    }
  }

  if (existing == false)
  {
    println("No such file or directory.")
  }
}