class View {
    fun printFileTree(depth: Int, fileTree: FileTree) {
        val name = fileTree.entryName.substringAfterLast("/")
        for (i in 0 until depth) {
            print("  |")
        }
        println("___$name")

        for (child in fileTree.subEntries) {
            printFileTree(depth + 1, child)
        }
    }

    fun printFile(fileList: MutableList<FileTree>) {
        println("Found ${fileList.size} files with specified name")
        for (file in fileList) {
            println("File found, creation time: ${file.fileTime}")
        }
    }

    fun printDirectory(directoryList: MutableList<FileTree>) {
        println("Found ${directoryList.size} directories with specified name")
        for (directory in directoryList) {
            println("Directory found, size: ${directory.getSubEntriesFileSize()} bytes")
        }
    }
}