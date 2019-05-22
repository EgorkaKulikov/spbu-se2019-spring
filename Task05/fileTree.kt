import java.nio.file.attribute.FileTime

enum class EntryType {
    DIRECTORY,
    FILE
}

class FileTree(
    var entryType: EntryType
    //default values for root directory
    , var entryName: String = ""
    , var fileSize: Long = 0L
    , var fileTime: FileTime? = FileTime.fromMillis(0)
) {
    var subTreeSize: Int = 1
    var subEntries: MutableList<FileTree> = mutableListOf()

    fun addSubEntry(subEntry: FileTree) {
        subEntries.add(subEntry)
        this.subTreeSize += subEntry.subTreeSize
    }

    fun findEntriesByName(name: String, type: EntryType): MutableList<FileTree> {
        val searchName = if (this.entryType == EntryType.DIRECTORY)
            this.entryName.removeSuffix("/").substringAfterLast("/")
        else
            this.entryName.substringAfterLast("/")

        if (searchName == name
            && this.entryType == type) {
            return mutableListOf(this)
        }

        val entryList = mutableListOf<FileTree>()

        for (subEntry in this.subEntries) {
            entryList.addAll(subEntry.findEntriesByName(name, type))
        }

        return entryList
    }

    fun getSubEntriesFileSize(): Long {
        var subEntriesFileSize = this.fileSize

        for (subEntry in this.subEntries) {
            subEntriesFileSize += subEntry.getSubEntriesFileSize()
        }
        return subEntriesFileSize
    }
}