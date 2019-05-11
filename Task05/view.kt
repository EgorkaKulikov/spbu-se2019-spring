package zipinfo

import java.time.LocalDateTime
import java.util.zip.*

fun viewZip(zipFile: ZipFile) {


    val enumeration = zipFile.entries()

    while (enumeration.hasMoreElements())
    {
        val zipEntry = enumeration.nextElement() as ZipEntry
        val name = zipEntry.name
        println(name)
    }

}

fun printFindFile(time: LocalDateTime)  = println("Date of modification in format YYYY-MM-DD.T.HH:MM:SS: \n $time")

fun printFindFolder(folderName: String, size: Long) = println("The size of the $folderName is $size bytes")
