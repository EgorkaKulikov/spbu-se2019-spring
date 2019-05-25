package model

import exceptions.DecryptionException

@kotlin.ExperimentalUnsignedTypes
internal class DataDescriptor(data: UByteArray) {
    val compressedSize: Int
    val uncompressedSize: Int
    init {
        if (data.size < 12)
            throw DecryptionException("Incorrect length block \"language.Data Descriptor\"")
        compressedSize = data[7].toInt().shl(24) + data[6].toInt().shl(16) +
                data[5].toInt().shl(8) + data[4].toInt()
        uncompressedSize = data[11].toInt().shl(24) + data[10].toInt().shl(16) +
                data[9].toInt().shl(8) + data[8].toInt()
    }
}
