package zipinfo

import java.util.zip.*

fun findFolder(zipFile: ZipFile, folderName: String) {

    var folderExist = false
    val enumeration = zipFile.entries()
    var folderSize : Long = 0

    while (enumeration.hasMoreElements())
    {
        val zipEntry = enumeration.nextElement() as ZipEntry
        val name = zipEntry.name
        val size = zipEntry.size
        if ("/$folderName/" in name) {
            folderSize += size
            folderExist = true
        }
    }

    if (!folderExist) {
        println("There's no such folder: $folderName")
        return
    }

   printFindFolder(folderName, folderSize)

}

fun findFile(zipFile: ZipFile, fileName: String) {

    val enumeration = zipFile.entries()

    while (enumeration.hasMoreElements())
    {
        val zipEntry = enumeration.nextElement() as ZipEntry
        val name = zipEntry.name
        if (fileName in name && !zipEntry.isDirectory) {
            printFindFile(zipEntry.timeLocal)
            return
        }
    }
    println("There's no such file: $fileName")

}
