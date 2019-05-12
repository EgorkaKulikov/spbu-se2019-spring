package zipExplore

import java.time.LocalDateTime
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import kotlin.system.exitProcess

class Model(archiveName: String) {

    private var file: ZipFile

    init {

        try {
            file = ZipFile(archiveName)
        } catch (ex: Exception) {
            println("In Model:\n\t$ex")
            exitProcess(2)
        }

    }

    private fun findByName(name: String): Array<ZipEntry> {

        val result: MutableList<ZipEntry> = mutableListOf()
        val enum = file.entries()

        while (enum.hasMoreElements()) {
            val curEntry = enum.nextElement() as ZipEntry
            if (curEntry.name.endsWith(name)) {
                result.add(curEntry)
            }
        }

        return result.toTypedArray()

    }

    fun getFolderSize(folderName: String): Long {

        var folderSize: Long = 0
        val enum = file.entries()

        while (enum.hasMoreElements()) {
            val curEntry = enum.nextElement() as ZipEntry
            if (curEntry.name.startsWith(folderName)) {
                folderSize += curEntry.size
            }
        }

        return folderSize

    }

    fun getFileCreationDate(fileName: String): LocalDateTime = findByName(fileName)[0].timeLocal

    fun findFolder(folderName: String): Array<ZipEntry> = findByName("$folderName/")

    fun findFile(fileName: String): Array<ZipEntry> = findByName(fileName)

    fun getContent(): List<ZipEntry> = file.entries().toList()

}
