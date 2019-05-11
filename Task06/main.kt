import java.io.File
const val BYTESIZE = 8

@kotlin.ExperimentalUnsignedTypes
fun main() {
    val inputPath = "in.bmp"
    val image: ByteArray = File(inputPath).readBytes()

    //getting offset from header
    val firstImageOffsetByte = 10
    val numberOfImageOffsetBytes = 4
    var offset = 0
    var shift = 0
    for (i in 0 until numberOfImageOffsetBytes) {
        offset += image[firstImageOffsetByte + i].toInt() shl shift
        shift += BYTESIZE
    }

    //making subarray without header
    val fileHeader = image.sliceArray(0 until offset)
    val imageBytes = image.sliceArray(offset until image.size)


    var fixedBytes = ByteArray(imageBytes.size)
    var damagedBytes = ByteArray(imageBytes.size)

    for (percent in 5..15 step 5) {
        for (i in 0 until imageBytes.size step 2) {
            val word = HammingDataWord(0u, WORDLEN)

            word.value = (imageBytes[i].toUInt() shl BYTESIZE)
            if (i + 1 < imageBytes.size)
                word.value += imageBytes[i + 1].toUInt()
            val encoded = word.encode()
            encoded.corrupt(percent)

            val decodedFixed = encoded.decode()
            val decoded = encoded.removeControlBits()

            fixedBytes[i] = ((decodedFixed and 0xFF00u) shr BYTESIZE).toByte()
            if (i + 1 < imageBytes.size)
                fixedBytes[i + 1] = (decodedFixed and 0xFFu).toByte()

            damagedBytes[i] = ((decoded and 0xFF00u) shr BYTESIZE).toByte()
            if (i + 1 < imageBytes.size)
                damagedBytes[i + 1] = (decoded and 0xFFu).toByte()

        }
        fixedBytes = fileHeader + fixedBytes
        damagedBytes = fileHeader + damagedBytes

        val fixedImagePathName = "out$percent%Fixed.bmp"
        val damagedImagePathName = "out$percent%Damaged.bmp"

        File(fixedImagePathName).writeBytes(fixedBytes)
        File(damagedImagePathName).writeBytes(damagedBytes)
    }
}