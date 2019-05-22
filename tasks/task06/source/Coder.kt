class Coder(private val lengthOfWord: Int) {
    private val countOfControlBits: Int

    init {
        var count = 0
        while (lengthOfWord shr count != 0)
            count++
        countOfControlBits = count
    }

    private val lengthOfEncodedWord = lengthOfWord + countOfControlBits

    private fun Int.asIndex() = (1 shl this) - 1

    private fun computeControlBits(word: IntArray): IntArray {
        return IntArray(countOfControlBits) {
            var sum = 0

            for (i in 0 until lengthOfEncodedWord) {
                sum += word[i] * (((i + 1) shr it) and 0x1)
            }

            sum % 2
        }
    }

    private fun IntArray.withoutControlBits(): IntArray {
        var numberOfControlBit = 2

        return IntArray(lengthOfWord) {
            if (it + numberOfControlBit == numberOfControlBit.asIndex()) {
                numberOfControlBit++
            }
            this[it + numberOfControlBit]
        }
    }

    fun encode(data: ByteArray) = convert(data, lengthOfWord, lengthOfEncodedWord) { word: IntArray ->
        var numberOfControlBit = 0

        val encodedWord = IntArray(lengthOfEncodedWord) {
            if (it == numberOfControlBit.asIndex()) {
                numberOfControlBit++; 0
            } else {
                word[it - numberOfControlBit]
            }
        }

        val controlBits = computeControlBits(encodedWord)

        for (i in 0 until countOfControlBits) {
            encodedWord[i.asIndex()] = controlBits[i]
        }

        encodedWord
    }

    fun decode(data: ByteArray) = convert(data, lengthOfEncodedWord, lengthOfWord) { word: IntArray ->
        val controlBits = computeControlBits(word)

        var errorIndex = -1

        for (i in 0 until countOfControlBits) {
            errorIndex += controlBits[i] shl i
        }

        if (errorIndex in 0 until lengthOfEncodedWord) {
            word[errorIndex] = 1 - word[errorIndex]
        }

        word.withoutControlBits()
    }

    fun removeControlBits(data: ByteArray) = convert(data, lengthOfEncodedWord, lengthOfWord) { word: IntArray ->
        word.withoutControlBits()
    }
}

fun convert(data: ByteArray, lengthOfWord: Int, lengthOfTranslatedWord: Int, converter: (IntArray) -> IntArray): ByteArray {
    val countOfBits = data.size * Byte.SIZE_BITS
    val countOfWords = countOfBits / lengthOfWord
    val countOfConvertedBits = countOfWords * lengthOfTranslatedWord
    val countOfConvertedBytes = countOfConvertedBits / Byte.SIZE_BITS +
        if (countOfConvertedBits % Byte.SIZE_BITS != 0) 1 else 0

    val convertedData = ByteArray(countOfConvertedBytes)

    var indexOfByte = -1
    var indexOfBit = Byte.SIZE_BITS
    var currentByte = 0
    var indexOfConvertedByte = 0
    var indexOfConvertedBit = 0
    var currentConvertedByte = 0

    for (i in 0 until countOfWords) {
        val word = IntArray(lengthOfWord) {
            if (++indexOfBit >= Byte.SIZE_BITS) {
                indexOfBit = 0
                indexOfByte++
                currentByte = data[indexOfByte].toInt()
            }

            (currentByte shr indexOfBit) and 0x1
        }

        for (bit in converter(word)) {
            currentConvertedByte = currentConvertedByte or (bit shl indexOfConvertedBit)

            if (++indexOfConvertedBit >= Byte.SIZE_BITS) {
                convertedData[indexOfConvertedByte++] = currentConvertedByte.toByte()
                indexOfConvertedBit = 0
                currentConvertedByte = 0
            }
        }
    }

    if (indexOfConvertedBit != 0) {
        convertedData[indexOfConvertedByte] = currentConvertedByte.toByte()
    }

    return convertedData
}
