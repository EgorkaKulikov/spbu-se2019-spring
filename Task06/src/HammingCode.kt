import java.util.*

class HammingCode(private val data: ByteArray = ByteArray(0) { it.toByte() }, val codeLen: Int = 2) {

    inner class WordHammingCode(private val word: ByteArray, private val wordLen: Int = codeLen) {

        private var controlBitsPlaces = Array(getControlBitsCount() + 1) { i -> (1 shl (i - 1)) }
        private var controlBits = BitSet()
        private var encodedWord = BitSet()
        private val encodedWordLen = wordLen * 8 + controlBitsPlaces.size
        private var controlSequences = Array(controlBitsPlaces.size) { i -> buildControlSequence(i) }

        private fun getControlBitsCount(): Int {

            var res = 0
            var cur = 1

            while (cur <= wordLen * 8) {
                ++res
                cur = cur shl 1
            }

            return res

        }

        private fun buildControlSequence(cnt: Int): BitSet {

            val res = BitSet()

            if (cnt == 0) return res

            for (i in (1 shl (cnt - 1)) until encodedWordLen step (1 shl cnt)) {
                for (j in 1..(1 shl (cnt - 1))) {
                    res.set(i + j - 1)
                }
            }

            return res

        }

        fun encodeWord() {

            val bitWord = word.toBitSet()

            var bitWordPointer = 0

            for (i in 1 until encodedWordLen) {
                if (i and (i - 1) != 0) {
                    if (bitWord.get(bitWordPointer)) {
                        encodedWord.set(i)
                    }
                    ++bitWordPointer
                }
            }

            for (i in 1..controlBitsPlaces.lastIndex) {

                val cur = BitSet()

                cur.or(controlSequences[i])

                cur.and(encodedWord)

                if (cur.count(true) % 2 == 1) {
                    encodedWord.set(controlBitsPlaces[i])
                    controlBits.set(i)
                }

            }

        }

        fun decodeWord(wordToDecode: ByteArray): ByteArray {

            val bitDecodedWord = BitSet()

            val bitWord = wordToDecode.toBitSet()

            var bitWordPointer = 0

            for (i in 1 until encodedWordLen) {
                if (i and (i - 1) != 0) {
                    if (bitWord.get(bitWordPointer)) {
                        bitDecodedWord.set(i)
                    }
                    ++bitWordPointer
                }
            }

            for (i in 1..controlBitsPlaces.lastIndex) {
                if (controlBits.get(i)) {
                    bitDecodedWord.set(controlBitsPlaces[i])
                }
            }

            var errorPosition = 0

            for (i in 1..controlBitsPlaces.lastIndex) {

                val cur = BitSet()

                cur.or(controlSequences[i])

                cur.and(bitDecodedWord)

                if (cur.count(true) % 2 == 1) {
                    errorPosition += (1 shl (i - 1))
                }

            }

            bitDecodedWord.flip(errorPosition)

            var checker = 0

            for (i in 1..controlBitsPlaces.lastIndex) {

                val cur = BitSet()

                cur.or(controlSequences[i])

                cur.and(bitDecodedWord)

                if (cur.count(true) % 2 == 1) {
                    checker += (1 shl (i - 1))
                }

            }

            if (checker != 0) {
                bitDecodedWord.flip(errorPosition)
            }

            var decodedWordPointer = 0

            val decodedWord = BitSet()

            for (i in 1 until encodedWordLen) {
                if (i and (i - 1) != 0) {
                    if (bitDecodedWord.get(i)) {
                        decodedWord.set(decodedWordPointer)
                    }
                    ++decodedWordPointer
                }
            }

            return decodedWord.toByteArray(wordLen)

        }

    }

    private var encodedWords = Array(0) { _ -> WordHammingCode(ByteArray(0) { it.toByte() }) }

    fun encode() {

        for (i in data.indices step codeLen) {

            var curByteArray = ByteArray(0)

            for (j in 1..codeLen) {
                curByteArray += data[i + j - 1].toByteArray()
            }

            val curWord = WordHammingCode(curByteArray)

            curWord.encodeWord()

            encodedWords += curWord

        }

    }

    fun decode(dataToDecode: ByteArray): ByteArray {

        var res = ByteArray(0)

        for ((cnt, i) in (dataToDecode.indices step codeLen).withIndex()) {

            var curByteArray = ByteArray(0)

            for (j in 1..codeLen) {
                curByteArray += dataToDecode[i + j - 1].toByteArray()
            }

            val decodedWord = encodedWords[cnt].decodeWord(curByteArray)

            res += decodedWord

        }

        return res

    }

}