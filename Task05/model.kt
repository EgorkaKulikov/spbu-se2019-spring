package zipviewer

import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipException
import java.util.zip.ZipFile
import kotlin.system.exitProcess

class Model(zipFileName: String) {
    val data = try {
        ZipFile(zipFileName)
    }
    catch (e: ZipException) {
        println("Cannot interpret as ZIP: $zipFileName!")
        exitProcess(1)
    }
    catch (e: IOException) {
        println("Cannot open file: $zipFileName!")
        exitProcess(2)
    }

    fun findEntryByName(entryName: String): MutableList<ZipEntry> {
        val entriesWithSameName: MutableList<ZipEntry> = mutableListOf()

        var searchedEntry = data.getEntry(entryName)
        if (searchedEntry != null) {
            if (searchedEntry.isDirectory)
                searchedEntry.size = getDirectorySize(data, searchedEntry)

            entriesWithSameName.add(searchedEntry)
        }

        for (entry in data.entries())
            if (entry.isDirectory) {
                val directoryName = entry.name
                searchedEntry = data.getEntry(directoryName + entryName)

                if (searchedEntry != null) {
                    if (searchedEntry.isDirectory)
                        searchedEntry.size = getDirectorySize(data, searchedEntry)

                    entriesWithSameName.add(searchedEntry)
                }
            }

        return entriesWithSameName
    }

    private fun getDirectorySize(data: ZipFile, directory: ZipEntry): Long {
        var size = 0L

        for (entry in data.entries()) {
            if (entry.name.contains(directory.name))
                size += entry.size
        }

        return size
    }
}