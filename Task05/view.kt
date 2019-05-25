class View {
    fun printFileTree(depth: Int, fileTree: FileTree) {
        var name = fileTree.entryName

        if (fileTree.entryType == EntryType.DIRECTORY) {
            name = fileTree.entryName.dropLast(1)//removing last backslash
        }

        name = name.substringAfterLast("/")

        for (i in 0 until depth) {
            print("  |")
        }
        println("___$name")

        for (subEntry in fileTree.subEntries) {
            printFileTree(depth + 1, subEntry)
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