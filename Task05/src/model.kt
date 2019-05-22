package zipviewer

import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import kotlin.system.exitProcess

class Model (archiveName: String) {

    private val zipFile: ZipFile
    init {
        try {
            zipFile = ZipFile(archiveName)
        } catch (e: Exception) {
            println("Caught ${e.toString().substringBefore(":")} " +
                    "while trying to open archive $archiveName")
            exitProcess(1)
        }
    }

    fun getFolderList(folderName: String): List<ZipEntry> {
        var folderList: MutableList<ZipEntry> = mutableListOf()

        for (entry in zipFile.entries()) {
            var name = entry.name

            if (("/$folderName/" in name || name.startsWith("$folderName/"))
                    && entry !in folderList) {
                folderList.add(entry)
            }
        }

        return folderList.toList()
    }

    fun getFolderSize(folder: String): Long {
        var folderSize = 0L

        for (entry in zipFile.entries()) {
            if (entry.name.startsWith(folder.substringBeforeLast("/"))) {
                folderSize += entry.size
            }
        }

        return folderSize
    }

    fun getFileList(fileName: String): List<ZipEntry> {
        var fileList: MutableList<ZipEntry> = mutableListOf()

        for (entry in zipFile.entries()) {
            if (entry.name.endsWith("/$fileName")) {
                fileList.add(entry)
            }
        }

        return fileList.toList()
    }

    internal fun getEntries(): List<ZipEntry> {
        return zipFile.entries().toList()
    }
}
