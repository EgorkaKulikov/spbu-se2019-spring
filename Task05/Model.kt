import java.nio.file.attribute.FileTime
import java.util.zip.ZipEntry
import java.util.zip.ZipFile



class Model (val zipFile: ZipFile){

    fun findEntryByName( name : String) : ZipEntry? {
        for (e in zipFile.entries()) {
            if (e.name == name)
                return e
        }
        return null
    }

    fun getFolderInfo( name : String) : Pair<Boolean, Long>  {
        var entry : ZipEntry? = null
        var size : Long = 0
        for (e in zipFile.entries()) {
            if (e.name == name) {
                entry = e
            }
            if (e.name.startsWith(name)) {
                size += e.size
            }
        }
        return Pair(entry?.isDirectory ?: false, size)
    }

    fun getFileInfo( name : String) : Pair<Boolean, FileTime>  {
        val entry = findEntryByName(name) ?: return Pair(false, FileTime.fromMillis(0)) //something
        return Pair(true, entry.creationTime)
    }
}