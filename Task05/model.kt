package zipviewer

/*
Weird constants are from .ZIP File Format Specification.
https://pkware.cachefly.net/webdocs/casestudies/APPNOTE.TXT
*/


import java.io.File
import kotlin.system.exitProcess


data class zipfile(val zipname: String)
{	
	public fun name() = filename
	
	public fun time() = time

	public fun date() = date

	public fun filesize() = filesize

	public fun hasNext() : Boolean
	{
		if (size() >= 22 
			&& bitHasNext == true 
			&& bitNext == false)
		{
			when(bytesToLong(4))
			{
				0x04034B50L -> 
				{
					bitNext = true
					return true
				}
				0x02014B50L -> 
				{
					bitHasNext = false
					bitNext = true
					return false
				}
				0x05064B50L -> 
				{
					bitHasNext = false
					bitNext = true
					return false
				}
				else ->
				{
					println("Incorrect data.")
					exitProcess(3)
				}
			}
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
	
		if (bytesToLong(1).toUByte().toInt() and 8 == 8)
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


	private val zip = File(zipname).inputStream()

	private fun size() = zip.available()

	private var filename = ""

	private var time = 0L

	private var date = 0L

	private var filesize = 0L

	private var bitHasNext = true

	private var bitNext = false

	private fun bytesToLong(size: Int) : Long
	{
		val field = extract(size)
		var longField = 0L
		
		for (i in size-1 downTo 0)
		{
			longField *= 256L 
			longField += field[i].toUByte().toLong()
		}

		return longField
	}

	private	val charset = Charsets.UTF_8

	private fun bytesToString(size: Int) : String
	{
		return extract(size).toString(charset)
	}

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
}