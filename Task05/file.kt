import java.util.TreeMap

class Directory(val name: String, val fullpath: String) {
    var size: Long = 0
        private set
    val folders = TreeMap<String, Directory>()
    val files = TreeMap<String, FileStructure>()

    fun incSize(value: Long) {
        size += value
    }
}

data class FileStructure(
    val name: String,
    val fullpath: String,
    val size: Long,
    val timestamp: String
)