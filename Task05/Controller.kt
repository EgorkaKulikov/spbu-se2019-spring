import javafx.collections.FXCollections
import java.io.File
import java.io.IOException
import java.lang.Exception
import java.lang.NullPointerException
import java.util.zip.ZipException
import java.util.zip.ZipFile

class Controller : tornadofx.Controller()
{
    val values = FXCollections.observableArrayList("")
    private var currentZip : ZipFile? = null

    fun openZip(file : File?)
    {
        try
        {
            if (file != null)
                currentZip = ZipFile(file)
            else throw NullPointerException()
        }
        catch (e: IOException)
        {
            MainView().showMessage("Error: Cannot open file!", e.message ?: "")
        }
        catch (e: ZipException)
        {
            MainView().showMessage("Error: Cannot open file as zip!", e.message ?: "")
        }
        catch (e: NullPointerException)
        {
            MainView().showMessage("Error: You didn't choose file!", e.message ?: "")
            return
        }

        values.clear()
        values.addAll(ZipReader(currentZip!!).getListOfEntries())
    }

    fun getFileInfo(name : String)
    {
        try
        {
            if (currentZip == null)
                throw Exception("Zip archive was not open")
        }
        catch (e : Exception)
        {
            MainView().showMessage("Error!", e.message ?: "")
            return
        }

        val result = ZipReader(currentZip!!).findEntry(name)
        if (result.second != -1L)
            MainView().showMessage("Found file/directory:",
                "Name: " + result.first + "\nSize: " + result.second + " bytes")
        else
            MainView().showMessage("Found file/directory:", "File/directory does not exist!")
    }
}