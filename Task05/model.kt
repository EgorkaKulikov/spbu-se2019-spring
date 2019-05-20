import java.io.IOException
import java.util.zip.*
import kotlin.system.exitProcess

//index of root directory needed to deal with multiple entries in archive's root
const val ROOTINDEX = -1

class Model {
    var fileTree = FileTree(EntryType.DIRECTORY)

    fun extractData(archiveName: String) {
        try {
            val entries = ZipFile(archiveName).entries().toList()
            this.fileTree = generateFileTree(entries, ROOTINDEX)
        } catch (e: IOException) {
            println("Error opening file: ${e.message}")
            exitProcess(1)
        } catch (e: ZipException) {
            println("Error interpreting file as zip: ${e.message}")
            exitProcess(1)
        }
    }

    private fun generateFileTree(entries: List<ZipEntry>, entryIndex: Int): FileTree {
        val currTree: FileTree =
            if (entryIndex == ROOTINDEX)
                FileTree(EntryType.DIRECTORY)//root directory
            else
                FileTree(EntryType.DIRECTORY, getEntryName(entries[entryIndex]))

        val parentEntryName = currTree.entryName
        var subEntryIndex = entryIndex + 1

        while (subEntryIndex < entries.size
            && entries[subEntryIndex].name.startsWith(parentEntryName)) {
            if (entries[subEntryIndex].isDirectory) {
                val subEntry = generateFileTree(entries, subEntryIndex)
                currTree.addSubEntry(subEntry)
                subEntryIndex += subEntry.subTreeSize
            } else {
                val subEntryName = getEntryName(entries[subEntryIndex])
                val subEntryTime = entries[subEntryIndex].creationTime
                val subEntryFileSize = entries[subEntryIndex].size
                currTree.addSubEntry(FileTree(EntryType.FILE, subEntryName, subEntryFileSize, subEntryTime))
                subEntryIndex++
            }
        }
        return currTree
    }

    //removes last backslash in directory name
    private fun getEntryName(entry: ZipEntry): String {
        return if (entry.isDirectory) entry.name.dropLast(1)
        else entry.name
    }
}