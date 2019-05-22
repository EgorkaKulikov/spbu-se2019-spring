import java.io.File

class Hamming_Decode(filePath:String) {
    private val fileData = File(filePath).readBytes()
    private val wordLen = 21
    private val headerSize = 54
    private val magicMatrix = arrayListOf(1398101, 838860, 246723, 16320, 63)
    private val fileSize = fileData.size
    private val header = ByteArray(headerSize){fileData[it]}
    private val imageSize = fileSize - headerSize
    private val imageData = ByteArray(imageSize){fileData[it + headerSize]}
    //https://en.wikipedia.org/wiki/Hamming_code#General_algorithm

    private fun controlBitsCount(n: Int): Int {
        var res = 0
        for (i in 0..31) {
            if (n and (1 shl i) != 0) {
                res = res xor 1
            }
        }
        return res
    }


    private fun insertControlBits(data: Int): Int {
        var encodedData = 0
        var count = 15

        for (i in 1..wordLen) {
            if (i !in listOf(1, 2, 4, 8, 16)) {
                if (data.shr(count) % 2 == 1)
                    encodedData += 1 shl (wordLen - i)
                count--
            }
        }

        return encodedData
    }

    private fun encode(data: Int): Int = insertControlBits(data)

    private fun deleteControlBits(data: Int): Int {
        var newData = 0
        var count = 15

        for (i in 1..wordLen) {
            if (i !in listOf(1, 2, 4, 8, 16))  {
                if (data.shr(wordLen - i) % 2 == 1)
                    newData += 1 shl count
                count--
            }
        }

        return newData
    }

    private fun decode(encodedData: Int): Int {
        val clearedData = deleteControlBits(encodedData)
        var newEncodedData = insertControlBits(clearedData)

        var incorrectIndex = 0

        for (i in listOf(1, 2, 4, 8, 16)) {
            if (encodedData.and(1 shl (wordLen - i)) != newEncodedData.and(1 shl (wordLen - i)))
                incorrectIndex += i
        }

        newEncodedData = newEncodedData.xor(1 shl (wordLen - incorrectIndex))

        return deleteControlBits(newEncodedData)
    }

    private fun invertBit(word: Int, position: Int): Int {
        val numberLastBits = wordLen - position
        val lastBits = word % (1 shl numberLastBits)
        var newWord =  word shr numberLastBits
        var invertedBit = newWord % 2

        invertedBit = if (invertedBit == 0) 1 else 0
        newWord = (newWord shr 1) shl 1

        return ((newWord + invertedBit) shl numberLastBits) + lastBits
    }

    private fun spoilBit(dataBlock: Int, percent: Int): Int {
        var spoilData = dataBlock

        for (i in 1..21) {
            if ((1..100).random() <= percent) {
                spoilData = invertBit(spoilData, i)
            }
        }
        return spoilData
    }

    fun createImages() {
        for (percent in listOf(5, 9, 13)) {

            val spoiledImageData = ByteArray(imageSize)
            val restoredImageData = ByteArray(imageSize)

            var spoiledFileData = ByteArray(0)
            val spoiledFileName = "src/picture_${percent}_spoiled.bmp"

            val spoiledFile = File(spoiledFileName)
            var restoredFileData = ByteArray(0)

            val restoredFileName = "src/picture_${percent}_restored.bmp"
            val restoredFile = File(restoredFileName)

            for (i in 0 until imageSize step 2) {
                val value = if (i + 1 < imageSize) {
                    imageData[i].toInt().and(0xFF).shl(8) +
                            imageData[i + 1].toInt().and(0xFF)
                }
                else {
                    imageData[i].toInt().and(0xFF).shl(8)
                }

                val encoded = encode(value)
                val spoiled = spoilBit(encoded, percent)
                val decoded = decode(spoiled)
                spoiledImageData[i] = deleteControlBits(spoiled).and(0xFF00).shr(8).toByte()

                if (i + 1 < imageSize) {
                    spoiledImageData[i + 1] = deleteControlBits(spoiled).and(0xFF).toByte()
                }
                restoredImageData[i] = decoded.and(0xFF00).shr(8).toByte()

                if (i + 1 < imageSize) {
                    restoredImageData[i + 1] = decoded.and(0xFF).toByte()
                }

            }

            spoiledFileData += header
            spoiledFileData += spoiledImageData

            spoiledFile.createNewFile()
            spoiledFile.writeBytes(spoiledFileData)

            restoredFileData += header
            restoredFileData += restoredImageData

            restoredFile.createNewFile()
            restoredFile.writeBytes(restoredFileData)
        }
    }
}