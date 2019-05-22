package zipviewer

class Controller() {
    fun parseArgs(args: Array<String>) {
        when {
            args.size == 2 && args[1] == "print_archive" -> {
                val view = View(Model(args[0]))
                view.printArchive()
            }

            args.size == 3 && args[1] == "folder_info" -> {
                val model = Model(args[0])
                val view = View(model)
                view.printFolderList(model.getFolderList(args[2]))
            }

            args.size == 3 && args[1] == "file_info" -> {
                val model = Model(args[0])
                val view = View(model)
                view.printFileList(model.getFileList(args[2]))
            }

            else -> {
                getHelp()
            }
        }
    }
}

fun main(args: Array<String>) {
    val controller = Controller()
    controller.parseArgs(args)
}