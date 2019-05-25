const val BUFFER_SIZE = DEFAULT_BUFFER_SIZE

class Signature {
    companion object {

        const val CentralDirectoryHeader = "504b0102"
        const val CentralDirectoryHeaderBytes = 4

        const val FilenameLengthBytes = 2
        const val FilenameLengthOffset = 28

        const val FilenameOffset = 46

        const val FileModificationTimeBytes = 2
        const val FileModificationTimeOffset = 12

        const val FileModificationDateBytes = 2
        const val FileModificationDateOffset = 14

        const val FileUncompressedSizeBytes = 4
        const val FileUncompressedSizeOffset = 24

    }
}