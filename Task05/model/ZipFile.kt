package model

import exceptions.DecryptionException

@kotlin.ExperimentalUnsignedTypes
internal class ZipFile(val name: String, val data: UByteArray) {
    private fun MutableList<UByte>.toUByteArray() = UByteArray(size) { this[it] }
    var EOCD: EndOfCentralDirectoryRecord? = null
        set(value) { if (field == null) field = value }
    val listZipObjects = mutableListOf<ZipObject>()
    init {
        val blockData = mutableListOf<UByte>()
        var stopIndex = 0
        for (i in data.size - 1 downTo 0) {
            if (data[i - 3] == 80.toUByte() && data[i - 2] == 75.toUByte() && data[i - 1] == 5.toUByte() &&
                data[i] == 6.toUByte()) {
                blockData.reverse()
                EOCD = EndOfCentralDirectoryRecord(blockData.toUByteArray())
                blockData.clear()
                stopIndex = i - 3
                break
            }
            else
                blockData.add(data[i])
        }
        if (EOCD == null)
            throw DecryptionException("Record EOCD didn't found")
        val startIndex = EOCD!!.centralDirectoryOffset
        if (data[startIndex] != 80.toUByte() || data[startIndex + 1] != 75.toUByte() ||
            data[startIndex + 2] != 1.toUByte() || data[startIndex + 3] != 2.toUByte())
            throw DecryptionException("Incorrect record EOCD")
        if (stopIndex - startIndex != EOCD!!.centralDirectorySize)
            throw DecryptionException("Incorrect record EOCD")
        // blockData == empty list
        var index = startIndex + 4
        while (index < stopIndex) {
            if (data[index] == 80.toUByte() && data[index + 1] == 75.toUByte() && data[index + 2] == 1.toUByte() &&
                data[index + 3] == 2.toUByte()) {
                listZipObjects.add(ZipObject(blockData.toUByteArray()))
                blockData.clear()
                index += 4
            }
            else {
                blockData.add(data[index])
                index++
            }
        }
        listZipObjects.add(ZipObject(blockData.toUByteArray()))
        if (listZipObjects.size != EOCD!!.centralDirectoryRecordNumber)
            throw DecryptionException("Incorrect record EOCD")
    }
    fun readZipObjectLocalFileHeader(zipObjectName: String): ZipObject {
        val zipObject = listZipObjects.find { it.centralDirectoryFileHeader.fileName == zipObjectName }
        zipObject ?: throw Exception("Incorrect object's name in function \"readLocalFileHeader\"")
        var index = zipObject.centralDirectoryFileHeader.localFileHeaderOffset
        if (data[index] != 80.toUByte() || data[index + 1] != 75.toUByte() ||
            data[index + 2] != 3.toUByte() || data[index + 3] != 4.toUByte())
            throw DecryptionException("Incorrect record \"Central Directory File Header\"")
        index += 4
        val blockData = mutableListOf<UByte>()
        while (index < data.size && (data[index] != 80.toUByte() || data[index + 1] != 75.toUByte() ||
                    data[index + 3].toUInt() != data[index + 2] + 1.toUInt() || (data[index + 2] != 1.toUByte() &&
                    data[index + 2] != 3.toUByte() && data[index + 2] != 7.toUByte()))) {
            blockData.add(data[index])
            index++
        }
        if (index == data.size)
            throw DecryptionException("Incorrect record \"Local File Header\"")
        zipObject.localFileHeader = LocalFileHeader(blockData.toUByteArray())
        zipObject.dataDescriptor = if (data[index + 2] == 7.toUByte()) {
            index += 4
            blockData.clear()
            if (index + 16 >= data.size)
                throw DecryptionException("Incorrect record \"Data Descriptor\"")
            if (data[index + 12] != 80.toUByte() || data[index + 13] != 75.toUByte() ||
                data[index + 15].toUInt() != data[index + 14] + 1.toUInt() || (data[index + 14] != 1.toUByte() &&
                        data[index + 14] != 3.toUByte()))
                throw DecryptionException("Incorrect record \"Data Descriptor\"")
            for (i in 0 until 12)
                blockData.add(data[index + i])
            DataDescriptor(blockData.toUByteArray())
        }
        else
            null
        return zipObject
    }
}
