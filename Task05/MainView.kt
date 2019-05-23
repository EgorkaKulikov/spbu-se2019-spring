import javafx.beans.property.SimpleStringProperty
import javafx.stage.FileChooser
import tornadofx.*

class MainView : View()
{
    private val controller = Controller()
    private var input = SimpleStringProperty()

    override val root =
        form {

            title = "Zip viewer"

            hbox {

                button("Choose file")
                {
                    action {
                        val file = openFile("*.zip")
                        controller.openZip(file)
                    }
                }

                textfield(input)

                button("Find")
                {
                    action {
                        if (input.value != null)
                            controller.getFileInfo(input.value)
                    }
                }
            }


            listview(controller.values)
            {
                minWidth = 400.0
            }
        }

    private fun openFile(extension : String) : java.io.File?
    {
        val filter = FileChooser.ExtensionFilter(extension, extension)
        val files = chooseFile("Choose $extension file", arrayOf(filter), FileChooserMode.Single)
        return if (!files.isEmpty()) files[0] else null
    }

    fun showMessage(header : String, text : String)
    {
        information(header, text)
    }
}