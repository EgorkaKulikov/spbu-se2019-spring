package presenter

import java.time.LocalDateTime

internal interface Object {
    val name: String
    val path: List<String>
    val modificationDate: LocalDateTime
    val compressedSize: Int
    val uncompressedSize: Int
}
