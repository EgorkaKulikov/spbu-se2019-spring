import java.io.File

val controlIndexes = listOf(1, 2, 4, 8, 16)
const val HAMMING_LENGTH = 21
const val HEADER_SIZE = 54
const val BITS_CNT = 5

fun encode(word: Int): Int {
    var encodedWord = word

    for (i in 0 until BITS_CNT){
        encodedWord = insertBit(encodedWord, (1 shl i) - 1)
    }

    for (i in 0 until BITS_CNT){
        encodedWord = encodedWord or countBit(encodedWord, i)
    }
    return encodedWord
}

fun decode(word: Int): Int {
    val cleared = removeControlBits(word)
    val newWord = encode(cleared)

    var errorBit = 0

    for (i in controlIndexes) {
        if (word.and(1.shl(HAMMING_LENGTH - i)) != newWord.and(1.shl(HAMMING_LENGTH - i)))
            errorBit += i
    }

    return removeControlBits(newWord.xor(1.shl(HAMMING_LENGTH - errorBit)))
}


fun countBit(word: Int, i: Int): Int {
    var controlSum = 0
    for (pos in 0 until HAMMING_LENGTH){
        val bit = word.shr(pos).and(1)
        if ((pos + 1).and(1.shl(i)) != 0)
            controlSum = (controlSum + bit) % 2
    }
    return 1.shl(1.shl(i) - 1) * controlSum
}

fun insertBit(word : Int, index : Int): Int {
    return word.shr(index).shl(index + 1) + word.and(1.shl(index) - 1)
}

fun removeBit(word : Int, index : Int) : Int{
    return word.shr(index).shl(index - 1) + word.and(1.shl(index - 1) - 1)
}

fun removeControlBits(word : Int) : Int{
    var result = word
    for (i in (BITS_CNT - 1) downTo 0){
        result = removeBit(result, 1 shl i)
    }
    return result
}

fun damage(word: Int, percent: Int): Int {
    var damaged = word

    for (i in 0 until HAMMING_LENGTH) {
        if ((1..100).random() <= percent)
            damaged = damaged.xor(1 shl i)
    }

    return damaged
}

fun main() {
    val fileData = File("src/picture.bmp").readBytes()
    val fileSize = fileData.size

    val header = ByteArray(HEADER_SIZE) {i -> fileData[i]}

    val size = fileSize - HEADER_SIZE
    val data = ByteArray(size) {i -> fileData[i + HEADER_SIZE]}

    for (percent in listOf(5, 10, 15)) {
        val damagedData = ByteArray(size)
        val fixedData = ByteArray(size)

        for (i in 0 until size step 2) {
            var word = data[i].toInt().and(0xFF).shl(8)
            if (i + 1 < size)
                word += data[i + 1].toInt().and(0xFF)

            val encoded = encode(word)
            val encodedDamaged = damage(encoded, percent)
            val decoded = decode(encodedDamaged)

            damagedData[i] = removeControlBits(encodedDamaged).and(0xFF00).shr(8).toByte()
            fixedData[i] = decoded.and(0xFF00).shr(8).toByte()

            if (i + 1 < size) {
                damagedData[i + 1] = removeControlBits(encodedDamaged).and(0xFF).toByte()
                fixedData[i + 1] = decoded.and(0xFF).toByte()
            }

        }
        
        val damagedFile = File("src/damaged$percent%.bmp")
        damagedFile.createNewFile()
        damagedFile.writeBytes(header + damagedData)

        val fixedFileName = "src/fixed$percent%.bmp"
        val fixedFile = File(fixedFileName)
        fixedFile.createNewFile()
        fixedFile.writeBytes(header + fixedData)
    }
}
