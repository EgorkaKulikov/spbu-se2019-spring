import java.io.File

// transformation matrix:
//
// 101010101010101010101 = 1398101
// 011001100110011001100 = 838860
// 000111100001111000011 = 246723
// 000000011111111000000 = 16320
// 000000000000000111111 = 63

val transformationMatrixStrings = arrayListOf(1398101, 838860, 246723, 16320, 63) //transformation matrix strings presented in decimal
val listOfParityBitsIndexes = listOf(1, 2, 4, 8, 16)
const val wordLength = 21
const val headerSize = 54

fun bitsCount(n: Int): Int {
    var count = 0
    var num = n
    while (num > 0) {
        num = num.and(num - 1)
        count++
    }

    return count
}

fun addParityBits(data: Int): Int {
    var encodedData = 0
    var count = 15

    for (i in 1..wordLength) {
        if (i !in listOfParityBitsIndexes) {
            if (data.shr(count) % 2 == 1)
                encodedData += 1 shl (wordLength - i)
            count--
        }
    }

    for ((index, i) in listOfParityBitsIndexes.withIndex()) {
        val currParityBit = bitsCount(encodedData.and(transformationMatrixStrings[index])) % 2
        if (currParityBit == 1)
            encodedData = encodedData.or(1 shl (wordLength - i))
    }

    return encodedData
}

fun removeParityBits(data: Int): Int {
    var clearedData = 0
    var count = 15

    for (i in 1..wordLength) {
        if (i !in listOfParityBitsIndexes)  {
            if (data.shr(wordLength - i) % 2 == 1)
                clearedData += 1 shl count
            count--
        }
    }

    return clearedData
}

fun encode(data: Int): Int = addParityBits(data)

fun decode(encodedData: Int): Int {
    val clearedData = removeParityBits(encodedData)
    var newEncodedData = addParityBits(clearedData)

    var wrongBitIndex = 0

    for (i in listOfParityBitsIndexes) {
        if (encodedData.and(1 shl (wordLength - i)) != newEncodedData.and(1 shl (wordLength - i)))
            wrongBitIndex += i
    }

    newEncodedData = newEncodedData.xor(1 shl (wordLength - wrongBitIndex))

    return removeParityBits(newEncodedData)
}

fun corrupt(dataBlock: Int, percent: Int): Int {
    var corruptedData = dataBlock

    for (i in wordLength - 1 downTo 0) {
        if ((1..100).random() <= percent)
            corruptedData = corruptedData.xor(1 shl i)
    }

    return corruptedData
}

fun main() {
    val pictureName = "src/picture.bmp"

    val fileData = File(pictureName).readBytes()
    val fileSize = fileData.size

    val header = ByteArray(headerSize) {fileData[it]}

    val pictureSize = fileSize - headerSize
    val pictureData = ByteArray(pictureSize) {fileData[it + headerSize]}

    for (percent in listOf(5, 10, 15)) {
        val corruptedPictureData = ByteArray(pictureSize)
        val restoredPictureData = ByteArray(pictureSize)

        for (i in 0 until pictureSize step 2) {
            val dataBlock = if (i + 1 < pictureSize)
                pictureData[i].toInt().and(0xFF).shl(8) + pictureData[i + 1].toInt().and(0xFF)
            else
                pictureData[i].toInt().and(0xFF).shl(8).shl(8)

            val encodedDataBlock = encode(dataBlock)
            val corruptedEncodedDataBlock = corrupt(encodedDataBlock, percent)
            val decodedDataBlock = decode(corruptedEncodedDataBlock)

            corruptedPictureData[i] = removeParityBits(corruptedEncodedDataBlock).and(0xFF00).shr(8).toByte()
            if (i + 1 < pictureSize)
                corruptedPictureData[i + 1] = removeParityBits(corruptedEncodedDataBlock).and(0xFF).toByte()

            restoredPictureData[i] = decodedDataBlock.and(0xFF00).shr(8).toByte()
            if (i + 1 < pictureSize)
                restoredPictureData[i + 1] = decodedDataBlock.and(0xFF).toByte()
        }

        var corruptedFileData = ByteArray(0)
        corruptedFileData += header
        corruptedFileData += corruptedPictureData

        val corruptedFileName = "src/picture_${percent}_corrupted.bmp"
        val corruptedFile = File(corruptedFileName)
        corruptedFile.createNewFile()
        corruptedFile.writeBytes(corruptedFileData)

        var restoredFileData = ByteArray(0)
        restoredFileData += header
        restoredFileData += restoredPictureData

        val restoredFileName = "src/picture_${percent}_restored.bmp"
        val restoredFile = File(restoredFileName)
        restoredFile.createNewFile()
        restoredFile.writeBytes(restoredFileData)
    }
}