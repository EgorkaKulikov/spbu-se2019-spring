import java.util.zip.ZipEntry
import java.util.zip.ZipFile

const val INDENT = "╟───"

class ZipReader(private val zip : ZipFile)
{
    private fun calculateDirSize(dir : ZipEntry) : Long
    {
        var size : Long = 0
        for (entry in zip.entries())
            if (entry.name.contains(dir.name))
                size += entry.size

        return size
    }

    fun getListOfEntries() : ArrayList<String>
    {
        val entries = ArrayList<String>()

        for (entry in zip.entries())
        {
            if (entry != null)
            {
                val name = if (entry.isDirectory) entry.name.dropLast(1) else entry.name
                var finalName = ""
                for (character in name)
                    if (character == '/')
                        finalName += INDENT
                finalName += " " + name.substringAfterLast('/')
                entries.add(finalName)
            }
        }
        return entries
    }

    fun findEntry(name : String) : Pair<String, Long>
    {
        val foundEntry = zip.getEntry(name)

        if (foundEntry != null)
        {
            if (foundEntry.isDirectory)
            {
                return Pair(foundEntry.name.dropLast(1), calculateDirSize(foundEntry))
            }
            else
                return Pair(foundEntry.name, foundEntry.size)
        }
        else
            return Pair("File/directory does nod exist!", -1)
    }
}