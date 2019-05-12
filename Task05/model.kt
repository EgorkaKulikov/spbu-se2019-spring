import java.io.File
import java.io.InputStream

interface IModel {
    fun getRootDirectory(): Directory
    fun getDirsByName(searchFor: String): Array<Directory>
    fun getFilesByName(searchFor: String): Array<FileStructure>
}

class ZipModel(val zipName: String, val zipFile: File): IModel {
    // some .zip constants
    private object ZipInfo {
        const val FileSignature = 0x04034b50L
        const val SignatureSize = 4
        //const val FileSignature = 1347093252
    }

    private val zipReader = zipFile.inputStream()
    private val root = Directory(zipName.split('/').last(), zipName)

    init {
        // parsing .zip file using info from
        //      https://pkware.cachefly.net/webdocs/casestudies/APPNOTE.TXT
        while (zipReader.nextLong(4) == ZipInfo.FileSignature) {
            var offset = 0L

            zipReader.safeSkip(2)
            // checking for general purpose flag
            if (zipReader.nextLong(2) and 8 == 8L) {
                offset += 12
            }
            zipReader.safeSkip(2)

            val time = getTime(zipReader.nextLong(2))
            val date = getDate(zipReader.nextLong(2))
            val timestamp = "$date $time"
            zipReader.safeSkip(4)

            offset += zipReader.nextLong(4)

            val fileSize = zipReader.nextLong(4)
            val fileNameLength = zipReader.nextLong(2)
            offset += zipReader.nextLong(2)

            val fileName = zipReader.nextString(fileNameLength.toInt())
            zipReader.safeSkip(offset)

            addFile(fileName, fileSize, timestamp)
        }
    }

    override fun getRootDirectory(): Directory {
        return root
    }

    override fun getDirsByName(searchFor: String): Array<Directory> {
        val splittedPath = searchFor.split('/')

        // if we got simple name let's find all of them
        if (splittedPath.size == 1)
            return searchForDirByName(searchFor, root)

        // if we got full path to it
        var curDir = root
        for (i in 0 until splittedPath.size) {
            val nextDirName = splittedPath[i]
            if (!curDir.folders.contains(nextDirName))
                // there.s no folder with path given
                return arrayOf<Directory>()
            curDir = curDir.folders.getValue(nextDirName)
        }
        return arrayOf<Directory>(curDir)
    }

    override fun getFilesByName(searchFor: String): Array<FileStructure> {
        val splittedPath = searchFor.split('/')

        // if we got simple name let's find all of them
        if (splittedPath.size == 1)
            return searchForFileByName(searchFor, root)

        // if we got full path to it
        var curDir = root
        for (i in 0 until splittedPath.size - 1) {
            val nextDirName = splittedPath[i]
            if (!curDir.folders.contains(nextDirName))
                return arrayOf<FileStructure>()
            curDir = curDir.folders.getValue(nextDirName)
        }

        if (curDir.files.contains(splittedPath.last()))
            return arrayOf<FileStructure>(curDir.files.getValue(splittedPath.last()))
        return arrayOf<FileStructure>()
    }

    private fun searchForDirByName(searchFor: String, dir: Directory): Array<Directory> {
        val result = ArrayList<Directory>()

        for (nextDir in dir.folders) {
            if (nextDir.value.name.contentEquals(searchFor)) {
                result.add(nextDir.value)
            }
            result.addAll(searchForDirByName(searchFor, nextDir.value))
        }

        return result.toTypedArray()
    }

    private fun searchForFileByName(searchFor: String, dir: Directory): Array<FileStructure> {
        val result = ArrayList<FileStructure>()

        for (nextDir in dir.folders) {
            result.addAll(searchForFileByName(searchFor, nextDir.value))
        }
        if (dir.files.contains(searchFor))
            result.add(dir.files.getValue(searchFor))

        return result.toTypedArray()
    }

    private fun addFile(fileName: String, size: Long, timestamp: String) {
        val splittedPath = fileName.split('/')
        root.incSize(size)

        var curDir = root
        var fullDirName = splittedPath[0]

        for (i in 0 until splittedPath.size - 1) {
            if (!curDir.folders.contains(splittedPath[i])) {
                curDir.folders[splittedPath[i]] = Directory(splittedPath[i], fullDirName)
            }
            curDir = curDir.folders.getValue(splittedPath[i])
            curDir.incSize(size)
            fullDirName += "/${splittedPath[i + 1]}"
        }

        // if its not, we have a folder here
        if (splittedPath.last() != "")
            curDir.files[splittedPath.last()] =
                FileStructure(
                    splittedPath.last(),
                    fileName,
                    size,
                    timestamp
                )
    }

    // functions to work with InputStream
    // and parsing to date&time formats
    private fun InputStream.nextLong(bytes: Int): Long {
        var resultStr = ""
        for (i in this.getBytes(bytes).reversed()) {
            val dig = if (i >= 0)
                i.toString(16)
            else
                (256 + i).toString(16)
            if (dig.length < 2)
                resultStr += "0"
            resultStr += dig
        }
        return resultStr.toLong(16)
    }

    private fun InputStream.nextString(bytes: Int): String {
        return this.getBytes(bytes).toString(Charsets.UTF_8)
    }

    private fun InputStream.safeSkip(bytes: Long) {
        if (this.skip(bytes) == -1L) {
            forcedExit("Given archive is not a .zip file or damaged")
        }
    }

    private fun InputStream.getBytes(bytes: Int): ByteArray {
        val result = ByteArray(bytes)
        if (this.read(result) != bytes)
            forcedExit("Given archive is not a .zip file or damaged")
        return result
    }

    private fun getTime(rawTimeL: Long): String {
        val rawTime = rawTimeL.toInt()
        //val second = (rawTime and 0x1fL) shl 1
        val minute = (rawTime and 0x7e0) shr 5
        val hour = (rawTime) shr 11
        return "${addZero(hour)}:${addZero(minute)}"
    }

    private fun getDate(rawDateL: Long): String {
        val rawDate = rawDateL.toInt()
        val day = rawDate and 0x1f
        val month = (rawDate and 0x1e0) shr 5
        val year = 1980 + ((rawDate and 0xfe00) shr 9)
        return "${addZero(day)}.${addZero(month)}.$year"
    }

    // fun to add some '0' before the number to make it a given length
    private fun addZero(to: Int, length: Int = 2): String {
        var zero = ""
        val strTo = to.toString()
        for (i in 1..maxOf(0, length - strTo.length))
            zero += "0"
        return zero.plus(strTo)
    }
}