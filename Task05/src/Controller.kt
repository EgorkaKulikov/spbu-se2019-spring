package zipExplore

import kotlin.system.exitProcess

class Controller(args: Array<String>) {

    init {

        when {
            args.size == 2 && args[1] == "show-all" -> View(args[0]).printArchive()
            args.size == 3 && args[1] == "show-file" -> View(args[0]).printFile(args[2])
            args.size == 3 && args[1] == "show-folder" -> View(args[0]).printFolder(args[2])
            else -> printUsage()
        }

    }

    private fun printUsage() {

        println(
            """
            |Type one of the following commands:
            |   <Archive name>.zip show-all
            |   <Archive name>.zip show-folder <Folder name>
            |   <Archive name>.zip show-file <File name>
        """.trimMargin()
        )
        exitProcess(127)

    }

}

fun main(args: Array<String>) {
    Controller(args)
}
