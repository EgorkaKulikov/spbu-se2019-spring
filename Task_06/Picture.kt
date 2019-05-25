import java.io.File
import java.util.*

class Picture(dir_: String){
    private val headerSize : Int = 138
    private val dir = dir_
    private val file = File(dir)
    private val byteFile = file.readBytes()//ByteArray
    private val fileSize :Int = byteFile.size
    private val bodySize : Int = byteFile.size-headerSize
    private val bytePicHeader = ByteArray(headerSize){i -> byteFile[i]}
    private val bytePicBody = UByteArray(bodySize){i -> byteFile[i+headerSize].toUByte()}
    private val myHemming = Hemming()
    var bitBody = makeBitBody()
        get() = field
        set(value ){
            if(value != null){
                field = makeBitBody()
            }
        for(i in 0 until bitBody.size)
            bitBody[i]=value[i]
    }




    private fun toBit(byte_:UByte):Array<Boolean>{
        var byte = byte_
        val bits = Array<Boolean>(8) { false }
        var k = 0
        while (byte != 0.toUByte()) {
            bits[7-k] = (byte % 2.toUByte() != 0u)
            byte = (byte / 2.toUByte()).toUByte()
            k++
        }
        return bits
    }

    private fun toBitSet(byte:UByte):BitSet{
        val bits = toBit(byte)
        val bitSet = BitSet(8)
        for(i in 0..7)
        bitSet[i] = bits[i]
        return bitSet
    }




    private fun makeBitBody():Array<BitSet>{
        val bitBody = Array<BitSet>(bodySize){ BitSet(8) }
        for(i in 0 until bodySize){
            bitBody[i]= toBitSet(bytePicBody[i])

        }
        return bitBody
    }

    private fun bitSetToUByte(bitSet_: BitSet):UByte{
        var bitSet = bitSet_
        var byte = 0.toUByte()
        var k=1.toUByte()
        for (i in 7 downTo 0){
            if(bitSet[i])
                byte = (byte + k).toUByte()
            k= (k*2.toUByte()).toUByte()
        }
        return byte
    }

    fun makePic(name: String){
        val newBody = ByteArray(bodySize){i -> bitSetToUByte(bitBody[i]).toByte()}
        var result = ByteArray(0)
        result+=bytePicHeader
        result+=newBody
        val newPic = File("src/$name.bmp")
        newPic.createNewFile()
        newPic.writeBytes(result)
    }

}