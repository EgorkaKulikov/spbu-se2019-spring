class View(zipName : Model) {
    private val zip = zipName
    fun findFile(fileName: String) {
        val file = zip.getEntry(fileName)
        if (file == null) {
            println("There is no [$fileName] in [${zip.archiveName}]")
            return
        }
        print("$fileName exists in ")
        if (file.name.substringBeforeLast('/') == fileName)
            println("[${zip.archiveName}]")
        else
            println("[${file.name.substringBeforeLast('/')}]:")
        println("Date of modification in format {YYYY-MM-DD.T.HH:MM:SS}:")
        println("\t${file.timeLocal}")
    }

    fun getFolder(folderName: String) {
        val folderEntry = zip.getEntry(folderName)
        if (folderEntry == null) {
            println("Directory [$folderName] doesn't exist in [${zip.archiveName}]")
            return
        }
        if (!folderEntry.isDirectory) {
            println("[$folderName] refers to a file and not to a folder")
            return
        }
        val size = zip.getFoldersSize(folderName)
        print("Size of [$folderName]'s folder content is : ")
        print(
            when {
                size > 1 shl 30 -> "${size / (1 shl 30)} gb and ${(size % (1 shl 30)) / (1 shl 20)} mb."
                size > 1 shl 20 -> "${size / (1 shl 20)} mb and ${(size % (1 shl 20)) / 1024} kb."
                size > 1024 -> "${size / 1024} kb."
                else -> "$size b."
            }
        )

    }

    fun printZip() {
        val prefix = "../"
        if (zip.listOfEntries.isEmpty())
            println("Zip archive [${zip.archiveName}] is empty.")

        println("${(1421).toChar()} is a folder sign in further info:\n")
        for (entry in zip.listOfEntries) {
            val name = if (entry.isDirectory) entry.name.dropLast(1)
            else entry.name
            val depth = name.entrances('/')
            print("\t${" ".times(depth * 3)}${prefix.times(depth)}")
            if (entry.isDirectory)
                println("${(1421).toChar()}${name.substringAfterLast('/')}")
            else
                println(name.substringAfterLast('/'))
        }
    }

    private fun String.times(amount: Int): String {
        var result = ""
        for (i in 0 until amount)
            result += this
        return result
    }

    private fun String.entrances(char: Char): Int {
        var result = 0
        this.forEach { letter -> if (letter == char) ++result }
        return result
    }
}