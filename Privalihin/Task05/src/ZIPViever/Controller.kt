package ZIPViever

class Controller {
    private val model = Model()
    private val view = View()

    init {
        model.addObserver(view)
    }

    fun process(args: Array<String>) {
        when {
            args.size == 2 && args[1] == "display" -> model.viewArchive(args[0])
            args.size == 3 && args[1] == "find-folder" -> model.findFolder(args[0], args[2])
            args.size == 3 && args[1] == "find-file" -> model.findFile(args[0], args[2])
            args.size == 1 && args[0] == "help" -> println("Please, use one of the following requests:\n" +
                    "\"<archive_name> display\" to view the archive structure\n" +
                    "\"<archive_name> find-folder <folder_name>\" to find a folder in the archive\n" +
                    "\"<archive_name> find-file <file_name>\" to find a file in the archive\n")
            else -> println("Unrecognized command")
        }
    }
}