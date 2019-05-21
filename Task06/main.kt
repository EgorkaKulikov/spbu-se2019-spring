@kotlin.ExperimentalUnsignedTypes
fun main() {
    val img = image ("src/picture.bmp", 5)
    img.hammingEncode()
    img.addErrors()
    img.createFile("src/fileWith5%Errors.bmp")
    img.hammingDecode()
    img.createFile("src/corrected5%ErrorsFile.bmp")
}