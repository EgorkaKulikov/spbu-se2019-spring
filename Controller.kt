import java.io.File
import java.util.zip.*

fun main(args : Array<String>){
    val zipPath = args[0]
    val command: String
    try {
        command = args[1]
    }catch (e: ArrayIndexOutOfBoundsException){
        println("Write one of these commands: treeList")
        println("or directoryInform and directory name")
        println("or fileInform and file name ")
        return
    }

    val name: String
    val zipInput =
            try {
            ZipInputStream(
            File(zipPath).inputStream())
    }
            catch (e: Exception){
        println("Incorrect zip's path")
                return
    }
    ZipModel.setZipInput(zipInput)


    if (command == "treeList"){
        if (args.size > 2){
            println("Incorrect arguments. Excess values")
            return
        }else Viev.printArchive(zipPath)
    }


    if (command == "directoryInform" || command == "fileInform"){
        when{
            args.size < 3 -> {
                println("Write name of directory or file")
                return
            }
            args.size > 3 -> {
                println("Incorrect arguments. Write only the name")
                return
            }
            else -> {
                name = args[2]
                if (command == "directoryInform") {
                    ZipModel.newDirectoryInfo(name)
                }
                else ZipModel.findFileInfo(name)
            }
        }
    }





}