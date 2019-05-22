import java.io.File
import kotlin.random.Random

val HEADER_LENGTH = 54

fun degreeOfTwo(degree: Int): UInt {
    var result = 1U

    for (i in 0 until degree) {
        result = result * 2U
    }
    return result
}

fun encodeWord(word: UInt): UInt{
    var control = 0U
    var result = 0U
    var controlIterator = 0
    var infoIterator = 0
    var controlPosition = 1

    for(i in 0 until 21){
        if(i != controlPosition-1){
            if(word and degreeOfTwo(infoIterator) != 0U) {
                result = degreeOfTwo(20-i) xor result
            }
            infoIterator++
        }
        else{
            controlPosition *= 2
        }
    }

    controlPosition = 1

    for(i in 0 until 21){
        if((result and degreeOfTwo(20-i)) != 0U){
            control = control xor (i+1).toUInt()
        }
    }

    for(i in 0 until 21){
        if(i == controlPosition-1){
            if(control and degreeOfTwo(controlIterator) != 0U) {
                result = degreeOfTwo(20-i) xor result
            }
            controlPosition *= 2
            controlIterator++
        }
    }
    return result
}

fun encode(data: ByteArray): ByteArray{
    var j = 0
    val words = arrayOfNulls<Pair<Byte, Byte>>(data.size/2)
    val result = ByteArray(data.size/2*3)
    var word: UInt

    for(i in 0 until data.size/2){
            words[i] = Pair(data[2*i], data[2*i+1])
    }

    for(i in 0 until data.size/2){
        word = (words[i]!!.first.toUInt() shl 8) xor (words[i]!!.second.toUInt() and 255U)
        word = encodeWord(word)
        result[j] = (word shr 16).toByte()
        result[j+1] = ((word shr 8) and 255U).toByte()
        result[j+2] = (word and 255U).toByte()
        j += 3
    }
    return result
}

fun corrupt(data: ByteArray, percent: Int): ByteArray{
    var randomByte: Int
    var randomBit: Int

    for(i in 0 until (data.size * percent / 100)){
        randomByte = Random.Default.nextInt(data.size)
        randomBit = Random.Default.nextInt(8)
        data[randomByte] = (data[randomByte].toUByte() xor degreeOfTwo(randomBit).toUByte()).toByte()
    }
    return data
}

fun falseDecodeWord(word: UInt): UInt{
    var infoIterator = 0
    var controlPosition = 1
    var result = 0U

    for(i in 0 until 21){
        if(i != controlPosition-1){
            if(word and degreeOfTwo(20-i) != 0U) {
                result = degreeOfTwo(infoIterator) xor result
            }
            infoIterator++
        }
        else{
            controlPosition *= 2
        }
    }
    return result
}

fun trueDecodeWord(word: UInt): UInt{
    var control = 0U

    for(i in 0 until 21){
        if(word and degreeOfTwo(20-i) != 0U) {
            control = (i+1).toUInt() xor control
        }
    }

    if(control != 0U){
        return falseDecodeWord(word xor degreeOfTwo(21-control.toInt()))
    }
    else{
        return falseDecodeWord(word)
    }
}

fun decode(data: ByteArray, method: Boolean): ByteArray{
    val result = ByteArray(data.size/3*2)
    var temp: UInt
    var word: UInt

    for(i in 0 until data.size/3){
        temp = (data[3*i].toUInt() shl 16) xor ((data[3*i+1].toUInt() shl 8) and 65280U) xor (data[3*i+2].toUInt() and 255U)
        if(method) {
            word = trueDecodeWord(temp)
        }
        else{
            word = falseDecodeWord(temp)
        }
        result[i*2] = (word shr 8).toByte()
        result[i*2+1] = (word and 255U).toByte()
    }
    return result
}

fun main(){
    val path = readLine()
    val percent = readLine()!!.toInt()

    val picture = File(path)
    val bytePicture: ByteArray = picture.readBytes()
    val size = bytePicture.size

    val header = bytePicture.take(HEADER_LENGTH).toByteArray()
    var result: ByteArray

    var data = ByteArray(size - HEADER_LENGTH)
    data = bytePicture.takeLast(data.size).toByteArray()


    val newData = encode(data)
    val corruptedData = corrupt(newData, percent)

    val falseData = decode(corruptedData, false)
    val corruptedFileName = "${percent}_percent_corrupted_picture.bmp"
    val corruptedFile = File(corruptedFileName)
    result = header + falseData
    corruptedFile.writeBytes(result)

    data = decode(corruptedData, true)
    val fixedFileName = "${percent}_percent_fixed_picture.bmp"
    val fixedFile = File(fixedFileName)
    result = header + data
    fixedFile.writeBytes(result)
}