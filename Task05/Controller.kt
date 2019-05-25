import java.io.IOException
import java.lang.IllegalStateException
import java.util.zip.ZipException
import java.util.zip.ZipFile
import kotlin.system.exitProcess

class Controller (val name : String){
    val model : Model
    val view = View()
    init {
        try {
            model = Model(ZipFile(name))
        }
        catch (e : IOException) {
            view.printMessage("No such zip: $name")
            exitProcess(1)
        }
        catch (e : ZipException) {
            view.printMessage("Zip format error: $name")
            exitProcess(1)
        }
        catch (e : SecurityException) {
            view.printMessage("Security error: $name")
            exitProcess(1)
        }
    }

    fun handleCommand(args : List<String>) {
        try {
            when (args[0]) {
                "show" -> {
                    view.printTree(ZipTree(model.zipFile))
                }
                "foldInf" -> {
                    val result = model.getFolderInfo(args[1])
                    view.printSize(result.first, result.second)
                }
                "fileInf" -> {
                    val result = model.getFileInfo(args[1])
                    view.printCreationDateTime(result.first, result.second)
                }
            }
        }
        catch (e: IllegalStateException) {
            view.printMessage("Zip closed for some reason: $name")
            exitProcess(1)
        }
    }
}