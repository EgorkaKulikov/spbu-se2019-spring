import java.time.LocalDateTime

data class FileInfo(val size: Long, val date: LocalDateTime)

class DirectoryInfo {
    val directories = sortedMapOf<String, DirectoryInfo>()
    val files = sortedMapOf<String, FileInfo>()

    var size = 0L

    fun updateSize() {
        size = 0L

        for (directory in directories.values) {
            directory.updateSize()
            size += directory.size
        }

        for (file in files.values) {
            size += file.size
        }
    }

    private fun findDirectoryFromThis(path: List<String>): DirectoryInfo? {
        if (path.isEmpty()) {
            return this
        }
        
        val next = directories[path.first()]

        return next?.findDirectoryFromThis(path.subList(1, path.lastIndex + 1))
    }

    fun findDirectory(path: List<String>): DirectoryInfo? {
        findDirectoryFromThis(path)?.let { return it }

        for (directory in directories) {
            directory.value.findDirectory(path)?.let { return it }
        }

        return null
    }

    fun findFile(path: List<String>): FileInfo? {
        if (path.isEmpty()) {
            return null
        }

        findDirectoryFromThis(
            path.subList(0, path.lastIndex)
        )?.run { files[path.last()]?.let { return it } }

        for (directory in directories) {
            directory.value.findFile(path)?.let { return it }
        }

        return null
    }

    fun insertFile(path: List<String>, file: FileInfo): Boolean {
        if (path.isEmpty()) {
            return false
        }

        val directory = pavePath(path.subList(0, path.lastIndex))

        if (path.last() in directory.files) {
            return false
        }

        directory.files[path.last()] = file

        return true
    }

    fun pavePath(path: List<String>): DirectoryInfo {
        if (path.isEmpty()) {
            return this
        }

        val next = directories[path.first()] ?: DirectoryInfo().also {
            directories[path.first()] = it
        }

        if (path.size == 1) {
            return next
        }

        return next.pavePath(path.subList(1, path.lastIndex))
    }
}
