class Hamming {
    private val wordSize = 16
    private val encodedSize = 21
    private val additionalSize = 5
    private val bitMatrix: Array<UInt> by lazy { generateBitMatrix() }
    private val byteMasks: Array<UInt> by lazy { generateByteMasks() }

    private fun generateByteMasks(): Array<UInt> {
        val result = Array(3) { 0.toUInt() }
        result[0] = 255.toUInt()

        for (i in 1..2) {
            result[i] = result[i - 1] shl 8
        }

        return result
    }

    fun printMatrix() {
        for (i in bitMatrix) {
            printEncoded(i)
        }
    }

    fun printRaw(word: UInt) {
        for (i in wordSize downTo 1) {
            if (word and (1.toUInt() shl (i - 1)) != 0.toUInt()) {
                print(1)
            }
            else {
                print(0)
            }
        }

        println()
    }

    fun printEncoded(word: UInt) {
        for (i in encodedSize downTo 1) {
            if (word and (1.toUInt() shl (i - 1)) != 0.toUInt()) {
                print(1)
            }
            else {
                print(0)
            }
        }

        println()
    }

    private fun generateBitMatrix(): Array<UInt> {
        val result = Array(additionalSize) { 0.toUInt() }

        for (i in 0.until(additionalSize)) {
            for (j in 1..encodedSize) {
                if (j and (1 shl i) != 0) {
                    result[i] += (1.toUInt() shl (encodedSize - j))
                }
            }
        }

        return result
    }

    private fun bitParity(word: UInt): UInt {
        var result = 0.toUInt()

        for (i in 0.until(encodedSize)) {
            if (word and (1.toUInt() shl i) != 0.toUInt()) {
                result = result xor 1.toUInt()
            }
        }

        return result
    }

    fun encodeWord(word: UInt): UInt {
        var encodedWord = 0.toUInt()
        var shift = 2

        for (i in 1..wordSize) {
            if ((i + shift) and (1 shl shift).inv() == 0) {
                shift++
            }

            encodedWord += (word and (1.toUInt() shl (wordSize - i))) shl (additionalSize - shift)
        }

        for (i in 0.until(additionalSize)) {
            encodedWord += bitParity(bitMatrix[i] and encodedWord) shl (encodedSize - (1 shl i))
        }

        return encodedWord
    }

    fun decodeWord(_word: UInt): UInt {
        var errorPosition = 0
        var word = _word

        for (i in 0.until(additionalSize)) {
            errorPosition += (bitParity(bitMatrix[i] and word) shl i).toInt()
        }

        if (errorPosition != 0) {
            word = word xor (1.toUInt() shl (encodedSize - errorPosition))
        }

        var result = 0.toUInt()
        var shift = 2

        for (i in 1..encodedSize) {
            if (i and (1 shl shift).inv() == 0) {
                shift++
            } else {
                result += (word and (1.toUInt() shl (encodedSize - i))) shr (additionalSize - shift)

            }
        }

        return result
    }

    fun encode(data: ByteArray): ByteArray {
        val encoded = ByteArray((data.size / 2 + (data.size and 1)) * 3)
        var j = 0

        for (i in 0.until(data.size) step 2) {
            val word: UInt = ((data[i].toUInt() shl 8) and byteMasks[1]) +
                    if (i + 1 < data.size) data[i + 1].toUInt() and byteMasks[0] else 0.toUInt()
            val encodedWord = encodeWord(word)
            encoded[j] = ((encodedWord and byteMasks[2]) shr 16).toByte()
            encoded[j + 1] = ((encodedWord and byteMasks[1]) shr 8).toByte()
            encoded[j + 2] = (encodedWord and byteMasks[0]).toByte()
            j += 3
        }

        return encoded
    }

    fun decode(data: ByteArray): ByteArray {
        if (data.size % 3 != 0) {
            println("Error while decoding: Incorrect data size!")
            return ByteArray(0)
        }
        val decoded = ByteArray(data.size / 3 * 2)
        var j = 0

        for (i in 0.until(data.size) step 3) {
            var word: UInt = (data[i].toUInt() shl 16) and byteMasks[2]
            word += (data[i + 1].toUInt() shl 8) and byteMasks[1]
            word += data[i + 2].toUInt() and byteMasks[0]
            val decodedWord = decodeWord(word)
            decoded[j] = ((decodedWord and byteMasks[1]) shr 8).toByte()
            decoded[j + 1] = (decodedWord and byteMasks[0]).toByte()
            j += 2
        }

        return decoded
    }
}