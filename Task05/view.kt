import java.nio.file.attribute.FileTime

class View {
    fun printFileTree(depth: Int, fileTree: FileTree) {
        val name = fileTree.entryName.substringAfterLast("/")
        for (i in 0 until depth) {
            print("  |")
        }
        println("__$name")

        for (child in fileTree.subEntries) {
            printFileTree(depth + 1, child)
        }
    }

    fun printFile(file: Pair<Boolean, FileTime>) {
        if (file.first) {
            println("File found, creation time: ${file.second}")
        } else {
            println("File not found")
        }
    }

    fun printDirectory(directory: Pair<Boolean, Long>) {
        if (directory.first) {
            println("Directory found, size: ${directory.second} bytes")
        } else {
            println("Directory not found")
        }
    }
}