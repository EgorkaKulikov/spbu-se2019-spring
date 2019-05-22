import java.io.File

const val BMP_HEADER = 14*8

fun connect2Bytes(little : UByte, big : UByte) : UInt {
    return ((big.toUInt() shl 8) + little.toUInt())
}

fun disconnect2Bytes( word: UInt) : MutableList<UByte> {
    val bytes : MutableList<UByte> = mutableListOf()
    var bufWord = word
    for (i in 0..1){
        bytes.add ((bufWord % 256.toUInt()).toUByte())
        bufWord /= 256.toUInt()
    }
    return bytes
}

fun connect3Bytes(little : UByte, middle : UByte, big : UByte) : UInt {
    return ((big.toUInt() shl 16) + (middle.toUInt() shl 8)+ little.toUInt())
}

fun disconnect3Bytes( word: UInt) : MutableList<UByte> {
    val bytes : MutableList<UByte> = mutableListOf()
    var bufWord = word
    for (i in 0..2){
        bytes.add((bufWord % 256.toUInt()).toUByte())
        bufWord /=  256.toUInt()
    }
    return bytes
}
fun getBit(word: UInt, bit : Int) : UInt{
    return (word shr bit) and (1.toUInt())
}

fun getBitPower(word: UInt, bit : Int) : UInt{
    return word and (2.toUInt() shl (bit - 1))
}

fun getControlBit(word: UInt, bit: Int) : UInt {
    var sum : UInt= 0.toUInt()
    var i = bit
    while (i <= 21) {
        sum += getBit(word,i)
        if ((i+1) % bit == 0){
            i += bit
        }
        i += 1
    }
    return  sum% 2.toUInt()
}

fun getControlBitPower(word: UInt, bit: Int) : UInt {
    return  getControlBit(word, bit) * (2.toUInt() shl (bit - 1))
}
fun encodeWord( word: UInt) : UInt {
    var newWord :UInt = 0.toUInt()
    //add 1st bit
    newWord += getBitPower(word, 1)* 4.toUInt()
    //add 2-4 bits
    for (i in 2..4) {
        newWord += getBitPower(word, i) * 8.toUInt()
    }
    //add 5-11 bits
    for (i in 5..11) {
        newWord += getBitPower(word, i) * 16.toUInt()
    }
    //add 12-16 bits
    for (i in 12..16) {
        newWord += getBitPower(word, i) * 32.toUInt()
    }
    //add control bits
    var bit = 1
    while (bit < 32) {
        newWord += getControlBitPower(newWord, bit)
        bit *= 2
    }
    return newWord
}

fun fixWord(word: UInt) : UInt {
    var fixedWord = word

    var bit : UInt = 1.toUInt()
    var diffBit : UInt = 0.toUInt()
    while (bit < 32.toUInt()) {
        diffBit += (bit * getControlBit(word, bit.toInt()))
        bit *= 2.toUInt()
    }
    if (diffBit > 0.toUInt() && diffBit <= 21.toUInt()) {
        val bitPower : UInt = getBitPower(word, diffBit.toInt())
        if (bitPower > 0.toUInt()) {
            fixedWord -= bitPower
        } else {
            fixedWord += (2.toUInt() shl (diffBit.toInt() - 1))
        }
    }
    return fixedWord
}
fun decodeWord( word: UInt) : UInt {
    var newWord = 0.toUInt()
    newWord += getBitPower(word, 3)/ 4.toUInt()
    //add 2-4 bits
    for (i in 5..7) {
        newWord += getBitPower(word, i) / 8.toUInt()
    }
    //add 9-15 bits
    for (i in 9..15) {
        newWord += getBitPower(word, i) / 16.toUInt()
    }
    for (i in 17..21) {
        newWord += getBitPower(word, i) / 32.toUInt()
    }
    return newWord
}

fun encode(data: UByteArray) : UByteArray{
    val newData : MutableList<UByte> = mutableListOf()
    var i = 0
    while (i < data.size-1) {
        val encodedWord = encodeWord(connect2Bytes(data[i],data[i+1]))
        val newUBytes = disconnect3Bytes(encodedWord)
        newData.addAll(newUBytes)
        i+=2
    }
    if (i == data.size - 1) {
        val encodedWord = encodeWord(connect2Bytes(data[i], 0.toUByte()))
        val newUBytes = disconnect3Bytes(encodedWord)
        newData.addAll(newUBytes)
    }
    val arrayData = UByteArray(newData.size)
    for (i in 0..newData.size-1){
        arrayData[i] = newData[i]
    }
    return arrayData
}

fun decodeNoFix(data: UByteArray) : UByteArray{
    val newData : MutableList<UByte> = mutableListOf()
    var i = 0
    while (i < data.size - 2) {
        val decodedWord = decodeWord(connect3Bytes(data[i],data[i+1],data[i+2]))
        val newUBytes = disconnect2Bytes(decodedWord)
        newData.addAll(newUBytes)
        i+=3
    }
    if (i == data.size - 2) {
        val decodedWord = decodeWord(connect3Bytes(data[data.size -2 ],data[data.size - 1], 0.toUByte()))
        val newUBytes = disconnect2Bytes(decodedWord)
        newData.addAll(newUBytes)
    }
    if (i == data.size - 1) {
        val decodedWord = decodeWord(connect3Bytes(data[data.size -2 ],0.toUByte(), 0.toUByte()))
        val newUBytes = disconnect2Bytes(decodedWord)
        newData.addAll(newUBytes)
    }
    val arrayData = UByteArray(newData.size)
    for (i in 0..newData.size-1){
        arrayData[i] = newData[i]
    }
    return arrayData
}

fun decodeFix(data: UByteArray) : UByteArray{
    val newData : MutableList<UByte> = mutableListOf()
    var i = 0
    while (i < data.size - 2) {
        val decodedWord = decodeWord(fixWord(connect3Bytes(data[i],data[i+1],data[i+2])))
        val newUBytes = disconnect2Bytes(decodedWord)
        newData.addAll(newUBytes)
        i+=3
    }
    if (i == data.size - 2) {
        val decodedWord = decodeWord(fixWord(connect3Bytes(data[data.size -2 ],data[data.size - 1], 0.toUByte())))
        val newUBytes = disconnect2Bytes(decodedWord)
        newData.addAll(newUBytes)
    }
    if (i == data.size - 1) {
        val decodedWord = decodeWord(fixWord(connect3Bytes(data[data.size -2 ],0.toUByte(), 0.toUByte())))
        val newUBytes = disconnect2Bytes(decodedWord)
        newData.addAll(newUBytes)
    }
    val arrayData = UByteArray(newData.size)
    for (i in 0..newData.size-1){
        arrayData[i] = newData[i]
    }
    return arrayData
}

fun shakal(data: UByteArray, numberOfBits: Int){
    for (i in 1..numberOfBits) {
        val randomByte = (BMP_HEADER+1..data.size-1).random()
        val randomBit = (0..7).random()
        data[randomByte] = data[randomByte] xor (1 shl randomBit).toUByte()
    }
}

fun main(){
    val picture = File("C:/Users/NIKITA/Desktop/volk.bmp")
    val picture5percentFix = File("C:/Users/NIKITA/Desktop/volk5Fix.bmp")
    val picture5percentDmg = File("C:/Users/NIKITA/Desktop/volk5Dmg.bmp")
    val picture10percentFix = File("C:/Users/NIKITA/Desktop/volk10Fix.bmp")
    val picture10percentDmg = File("C:/Users/NIKITA/Desktop/volk10Dmg.bmp")
    val picture15percentFix = File("C:/Users/NIKITA/Desktop/volk15Fix.bmp")
    val picture15percentDmg = File("C:/Users/NIKITA/Desktop/volk15Dmg.bmp")
    val data = picture.readBytes().toUByteArray()
    val encodedData = encode(data)

    var copyEncoded = encodedData.copyOf()
    shakal(copyEncoded, (copyEncoded.size*0.05*8).toInt())
    picture5percentDmg.writeBytes(decodeNoFix(copyEncoded).toByteArray())
    picture5percentFix.writeBytes(decodeFix(copyEncoded).toByteArray())

    copyEncoded = encodedData.copyOf()
    shakal(copyEncoded, (copyEncoded.size*0.1*8).toInt())
    picture10percentDmg.writeBytes(decodeNoFix(copyEncoded).toByteArray())
    picture10percentFix.writeBytes(decodeFix(copyEncoded).toByteArray())

    copyEncoded = encodedData.copyOf()
    shakal(copyEncoded, (copyEncoded.size*0.15*8).toInt())
    picture15percentDmg.writeBytes(decodeNoFix(copyEncoded).toByteArray())
    picture15percentFix.writeBytes(decodeFix(copyEncoded).toByteArray())
}