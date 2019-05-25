import java.io.*
import java.util.zip.*
import kotlin.system.exitProcess

class Model(val archiveName: String) {
    private fun getZipEntries() : List<ZipEntry> {
        val zipFile : ZipFile
        try {
            zipFile = ZipFile(archiveName)
        } catch (e: IOException) {
            println("Couldn't find [$archiveName]")
            exitProcess(3)
        } catch (e: ZipException) {
            println("Couldn't open [$archiveName] as ZipFile")
            exitProcess(2)
        }
        return zipFile.entries().toList().sortedBy { it.name }
    }

    val listOfEntries = getZipEntries()

    fun getEntry(fileName : String) : ZipEntry?{
        for (entry in listOfEntries){
            val name = if (entry.isDirectory) entry.name.dropLast(1)
                            else entry.name
            if (name.substringAfterLast('/') == fileName)
                return entry
            }
        return null
    }

    fun getFoldersSize(folderName : String) : Long{
        var size = 0L
        val directory = "$folderName/"
        for (entry in listOfEntries){
            if (entry.name.contains(directory)){
                size += entry.size
            }
        }
        return size
    }

    /*fun getZipInput() : ZipInputStream{
        try{
            return ZipInputStream(FileInputStream(archive))
        }
        catch(e: Exception){
            println("Couldn't open [$archive] as ZipInputStream")
            exitProcess(2)
        }
    }*/
}