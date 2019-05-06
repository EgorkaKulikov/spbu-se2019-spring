import java.io.File

import java.util.*

fun BitSet.count(type: Boolean): Int {

    var res = 0

    for (i in 0..this.length()) {
        if (this.get(i) == type) {
            ++res
        }
    }

    return res

}

fun BitSet.toByteArray(cnt: Int): ByteArray {

    var res = ByteArray(0)

    for (i in 0 until cnt) {

        var cur = 0

        for (j in 0..7) {
            if (this.get(8 * i + j)) {
                cur += (1 shl j)
            }
        }

        res += cur.toByte().toByteArray()

    }

    return res

}

fun Byte.toBitSet(): BitSet {

    var cur = this.toInt()

    val res = BitSet()

    for (i in 0..7) {
        if (cur % 2 == 1) {
            res.set(i, true)
        } else {
            res.set(i, false)
        }
        cur = cur ushr 1
    }

    return res

}

fun Byte.toByteArray(): ByteArray {

    val res = ByteArray(1)

    res[0] = this

    return res

}

fun ByteArray.toBitSet(): BitSet {

    val res = BitSet()

    for (i in this.indices) {
        val cur = this[i].toBitSet()
        for (j in 0..7) {
            if (cur.get(j)) {
                res.set(8 * i + j, true)
            } else {
                res.set(8 * i + j, false)
            }
        }
    }

    return res

}

fun addSalt(data: ByteArray, percent: Int): ByteArray {

    val res = ByteArray(data.size) { i -> data[i] }

    for (i in 1..(res.size * percent / 100)) {
        val pos = (res.indices).random()
        val curBitSet = res[pos].toBitSet()
        val bit = (0..7).random()
        curBitSet.flip(bit)
        res[pos] = curBitSet.toByteArray(1)[0]
    }

    return res

}

fun main() {

    val fileName = "src/Picture.bmp"
    val file = File(fileName)
    val input: ByteArray = file.readBytes()
    val headerSize = 54

    var headerData = ByteArray(0)

    for (i in 0 until headerSize) {
        headerData += input[i]
    }

    val dataSize = input.size - headerSize
    val data = ByteArray(dataSize)

    for (i in headerSize until input.size)
        data[i - headerSize] = input[i]

    val hammingCode = HammingCode(data)
    hammingCode.encode()

    for (percent in 5..55 step 10) {

        val saltedData = addSalt(data, percent)

        val newFileNameSalted = "src/PictureSalted$percent%.bmp"
        val newFileSalted = File(newFileNameSalted)
        newFileSalted.createNewFile()
        var resultSalted = headerData
        resultSalted += saltedData
        newFileSalted.writeBytes(resultSalted)

        val decodedData = hammingCode.decode(saltedData)

        val newFileNameDecoded = "src/PictureDecoded$percent%.bmp"
        val newFileDecoded = File(newFileNameDecoded)
        newFileDecoded.createNewFile()
        var resultDecoded = headerData
        resultDecoded += decodedData
        newFileDecoded.writeBytes(resultDecoded)

    }

}