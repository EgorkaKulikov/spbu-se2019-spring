import java.nio.file.attribute.FileTime

class FileTree(var entryName: String = "") {
    //entryName default value is for root directory
    var subEntries: MutableList<FileTree> = mutableListOf()
    var subTreeSize = 1
    //default values for root directory
    private var fileSize: Long = 0L
    var fileTime: FileTime = FileTime.fromMillis(0)

    //secondary constructor for non-root entries
    constructor(_fileSize: Long, _fileTime: FileTime, _entryName: String = "") : this(_entryName) {
        fileSize = _fileSize
        fileTime = _fileTime
    }

    fun getSubEntriesFileSize(): Long {
        var result = this.fileSize
        for (subEntry in this.subEntries) {
            result += subEntry.getSubEntriesFileSize()
        }
        return result
    }

    fun addSubEntry(subEntry: FileTree) {
        subEntries.add(subEntry)
        this.subTreeSize += subEntry.subTreeSize
    }

    fun findEntryByName(name: String): FileTree? {
        if (this.entryName.substringAfterLast("/") == name) {
            return this
        }

        for (subEntry in this.subEntries) {
            val result = subEntry.findEntryByName(name)
            if (result != null)
                return result
        }

        return null
    }
}