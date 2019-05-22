package zipviewer

class Controller {
    fun processRequest(args: Array<String>) {
        when {
            args.size == 1 && args[0] == "help" -> {
                println("""Type one of the following commands:
                    |%zipFile_full_path% structure
                    |%zipFile_full_path% folder_info %folder_name%
                    |%zipFile_full_path% file_info %file_name%
                    |help
                """.trimMargin())
            }
            args.size == 2 && args[1] == "structure" -> {
                val data = Model(args[0]).data
                View().printStructure(data)
            }
            args.size == 3 && args[1] == "folder_info" -> {
                val foldersWithSameName = Model(args[0]).findEntryByName(args[2])
                View().printFolderInfo(foldersWithSameName)
            }
            args.size == 3 && args[1] == "file_info" -> {
                val filesWithSameName = Model(args[0]).findEntryByName(args[2])
                View().printFileInfo(filesWithSameName)
            }
            else -> {
                println("Wrong command! Type 'help' for available commands.")
            }
        }
    }
}