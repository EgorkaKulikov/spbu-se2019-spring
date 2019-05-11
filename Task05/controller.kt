package zipinfo

import java.util.zip.ZipFile

fun main(args : Array<String>) {

    lateinit var zipFile : ZipFile

    if (args.isNotEmpty()) {
        try {
            zipFile = ZipFile(args[0])
        } catch (e: Exception) {
            println("There's is no such archive: ${args[0]}")
            return
        }
    }

    when {
        args.size == 2 && args[1] == "list" -> viewZip(zipFile)
        args.size == 3 && args[1] == "folder" -> findFolder(zipFile, args[2])
        args.size == 3 && args[1] == "file" -> findFile(zipFile, args[2])
        else -> println("Please, input archive's name, and what you want to do in one of the following format:\n" +
                "archive's_name list\n" +
                "archive's_name folder folder's_name\n" +
                "archive's_name  file file's_name\n")
    }

}
