package model

import language.betweenPresenterAndModel.*
import java.io.File

@kotlin.ExperimentalUnsignedTypes
internal object Model {
    private var mainZipFile: ZipFile? = null
    fun execute(commands: List<PMCommand>): List<MPData> {
        val ans = mutableListOf<MPData>()
        for (command in commands) {
            when (command) {
                is PMOpenArchive -> {
                    val archiveFile: File
                    val bytesFile: ByteArray
                    try {
                        archiveFile = File(command.archiveName)
                        bytesFile = archiveFile.readBytes()
                    }
                    catch (e: Exception) {
                        throw exceptions.UserInputException("File \"${command.archiveName}\" not found")
                    }
                    val data = UByteArray(bytesFile.size) {bytesFile[it].toUByte()}
                    mainZipFile = ZipFile(command.archiveName, data)
                    ans.add(MPArchiveStructure(mainZipFile!!))
                }
                is PMInfoAboutObject -> {
                    mainZipFile ?: throw Exception("Main zip file in model don't create")
                    if (mainZipFile!!.name != command.archiveName) {
                        val bytesFile = File(command.archiveName).readBytes()
                        val data = UByteArray(bytesFile.size) {bytesFile[it].toUByte()}
                        mainZipFile = ZipFile(command.archiveName, data)
                    }
                    ans.add(MPInfoAboutObject(mainZipFile!!.readZipObjectLocalFileHeader(command.objectName)))
                }
            }
        }
        return ans.toList()
    }
}
