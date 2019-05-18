import java.io.File
import kotlin.math.log2
import kotlin.random.Random

const val PATH = "C:/task06"
const val BMP_HEADER = 54
const val WORD_LENGTH = 16
val controlBitsAmount = log2(WORD_LENGTH.toDouble()).toInt() + 1
val encodedWORD_LENGTH = WORD_LENGTH + controlBitsAmount

fun insertBit(value: Int, position: Int): Int {
    val leftSlice = (value shr position) shl (position + 1)
    val rightSlice = value and ((1 shl position) - 1)
    return leftSlice + rightSlice
}

fun deleteBit(value: Int, position: Int): Int {
    val leftSlice = (value shr position) shl (position - 1)
    val rightSlice = value and ((1 shl (position - 1)) - 1)
    return leftSlice + rightSlice
}

fun extractControlBits(value: Int): Int {
    var result = value
    for (i in (controlBitsAmount - 1) downTo 0) {
        result = deleteBit(result, 1 shl i)
    }
    return result
}


fun encodeByHamming(value: Int): Int {

    var encodedValue = value

    // Inserting control bits
    for (i in 0 until controlBitsAmount) {
        encodedValue = insertBit(encodedValue, (1 shl i) - 1)
    }

    // Counting control bits
    for (i in 0 until controlBitsAmount) {
        var controlSum = 0
        for (position in 0 until encodedWORD_LENGTH) {
            val currentBit = (encodedValue shr position) and 1
            if ((position + 1) and (1 shl i) != 0) {
                // In case current position is under control of i-th control bit
                controlSum = (controlSum + currentBit) % 2
            }
        }
        val controlBit = (1 shl ((1 shl i) - 1)) * controlSum
        encodedValue = encodedValue or controlBit
    }

    return encodedValue
}


@ExperimentalUnsignedTypes
fun crash(data: UByteArray, percent: Int): UByteArray {

    val damagedBitsAmount = ((data.size * 8) * (percent / 100F)).toInt()

    for (i in 0 until damagedBitsAmount) {
        val bytePosition = Random.nextInt(data.size)
        val bitPosition = Random.nextInt(8)

        val current = data[bytePosition]
        data[bytePosition] = current xor (1 shl bitPosition).toUByte()
    }

    return data
}


fun decodeByHamming(value: Int): Int {

    // Only last 21 bits are significant
    val decodedValue = value and 0x1FFFFF
    var positionToFix = 0

    for (i in 0 until controlBitsAmount) {
        var realControlSum = 0
        for (position in 0 until encodedWORD_LENGTH) {
            val currentBit = (decodedValue shr position) and 1
            if ((position + 1) and (1 shl i) != 0) {
                realControlSum = (realControlSum + currentBit) % 2
            }
        }
        val currentControlSum = (decodedValue shr ((1 shl i) - 1)) and 1
        realControlSum = (realControlSum + currentControlSum) % 2
        if (currentControlSum != realControlSum) {
            positionToFix += (1 shl i)
        }
    }

    return if (positionToFix == 0)
        decodedValue
    else
        decodedValue xor (1 shl (positionToFix - 1))
}


@ExperimentalUnsignedTypes
fun main() {
    val pathToImage = "$PATH/image.bmp"
    val image = File(pathToImage).readBytes()

    val header = image.slice(0 until BMP_HEADER).toByteArray()
    val data = image.slice(BMP_HEADER until image.size).toByteArray()
    val encodedData = mutableListOf<UByte>()

        // Encoding data

    for (i in 0 until data.size step 2) {
        val word = if (i < data.size - 1)
            (data[i].toInt() shl 8) + data[i + 1].toInt()
        else
            data[i].toInt()

        val encodedWord = encodeByHamming(word)
        encodedData.add(((encodedWord and 0xFF0000) shr 16).toUByte())  // First 8-bits-group of value mask
        encodedData.add(((encodedWord and 0x00FF00) shr 8) .toUByte())  // Second 8-bits-group of value mask
        encodedData.add((encodedWord and 0x0000FF).toUByte())           // Third 8-bits-group of value mask
    }

        // Crashing and decoding

    for (percent in arrayOf(5, 10, 15)) {

        val damagedData = crash(
            data = encodedData.toUByteArray(),
            percent = percent
        )

        val decodedFixedData = mutableListOf<Byte>()
        val decodedDamagedData = mutableListOf<Byte>()

        for (i in 0 until damagedData.size step 3) {
            val word = (damagedData[i].toInt() shl 16) +
                    (damagedData[i + 1].toInt() shl 8) +
                    damagedData[i + 2].toInt()

            val decodedFixedWord = extractControlBits(decodeByHamming(word))
            decodedFixedData.add(((decodedFixedWord and 0xFF00) shr 8).toByte())
            decodedFixedData.add((decodedFixedWord and 0x00FF).toByte())

            val decodedDamagedWord = extractControlBits(word)
            decodedDamagedData.add(((decodedDamagedWord and 0xFF00) shr 8).toByte())
            decodedDamagedData.add((decodedDamagedWord and 0x00FF).toByte())
        }

        File("$PATH/fixed_${percent}_percent.bmp").writeBytes(header + decodedFixedData.toByteArray())
        File("$PATH/damaged_${percent}_percent.bmp").writeBytes(header + decodedDamagedData.toByteArray())
    }
}