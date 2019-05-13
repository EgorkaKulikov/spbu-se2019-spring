import java.io.File

const val FIRST_BYTE_PICTURE = 54
const val SECOND_BYTE_MASK = 0xFFU

@kotlin.ExperimentalUnsignedTypes
fun main() {
    val nameFile = "src/picture_.bmp"
    val file = File(nameFile)
    val fileContent = file.readBytes()
    val fileSize = fileContent.size

    val headerBytesArray = ByteArray(FIRST_BYTE_PICTURE) { i -> fileContent[i] }
    val picSize = fileSize - FIRST_BYTE_PICTURE
    val bmpColorBytesArray = ByteArray(picSize) { i -> fileContent[FIRST_BYTE_PICTURE + i] }

    for (percentOfDistortion in 5..15 step 5) {
        val corruptedData = ByteArray(picSize)
        val fixedData = ByteArray(picSize)

        for (i in 0 until picSize step 2) {
            val codedData = if (i + 1 < picSize) {
                code(bmpColorBytesArray[i], bmpColorBytesArray[i+1])
            } else {
                code(bmpColorBytesArray[i], 0)
            }

            var corruptedDataUnit = corruptWord(codedData, percentOfDistortion)
            val fixedDataUnit = decode(corruptedDataUnit)

            corruptedDataUnit = convertToWord(corruptedDataUnit)

            corruptedData[i] = (corruptedDataUnit shr 8).toByte()
            fixedData[i] = (fixedDataUnit shr 8).toByte()
            if (i + 1 < picSize) {
                corruptedData[i + 1] = (corruptedDataUnit and (SECOND_BYTE_MASK)).toByte()
                fixedData[i + 1] = (fixedDataUnit and (SECOND_BYTE_MASK)).toByte()
            }
        }

        val corruptedFileContent = headerBytesArray + corruptedData
        val fixedFileContent = headerBytesArray + fixedData

        val pathNameOfCorruptedData = "src/picture_$percentOfDistortion%_corrupted.bmp"
        File(pathNameOfCorruptedData).writeBytes(corruptedFileContent)

        val pathNameOfFixedData = "src/picture_$percentOfDistortion%_fixed.bmp"
        File(pathNameOfFixedData).writeBytes(fixedFileContent)
    }
}