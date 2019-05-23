class controller() {
    fun start (args: Array<String>) {
        if (args.size != 0) {
            val model = model(args[0])
            val view = view()
            if (args[1] == "getZipStracture") {
                view.printStructure(model.root, 0)
            }
            if (args[1] == "getFileDate" && args.size>=2)
                view.printFileDate(model.getFileTimeCreation(args[2]))
            if (args[1] == "getFolderSize" && args.size>=2)
                view.printFolderSize(model.getFolderSize(args[2]))
        }

    }
}

fun main (args: Array<String>) {
    val controller = controller()
    controller.start(args)
}