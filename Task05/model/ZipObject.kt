package model

@kotlin.ExperimentalUnsignedTypes
internal class ZipObject(data: UByteArray) {
    val centralDirectoryFileHeader = CentralDirectoryFileHeader(data)
    var localFileHeader: LocalFileHeader? = null
    var dataDescriptor: DataDescriptor? = null
}
