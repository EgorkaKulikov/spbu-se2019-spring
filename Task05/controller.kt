class controller() {
    fun start (args: Array<String>) {
        if (args.size >= 2) {
            val model = model(args[0])
            val view = view()
            if (args[1] == "getZipStructure") {
                view.printStructure(model.root, 0)
            }
            else if (args[1] == "getFileDate" && args.size>=3)
                view.printFileDate(model.getFileTimeCreation(args[2]))
            else if (args[1] == "getFolderSize" && args.size>=3)
                view.printFolderSize(model.getFolderSize(args[2]))
            else
                view.printMenu()
        }
        else {
            val view = view()
            view.printMenu()
        }
    }
}

fun main (args: Array<String>) {
    val controller = controller()
    controller.start(args)
}