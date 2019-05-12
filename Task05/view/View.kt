package view

import language.betweenPresenterAndView.*
import presenter.Presenter

@kotlin.ExperimentalUnsignedTypes
internal object View {
    private val pathJava = "..."
    private val pathIntelliJ = "..."
    private val pathProduction = "..."
    private val pathKotlinLib = "..."
    // аналогично main.kt
    var maxAmountDisplayedObjectsInFolder = 10
        private set
    var maxHeightDisplayedTreeFolder = 3
        private set
    var mainArchiveName: String? = null
        private set
    fun execute(userCommands: List<String>, prevViewCondition: ViewCondition): ViewCondition {
        var index = 0
        var viewCondition = prevViewCondition
        while (index < userCommands.size) {
            when (val command = userCommands[index]) {
                "-createNewWindow" -> {
                    try {
                        ProcessBuilder(
                            "cmd", "/c",
                            "start $pathJava \"-javaagent:$pathIntelliJ\\lib\\idea_rt.jar=61388:" +
                                    "$pathIntelliJ\\bin\" -Dfile.encoding=UTF-8 " +
                                    "-classpath $pathProduction;" +
                                    "$pathKotlinLib\\kotlin-stdlib.jar;" +
                                    "$pathKotlinLib\\kotlin-reflect.jar;" +
                                    "$pathKotlinLib\\kotlin-test.jar;" +
                                    "$pathKotlinLib\\kotlin-stdlib-jdk7.jar;" +
                                    "$pathKotlinLib\\kotlin-stdlib-jdk8.jar view.ViewProcessKt\n"
                        ).start()
                        index++
                    }
                    catch (e: Exception) { return VCException(Exception("Failed to start new ViewProcess")) }
                }
                "-openNewWindow" -> {
                    try {
                        ProcessBuilder(
                            "cmd", "/c",
                            "start $pathJava \"-javaagent:$pathIntelliJ\\lib\\idea_rt.jar=61388:" +
                                    "$pathIntelliJ\\bin\" -Dfile.encoding=UTF-8 " +
                                    "-classpath $pathProduction;" +
                                    "$pathKotlinLib\\kotlin-stdlib.jar;" +
                                    "$pathKotlinLib\\kotlin-reflect.jar;" +
                                    "$pathKotlinLib\\kotlin-test.jar;" +
                                    "$pathKotlinLib\\kotlin-stdlib-jdk7.jar;" +
                                    "$pathKotlinLib\\kotlin-stdlib-jdk8.jar view.ViewProcessKt " +
                                    "${userCommands.subList(index + 1, userCommands.size)
                                        .joinToString(separator = "\" \"", prefix = "\"", postfix = "\"")}\n").start()
                    }
                    catch (e: Exception) { return VCException(Exception("Failed to start new ViewProcess")) }
                    return prevViewCondition
                }
                "-help" -> {
                    viewCondition = VCHelp()
                    index++
                }
                "-moreInformation", "-moreInfo" -> {
                    if (viewCondition !is VCFoundObject)
                        return VCException(exceptions.UserInputException("The file you want to get more " +
                                "information about isn't known"))
                    try {
                        val result = Presenter.execute(listOf(
                            VPInfoAboutObject(viewCondition.foundObject.path
                                .plus(viewCondition.foundObject.name).joinToString("/"))))[0]
                        if (result !is PVInfoAboutObject)
                            return VCException(Exception("Presenter.execute returned data of the wrong type"))
                        viewCondition = VCMoreInfoAboutObject(
                            result.fileName,
                            result.modificationDate,
                            result.compressedSize,
                            result.uncompressedSize,
                            result.compressionMethod,
                            result.versionToExtract,
                            result.versionMadeBy,
                            result.extraField,
                            result.fileComment,
                            result.fileData)
                    }
                    catch (e: Exception) { return VCException(e) }
                    index++
                }
                "-showArchive" -> {
                    if (mainArchiveName == null)
                        return VCException(exceptions.UserInputException("You have not opened the archives"))
                    try {
                        val result = Presenter.execute(listOf(VPOpenArchive(mainArchiveName!!,
                            maxAmountDisplayedObjectsInFolder,
                            maxHeightDisplayedTreeFolder)))[0]
                        if (result !is PVArchiveStructure)
                            return VCException(Exception("Presenter.execute returned data of the wrong type"))
                        viewCondition = VCArchiveStructure(result.archive)
                    }
                    catch (e: Exception) { return VCException(e) }
                    index++
                }
                "-openArchive" -> {
                    if (index + 1 >= userCommands.size)
                        return VCException(exceptions.UserInputException("You didn't enter an archive name after " +
                                "the command \"-openArchive\""))
                    val archiveName = userCommands[index + 1]
                    try {
                        val result = Presenter.execute(listOf(VPOpenArchive(archiveName,
                            maxAmountDisplayedObjectsInFolder,
                            maxHeightDisplayedTreeFolder)))[0]
                        if (result !is PVArchiveStructure)
                            return VCException(Exception("Presenter.execute returned data of the wrong type"))
                        viewCondition = VCArchiveStructure(result.archive)
                    }
                    catch (e: Exception) { return VCException(e) }
                    mainArchiveName = archiveName
                    index += 2
                }
                "-openFolder" -> {
                    if (index + 1 >= userCommands.size)
                        return VCException(exceptions.UserInputException("You didn't enter a folder name after " +
                                "the command \"-openFolder\""))
                    val objectName = userCommands[index + 1]
                    try {
                        val result = Presenter.execute(listOf(VPFindObject(objectName)))[0]
                        if (result !is PVFoundObjects)
                            return VCException(Exception("Presenter.execute returned data of the wrong type"))
                        val folders = result.listFoundObjects.filter { it is Folder }
                        viewCondition = when (folders.size) {
                            0 -> VCListFoundObjectsIsEmpty()
                            1 -> {
                                val folder = folders[0]
                                val folderStructure = Presenter.execute(listOf(VPOpenFolder(
                                    folder.path.plus(folder.name).joinToString("/"),
                                    maxAmountDisplayedObjectsInFolder,
                                    maxHeightDisplayedTreeFolder)))[0]
                                if (folderStructure !is PVFolderStructure)
                                    return VCException(Exception("Presenter.execute returned data of the wrong type"))
                                if (folderStructure.folder == null)
                                    return VCException(exceptions.UserInputException("The archive isn't yet open"))
                                VCFolderStructure(folderStructure.folder)
                            }
                            else -> VCListFoundObjects(folders, command,
                                "Выберите папку, которую следует открыть:")
                        }
                    }
                    catch (e: Exception) { return VCException(e) }
                    index += 2
                }
                "-find" -> {
                    if (index + 1 >= userCommands.size)
                        return VCException(exceptions.UserInputException("You didn't enter an object name after " +
                                "the command \"-find\""))
                    val objectName = userCommands[index + 1]
                    try {
                        val result = Presenter.execute(listOf(VPFindObject(objectName)))[0]
                        if (result !is PVFoundObjects)
                            return VCException(Exception("Presenter.execute returned data of the wrong type"))
                        viewCondition = when (result.listFoundObjects.size) {
                            0 -> VCListFoundObjectsIsEmpty()
                            1 -> VCFoundObject(result.listFoundObjects[0])
                            else -> VCListFoundObjects(result.listFoundObjects, command,
                                "Выберите объект, который вы искали:")
                        }
                    }
                    catch (e: Exception) { return VCException(e) }
                    index += 2
                }
                "-findFile" -> {
                    if (index + 1 >= userCommands.size)
                        return VCException(exceptions.UserInputException("You didn't enter an object name after " +
                                "the command \"-find\""))
                    val objectName = userCommands[index + 1]
                    try {
                        val result = Presenter.execute(listOf(VPFindObject(objectName)))[0]
                        if (result !is PVFoundObjects)
                            return VCException(Exception("Presenter.execute returned data of the wrong type"))
                        val files = result.listFoundObjects.filter { it is File }
                        viewCondition = when (files.size) {
                            0 -> VCListFoundObjectsIsEmpty()
                            1 -> VCFoundObject(files[0])
                            else -> VCListFoundObjects(files, command,
                                "Выберите объект, который вы искали:")
                        }
                    }
                    catch (e: Exception) { return VCException(e) }
                    index += 2
                }
                "-findFolder" -> {
                    if (index + 1 >= userCommands.size)
                        return VCException(exceptions.UserInputException("You didn't enter an object name after " +
                                "the command \"-find\""))
                    val objectName = userCommands[index + 1]
                    try {
                        val result = Presenter.execute(listOf(VPFindObject(objectName)))[0]
                        if (result !is PVFoundObjects)
                            return VCException(Exception("Presenter.execute returned data of the wrong type"))
                        val folders = result.listFoundObjects.filter { it is Folder }
                        viewCondition = when (folders.size) {
                            0 -> VCListFoundObjectsIsEmpty()
                            1 -> VCFoundObject(folders[0])
                            else -> VCListFoundObjects(folders, command,
                                "Выберите объект, который вы искали:")
                        }
                    }
                    catch (e: Exception) { return VCException(e) }
                    index += 2
                }
                "-selectFromList", "-select" -> {
                    if (viewCondition !is VCListFoundObjects)
                        return VCException(exceptions.UserInputException("you can only use the command" +
                                "\"$command\" when you make a selection from a list"))
                    if (index + 1 >= userCommands.size)
                        return VCException(exceptions.UserInputException("You didn't enter a number after " +
                                "the command \"$command\""))
                    val number: Int
                    try { number = userCommands[index + 1].toInt() }
                    catch (e: Exception) { return VCException(exceptions.UserInputException("You enter NaN")) }
                    if (number <= 0 || number > viewCondition.listObjects.size)
                        return VCException(exceptions.UserInputException("The number must be between 1 " +
                                "and the number of entries in the list"))
                    val selectedObject = viewCondition.listObjects[number - 1]
                    try {
                        viewCondition = when (viewCondition.prevCommand) {
                            "-find", "-findFile", "-findFolder" -> {
                                VCFoundObject(selectedObject)
                            }
                            "-openFolder" -> {
                                val result = Presenter.execute(listOf(VPOpenFolder(
                                    selectedObject.path.plus(selectedObject.name).joinToString("/"),
                                    maxAmountDisplayedObjectsInFolder,
                                    maxHeightDisplayedTreeFolder)))[0]
                                if (result !is PVFolderStructure)
                                    return VCException(Exception("Presenter.execute returned data of the wrong type"))
                                if (result.folder == null)
                                    return VCException(exceptions.UserInputException("The archive isn't yet open"))
                                VCFolderStructure(result.folder)
                            }
                            else -> return VCException(Exception("Unexpected command called list"))
                        }
                    }
                    catch (e: Exception) { return VCException(e) }
                    index += 2
                }
                "-setMaxAmountDisplayedObjectsInFolder", "-setAmountObjects" -> {
                    if (index + 1 >= userCommands.size)
                        return VCException(exceptions.UserInputException("You didn't enter a number after " +
                                "the command \"$command\""))
                    val number: Int
                    try { number = userCommands[index + 1].toInt() }
                    catch (e: Exception) { return VCException(exceptions.UserInputException("You enter NaN")) }
                    if (number <= 0)
                        return VCException(exceptions.UserInputException("Invalid negative number"))
                    maxAmountDisplayedObjectsInFolder = number
                    index += 2
                }
                "-setMaxHeightDisplayedTreeFolder", "-setHeightTree" -> {
                    if (index + 1 >= userCommands.size)
                        return VCException(exceptions.UserInputException("You didn't enter a number after " +
                                "the command \"$command\""))
                    val number: Int
                    try { number = userCommands[index + 1].toInt() }
                    catch (e: Exception) { return VCException(exceptions.UserInputException("You enter NaN")) }
                    if (number <= 0)
                        return VCException(exceptions.UserInputException("Invalid negative number"))
                    maxHeightDisplayedTreeFolder = number
                    index += 2
                }
                "-exit" -> {
                    return VCExit()
                }
                else -> {
                    return VCHelp("Неизвестная команда: $command")
                }
            }
        }
        return viewCondition
    }
}
