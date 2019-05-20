class Controller(model: Model, view: View, args: Array<String>) {
    init {
        when {
            args.size == 1
                    && ((args[0] == "-h") || (args[0] == "--help")) -> {
                println(
                    "Input path to zip file as a first parameter.\n"
                            + "Use one of the specified commands:\n"
                            + "-l, --list               Print archive structure\n"
                            + "-f, --file [name]        Find file\n"
                            + "-d, --directory [name]   Find directory\n"
                )
            }
            args.size == 2
                    && ((args[1] == "-l") || (args[1] == "--list")) -> {
                model.extractData(args[0])
                view.printFileTree(0, model.fileTree)
            }
            args.size == 3
                    && ((args[1] == "-f") || (args[0] == "--file")) -> {
                model.extractData(args[0])
                view.printFile(model.fileTree.findEntriesByName(args[2], EntryType.FILE))

            }
            args.size == 3
                    && ((args[1] == "-d") || (args[0] == "--directory")) -> {
                model.extractData(args[0])
                view.printDirectory(model.fileTree.findEntriesByName(args[2], EntryType.DIRECTORY))
            }
            else -> {
                println("Invalid arguments, use -h or --help")
            }
        }
    }
}