package language.betweenPresenterAndModel

internal sealed class PMCommand

internal data class PMOpenArchive(val archiveName: String): PMCommand()

internal data class PMInfoAboutObject(val archiveName: String, val objectName: String): PMCommand()
