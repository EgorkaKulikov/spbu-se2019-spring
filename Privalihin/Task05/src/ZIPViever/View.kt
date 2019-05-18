package ZIPViever

import org.omg.CORBA.Object
import java.lang.Exception
import java.util.*
import java.util.zip.*

class View: Observer {
    override fun update(_model: Observable, _request: Any) {
        val request = try {
            _request as Request
        }
        catch (e: Exception) {
            println("Error while processing request")
            return
        }

        val model = try {
            _model as Model
        }
        catch (e: Exception) {
            println("Error while processing model")
            return
        }

        when(request) {
            Request.ARCHIVE -> printArchive(model.result)
            Request.FOLDER -> printFolder(model.result)
            Request.FILE -> printFile(model.result)
        }
    }

    private fun printArchive(entries: MutableList<ZipEntry>) {
        for (entry in entries) {
            println(getIndentedName(entry.name))
        }
    }

    private fun printFolder(entries: MutableList<ZipEntry>) {
        for (entry in entries) {
            println("The folder ${entry.name} has the size of ${entry.size} bytes")
        }
    }

    private fun printFile(entries: MutableList<ZipEntry>) {
        for (entry in entries) {
            println("The file ${entry.name} was created on ${entry.creationTime}")
        }
    }
}