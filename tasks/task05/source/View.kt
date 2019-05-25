interface View {

    fun showStructure(directoryInfo: DirectoryInfo)

    fun showFileInfo(file: FileInfo)

    fun showDirectoryInfo(directory: DirectoryInfo)

    fun showError(message: String)
}
