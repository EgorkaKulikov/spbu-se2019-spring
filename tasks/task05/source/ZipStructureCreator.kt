import java.util.zip.ZipFile

fun createStructureOfZipFile(name: String): DirectoryInfo? {
    val file = try {
        ZipFile(name)
    } catch(exception: Exception) {
        return null
    }

    val rootDirectory = DirectoryInfo()

    for (entry in file.entries()) {
        val path = pathTo(entry.name)

        if (entry.isDirectory) {
            rootDirectory.pavePath(path)
        } else {
            rootDirectory.insertFile(path, FileInfo(entry.size, entry.timeLocal))
        }
    }

    file.close()

    return rootDirectory
}
