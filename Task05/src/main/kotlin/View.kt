package zipExplore

import java.time.format.DateTimeFormatter
import org.apache.commons.io.FileUtils

class View(private var archiveName: String) {

    private val archive = Model(archiveName)

    fun printArchive() {

        val archiveContent = archive.getContent()

        if (archiveContent.isEmpty()) {
            println("$archiveName is empty")
            return
        }

        val offsets: MutableMap<String, String> = mutableMapOf()

        for (i in archiveContent.indices) {

            val instance = archiveContent[i]
            val prefix = (instance.name.dropLast(1)).substringBeforeLast('/') + "/"
            val curOffset = offsets[prefix] ?: ""

            if (instance.isDirectory) {
                val suffix = (instance.name.dropLast(1)).substringAfterLast('/')
                offsets[instance.name] = "$curOffset╚${"═".repeat(suffix.length)}"
                println("$curOffset$suffix/")
            } else {
                val suffix = (instance.name).substringAfterLast('/')
                println("$curOffset$suffix")
            }

        }

    }

    fun printFolder(folderName: String) {

        if (folderName[folderName.lastIndex] != '/') {
            printFolder("$folderName/")
            return
        }

        val foundFolder = archive.findFolder(folderName.dropLast(1))

        if (foundFolder.isEmpty()) {
            println("There is no folder \"$folderName\" in $archiveName")
            return
        }

        for (instance in foundFolder) {
            println(
                "Folder: ${instance.name}, " +
                        "Size = ${FileUtils.byteCountToDisplaySize(archive.getFolderSize(instance.name))}"
            )
        }

    }

    fun printFile(fileName: String) {

        val foundFile = archive.findFile(fileName)

        if (foundFile.isEmpty()) {
            println("There is no file \"$fileName\" in $archiveName")
            return
        }

        val formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY HH:mm")

        for (instance in foundFile) {
            println(
                "File: ${instance.name}, " +
                        "Creation date = ${archive.getFileCreationDate(instance.name).format(formatter)}"
            )
        }

    }

}
