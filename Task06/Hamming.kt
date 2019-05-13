import java.lang.Exception
import kotlin.math.log2
import kotlin.math.floor

const val HAMMING_WORD_SIZE = 21

fun getPowOfTwo(number: Int): Int {
    if (number <= 0) {
        throw Exception("Non-positive argument to get power of 2")
    }

    return floor(log2(number.toDouble())).toInt()
}

fun isPowerOfTwo(number : Int): Boolean = (number and (number - 1) == 0)

@kotlin.ExperimentalUnsignedTypes
fun convertToHammingWord(word: UInt): UInt{
    //adding Control bits
    var newWord = ((word shr 5) shl 6) + (word % (1U shl 5))
    newWord = ((newWord shr 13) shl 14) + (newWord % (1U shl 13))
    newWord = ((newWord shr 17) shl 18) + (newWord % (1U shl 17))

    //Updating Control bits
    for (bitIndex in HAMMING_WORD_SIZE downTo 1){
        var index = bitIndex
        val currBitIndexValue =  (newWord shr (HAMMING_WORD_SIZE - index)) % 2U

        while(index != 0 && !isPowerOfTwo(bitIndex)){
            val currControlBitIndex = 1 shl getPowOfTwo(index)
            val numbOfBitsBeforeControl = HAMMING_WORD_SIZE - currControlBitIndex
            val lastBits = newWord % (1U shl numbOfBitsBeforeControl)

            newWord = newWord shr numbOfBitsBeforeControl
            val changedControlBit = (newWord % 2U) xor currBitIndexValue
            newWord = (((newWord shr 1) shl 1) + changedControlBit)
            newWord = (newWord shl numbOfBitsBeforeControl) + lastBits

            index -= currControlBitIndex
        }
    }

    return newWord
}

@kotlin.ExperimentalUnsignedTypes
fun convertToWord(hammingWord: UInt): UInt{
    var newWord = 0U

    for (i in 1..HAMMING_WORD_SIZE){
        if (!isPowerOfTwo(i)){
            newWord = (newWord shl 1) + (hammingWord shr (HAMMING_WORD_SIZE - i)).and(1U)
        }
    }

    return newWord
}

@kotlin.ExperimentalUnsignedTypes
fun getCtrlBits(hammingWord: UInt): UInt {
    var currCtrBitIndex = 16
    var ctrlBits = 0U

    while(currCtrBitIndex != 0){
        val currCtrBitValue = (hammingWord shr (HAMMING_WORD_SIZE - currCtrBitIndex)) % 2U
        ctrlBits = (ctrlBits shl 1) + currCtrBitValue
        currCtrBitIndex = currCtrBitIndex shr 1
    }

    return ctrlBits
}

@kotlin.ExperimentalUnsignedTypes
fun code(firstByte: Byte, secondByte: Byte): UInt {
    //creating word
    val word = (firstByte.toUInt() shl 8) + secondByte.toUInt()

    return convertToHammingWord(word)
}

@kotlin.ExperimentalUnsignedTypes
fun invertBit(word: UInt, position: Int, sizeWord: Int): UInt {
    val numberLastBits = sizeWord - position
    val lastBits = word % (1U shl numberLastBits)
    var newWord =  word shr numberLastBits
    var invertedBit = newWord % 2U

    invertedBit = if (invertedBit == 0U) 1U else 0U
    newWord = (newWord shr 1) shl 1

    return ((newWord + invertedBit) shl numberLastBits) + lastBits
}

@kotlin.ExperimentalUnsignedTypes
fun decode(codedHammingWord: UInt): UInt {
    val word = convertToWord(codedHammingWord)
    val newCodedHammingWord = convertToHammingWord(word)

    val trueCtrlBits = getCtrlBits(newCodedHammingWord)
    val otherCtrlBits = getCtrlBits(codedHammingWord)
    var fixedWord = codedHammingWord

    val wrongPosition = trueCtrlBits xor otherCtrlBits
    if (wrongPosition != 0U) {
        fixedWord = invertBit(codedHammingWord, wrongPosition.toInt(), HAMMING_WORD_SIZE)
    }

    return convertToWord(fixedWord)
}

@kotlin.ExperimentalUnsignedTypes
fun corruptWord(word: UInt, percent: Int): UInt {
    var corruptedWord = word

    for (i in 1..21){
        if ((1..100).random() <= percent){
            corruptedWord = invertBit(corruptedWord, i, HAMMING_WORD_SIZE)
        }
    }

    return corruptedWord
}