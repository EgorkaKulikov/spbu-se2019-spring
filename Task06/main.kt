import java.io.File
import kotlin.math.log2

const val LengthBlock = 16
val decodeTable: Array<Int?> by lazy {
    val answer = Array<Int?>(1 shl 24) {null}
    for (code in 0 until (1 shl LengthBlock)) {
        val hammingCode = codeHamming(code)
        answer[hammingCode] = code
        for (i in 0 until 24)
            answer[hammingCode xor (1 shl i)] = code
    }
    return@lazy answer
}

fun codeHamming(code: Int): Int {
    var codeHammingSize = LengthBlock + log2((LengthBlock + 1).toFloat()).toInt()
    while (codeHammingSize + 1 > 1 shl (codeHammingSize - LengthBlock + 1) - 1)
        codeHammingSize++
    var pow2 = 1
    var result = 0
    var j = 0
    for (i in 0 until codeHammingSize) {
        if (pow2 == i + 1) {
            pow2 = pow2 shl 1
        }
        else {
            if (code and (1 shl j) != 0) {
                result += 1 shl i
                var indexThisBit = i + 1
                var indexChecksum = 0
                while (indexThisBit != 0) {
                    if (indexThisBit and 1 == 1)
                        result = result xor (1 shl indexChecksum)
                    indexThisBit = indexThisBit shr 1
                    indexChecksum = indexChecksum * 2 + 1
                }
            }
            j++
        }
    }
    return result
}

fun randomIntArray(from: Int, to: Int, amount: Int): IntArray {
    val randomIntegers = mutableSetOf<Int>()
    while (randomIntegers.size < amount) {
        randomIntegers.add((from..to).random())
    }
    return randomIntegers.toIntArray()
}

fun Array<Int>.hammingDataBlocksToPictureData(): ByteArray {
    val result = ByteArray(size * LengthBlock / 8)
    for (i in 0 until size) {
        var pow2 = 1
        var j = 0
        var k = 0
        while (j < LengthBlock) {
            if (k + 1 == pow2) {
                pow2 *= 2
            }
            else {
                if (this[i] and (1 shl k) != 0)
                    result[i * (LengthBlock / 8) + (LengthBlock - 1 - j) / 8] =
                        result[i * (LengthBlock / 8) + (LengthBlock - 1 - j) / 8].plus(1 shl (j and 7)).toByte()
                j++
            }
            k++
        }
    }
    return result
}

fun Array<Int>.dataBlocksToPictureData(): ByteArray {
    val result = ByteArray(size * (LengthBlock / 8))
    for (i in 0 until size) {
        for (j in 0 until (LengthBlock / 8)) {
            result[(i + 1) * (LengthBlock / 8) - j - 1] = ((this[i] shr (j * 8)) and 255).toByte()
        }
    }
    return result
}

@kotlin.ExperimentalUnsignedTypes
fun main() {
    val pictureData = File("src\\picture.bmp").readBytes()
    val pictureHeader = pictureData.asList().subList(0, 54).toByteArray()
    for (percentDamage in arrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 15)) {
        val encodingPictureData = Array((pictureData.size - 54) * 8 / LengthBlock) {(0 until (1 shl LengthBlock)).random()}
        for (i in 54 until pictureData.size step (LengthBlock / 8)) {
            var code = 0
            for (j in 0 until LengthBlock / 8) {
                code = (code shl 8) + pictureData[i + j].toUByte().toInt()
            }
            encodingPictureData[(i - 54) / (LengthBlock / 8)] = codeHamming(code)
        }
        val damageBits = randomIntArray(0, (pictureData.size - 54) * 8 - 1,
            (pictureData.size - 54) * 8 * percentDamage / 100)
        for (damageIndex in damageBits) {
            val i = (damageIndex shr 3) / (LengthBlock / 8)
            val j = damageIndex - i * 8 * (LengthBlock / 8)
            encodingPictureData[i] = encodingPictureData[i] xor (1 shl j)
        }
        val damagePictureFile = File("src\\pictureDamage$percentDamage%.bmp")
        damagePictureFile.writeBytes(pictureHeader + encodingPictureData.hammingDataBlocksToPictureData())
        val decodingPictureData = Array(encodingPictureData.size) {
            decodeTable[encodingPictureData[it]] ?: 0
        }
        val correctPictureFile = File("src\\pictureCorrect$percentDamage.bmp")
        correctPictureFile.writeBytes(pictureHeader + decodingPictureData.dataBlocksToPictureData())
    }
}
