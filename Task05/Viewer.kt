import java.util.zip.ZipEntry

const val COMMANDS = "general_info\n\t" +
                     "folder_info\n\t" +
                     "file_info\n"

const val USER_GUIDE = "Enter data in the next format: \n\t" +
                        "<path name> <command name> <name of folder or file>\n" +
                        "Commands to use: \n\t$COMMANDS" +
                        "NOTE: Third argument needed only for commands " +
                        "\"folder_info\" and " +
                        "\"file_info\""

class Viewer {
    fun printDirectoryTree(listFiles: List<ZipEntry>) {
        var lastDirectory = mutableListOf<String>()

        for (files in listFiles) {
            var currName = files.name

            if (!currName.contains("/")) {
                println(currName)
                continue
            }

            val newDirectory = mutableListOf<String>()
            for (folder in lastDirectory){
                if (currName.contains(folder)) {
                    newDirectory.add(folder)
                }
            }
            lastDirectory = newDirectory

            var index = 1

            while (currName.contains("/")) {
                val currDirectory = currName.substringBefore("/")
                if (index > lastDirectory.size) {
                    lastDirectory.add(currDirectory)
                    println(currDirectory)
                }
                index++

                currName = currName.substringAfter("/")
                if (currName != "") {
                    when {
                        !currName.contains("/") -> { println("|__$currName") }
                        !currName.substringBeforeLast("/").contains("/")
                                && currName.endsWith("/")-> {
                            print("|__")
                        }
                        else -> {print("|  ")}
                    }
                }
            }
        }
    }

    fun printFolderInfo(folderName: String, pathName: String, size: Long, isFolderExists: Boolean) {
        print("Folder \"$folderName\"")

        if (isFolderExists) {
            if (size == 0L) {
                println("(in $pathName) is empty")
            } else {
                println("(in $pathName) has size between ${size-1} and $size KB")
            }
        } else {
            println(" doesn't exist")
        }
    }

    fun printFileInfo(fileName: String, time: String, isFileExists: Boolean) {
        if (isFileExists) {
            println("Last modification of $fileName was $time")
        } else {
            println("File \"$fileName\" doesn't exist")
        }
    }

    fun printErrorMessage(message: String) = println(message)
}