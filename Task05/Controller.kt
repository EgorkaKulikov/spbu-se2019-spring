import java.lang.Exception
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    val zipExecutor = try {
        Model(args[0])
    } catch (ex: Exception) {
        println("You didn't write any parameter")
        println(USER_GUIDE)
        exitProcess(1)
    }

    try {
        when {
            args[1] == "general_info" -> {
                zipExecutor.getGeneralInfo()
            }
            args[1] == "folder_info" -> {
                zipExecutor.getFolderSize(args[2])
            }
            args[1] == "file_info" -> {
                zipExecutor.getFileInfo(args[2])
            }
            else -> {
                zipExecutor.viewer.printErrorMessage(USER_GUIDE)
            }
        }
    } catch(ex: Exception){
        zipExecutor.viewer.printErrorMessage("You didn't write some of the next parameters: \n"
                                            + "\tCommand\n"
                                            + "\tFile name or folder name\n"
                                            + USER_GUIDE)
        exitProcess(1)
    }
}