import java.io.File
import java.io.FileNotFoundException
import java.util.zip.*

fun main(args: Array<String>) {

    val zipPath = args[0]
    val command: String

    val zipInput =
        try {
            ZipInputStream(
                File(zipPath).inputStream()
            )
        } catch (e: FileNotFoundException) {
            println("Incorrect zip's path")
            return
        }

    ZipModel.setZipInput(zipInput)

    try {
        command = args[1]

        if (command !in listOf("treeList", "directoryInform", "fileInform")) {
            throw NoSuchElementException()
        }

    } catch (e: RuntimeException) {
        println("Error, invalid input data.\nWrite one of these commands: treeList")
        println("or directoryInform and directory name")
        println("or fileInform and file name ")
        return
    }

    val name: String


    if (command == "treeList") {
        if (args.size > 2) {
            println("Incorrect arguments. Excess values")
            return
        } else View.printArchive(zipPath)
    }


    if (command == "directoryInform" || command == "fileInform") {
        name = args[2]
        when {
            args.size < 3 -> {
                println("Write name of directory or name of file")
                return
            }

            args.size > 3 -> {
                println("Incorrect arguments. Write only the name")
                return
            }

            else -> {
                if (command == "directoryInform") {
                    ZipModel.newDirectoryInfo(name)
                } else ZipModel.findFileInfo(name)
            }

        }
    }

}