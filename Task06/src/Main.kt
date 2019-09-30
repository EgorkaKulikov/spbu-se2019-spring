import java.io.File
import java.io.FileNotFoundException
import kotlin.system.exitProcess

const val BMP_HEADER_SIZE = 112 //14 * 8 bytes
const val WORD_SIZE = 21

fun main()
{
    println("Enter path to .bmp image:")
    val pathToPicture = readLine()
    var data = ByteArray(0)

    try
    {
        data = File(pathToPicture).readBytes()
    }
    catch (e: FileNotFoundException)
    {
        println("File not found! ($pathToPicture)")
        exitProcess(1)
    }

    val size = data.size

    val header = ByteArray(BMP_HEADER_SIZE) { i -> data[i] }

    val imageSize = size - BMP_HEADER_SIZE
    val imageData = ByteArray(imageSize) { i -> data[i + BMP_HEADER_SIZE] }

    createImages(imageSize, imageData, header)
}

fun createImages(imageSize : Int, imageData : ByteArray, header: ByteArray)
{
    for (currentPercent in 5..15 step 5)
    {
        val corruptedData = ByteArray(imageSize)
        val restoredData = ByteArray(imageSize)

        for (i in 0 until imageSize step 2)
        {
            //Merging two bytes into single data segment
            val currentDataSegment =
                if (i + 1 < imageSize)
                    imageData[i].toInt().and(0xFF).shl(8) +
                            imageData[i + 1].toInt().and(0xFF)
                else
                    imageData[i].toInt().and(0xFF).shl(8)

            val corruptedEncodedSegment = corrupt(encode(currentDataSegment), currentPercent)

            //Corrupting data and splitting data segment
            corruptedData[i] = removeControlBits(corruptedEncodedSegment).and(0xFF00).shr(8).toByte()
            if (i + 1 < imageSize)
                corruptedData[i + 1] = removeControlBits(corruptedEncodedSegment).and(0xFF).toByte()

            //Restoration data and splitting data segment
            restoredData[i] = decode(corruptedEncodedSegment).and(0xFF00).shr(8).toByte()
            if (i + 1 < imageSize)
                restoredData[i + 1] = decode(corruptedEncodedSegment).and(0xFF).toByte()
        }

        createFile(header, corruptedData, "images/img_corrupted_$currentPercent.bmp")
        createFile(header, restoredData,  "images/img_restored_$currentPercent.bmp")
    }
    println("Images created in 'images' folder")
}

fun createFile(header: ByteArray, data: ByteArray, name: String)
{
    var fileData = ByteArray(0)
    fileData += header
    fileData += data

    val file = File(name)
    file.createNewFile()
    file.writeBytes(fileData)
}

private fun corrupt(dataBlock: Int, percent: Int): Int
{
    var result = dataBlock

    for (i in 1..WORD_SIZE)
        if ((1..100).random() <= percent)
            result = result.xor(1 shl i)

    return result
}

private fun addControlBits(data: Int): Int
{
    var result = 0
    var num = 15

    for (i in 1..WORD_SIZE)
    {
        if (i !in listOf(1, 2, 4, 8, 16))
        {
            if (data.shr(num) % 2 == 1)
                result += 1 shl (WORD_SIZE - i)

            num--
        }
    }

    return result
}

private fun removeControlBits(data : Int) : Int
{
    var result = 0
    var num = 15

    for (i in 1..WORD_SIZE)
    {
        if (i !in listOf(1, 2, 4, 8, 16))
        {
            if (data.shr(WORD_SIZE - i) % 2 == 1)
                result += 1 shl num

            num--
        }
    }

    return result
}

private fun decode(encodedData: Int): Int
{
    val cleanData = removeControlBits(encodedData)
    var newData = addControlBits(cleanData)

    var incorrectIndex = 0

    for (i in listOf(1, 2, 4, 8, 16))
        if (encodedData.and(1 shl (WORD_SIZE - i)) !=
            newData.and(1 shl (WORD_SIZE - i)))
            incorrectIndex += i


    newData = newData.xor(1 shl (WORD_SIZE - incorrectIndex))

    return removeControlBits(newData)
}

private fun encode(data: Int) = addControlBits(data)