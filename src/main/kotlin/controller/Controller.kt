import java.io.File
import kotlin.system.exitProcess

class Controller(private val args: Array<String>) : IController {

    override val model = Model()
    override val view = Viewer(model)
    private val parameters = HashMap<String, String>()

    init {
        parseArguments()
        validateArguments()
        runModel()
    }

    override fun parseArguments() {
        when (args.size) {
            1 -> parameters ["pathToArchive"] = args[0]
            3 -> {
                parameters ["pathToArchive"] = args[0]
                parameters["command"] = args[1]
                when (parameters["command"]) {
                    "--file" -> parameters["fileName"] = args[2]
                    "--folder" -> parameters["folderName"] = args[2]
                    else -> exit("Wrong command name. Choose <--file> or <--folder>.", 1)
                }
            }
            !in (1..3 step 2) -> exit("Wrong number of arguments. \n" + help(), 1)
        }
    }

    override fun validateArguments() {
        validateArchiveFormat(parameters["pathToArchive"])
    }

    override fun runModel() {
        model.parseArchive(parameters)
        model.buildFilesStructure()
    }



    private fun validateArchiveFormat(pathToArchive: String?) {
        if (pathToArchive == null)
            exit("Path to archive was not selected.", 1)
        if (pathToArchive!!.substring(pathToArchive.length - 4) != ".zip")
            exit("Only .zip archives required.", 3)
        if (!File(pathToArchive).exists())
            exit("No such archive.", 2)
    }

    private fun exit(message: String, status: Int) {
        println(message)
        exitProcess(status)
    }

    private fun help(): String =
            "\nSpecify arguments in the following ways: \n\n" +
            "1. To see archive tree structure: \n" +
                    "   <../archive_name.zip> \n\n" +
            "2. To find file in archive: \n" +
                    "   <../archive_name.zip> --file <../target_file_name>. \n\n" +
            "3. To find folder in archive: \n" +
                    "   <../archive_name.zip> --folder <../target_folder_name>.\n"
}