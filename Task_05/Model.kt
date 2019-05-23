import java.io.IOException
import java.util.zip.*
import kotlin.system.exitProcess


class Model (fileName : String){


    val zipFile =try {
        ZipFile(fileName)
    }
    catch (e: ZipException){
       println("there is no zip file")
        exitProcess(1)
    }
    catch (e : IOException){
        println("can't open zip file")
        exitProcess(2)
    }


    fun findInFile(entryName: String): MutableList<ZipEntry> {
        val filesWithSameName: MutableList<ZipEntry> = mutableListOf()

        var searchedEntry = zipFile.getEntry(entryName)
        if (searchedEntry != null) {
            if (searchedEntry.isDirectory)
                searchedEntry.size = getFileSize(searchedEntry)

            filesWithSameName.add(searchedEntry)
        }

        for (entry in zipFile.entries())
            if (entry.isDirectory) {
                val directoryName = entry.name
                searchedEntry = zipFile.getEntry(directoryName + entryName)

                if (searchedEntry != null) {
                    if (searchedEntry.isDirectory)
                        searchedEntry.size = getFileSize( searchedEntry)

                    filesWithSameName.add(searchedEntry)
                }
            }

        return filesWithSameName
    }

    private fun getFileSize(dir: ZipEntry): Long {
        var size = 0L

        for (entry in zipFile.entries()) {
            if (entry.name.contains(dir.name))
                size += entry.size
        }

        return size
    }
}


