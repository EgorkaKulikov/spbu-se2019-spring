package ZIPViever

import org.jboss.shrinkwrap.descriptor.api.Mutable
import java.util.*
import java.util.zip.*

class Model: Observable() {
    internal var result: MutableList<ZipEntry> = mutableListOf()
        private set

    private fun getZIP(name: String): ZipFile? {
        try {
            return  ZipFile(name)
        }
        catch (e: Exception) {
            println("Error when opening archive: $name")
            return null
        }
    }

    internal fun viewArchive(archiveName: String) {
        result.clear()
        val archive: ZipFile = getZIP(archiveName)?: return

        for (entry in archive.entries()) {
                result.add(entry)
        }

        setChanged()
        notifyObservers(Request.ARCHIVE)
    }

    internal fun findFolder(archiveName: String, folderName: String) {
        result.clear()
        val archive: ZipFile = getZIP(archiveName)?: return
        var curFolder: ZipEntry? = null

        for (entry in archive.entries()) {
            if (entry.isDirectory || (curFolder?.name?: "") !in entry.name) {
                curFolder?.let{ result.add(curFolder!!) }
                curFolder = if(getName(entry.name) == folderName) entry else null
            }
            else {
                curFolder?.setSize(curFolder!!.size + entry.size)
            }
        }

        if (result.isEmpty()) {
            println("Folder not found!")
            return
        }

        setChanged()
        notifyObservers(Request.FOLDER)
    }

    internal fun findFile(archiveName: String, fileName: String) {
        result.clear()
        val archive: ZipFile = getZIP(archiveName)?: return

        for (entry in archive.entries()) {
            if (!entry.isDirectory
                && getName(entry.name) == fileName) {
                result.add(entry)
            }
        }

        if (result.isEmpty()) {
            println("File not found!")
            return
        }

        setChanged()
        notifyObservers(Request.FILE)
    }
}