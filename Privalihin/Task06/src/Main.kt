import cucumber.api.java.hu.Ha
import java.io.File
import kotlin.random.Random
import kotlin.test.assertEquals

fun corruptRandomBit(data: ByteArray) {
    val bytePosition = Random.Default.nextInt(data.size)
    val bitPositionLimit = if (bytePosition % 3 == 0) 8 else 5
    val bitPosition = Random.Default.nextInt(bitPositionLimit)
    data[bytePosition] = (data[bytePosition].toInt() xor (1 shl bitPosition)).toByte()
}

fun corruptData(data: ByteArray, percentage: Int) {
    val quantity = data.size * percentage * 8 / 100

    for (i in 1..quantity) {
        corruptRandomBit(data)
    }
}

fun printByteArray(data: ByteArray) {
    for (k in 0.until(data.size)) {
        for (i in 8 downTo 1) {
            if (data[k].toInt() and (1 shl (i - 1)) != 0) {
                print(1)
            } else {
                print(0)
            }
        }

        print(" ")
    }

    println()
}

fun main() {
    val myHamming = Hamming()

    for (i in 1..7) {
        val fileName = "img/picture${i}.bmp"
        val file = File(fileName)
        val fileData: ByteArray = file.readBytes()
        val fileSize = fileData.size
        val headerSize = 54
        val header = ByteArray(54) { i -> fileData[i] }
        val data = ByteArray(size = fileSize - headerSize) { i -> fileData[i + headerSize] }
        val encodedData = myHamming.encode(data)

        for (percentage in listOf(5, 10, 15)) {
            val corruptedData = encodedData
            corruptData(corruptedData, percentage)
            val decodedData = myHamming.decode(corruptedData)
            val fileNameFixedData = "img/FixedPicture${i}_${percentage}_percent.bmp"
            val fileFixedData = File(fileNameFixedData)
            fileFixedData.createNewFile()
            var resultFixedData = ByteArray(0)

            resultFixedData += header
            resultFixedData += decodedData
            fileFixedData.writeBytes(resultFixedData)
        }
    }
}
