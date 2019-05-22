interface Command {

    companion object {
        val all = arrayOf(
            Structure,
            FileInfo,
            FolderInfo
        )
    }

    val countOfArguments: Int
    val name: String
    val format: String

    fun doSomething(arguments: Array<String>, structure: DirectoryInfo, view: View)

    object Structure: Command {

        override val countOfArguments = 2
        override val name = "structure"
        override val format = "$name <archive name>"

        override fun doSomething(arguments: Array<String>, structure: DirectoryInfo, view: View) {
            view.showStructure(structure)
        }
    }

    object FileInfo: Command {

        override val countOfArguments = 3
        override val name = "file-info"
        override val format = "$name <archive name> <file name>"

        override fun doSomething(arguments: Array<String>, structure: DirectoryInfo, view: View) {
            val file = structure.findFile(pathTo(arguments[2]))

            if (file == null) {
                view.showError("Archive does not have file ${arguments[2]}")
            } else {
                view.showFileInfo(file)
            }
        }
    }

    object FolderInfo: Command {

        override val countOfArguments = 3
        override val name = "folder-info"
        override val format = "$name <archive name> <folder name>"

        override fun doSomething(arguments: Array<String>, structure: DirectoryInfo, view: View) {
            val directory = structure.findDirectory(pathTo(arguments[2]))

            if (directory == null) {
                view.showError("Archive does not have folder ${arguments[2]}")
            } else {
                view.showDirectoryInfo(directory)
            }
        }
    }
}
