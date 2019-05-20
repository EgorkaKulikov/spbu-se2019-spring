import java.io.File
import kotlin.math.pow
import kotlin.random.Random
import kotlin.math.log2

class image (path: String, _errorPersent: Int) {
    private val file = File(path)
    private val errorPersent = _errorPersent
    private val inputBytes = file.readBytes()
    private val inputBits = Array<Boolean> (inputBytes.size*8) {false}
    private val hammingBits = Array<Boolean> (inputBytes.size*3) {false} // 6 bits on each 2 bytes
    private val headerSize = 54

    @kotlin.ExperimentalUnsignedTypes
    fun getBitRepresentation () {
        for (i in 0..(inputBytes.size-1)) {
            var tempByte = inputBytes[i].toUByte()
            var cycle =0
            while(tempByte > 0.toUByte()) {
                if (tempByte-(tempByte/2.toUByte())*2.toUByte()>0.toUByte())
                    inputBits[i*8+7-cycle] = true
                tempByte = (tempByte/2.toUByte()).toUByte()
                if (tempByte <= 0.toUByte())
                    cycle=0
                else
                    cycle++
            }
        }
    }

    @kotlin.ExperimentalUnsignedTypes
    fun hammingEncode () {
        getBitRepresentation()
        val tempArray =  Array<Boolean>(21) {false} // size of hamming word
        for (j in 0..(inputBytes.size/2 - 1)) {
            var controlBits = 0
            for (i in 0..tempArray.size-1) {
                if (i==0 || i==1 || i==3|| i==7|| i ==15) { // places of control bits
                    tempArray[i] = false
                    controlBits++
                }
                else {
                    tempArray[i] = inputBits[j*8*2+i-controlBits]
                }
            }

            for(i in 0..(controlBits-1)) {
                var sum = 0
                val place = 2.0.pow(i).toInt()
                var temp = place-1
                while (temp <= (tempArray.size-1)) {
                    for (l in temp..(temp+place-1)) {
                        if (l > (tempArray.size-1)) {
                            temp = tempArray.size
                            break
                        }
                        if (tempArray[l] == true)
                            sum++
                    }
                    temp = temp + 2*place
                }
                if (sum%2 == 0)
                    hammingBits[j*6+i] = false
                else
                    hammingBits[j*6+i] = true
            }

            var parityBit = 0
            for (i in 0..(tempArray.size-1)) {
               if (tempArray[i] == true)
                   parityBit++
            }
            if (parityBit%2 == 0)
                hammingBits[j*6+controlBits] = false
            else
                hammingBits[j*6+controlBits] = true
        }
    }

    private var newBytes = ByteArray(inputBytes.size)
    fun getByteRepresentation () {
        val newBytesInInt = Array<Int>(inputBytes.size) {0}
        for(i in 0..(inputBytes.size-1)) {
            var result = 0
            for (j in 0..7) { // size of byte
                if (inputBits[i*8+7-j] == true)
                    result += 2.0.pow(j).toInt()
            }
            newBytesInInt[i] = result
        }
        newBytes = ByteArray(inputBytes.size) {i -> newBytesInInt[i].toByte()}
    }

    fun addErrors () {
        val numberOfErrors = (inputBits.size*errorPersent)/100
        val indexesOfErrors = Array<Int> (numberOfErrors) {-1}
        for (i in 0..(numberOfErrors-1)) {
            while (true) {
                val index = Random.nextInt(headerSize * 8, (inputBits.size-1))
                var flag = 1
                for (j in 0..(numberOfErrors-1) ) {
                    if (index == indexesOfErrors[j]) {
                        flag = 0
                        break
                    }
                }
                if (flag == 1) {
                    indexesOfErrors[i] = index
                    break
                }
            }
        }

        for (i in 0..(indexesOfErrors.size-1))
            inputBits[indexesOfErrors[i]] = !inputBits[indexesOfErrors[i]]
        getByteRepresentation()
    }

    fun createFile (fileName: String) {
        val _file = File(fileName)
        _file.writeBytes(newBytes)
    }

    @kotlin.ExperimentalUnsignedTypes
    fun hammingDecode() {
        val previousHammingBits = Array<Boolean> (inputBytes.size*3) {false}
        for (i in 0..(inputBytes.size*3-1))
            previousHammingBits[i] = hammingBits[i]

        hammingEncode()

        for (i in 0..(inputBytes.size/2 - 1)) {
            if (previousHammingBits[i*6+5] == hammingBits[i*6+5]) { // parity bits are equal
                continue
            }
            var indexOfError = 0
            for (j in 0..4) {   // number of control bits
                if (previousHammingBits[i*6+j] != hammingBits[i*6+j])
                    indexOfError += 2.0.pow(j).toInt()
            }
            if (indexOfError>21 || indexOfError == 0 || indexOfError == 1 || indexOfError == 2
                || indexOfError == 4 || indexOfError== 8 || indexOfError ==16) // conrol bit places
                continue
            indexOfError = indexOfError - log2(indexOfError.toFloat()).toInt() - 2
            inputBits[i*8*2+indexOfError] = !inputBits[i*8*2+indexOfError]
        }
        getByteRepresentation()
    }
}