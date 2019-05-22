import java.io.RandomAccessFile

fun RandomAccessFile.read(from: Int, countOfBytes: Int): ByteArray {
    val data = ByteArray(countOfBytes)

    seek(from.toLong())
    read(data, 0, countOfBytes)

    return data
}

fun main() {
    val file = RandomAccessFile("C:/Workspace/image.bmp", "r")

    val beginOfImagesBytes = file.read(10, 4)

    val beginOfPixelData = (beginOfImagesBytes[3].toInt() shl 24) or
        (beginOfImagesBytes[2].toInt() shl 16) or
        (beginOfImagesBytes[1].toInt() shl 8) or
        beginOfImagesBytes[0].toInt()

    val header = file.read(0, beginOfPixelData)
    val data = file.read(beginOfPixelData, (file.length() - beginOfPixelData).toInt())

    val coder = Coder(16)

    val encodedData = coder.encode(data)

    for (percentage in 5..15 step 5) {
        val noisedData = convert(encodedData, Byte.SIZE_BITS, Byte.SIZE_BITS) { word: IntArray ->
            IntArray(word.size) {
                when {
                    (0..99).random() < percentage -> 1 - word[it]
                    else                          -> word[it]
                }
            }
        }

        val noisedFile = RandomAccessFile("C:/Workspace/${percentage}_noised.bmp", "rw")
        noisedFile.write(header)
        noisedFile.write(coder.removeControlBits(noisedData))

        val restoredFile = RandomAccessFile("C:/Workspace/${percentage}_restored.bmp", "rw")
        restoredFile.write(header)
        restoredFile.write(coder.decode(noisedData))
    }
}
