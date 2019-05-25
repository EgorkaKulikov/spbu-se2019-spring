package presenter

import java.time.LocalDateTime

internal class File(override val name: String,
                    override val path: List<String>,
                    override val modificationDate: LocalDateTime,
                    override val compressedSize: Int,
                    override val uncompressedSize: Int): Object {
    var data: ByteArray? = null
        set(value) { if (field == null) field = value }
}
