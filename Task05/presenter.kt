import java.io.File

interface IPresenter {
    fun runCommand(args: Array<String>)

}

class Presenter(private val view: IView): IPresenter {
    private object ExitMessages {
        const val IncorrectInput =
            "Incorrect input\n" +
            "Use one of the following samples:\n" +
            "- load <name of a .zip file>\n" +
            "- dir <name of a .zip file> <name of a directory>\n" +
            "- file <name of a .zip file> <name of a file>\n"

        fun IncorrectZipName(filename: String): String {
            return "Can't read file '$filename'\n" +
                    "Check the name or current directory\n"
        }

    }

    override fun runCommand(args: Array<String>) {
        if (!((args.size == 2 && args[0] == "load")
                || args.size == 3)) {
            forcedExit(ExitMessages.IncorrectInput)
        }

        val filename = args[1]
	// we have a third parameter for 'dir' and 'file' commands
	val path = if (args.size == 3) args[2] else ""

        val zipFile = File(filename)
        if (!zipFile.canRead()) {
            forcedExit(ExitMessages.IncorrectZipName(filename))
        }
        val model = ZipModel(filename, zipFile)

        val command = args[0]
        when (command) {
            "load" -> {
                view.showZipStructure(model.getRootDirectory())
            }
            "dir" -> {
                view.showDirectories(model.getDirsByName(path))
            }
            "file" -> {
                view.showFiles(model.getFilesByName(path))
            }

            else -> forcedExit(ExitMessages.IncorrectInput)
        }
    }
}