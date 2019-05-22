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
            this.fileTree = generateFileTree(entries.sortedBy { it.name }, ROOTINDEX)
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
                FileTree(EntryType.DIRECTORY, entries[entryIndex].name)

        val currEntryName = currTree.entryName
        var subEntryIndex = entryIndex + 1

        while (subEntryIndex < entries.size
            && entries[subEntryIndex].name.startsWith(currEntryName)
        ) {
            if (entries[subEntryIndex].isDirectory) {
                val subEntry = generateFileTree(entries, subEntryIndex)
                currTree.addSubEntry(subEntry)
                subEntryIndex += subEntry.subTreeSize
            } else {
                currTree.addSubEntry(
                    FileTree(
                        EntryType.FILE, entries[subEntryIndex].name
                        , entries[subEntryIndex].size, entries[subEntryIndex].creationTime
                    )
                )
                subEntryIndex++
            }
        }
        return currTree
    }
}