package model

import exceptions.DecryptionException
import java.lang.Exception
import java.lang.StringBuilder
import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@kotlin.ExperimentalUnsignedTypes
internal class LocalFileHeader(data: UByteArray) {
    val fileName: String
    val modificationDate: LocalDateTime
    val compressedSize: Int
    val uncompressedSize: Int
    val extraField: String
    val versionToExtract: Int
    val compressionMethod: UByte
    val fileData: String
    init {
        if (data.size < 26)
            throw DecryptionException("Incorrect length block \"Local File Header\"")
        versionToExtract = data[1].toInt().shl(8) + data[0].toInt()
        compressionMethod = data[4]
        if (data[5] != 0.toUByte() || compressionMethod > 8.toUByte())
            throw DecryptionException("Incorrect compression method")
        val year = data[9].toInt().shr(1) + 1980
        val month = data[9].and(0x01.toUByte()).toInt().shl(3) + data[8].toInt().shr(5)
        val day = data[8].and(0x1F.toUByte()).toInt()
        val hour = data[7].toInt().shr(3)
        val minute = data[6].toInt().shr(5) + data[7].and(0x07.toUByte()).toInt().shl(3)
        val second = data[6].and(0x1F.toUByte()).toInt().shl(1)
        try {
            modificationDate = LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.of(hour, minute, second))
        }
        catch (e: Exception) {
            throw if (e is DateTimeException) DecryptionException("Incorrect modification date") else e
        }
        compressedSize = data[17].toInt().shl(24) + data[16].toInt().shl(16) +
                data[15].toInt().shl(8) + data[14].toInt()
        uncompressedSize = data[21].toInt().shl(24) + data[20].toInt().shl(16) +
                data[19].toInt().shl(8) + data[18].toInt()
        val fileNameSize = data[23].toInt().shl(8) + data[22].toInt()
        val extraFieldSize = data[25].toInt().shl(8) + data[24].toInt()
        if (data.size != 26 + fileNameSize + extraFieldSize + compressedSize)
            throw DecryptionException("Incorrect length block \"Local File Header\"")
        val fileNameBuilder = StringBuilder()
        val extraFieldBuilder = StringBuilder()
        val fileDataBuilder = StringBuilder()
        for (i in 26 until 26 + fileNameSize)
            fileNameBuilder.append(data[i].toByte().toChar())
        for (i in 26 + fileNameSize until 26 + fileNameSize + extraFieldSize)
            extraFieldBuilder.append(data[i].toByte().toChar())
        for (i in 26 + fileNameSize + extraFieldSize until 26 + fileNameSize + extraFieldSize + compressedSize)
            fileDataBuilder.append(data[i].toByte().toChar())
        fileName = fileNameBuilder.toString()
        extraField = extraFieldBuilder.toString()
        fileData = fileDataBuilder.toString()
    }
}
