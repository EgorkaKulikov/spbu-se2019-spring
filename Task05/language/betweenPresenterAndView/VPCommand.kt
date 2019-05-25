package language.betweenPresenterAndView

internal sealed class VPCommand

internal data class VPOpenArchive(val archiveName: String, val maxAmountDisplayedObjectsInFolder: Int,
                                  val maxHeightDisplayedTreeFolder: Int): VPCommand()

internal data class VPOpenFolder(val folderName: String, val maxAmountDisplayedObjectsInFolder: Int,
                                  val maxHeightDisplayedTreeFolder: Int): VPCommand()

internal data class VPFindObject(val objectName: String): VPCommand()

internal data class VPInfoAboutObject(val objectName: String): VPCommand()
