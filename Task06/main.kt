package crypt


import java.io.File
import kotlin.system.exitProcess

/*
  Encrypt, deform and decrypt BMP-image.
*/
fun main(args: Array<String>)
{
  if (args.size < 2)
  {
    println("Use following model:")
    println("%filename %percentdamage1 ... %percentdamageN")
    exitProcess(1)
  }

  val filename = args[0]

  if (File(filename).canRead() == false)
  {
    println("Error of reading $filename.")
    exitProcess(2)
  }

  val encrypted = filename.plus("enc")

  encrypt(filename, encrypted)

  for (i in 1 until args.size)
  {
    val filebmp = filename.dropLast(4)
    val damage = args[i].toIntOrNull()
    
    if (damage == null)
    {
      println("Can`t deform file with ${args[i]}%.")
    }
    else
    {
      val noised = filebmp.plus("dmg${args[i]}")

      noise(encrypted, noised, damage)

      val decrypted = filebmp.plus("dec${args[i]}")

      decrypt(noised, decrypted)

      val repared = filebmp.plus("rep${args[i]}.bmp")
      val crushed = filebmp.plus("crs${args[i]}.bmp")

      decode(filename, noised, crushed)
      decode(filename, decrypted, repared)

      File(noised).delete()
      File(decrypted).delete()
    }
  }
  
  File(encrypted).delete()
}