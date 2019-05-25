package model

import exceptions.DecryptionException
import java.lang.StringBuilder

@kotlin.ExperimentalUnsignedTypes
internal class EndOfCentralDirectoryRecord(data: UByteArray) {
    val centralDirectoryRecordNumber: Int
    val centralDirectorySize: Int
    val centralDirectoryOffset: Int
    val comment: String
    init {
        if (data.size < 18)
            throw DecryptionException("Incorrect length block \"End Of Central Directory Record\"")
        centralDirectoryRecordNumber = data[5].toInt().shl(8) + data[4].toInt()
        if (data[7].toInt().shl(8) + data[6].toInt() != centralDirectoryRecordNumber)
            throw DecryptionException("This program doesn't support multi-disk zip")
        centralDirectorySize = data[11].toInt().shl(24) + data[10].toInt().shl(16) +
                data[9].toInt().shl(8) + data[8].toInt()
        centralDirectoryOffset = data[15].toInt().shl(24) + data[14].toInt().shl(16) +
                data[13].toInt().shl(8) + data[12].toInt()
        val commentSize = data[17].toInt().shl(8) + data[16].toInt()
        if (data.size != 18 + commentSize)
            throw DecryptionException("Incorrect length block \"End Of Central Directory Record\"")
        val commentBuilder = StringBuilder()
        for (i in 18 until 18 + commentSize)
            commentBuilder.append(data[i].toByte().toChar())
        comment = commentBuilder.toString()
    }
}
