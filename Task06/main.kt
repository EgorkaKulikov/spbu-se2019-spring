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

  for (i in 1 until args.size)
  {
    val damage = args[i].toIntOrNull()
    
    if (damage == null)
    {
      println("Can`t deform file with ${args[i]}%.")
    }
    else
    {
      val encrypted = filename.plus("enc")

      encrypt(filename, encrypted)

      val noised = filename.plus("dmg${args[i]}")

      noise(encrypted, noised, damage)

      val decrypted = filename.plus("dec${args[i]}")

      decrypt(noised, decrypted)

      val repared = filename.plus("rep${args[i]}")
      val crushed = filename.plus("crs${args[i]}")

      decode(filename, noised, crushed)
      decode(filename, decrypted, repared)
    }
  }
}