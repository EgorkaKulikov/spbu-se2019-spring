class Controller() {
    fun parse(args: Array<String>) {
        if (!((args[0] == "folder" && args.size == 3)
                    || (args[0] == "file" && args.size == 3)
                    || (args[0] == "print" && args.size == 2))
        ) {
            println("Wrong input arguments")
            println("Type one of the following commands:")
            println("'folder [FolderName] [~/ZipArchiveName]'")
            println("'file [FileName] [~/ZipArchiveName]'")
            println("'print [~/ZipArchiveName]'")
            return
        }
        val archiveName = if (args[0] == "print") args[1] else args[2]
        val zip = Model(archiveName)
        val viewer = View(zip)
        when (args[0]) {
            "print" -> viewer.printZip()
            "file" -> viewer.findFile(args[1])
            "folder" -> viewer.getFolder(args[1])
        }
    }
}