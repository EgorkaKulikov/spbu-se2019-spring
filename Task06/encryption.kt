package crypt


import java.io.File
import kotlin.random.Random


fun encrypt(input: String, output: String)
{
  val bmp = File(input).inputStream()
  val crypted = File(output).outputStream()

  bmp.skip(2)
  val lenghtbuf = ByteArray(4)
  var lenght = 0L
  bmp.read(lenghtbuf)
  for (i in 3 downTo 0)
  {
    lenght *= 256L
    lenght += lenghtbuf[i].toUByte().toLong()
  }
  bmp.skip(4)
  val offsetbuf = ByteArray(4)
  var offset = 0L
  bmp.read(offsetbuf)
  for (i in 3 downTo 0)
  {
    offset *= 256L
    offset += offsetbuf[i].toUByte().toLong()
  }
  bmp.skip(offset - 14L)

  var rbuf = ByteArray(2)
  var wbuf = ByteArray(3)
  var lbuf: Long

  for (i in 0..((lenght - offset)/2))
  {
    bmp.read(rbuf)

    lbuf = 0L
    for (j in 1 downTo 0)
    {
      lbuf *= 256L
      lbuf += rbuf[j].toUByte().toLong()
    }

    lbuf = haming(lbuf)

    for (j in 0..2)
    {
      wbuf[j] = (lbuf % 256L).toByte()
      lbuf /= 256L
    }

    crypted.write(wbuf)
  }

  bmp.close()
  crypted.close()
}

fun decrypt(input: String, output: String)
{
  val crypted = File(input).inputStream()
  val decrypted = File(output).outputStream()
  
  var buf = ByteArray(3)
  var lbuf: Long
  var ten = 0

  while ( crypted.read(buf) != -1)
  {
    lbuf = 0L
    
    for (i in 2 downTo 0)
    {
      lbuf *= 256L
      lbuf += buf[i].toUByte().toLong()
    }

    lbuf = dehaming(lbuf)
          
    for (i in 0..2)
    {
      buf[i] = (lbuf % 256L).toByte()
      lbuf /= 256L
    }

    decrypted.write(buf)
  }

  crypted.close()
  decrypted.close()
}

fun decode(origin: String, input: String, output: String)
{
  val table = File(origin).inputStream()
  val decoded = File(output).outputStream()

  val start = ByteArray(14)
  table.read(start)

  var lenght = 0L
  for (i in 5 downTo 2)
  {
    lenght *= 256L
    lenght += start[i].toUByte().toLong()
  }

  var offset = 0L
  for (i in 13 downTo 10)
  {
    offset *= 256L
    offset += start[i].toUByte().toLong()
  }

  decoded.write(start)

  val tobeg = ByteArray((offset - 14L).toInt())
  table.read(tobeg)
  decoded.write(tobeg)
  table.close()

  val encoded = File(input).inputStream()

  var rbuf = ByteArray(3)
  var wbuf = ByteArray(2)
  var lbuf: Long
  for (i in 0..((lenght - offset)/2))
  {
    encoded.read(rbuf)

    lbuf = 0L
    for (j in 2 downTo 0)
    {
      lbuf *= 256L
      lbuf += rbuf[j].toUByte().toLong()
    }

    lbuf = unhaming(lbuf).toLong()

    for (j in 0..1)
    {
      wbuf[j] = (lbuf % 256).toByte()
      lbuf /= 256
    }

    decoded.write(wbuf)
  }

  encoded.close()
  decoded.close()
}

fun noise(input: String, output: String, damage: Int)
{
  val origin = File(input).inputStream()
  val damaged = File(output).outputStream()

  var rbuf = ByteArray(1)
  var buf: UInt
  var log: UInt

  while (origin.read(rbuf) != -1)
  {
    buf = rbuf[0].toUInt()
    log = 1.toUInt()
    
    for (i in 0..7)
    {
      if (Random.nextInt(0, 100) < damage)
      {
        if (buf and log == 0.toUInt())
        {
          buf += log
        }
        else
        {
          buf -= log
        }
      }

      log *= 2.toUInt()
    }

    rbuf[0] = buf.toByte()

    damaged.write(rbuf)
  }

  origin.close()
  damaged.close()
}