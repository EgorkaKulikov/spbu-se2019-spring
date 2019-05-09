class Archive {
    data class FileInstance(
            val path: String,
            val depth: Int,
            val name: String,
            val isFolder: Boolean,
            val timestamp: String?,
            val size: Int?
    )
    val files = mutableListOf<FileInstance>()
    var targetFileStated = false
    var targetFolderStated = false
}