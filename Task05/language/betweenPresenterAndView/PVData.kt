package language.betweenPresenterAndView

import java.time.LocalDateTime

internal sealed class PVData

internal class PVArchiveStructure(archive: presenter.Archive, val maxAmountDisplayedObjectsInFolder: Int,
                                  val maxHeightDisplayedTreeFolder: Int): PVData() {
    val archive = view.StructureObject(archive.name)
    private fun containerScanner(container: presenter.Container, recursionDepth: Int = 1): List<view.StructureObject> {
        if (recursionDepth > maxHeightDisplayedTreeFolder)
            return listOf()
        if (container.listObjects.size > maxAmountDisplayedObjectsInFolder)
            return listOf()
        val result = mutableListOf<view.StructureObject>()
        for (obj in container.listObjects) {
            when (obj) {
                is presenter.Folder -> {
                    val newObject = view.StructureObject(obj.name)
                    newObject.listObjects = containerScanner(obj, recursionDepth + 1)
                    result.add(newObject)
                }
                is presenter.File -> {
                    result.add(view.StructureObject(obj.name))
                }
                else -> throw Exception("Was founded unexpected class")
            }
        }
        return result.toList()
    }
    init {
        this.archive.listObjects = containerScanner(archive)
    }
}

internal class PVFolderStructure(folder: presenter.Folder?, val maxAmountDisplayedObjectsInFolder: Int,
                                 val maxHeightDisplayedTreeFolder: Int): PVData() {
    val folder = if (folder == null)
        null
    else
        view.StructureObject(folder.name)
    private fun containerScanner(container: presenter.Container, recursionDepth: Int = 1): List<view.StructureObject> {
        if (recursionDepth > maxHeightDisplayedTreeFolder)
            return listOf()
        if (container.listObjects.size > maxAmountDisplayedObjectsInFolder)
            return listOf()
        val result = mutableListOf<view.StructureObject>()
        for (obj in container.listObjects) {
            when (obj) {
                is presenter.Folder -> {
                    val newObject = view.StructureObject(obj.name)
                    newObject.listObjects = containerScanner(obj, recursionDepth + 1)
                    result.add(newObject)
                }
                is presenter.File -> {
                    result.add(view.StructureObject(obj.name))
                }
                else -> throw Exception("Was founded unexpected class")
            }
        }
        return result.toList()
    }
    init {
        if (folder != null)
            this.folder!!.listObjects = containerScanner(folder)
    }
}

internal class PVFoundObjects(listFoundObjects: List<presenter.Object>): PVData() {
    val listFoundObjects = List(listFoundObjects.size) {
        return@List when (val obj = listFoundObjects[it]) {
            is presenter.File -> view.File(
                obj.name,
                obj.path,
                obj.modificationDate,
                obj.compressedSize,
                obj.uncompressedSize
            )
            is presenter.Folder -> view.Folder(
                obj.name,
                obj.path,
                obj.modificationDate,
                obj.compressedSizeAllObjects,
                obj.uncompressedSizeAllObjects
            )
            else -> throw Exception("Was founded unexpected class")
        }
    }
}

internal class PVInfoAboutObject(val fileName: List<String?>,
                                 val modificationDate: List<LocalDateTime?>,
                                 val compressedSize: List<Int?>,
                                 val uncompressedSize: List<Int?>,
                                 val compressionMethod: List<Int?>,
                                 val versionToExtract: List<Int?>,
                                 val versionMadeBy: Int,
                                 val extraField: List<String?>,
                                 val fileComment: String,
                                 val fileData: String?): PVData()
