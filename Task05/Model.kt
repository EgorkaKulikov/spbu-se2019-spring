import java.io.IOException
import java.util.zip.ZipException
import java.util.zip.ZipFile
import kotlin.system.exitProcess

class Model(pathName: String) {
    var viewer = Viewer()

    private var zipFile = try {
        ZipFile(pathName)
    } catch (ex: ZipException) {
        this.viewer.printErrorMessage("path Name error: ${ex.message}")
        exitProcess(2)
    } catch (ex: IOException) {
        this.viewer.printErrorMessage("path Name error: ${ex.message}")
        exitProcess(3)
    } catch (ex: SecurityException) {
        this.viewer.printErrorMessage("path Name error: ${ex.message}")
        exitProcess(4)
    }

    private val filesEnum = zipFile.entries()

    fun getGeneralInfo() {
        viewer.printDirectoryTree(filesEnum.toList())
    }

    fun getFolderSize(folderName: String){
        var sizeFolder = 0L
        var isFolderExists = false
        var currName = ""
        var isPreviewNeededFolder = false
        var isLastNeededFolder = false

        while(filesEnum.hasMoreElements()){
            val previewPathFolder = currName.substringBefore(folderName)
            val currElement = filesEnum.nextElement()
            currName = currElement.name
            val isFile = !currName.endsWith("/")
            val pathFolder = currName.substringBefore(folderName)

            if (pathFolder != previewPathFolder && isPreviewNeededFolder) {
                viewer.printFolderInfo(folderName
                    , previewPathFolder
                    , sizeFolder, true)
                sizeFolder = 0L
            }
            isPreviewNeededFolder = false

            if (currName.contains(folderName)){
                if (isFile) {
                    sizeFolder += Math.ceil(currElement.size.toDouble() / 1024.0).toLong()
                }

                isPreviewNeededFolder = true
                isFolderExists = true
                if (!filesEnum.hasMoreElements()) {
                    isLastNeededFolder = true
                }
            }
        }
        if (isLastNeededFolder) {
            viewer.printFolderInfo(folderName
                , currName.substringBefore(folderName)
                , sizeFolder, true)
        }

        if (!isFolderExists) {
            viewer.printFolderInfo(folderName, "", sizeFolder, false)
        }
    }

    fun getFileInfo(fileName: String) {
        var isFileExists = false

        while(filesEnum.hasMoreElements()){
            val currElement = filesEnum.nextElement()
            val isFile = !currElement.name.endsWith("/")

            if (currElement.name.contains(fileName) && isFile) {
                isFileExists = true
                val time = currElement.timeLocal.toString()
                viewer.printFileInfo(fileName, time.replace("T", " "), true)
            }
        }

        if (!isFileExists) {
            viewer.printFileInfo(fileName, "", false)
        }
    }
}