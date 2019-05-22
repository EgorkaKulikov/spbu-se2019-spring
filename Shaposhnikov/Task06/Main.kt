import java.io.File
import kotlin.random.Random

const val HEADER_LENGTH = 84
//54 is gonna break images for some reason. I suppose the Header should be bigger
//84 came from manual calculations
const val WORD_LENGTH = 16
const val CONTROL_BITS = 5
const val CODED_WORD_LENGTH = WORD_LENGTH + CONTROL_BITS

fun main(){
    val path = "src/Task06"
    val image = File("$path/orig.bmp").readBytes()
    val size = image.size
    val header = ByteArray(HEADER_LENGTH) {index -> image[index]}
    val data = ByteArray(size - HEADER_LENGTH) {index -> image[index + HEADER_LENGTH]}

    for (percent in listOf(5, 10, 15)){
        val restoredData = ByteArray(size)
        val corruptedData = ByteArray(size)

        for (i in 0 until data.size step 2){
            val codedWord = if (i < size - 1)
                encodeWord(data[i].toInt().and(0xFF).shl(8) + data[i + 1].toInt().and(0xFF))
            else
                encodeWord(data[i].toInt().and(0xFF).shl(8))

            val corruptedWord = corruptWord(codedWord, percent)
            corruptedData[i] = (removeControlBits(corruptedWord).and(0xFF00) shr 8).toByte()
            restoredData[i] = (removeControlBits(decodeWord(corruptedWord)).and(0xFF00) shr 8).toByte()

            if(i < data.size - 1){
                    corruptedData[i+1] =
                        (removeControlBits(corruptedWord) and 0xFF).toByte()
                    restoredData[i+1] =
                        (removeControlBits(decodeWord(corruptedWord)) and 0xFF).toByte()
                }
        }

        File("$path/corrupted_$percent.bmp").writeBytes(header + corruptedData)
        File("$path/restored_$percent.bmp").writeBytes(header + restoredData)
    }
}


fun encodeWord(word : Int) : Int{
    var encodedWord = word
    //inserting control bits
    for (i in 0 until CONTROL_BITS){
        encodedWord = insertBit(encodedWord, (1 shl i) - 1)
    }

    //counting control bits
    for (controlBit in 0 until CONTROL_BITS){
        var controlBitSum = 0
        for (index in 0 until CODED_WORD_LENGTH){
            val currentBit = (encodedWord shr index) and 1
            if ((index+1) and (1 shl controlBit) != 0)
                controlBitSum = (controlBitSum + currentBit) % 2
        }
        encodedWord = encodedWord or
                (1 shl ((1 shl controlBit) - 1)) * controlBitSum
    }
    return encodedWord
}

fun decodeWord(word : Int) : Int {
    //it's designed to fix 1 incorrect bit. Other ways it's gonna corrupt even more i suppose

    var wrongIndex = 0
    val newWord = encodeWord(removeControlBits(word))

    for (i in 0 until CONTROL_BITS){
        val curIndex = (1 shl i) - 1
        if ((newWord shr (curIndex - 1)) and 1 != (word shr (curIndex - 1)) and 1)
            wrongIndex += curIndex
    }
    return invertBit(word, wrongIndex)
}

fun removeControlBits(word : Int) : Int{
    var result = word
    for (i in (CONTROL_BITS - 1) downTo 0){
        result = removeBit(result, 1 shl i)
    }
    return result
}

fun corruptWord(word : Int, percent : Int) : Int{
    var corruptedWord = word
    for (i in 1..CODED_WORD_LENGTH){
            if ((Random.nextInt(100)) <= percent){
                corruptedWord = invertBit(corruptedWord, i)
            }
        }
    return corruptedWord
}

fun invertBit(word : Int, pos : Int) : Int = word xor (1 shl (pos - 1))

fun insertBit(word : Int, index : Int) : Int{
    val left = (word shr index) shl (index + 1)
    val right = word and ((1 shl index) - 1)
    return left + right
}

fun removeBit(word : Int, index : Int) : Int{
    val left = (word shr index) shl (index - 1)
    val right = word and ((1 shl (index - 1)) - 1)
    return left + right
}
