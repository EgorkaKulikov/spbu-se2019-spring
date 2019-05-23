import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class model(filePath: String) {
    val zipFile = ZipFile(filePath)
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
                    val newFolder = tempFolder!!.addFolder(tempFolder, file.name)
                    tempFolder = newFolder
                    numberOfSlashes = newNumberOfSlashes
                }
                if (newNumberOfSlashes <= numberOfSlashes) {
                    for (i in 0..(numberOfSlashes - newNumberOfSlashes)) {
                        tempFolder = tempFolder!!.parent
                    }
                    val newFolder = tempFolder!!.addFolder(tempFolder, file.name)
                    tempFolder = newFolder
                    numberOfSlashes = newNumberOfSlashes
                }
            } else {
                tempFolder!!.addFile(file)
            }
        }
        return root
    }

    fun FileDFS(startNode: folder, wantedFileName: String): ZipEntry? {
        for (file in startNode.internalFiles) {
            var indexOfFile = 0
            for (i in (file.name.length - 1) downTo 0) {
                if (file.name[i] == '/') {
                    indexOfFile = i + 1
                    break
                }
            }
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
        var file: ZipEntry? = null
        file = FileDFS(root, _fileName)
        if (file == null)
            return null
        else
            return file.lastModifiedTime.toString()
    }

    fun findFolderWithDFS (startNode: folder, wantedFolderName: String): folder? {
        if (startNode.name == wantedFolderName)
            return startNode
        for (folder in startNode.internalFolders) {
            val result = findFolderWithDFS(folder, wantedFolderName)
            if (result != null)
                return result
        }
        return null
    }

    fun folderSizeWithDFS (startNode: folder): Long {
        var sum = 0.toLong()
        for (file in startNode.internalFiles)
            sum += file.size

        for (folder in startNode.internalFolders) {
            sum += folderSizeWithDFS(folder)
        }
        return sum
    }

    fun getFolderSize(folderName: String): Long? {
        var folder: folder? = null
        folder = findFolderWithDFS(root, folderName)
        if (folder == null)
            return null
        else
            return folderSizeWithDFS(folder)


    }
}