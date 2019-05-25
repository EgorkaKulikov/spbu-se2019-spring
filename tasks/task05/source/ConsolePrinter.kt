import java.time.format.DateTimeFormatter

class ConsolePrinter : View {

    private val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")

    private fun formatSize(size: Long): String {
        val firstLetter = arrayOf("", "Ki", "Mi", "Gi", "Ti", "Pi")
        var index = 0

        while (size shr (index + 1) * 10 != 0L && index < firstLetter.size) {
            index++
        }

        val unitSize = 1 shl index * 10

        val result = ((size.toFloat() / unitSize) * 10).toInt() / 10F

        return "$result ${firstLetter[index]}B"
    }

    private fun printDirectory(directory: DirectoryInfo, prefix: String = ""): Unit = with(directory) {
        var directoriesCount = directories.size
        var filesCount = files.size

        for (subFolder in directories) {
            directoriesCount--

            fun getSymbol(): String {
                return if (subFolder.value.directories.size > 0 || subFolder.value.files.size > 0) {
                    "╦"
                } else {
                    "═"
                }
            }

            if (directoriesCount > 0 || filesCount > 0) {
                println("$prefix╠═${getSymbol()} ${subFolder.key}")
                printDirectory(subFolder.value, "$prefix║ ")
            } else {
                println("$prefix╚═${getSymbol()} ${subFolder.key}")
                printDirectory(subFolder.value, "$prefix  ")
            }
        }

        for (file in files) {
            filesCount--

            if (filesCount > 0) {
                println("$prefix╠══ ${file.key}")
            } else {
                println("$prefix╚══ ${file.key}")
            }
        }
    }

    override fun showStructure(directoryInfo: DirectoryInfo) {
        printDirectory(directoryInfo)
    }

    override fun showFileInfo(file: FileInfo) {
        println("${formatSize(file.size)} | ${file.date.format(formatter)}")
    }

    override fun showDirectoryInfo(directory: DirectoryInfo) {
        println(formatSize(directory.size))
    }

    override fun showError(message: String) {
        println(message)
    }
}
