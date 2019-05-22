package zipviewer

import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class View {
    fun printStructure(data: ZipFile) {
        val zipEntries = data.entries()

        while(zipEntries.hasMoreElements()) {
            val currEntry = zipEntries.nextElement()
            val name = if (currEntry.isDirectory) currEntry.name.dropLast(1) else currEntry.name

            var indent = ""
            for (i in name) {
                if (i == '/')
                    indent += "|   "
            }

            if (indent != "")
                println(indent.dropLast(4) + "|___" + name.substringAfterLast('/'))
            else
                println(name)
        }
    }

    fun printFolderInfo(foldersWithSameName: MutableList<ZipEntry>) {
        if (foldersWithSameName.isEmpty()) {
            println("No such folder found!")
            return
        }

        for (folder in foldersWithSameName) {
            val folderSize = folder.size
            println(folder.name)
            println("$folderSize bytes")
            println()
        }
    }

    fun printFileInfo(filesWithSameName: MutableList<ZipEntry>) {
        if (filesWithSameName.isEmpty()) {
            println("No such file found!")
            return
        }

        for (file in filesWithSameName) {
            val creationTime = file.creationTime.toString()
            println(file.name)
            println(creationTime)
            println()
        }
    }
}