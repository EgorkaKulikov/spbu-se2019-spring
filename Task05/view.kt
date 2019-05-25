package zipinfo

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
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

fun printFindFile(time: LocalDateTime) {

    val formatter : DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-YYYY HH:mm")
    val formatDateTime: String = time.format(formatter)

    println("Creation date of the file: \n$formatDateTime")

}

fun printFindFolder(folderName: String, size: Long) = println("The size of the $folderName is $size bytes")
