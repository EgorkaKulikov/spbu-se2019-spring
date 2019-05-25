package model

import exceptions.DecryptionException
import java.lang.Exception
import java.lang.StringBuilder
import java.time.DateTimeException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@kotlin.ExperimentalUnsignedTypes
internal class CentralDirectoryFileHeader(data: UByteArray) {
    val fileName: String
    val modificationDate: LocalDateTime
    val compressedSize: Int
    val uncompressedSize: Int
    val extraField: String
    val versionMadeBy: Int
    val versionToExtract: Int
    val compressionMethod: UByte
    val fileComment: String
    val localFileHeaderOffset: Int
    init {
        if (data.size < 42)
            throw DecryptionException("Incorrect length block \"Central Directory File Header\"")
        versionToExtract = data[3].toInt().shl(8) + data[2].toInt()
        versionMadeBy = data[1].toInt().shl(8) + data[0].toInt()
        compressionMethod = data[6]
        if (data[7] != 0.toUByte() || compressionMethod > 8.toUByte())
            throw DecryptionException("Incorrect compression method")
        val year = data[11].toInt().shr(1) + 1980
        val month = data[11].and(0x01.toUByte()).toInt().shl(3) + data[10].toInt().shr(5)
        val day = data[10].and(0x1F.toUByte()).toInt()
        val hour = data[9].toInt().shr(3)
        val minute = data[8].toInt().shr(5) + data[9].and(0x07.toUByte()).toInt().shl(3)
        val second = data[8].and(0x1F.toUByte()).toInt().shl(1)
        try {
            modificationDate = LocalDateTime.of(LocalDate.of(year, month, day), LocalTime.of(hour, minute, second))
        }
        catch (e: Exception) {
            throw if (e is DateTimeException) DecryptionException("Incorrect modification date") else e
        }
        compressedSize = data[19].toInt().shl(24) + data[18].toInt().shl(16) +
                data[17].toInt().shl(8) + data[16].toInt()
        uncompressedSize = data[23].toInt().shl(24) + data[22].toInt().shl(16) +
                data[21].toInt().shl(8) + data[20].toInt()
        val fileNameSize = data[25].toInt().shl(8) + data[24].toInt()
        val extraFieldSize = data[27].toInt().shl(8) + data[26].toInt()
        val fileCommentSize = data[29].toInt().shl(8) + data[28].toInt()
        if (data.size != 42 + fileNameSize + extraFieldSize + fileCommentSize)
            throw DecryptionException("Incorrect length block \"Central Directory File Header\"")
        localFileHeaderOffset = data[41].toInt().shl(24) + data[40].toInt().shl(16) +
                data[39].toInt().shl(8) + data[38].toInt()
        val fileNameBuilder = StringBuilder()
        val extraFieldBuilder = StringBuilder()
        val fileCommentBuilder = StringBuilder()
        for (i in 42 until 42 + fileNameSize)
            fileNameBuilder.append(data[i].toByte().toChar())
        for (i in 42 + fileNameSize until 42 + fileNameSize + extraFieldSize)
            extraFieldBuilder.append(data[i].toByte().toChar())
        for (i in 42 + fileNameSize + extraFieldSize until 42 + fileNameSize + extraFieldSize + fileCommentSize)
            fileCommentBuilder.append(data[i].toByte().toChar())
        fileName = fileNameBuilder.toString()
        extraField = extraFieldBuilder.toString()
        fileComment = fileCommentBuilder.toString()
    }
}
