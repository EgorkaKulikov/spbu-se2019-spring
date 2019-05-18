import java.io.BufferedInputStream
import java.io.File
import java.util.Observable

class Model: IModel, Observable() {

    private val paths = mutableListOf<String>()
    private val timeStamps = mutableMapOf<String, String>()
    private val folderSizes = mutableMapOf<String, Int>()
    private val archive = Archive()

    private lateinit var buffer: ByteArray
    private lateinit var lastBufferCopy: ByteArray
    private lateinit var mergedBuffer: ByteArray



    override fun parseArchive(parameters: HashMap<String, String>) {

        buffer = ByteArray(BUFFER_SIZE)
        lastBufferCopy = byteArrayOf()

            // Parameters processing

        val pathToArchive = parameters["pathToArchive"]

        val targetFileName = parameters["fileName"]
        if (targetFileName != null)
            archive.targetFileStated = true

        val targetFolderName = parameters["folderName"]
        if (targetFolderName != null)
            archive.targetFolderStated = true

            // Data processing

        File(pathToArchive).inputStream().buffered().use { input ->

            while (true) {
                val bufferSize = input.read(buffer)
                if (bufferSize <= 0) // Nothing to read
                    break

                mergedBuffer = lastBufferCopy + buffer
                var position = 0
                var wasBufferResized = false

                while (position + Signature.CentralDirectoryHeaderBytes <= lastBufferCopy.size + bufferSize) {

                    // Checking for Central Directory File Header
                    if (mergedBuffer.getBytes(position, 4).toHexString() == Signature.CentralDirectoryHeader) {


                            // Extracting file name length

                        var offset = position + Signature.FilenameLengthOffset
                        // If current buffer is too small, resize it
                        if (offset + Signature.FilenameLengthBytes > mergedBuffer.size) {
                            resizeBuffer(
                                    inputStream = input,
                                    size = offset + Signature.FilenameLengthBytes - mergedBuffer.size
                            )
                            wasBufferResized = true
                        }
                        val filenameLength = mergedBuffer
                                .getBytes(offset, Signature.FilenameLengthBytes)
                                .toInt()



                            // Extracting file name

                        offset = position + Signature.FilenameOffset
                        // If current buffer is too small, resize it
                        if (offset + filenameLength > mergedBuffer.size) {
                            resizeBuffer(
                                    inputStream = input,
                                    size = offset + filenameLength - mergedBuffer.size
                            )
                            wasBufferResized = true
                        }
                        val filename = mergedBuffer
                                .getBytes(offset, filenameLength)
                                .toFilename()
                        paths.add(filename)


                            // Extracting file date&time if needed

                        if (targetFileName != null && filename.substringAfterLast('/') == targetFileName) {

                            offset = position + Signature.FileModificationTimeOffset
                            val fileModificationTime = mergedBuffer
                                    .getBytes(offset, Signature.FileModificationTimeBytes)
                                    .toTime()

                            offset = position + Signature.FileModificationDateOffset
                            val fileModificationDate = mergedBuffer
                                    .getBytes(offset, Signature.FileModificationDateBytes)
                                    .toDate()

                            val timestamp = "${fileModificationDate.padEnd(11)} $fileModificationTime"
                            timeStamps[filename] = timestamp
                        }



                            // Extracting file size if it's located in target directory

                        if (targetFolderName != null && filename.contains(targetFolderName)) {

                            offset = position + Signature.FileUncompressedSizeOffset
                            val size = mergedBuffer
                                    .getBytes(offset, Signature.FileUncompressedSizeBytes)
                                    .toInt()
                            // Changing target directories sizes, even if they're embedded in each other
                            for (index in 0 until filename.length - targetFolderName.length) {
                                val current = filename.substring(index, index + targetFolderName.length + 1)
                                if (current == "$targetFolderName/") {
                                    val prefix = filename.substring(0, index + targetFolderName.length + 1)
                                    folderSizes[prefix] = folderSizes[prefix]?.plus(size) ?: size
                                }
                            }
                        }

                        position += Signature.FilenameOffset + filenameLength
                    }
                    else {
                        position += 1
                    }
                }
                lastBufferCopy = if (wasBufferResized)
                    byteArrayOf()
                else
                    buffer.copyOf()
            }
        }
    }

    override fun buildFilesStructure() {
        for (path in paths) {
            var items = path.split('/')
            var isFolder = false
            if (items.last() == "") {
                items = items.dropLast(1)
                isFolder = true
            }
            archive.files.add(
                    Archive.FileInstance(
                            path = path,
                            depth = items.size - 1,
                            name = items.last(),
                            isFolder = isFolder,
                            timestamp = timeStamps[path],
                            size = folderSizes[path]
                    )
            )
        }
        archive.files.sortBy { it.path }
        setChanged()
        notifyObservers(archive)
    }





    private fun resizeBuffer(inputStream: BufferedInputStream, size: Int) {
        val temp = ByteArray(size)
        inputStream.read(temp)
        mergedBuffer += temp
    }

    private fun ByteArray.getBytes(startIndex: Int, amount: Int): ByteArray {
        if (this.size < startIndex + amount)
            throw Exception("Archive is damaged")
        val temp = mutableListOf<Byte>()
        for (index in startIndex until (startIndex + amount)) {
            temp.add(this[index])
        }
        return temp.toByteArray()
    }

    private fun ByteArray.toHexString(): String {
        var result = ""
        for (value in this) {
            val unsigned = if (value >= 0)
                value.toString(16)
            else
                (256 + value).toString(16)
            result += if (unsigned.length < 2)
                "0$unsigned"
            else
                unsigned
        }
        return result
    }

    private fun ByteArray.toInt(): Int = this.reversedArray().toHexString().toInt(16)

    private fun ByteArray.toFilename(): String {
        var filename = ""
        for (byte in this) {
            filename += byte.toChar()
        }
        return filename
    }

    private fun ByteArray.toTime(): String {
        val bytesTime = this.toInt()
        val minute = (bytesTime and 0x7E0) shr 5
        val hour = bytesTime shr 11
        return "$hour:$minute"
    }

    private fun ByteArray.toDate(): String {
        val bytesDate = this.toInt()
        val day = bytesDate and 0x1F
        val month = (bytesDate and 0x1E0) shr 5
        val year = ((bytesDate and 0xFE00) shr 9) + 1980
        return "$day/$month/$year"
    }

}
