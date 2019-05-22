import java.io.File
import kotlin.random.Random

// Hamming Encode/Decode

const val BMP_HEADER_SIZE = 54
const val PATH = "D:/spbu/Sem2/picSources/"

const val ENCODED_WORD_LENGTH = 21
const val CONTROL_BITS = 5
const val DECODED_WORD_LENGTH = 16

const val ENCODED_MASK = 0x1FFFFF
const val DECODED_MASK = 0xFFFF

fun Int.setBit(pos: Int, bit: Int): Int {
    val rightPart = this and ((1 shl pos) - 1)
    return ((((this shr (pos + 1)) shl 1) + bit) shl pos) + rightPart
}

fun Int.getBit(pos: Int): Int {
    return (this shr pos) % 2
}

fun Int.addBit(pos: Int, add: Int): Int {
    return this.setBit(pos, (this.getBit(pos) + add) % 2)
}

fun Int.isPow2(): Boolean {
    return (this and (this - 1)) == 0
}

fun encoding(message: Int): Int {
    // just for safety
    val msgToEncode = message and DECODED_MASK

    // filling @encodedMsg with our info
    var encodedMsg = 0
    var nextMsgPos = 0
    for (i in 0 until ENCODED_WORD_LENGTH) {
        if (!(i + 1).isPow2()) {
            encodedMsg = encodedMsg.setBit(i, msgToEncode.getBit(nextMsgPos))
            nextMsgPos++
        }
    }

    // setting controlBits
    for (pos in 0 until ENCODED_WORD_LENGTH) {
        for (controlBit in 0 until CONTROL_BITS) {
            if (((pos + 1) and (1 shl controlBit)) != 0) {
                encodedMsg = encodedMsg.addBit((1 shl controlBit), encodedMsg.getBit(pos))
            }
        }
    }

    return encodedMsg
}

fun extractControlBits(value: Int): Int {
    var result = 0
    var nextControlBit = 0
    for (pos in 0 until ENCODED_WORD_LENGTH) {
        if ((pos + 1).isPow2()) {
            result = result.setBit(nextControlBit, value.getBit(pos))
            nextControlBit++
        }
    }
    return result
}

fun extractMessage(value: Int): Int {
    var result = 0
    var nextMessageBit = 0
    for (pos in 0 until ENCODED_WORD_LENGTH) {
        if (!(pos + 1).isPow2()) {
            result = result.setBit(nextMessageBit, value.getBit(pos))
            nextMessageBit++
        }
    }
    return result
}

@ExperimentalUnsignedTypes
fun noiseSimulation(data: UByteArray, percent: Int): UByteArray {
    val bitsToChange = ((data.size * 8) * (percent.toFloat() / 100F)).toInt()
    for (i in 0 until bitsToChange) {
        val byteIndex = Random.nextInt(data.size)
        val bitIndex = Random.nextInt(8)
        val copy = data[byteIndex]
        data[byteIndex] = copy xor (1 shl bitIndex).toUByte()
    }
    return data
}

fun decoding(message: Int): Int {
    // just for safety
    var msgToDecode = message and ENCODED_MASK

    // recalculating controlBits
    var checkControlBits = 0
    for (pos in 0 until ENCODED_WORD_LENGTH) {
        for (controlBit in 0 until CONTROL_BITS) {
            if (((pos + 1) and (1 shl controlBit)) != 0) {
                if (!(pos + 1).isPow2())
                    checkControlBits = checkControlBits.addBit((1 shl controlBit), msgToDecode.getBit(pos))
            }
        }
    }

    val posToChange = extractControlBits(checkControlBits) xor extractControlBits(msgToDecode)
    if (posToChange != 0)
        msgToDecode = msgToDecode.addBit(posToChange, 1)
    return extractMessage(msgToDecode)
}

@ExperimentalUnsignedTypes
fun main() {
    val originalImage = File(PATH + "original.bmp").readBytes()
    val header = originalImage.slice(0 until BMP_HEADER_SIZE).toByteArray()
    val data = originalImage.slice(BMP_HEADER_SIZE until originalImage.size).toByteArray()
    val encodedData = arrayListOf<UByte>()

    for (i in 0 until data.size step 2) {
        val message = if (i < data.size - 1)
            (data[i].toInt() shl 8) + data[i + 1].toInt()
        else
            data[i].toInt()

        val encodedMessage = encoding(message)
        encodedData.add((encodedMessage shr 16).toUByte())
        encodedData.add(((encodedMessage and 0x00FF00) shr 8).toUByte())
        encodedData.add((encodedMessage and 0x0000FF).toUByte())
    }

    for (percent in 5..15 step 5) {
        val noisyData = noiseSimulation(encodedData.toUByteArray(), percent)
        val decodedData = arrayListOf<Byte>()
        val damagedData = arrayListOf<Byte>()

        for (i in 0 until noisyData.size step 3) {
            val word = (noisyData[i].toInt() shl 16) + (noisyData[i + 1].toInt() shl 8) + noisyData[i + 2].toInt()
            val decodedWord = decoding(word)
            val damagedWord = extractMessage(word)

            decodedData.add((decodedWord shr 8).toByte())
            decodedData.add((decodedWord and 0x00FF).toByte())

            damagedData.add((damagedWord shr 8).toByte())
            damagedData.add((damagedWord and 0x00FF).toByte())
        }

        File(PATH + "${percent}_damaged.bmp").writeBytes(header + damagedData.toByteArray())
        File(PATH + "${percent}_decoded.bmp").writeBytes(header + decodedData.toByteArray())
    }
}