package zipviewer


import java.io.File
import kotlin.system.exitProcess


/*
  Weird constants are from .ZIP File Format Specification.
  https://pkware.cachefly.net/webdocs/casestudies/APPNOTE.TXT
*/
data class Zipfile(val zipname: String)
{ 
  public fun name() = filename
 
  public fun filesize() = filesize

  public fun struct(): List<String>
  {
    var structure = mutableListOf<String>()

    while (hasNext() == true)
    {
      next()
      structure.add(filename)
    }

    return structure.sorted()
  }

  public fun hasNext() : Boolean
  {
    if (size() >= 22 
      && bitHasNext == true 
      && bitNext == false)
    {
      bitNext = true

      when(bytesToLong(4))
      {
        0x04034B50L -> bitHasNext = true
        
        0x02014B50L,
        0x05064B50L -> bitHasNext = false
        
        else ->
        {
          println("Incorrect data.")
          exitProcess(3)
        }
      }

      return bitHasNext
    }
    else if (bitNext == true)
    {
      return bitHasNext
    }
    else
    {
        println("Incorrect size of data.")
        exitProcess(3)
    }
  }

  public fun next()
  {
    var offset = 0L
    
    zip.skip(2L)
  
    if (bytesToLong(1).toInt() and 8 == 8)
    {
      offset += 16L
    }
  
    zip.skip(3L)
    
    time = bytesToLong(2)
    date = bytesToLong(2)
    
    zip.skip(4L)
    
    
    offset += bytesToLong(4)
      
    filesize = bytesToLong(4)
    
    val nameOffset = bytesToLong(2).toInt()
    offset += bytesToLong(2)
    filename = bytesToString(nameOffset)
    
    if (zip.skip(offset) == -1L)
    {
      println("Error of positioning.")
      exitProcess(4)
    }

    bitNext = false
  }

/*
  Using MS-DOS date & time format.
  http://www.vsft.com/hal/dostime.htm
*/
  public fun year() = (1980 + (date and 0xFE00) / 0x200).toInt()

  public fun month() = ((date and 0x1E0) / 0x20).toInt()

  public fun day() = (date and 0x1F).toInt()

  public fun hour() = (time / 0x800).toInt()

  public fun minute() = ((time and 0x7E0) / 0x20).toInt()

  public fun second() = ((time and 0x1F) * 2).toInt()

  public fun closeStream() = zip.close()

  private val zip = File(zipname).inputStream()

  private fun size() = zip.available()

  private var filename = ""

  private var date = 0L

  private var time = 0L

  private var filesize = 0L

  private var bitHasNext = true

  private var bitNext = false

  private fun extract(size: Int) : ByteArray
  {
    val record = ByteArray(size)
    
    if (zip.read(record) == size)
    {
      return record
    }
    else
    {
      println("Error of reading.")
      exitProcess(4)
    }   
  }

  private fun bytesToLong(size: Int) : Long
  {
    val field = extract(size)
    var longField = 0L
    
    for (i in size-1 downTo 0)
    {
      longField *= 256L 
      val signedByte = field[i].toLong()
      
      if (field[i] < 0)
      {
        longField += signedByte + 256L
      }
      else
      {
        longField += signedByte
      }
    }

    return longField
  }

  private val charset = Charsets.UTF_8

  private fun bytesToString(size: Int) : String
  {
    return extract(size).toString(charset)
  }
}
