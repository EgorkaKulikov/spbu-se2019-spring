package zipviewer


import java.io.File
import kotlin.system.exitProcess


fun main(args: Array<String>)
{
  if (!(args.size == 2 && args[0].contentEquals("tree") 
    || args.size == 3 && args[0] in listOf("size", "time")))
  {
    println("Wrong arguments.")
    println("Use one of following models:")
    println("tree %zipname")
    println("size %zipname %dirname")
    println("time %zipname %filename")
    exitProcess(1)
  }
    
  val zipname = args[1]
  
  if (File(zipname).canRead() == false)
  {
    println("Can`t read '$zipname'.")
    exitProcess(2)
  }
  
  val opened = Zipfile(zipname)
  
  when (args[0])
  {
    "tree" -> tree(opened)
    "size" -> size(opened, args[2])
    "time" -> time(opened, args[2])
  }
  
  opened.closeStream()
}
