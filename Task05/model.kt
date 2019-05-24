import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipException
import java.util.zip.ZipFile
import kotlin.system.exitProcess

class model(filePath: String) {
    private val zipFile = try {
        ZipFile(filePath)
    } catch (exception: ZipException) {
        println("wrong file name")
        exitProcess(1)
    } catch (exception: IOException) {
        println("wrong file name")
        exitProcess(1)
    }
    val root = createTree()

    fun createTree(): folder {
        val root = folder(null, zipFile.name)
        var tempFolder: folder? = root
        var numberOfSlashes = 0
        for (file in zipFile.entries()) {
            if (file.name[file.name.length - 1] == '/') {
                var newNumberOfSlashes = 0
                for (i in 0..(file.name.length - 1))
                    if (file.name[i] == '/')
                        newNumberOfSlashes++

                if (newNumberOfSlashes - numberOfSlashes >= 1) {
                    newNumberOfSlashes = numberOfSlashes + 1
                    val newFolder = tempFolder!!.addFolder(file.name)
                    tempFolder = newFolder
                    numberOfSlashes = newNumberOfSlashes
                }
                else if (newNumberOfSlashes <= numberOfSlashes) {
                    for (i in 0..(numberOfSlashes - newNumberOfSlashes)) {
                        tempFolder = tempFolder!!.parent
                    }
                    val newFolder = tempFolder!!.addFolder(file.name)
                    tempFolder = newFolder
                    numberOfSlashes = newNumberOfSlashes
                }
            } else {
                tempFolder!!.addFile(file)
            }
        }
        return root
    }

    private fun FileDFS(startNode: folder, wantedFileName: String): ZipEntry? {
        for (file in startNode.internalFiles) {
            var indexOfFile = file.name.lastIndexOf('/')
            indexOfFile++
            val fileName = file.name.substring(indexOfFile)
            if (wantedFileName == fileName) {
                return file
            }
        }
        for (folder in startNode.internalFolders) {
            val result = FileDFS(folder, wantedFileName)
            if (result != null)
                return result
        }
        return null
    }

    fun getFileTimeCreation(_fileName: String): String? {
        val file: ZipEntry?
        file = FileDFS(root, _fileName)
        if (file == null)
            return null
        else
            return file.lastModifiedTime.toString()
    }

    private fun findFolderWithDFS (startNode: folder, wantedFolderName: String): folder? {
        if (startNode.name == wantedFolderName)
            return startNode
        for (folder in startNode.internalFolders) {
            val result = findFolderWithDFS(folder, wantedFolderName)
            if (result != null)
                return result
        }
        return null
    }

    private fun folderSizeWithDFS (startNode: folder): Long {
        var sum = 0.toLong()
        for (file in startNode.internalFiles)
            sum += file.size

        for (folder in startNode.internalFolders) {
            sum += folderSizeWithDFS(folder)
        }
        return sum
    }

    fun getFolderSize(folderName: String): Long? {
        val folder: folder?
        folder = findFolderWithDFS(root, folderName)
        if (folder == null)
            return null
        else
            return folderSizeWithDFS(folder)
    }
}