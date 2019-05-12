package zipExplore

import kotlin.system.exitProcess

class Controller(args: Array<String>) {

    private var archiveName: String

    init {
        if (args.size < 2 ||
            !(args[0].endsWith(".zip")) ||
            args[1] !in listOf("show-all", "show-file", "show-folder") ||
            (args[1] == "show-all" && args.size != 2) ||
            (args[1] != "show-all" && args.size != 3) ||
            args.size > 3
        ) printUsage()
        archiveName = args[0]
        when (args[1]) {
            "show-all" -> View(archiveName).printArchive()
            "show-file" -> View(archiveName).printFile(args[2])
            "show-folder" -> View(archiveName).printFolder(args[2])
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
