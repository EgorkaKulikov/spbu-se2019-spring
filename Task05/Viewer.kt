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
    fun printDirectoryTree(listFiles: List<String>) {
        var lastDirectoryList = mutableListOf<String>()

        for (file in listFiles) {
            var currName = file

            if (!currName.contains("/")) {
                println(currName)
                continue
            }

            val newDirectoryList = mutableListOf<String>()
            val lastDirectoryIterator = lastDirectoryList.iterator()

            while (currName.contains("/")) {
                val currDirectory = currName.substringBefore("/")

                newDirectoryList.add(currDirectory)
                if (lastDirectoryIterator.hasNext()){
                    if (currDirectory != lastDirectoryIterator.next()) {
                        println(currDirectory)
                    }
                } else {
                    println(currDirectory)
                }


                currName = currName.substringAfter("/")
                if (currName != "") {
                    when {
                        !currName.contains("/") -> { println("|__$currName") }
                        !currName.substringBeforeLast("/").contains("/")
                                && currName.endsWith("/")-> {
                            print("|__")
                        }
                        currName != "" -> {print("   ")}
                    }
                }
            }
            lastDirectoryList = newDirectoryList
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
            println("File \"$fileName\" was created $time")
        } else {
            println("File \"$fileName\" doesn't exist")
        }
    }

    fun printErrorMessage(message: String) = println(message)
}