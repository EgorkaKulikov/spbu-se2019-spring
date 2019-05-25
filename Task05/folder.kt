import java.util.*
import java.util.zip.ZipEntry

class folder(_parent: folder?, _name: String) {
    val name = _name
    val parent = _parent
    val internalFolders = LinkedList<folder>()
    val internalFiles = LinkedList<ZipEntry>()
    fun addFolder(fullName: String): folder {
        var indexOfFolder = 0
        for (i in (fullName.length - 2) downTo 0) {
            if (fullName[i] == '/') {
                indexOfFolder = i + 1
                break
            }
        }
        val folderName = fullName.substring(indexOfFolder)
        val tempFolder = folder(this, folderName)
        internalFolders.add(tempFolder)
        return tempFolder
    }

    fun addFile(file: ZipEntry) {
        internalFiles.add(file)
    }
}