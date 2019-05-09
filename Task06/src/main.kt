import java.io.File

val magicConstants = arrayListOf(0, 2796202, 838860, 3207408, 65280, 4128768)

fun Byte.toInt(count: Int): Int {

    val result = this.toInt()

    return (result and ((1 shl count) - 1))
}

fun count(n: Int): Int {

    var result = 0

    for (i in 0..31) {
        if (n and (1 shl i) != 0) {
            result = result xor 1
        }
    }

    return result
}

fun encode(data: Int): Int {
    var result = 0
    var j = 0

    for (i in 3..21) {
        if (i and (i - 1) != 0) {
            result += (data and (1 shl j)) shl (i - j)
            ++j
        }
    }

    for (i in 1..5) {
        result += count(magicConstants[i] and result) shl (1 shl (i - 1))
    }

    return result
}

fun decode(data: Int, alteredData: Int): Int {

    if (data == alteredData) return data

    var alteredBit = 0
    val encodedData = encode(data)
    var encodedAlteredData = encode(alteredData)

    for (i in listOf(1, 2, 4, 8, 16)) {
        if ((encodedData and (1 shl i)) != (encodedAlteredData and (1 shl i))) {
            alteredBit += i
            encodedAlteredData = encodedAlteredData xor (1 shl i)
        }
    }

    encodedAlteredData = encodedAlteredData xor (1 shl alteredBit)

    if (encodedAlteredData != encodedData) return alteredData

    return data

}

fun alterBits(data: ByteArray, percent: Int): ByteArray {

    val result = ByteArray(data.size) { i -> data[i] }

    for( i in 1..(data.size * percent / 100)) {

        val position = result.indices.random()
        val bit = (0..7).random()

        result[position] = (result[position].toInt() xor (1 shl bit)).toByte()
    }

    return result
}

fun main() {

    val fileName = "src/picture.bmp"
    val file = File(fileName)
    val fileData: ByteArray = file.readBytes()
    val fileSize = fileData.size

    val headerSize = 54
    val header = ByteArray(54) { i -> fileData[i] }

    val data = ByteArray(size = fileSize - headerSize) { i -> fileData[i + headerSize] }

    for (percent in listOf(5, 10, 15)) {

        val alteredData = alterBits(data, percent)

        val fileNameAlteredData = "src/AlteredPicture_${percent}_percent.bmp"
        val fileAlteredData = File(fileNameAlteredData)

        fileAlteredData.createNewFile()

        var resultAlteredData = ByteArray(0)

        resultAlteredData += header
        resultAlteredData += alteredData
        fileAlteredData.writeBytes(resultAlteredData)

        var fixedByteData = ByteArray(0)

        for (i in 0 until data.size step 2) {

            var group: Int = data[i + 1].toInt(8)

            group = group shl 8
            group += data[i].toInt(8)

            var alteredGroup: Int = alteredData[i + 1].toInt(8)

            alteredGroup = alteredGroup shl 8
            alteredGroup += alteredData[i].toInt(8)

            val decoded = decode(group, alteredGroup)

            fixedByteData += decoded.toByte()
            fixedByteData += (decoded ushr 8).toByte()

        }

        val fileNameFixedData = "src/FixedPicture_${percent}_percent.bmp"
        val fileFixedData = File(fileNameFixedData)

        fileFixedData.createNewFile()

        var resultFixedData = ByteArray(0)

        resultFixedData += header
        resultFixedData += fixedByteData
        fileFixedData.writeBytes(resultFixedData)

    }

}