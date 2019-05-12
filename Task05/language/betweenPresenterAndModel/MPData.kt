package language.betweenPresenterAndModel

internal sealed class MPData

@kotlin.ExperimentalUnsignedTypes
internal class MPArchiveStructure(zipFile: model.ZipFile): MPData() {
    val archive = presenter.Archive(zipFile.name)
    init {
        for (zipObject in zipFile.listZipObjects) {
            val totalNameZipObject = zipObject.centralDirectoryFileHeader.fileName.split("/")
            val newObject = if (totalNameZipObject.last() == "" && totalNameZipObject.size > 1)
                presenter.Folder(totalNameZipObject[totalNameZipObject.size - 2],
                    totalNameZipObject.subList(0, totalNameZipObject.size - 2),
                    zipObject.centralDirectoryFileHeader.modificationDate,
                    zipObject.centralDirectoryFileHeader.compressedSize,
                    zipObject.centralDirectoryFileHeader.uncompressedSize)
            else
                presenter.File(totalNameZipObject[totalNameZipObject.size - 1],
                    totalNameZipObject.subList(0, totalNameZipObject.size - 1),
                    zipObject.centralDirectoryFileHeader.modificationDate,
                    zipObject.centralDirectoryFileHeader.compressedSize,
                    zipObject.centralDirectoryFileHeader.uncompressedSize)
            val path = if (totalNameZipObject.last() == "" && totalNameZipObject.size > 1)
                totalNameZipObject.subList(0, totalNameZipObject.size - 2)
            else
                totalNameZipObject.subList(0, totalNameZipObject.size - 1)
            archive.add(newObject, path)
        }
    }
}

@kotlin.ExperimentalUnsignedTypes
internal class MPInfoAboutObject(zipObject: model.ZipObject): MPData() {
    val fileName = listOf(zipObject.centralDirectoryFileHeader.fileName, zipObject.localFileHeader?.fileName)
    val modificationDate = listOf(zipObject.centralDirectoryFileHeader.modificationDate,
        zipObject.localFileHeader?.modificationDate)
    val compressedSize = listOf(zipObject.centralDirectoryFileHeader.compressedSize, if (zipObject.dataDescriptor != null)
        zipObject.dataDescriptor!!.compressedSize
    else
        zipObject.localFileHeader?.compressedSize)
    val uncompressedSize = listOf(zipObject.centralDirectoryFileHeader.uncompressedSize, if (zipObject.dataDescriptor != null)
        zipObject.dataDescriptor!!.uncompressedSize
    else
        zipObject.localFileHeader?.uncompressedSize)
    val extraField = listOf(zipObject.centralDirectoryFileHeader.extraField, zipObject.localFileHeader?.extraField)
    val versionToExtract = listOf(zipObject.centralDirectoryFileHeader.versionToExtract,
        zipObject.localFileHeader?.versionToExtract)
    val compressionMethod = listOf(zipObject.centralDirectoryFileHeader.compressionMethod.toInt(),
        zipObject.localFileHeader?.compressionMethod?.toInt())
    val fileData = zipObject.localFileHeader?.fileData
    val fileComment = zipObject.centralDirectoryFileHeader.fileComment
    val versionMadeBy = zipObject.centralDirectoryFileHeader.versionMadeBy
}
