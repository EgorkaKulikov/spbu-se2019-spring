package presenter

import language.betweenPresenterAndModel.*
import language.betweenPresenterAndView.*
import model.Model

@kotlin.ExperimentalUnsignedTypes
internal object Presenter {
    private val mapArchives = mutableMapOf<String, Archive>()
    private var mainArchive: Archive? = null
    fun execute(commands: List<VPCommand>): List<PVData> {
        val ans = mutableListOf<PVData>()
        for (command in commands) {
            when (command) {
                is VPOpenArchive -> {
                    if (command.archiveName in mapArchives.keys) {
                        mainArchive = mapArchives[command.archiveName]
                        ans.add(PVArchiveStructure(mainArchive!!,
                            command.maxAmountDisplayedObjectsInFolder,
                            command.maxHeightDisplayedTreeFolder))
                    }
                    else {
                        val ansModel = Model.execute(listOf(PMOpenArchive(command.archiveName)))[0]
                        if (ansModel is MPArchiveStructure) {
                            mapArchives[command.archiveName] = ansModel.archive
                            mainArchive = ansModel.archive
                            ans.add(PVArchiveStructure(ansModel.archive,
                                command.maxAmountDisplayedObjectsInFolder,
                                command.maxHeightDisplayedTreeFolder))
                        }
                        else
                            throw Exception("Unexpected behavior Model.execute")
                    }
                }
                is VPOpenFolder -> {
                    if (mainArchive == null)
                        throw exceptions.UserInputException("You have not opened the archives")
                    else {
                        val totalFolderName = command.folderName.split('/')
                        var folder: Container = mainArchive!!
                        for (nextFolderName in totalFolderName) {
                            val nextObject = folder.listObjects.find { nextFolderName == it.name }
                            if (nextObject != null && nextObject is Folder)
                                folder = nextObject
                            else
                                throw Exception("Folder with desired name was not found")
                        }
                        if (folder !is Folder)
                            throw Exception("The found object is not a folder")
                        ans.add(PVFolderStructure(folder,
                            command.maxAmountDisplayedObjectsInFolder,
                            command.maxHeightDisplayedTreeFolder))
                    }
                }
                is VPFindObject -> {
                    if (mainArchive == null)
                        throw exceptions.UserInputException("You have not opened the archives")
                    else {
                        val totalObjectName = command.objectName.split('/')
                        var answer = mainArchive!!.find(totalObjectName[0])
                        var fullSearch = false
                        for (i in 1 until totalObjectName.size) {
                            val newAnswer = mutableListOf<Object>()
                            val objectName = totalObjectName[i]
                            if (fullSearch) {
                                if (objectName == "*" || objectName == "?")
                                    throw Exception("After \"*\" should be the name of the object, not \"*\" or \"?\"")
                                for (folder in answer.filter { it is Folder }) {
                                    if (folder is Folder) {
                                        newAnswer.addAll(folder.find(objectName))
                                    }
                                }
                                fullSearch = false
                            }
                            else {
                                when (objectName) {
                                    "?" -> {
                                        for (folder in answer.filter { it is Folder }) {
                                            if (folder is Folder) {
                                                newAnswer.addAll(folder.listObjects)
                                            }
                                        }
                                    }
                                    "*" -> {
                                        if (i + 1 == totalObjectName.size)
                                            throw exceptions.UserInputException("After \"*\" should be the name of the object")
                                        fullSearch = true
                                    }
                                    else -> {
                                        for (folder in answer.filter { it is Folder }) {
                                            if (folder is Folder) {
                                                val foundElement = folder.listObjects.find { it.name == objectName }
                                                if (foundElement != null)
                                                    newAnswer.add(foundElement)
                                            }
                                        }
                                    }
                                }
                            }
                            answer = newAnswer.toList()
                        }
                        ans.add(PVFoundObjects(answer))
                    }
                }
                is VPInfoAboutObject -> {
                    if (mainArchive == null) {
                        throw exceptions.UserInputException("You have not opened the archives")
                    }
                    else {
                        val result = Model.execute(listOf(PMInfoAboutObject(mainArchive!!.name, command.objectName)))[0]
                        if (result !is MPInfoAboutObject)
                            throw Exception("Model.execute incorrectly responded to a request")
                        ans.add(
                            PVInfoAboutObject(
                                result.fileName,
                                result.modificationDate,
                                result.compressedSize,
                                result.uncompressedSize,
                                result.compressionMethod,
                                result.versionToExtract,
                                result.versionMadeBy,
                                result.extraField,
                                result.fileComment,
                                result.fileData
                            )
                        )
                    }
                }
            }
        }
        return ans.toList()
    }
}
