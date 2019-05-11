import kotlin.random.Random

const val WORDLEN = 16
const val ENCODEDWORDLEN = 21
const val MAXPROBABILITY = 100

@kotlin.ExperimentalUnsignedTypes
class HammingDataWord(var value: UInt, var length: Int) {
    fun decode(): UInt{
        val encodedWord = this.value
        val fixedWord = HammingDataWord(encodedWord, ENCODEDWORDLEN)
        //sum of bits controlled by current control bit
        var controlledBitsSum = 0

        for (i in 0 until this.length) {
            //true for control bit numbers
            if (((i + 1) and i) == 0) {
                fixedWord.value = encodedWord
                fixedWord.setBit(i, 0u)
                fixedWord.setControlBit(i)

                if (fixedWord.value != encodedWord) {
                    controlledBitsSum += i + 1
                }
            }
        }

        if (controlledBitsSum > 0) {
            if (fixedWord.getBitIsOne(controlledBitsSum - 1)){
                fixedWord.setBit(controlledBitsSum - 1, 0u)
            } else {
                fixedWord.setBit(controlledBitsSum - 1, 1u)
            }
        }

        return fixedWord.removeControlBits()
    }

    fun removeControlBits(): UInt {
        val result = HammingDataWord(0u, WORDLEN)
        var k = 0
        
        for (i in 0 until ENCODEDWORDLEN) {
            //false for control bits
            if (((i + 1) and i) != 0) {
                if (this.getBitIsOne(i)) {
                    result.setBit(k, 1u)
                }
                k++
            }
        }

        return result.value
    }

    fun encode(): HammingDataWord{
        val encodedWord = HammingDataWord(0u, ENCODEDWORDLEN)
        var j = 0

        for (i in 0 until encodedWord.length) {
            //false for control bits
            if (((i + 1) and i) != 0) {
                if (this.getBitIsOne(j)) {
                    encodedWord.setBit(i, 1u)
                }
                j++
            }
        }

        for (i in 0 until encodedWord.length) {
            //true for control bits
            if (((i + 1) and i) == 0) {
                encodedWord.setControlBit(i)
            }
        }
        return encodedWord
    }

    fun setControlBit(controlBitNumber: Int) {
        var controlBitCounter = 0
        val currentStep = controlBitNumber + 1

        for (i in controlBitNumber..ENCODEDWORDLEN step 2 * currentStep) {
            for (j in 0 until currentStep) {
                if (this.getBitIsOne(i + j)) {
                    controlBitCounter++
                }
            }
        }

        if (controlBitCounter % 2 == 1)
            this.setBit(controlBitNumber, 1u)
        else
            this.setBit(controlBitNumber, 0u)

    }

    //returns true if bit is 1
    fun getBitIsOne(index: Int): Boolean {
        return this.value and (1u shl (this.length - index - 1)) != 0u
    }

    //sets index bit to requested value
    fun setBit(index: Int, bit: UInt) {
        val shiftedBit = 1u shl (this.length - index - 1)
        when(bit) {
            1u -> this.value = this.value or shiftedBit
            else -> this.value = this.value and shiftedBit.inv()
        }
    }

    //corrupts bits with requested probability in percent
    fun corrupt(percent: Int) {
        val result = this
        for (i in 0 until this.length) {
            val probabilityCounter = Random.nextInt(MAXPROBABILITY)
            if (probabilityCounter < percent) {
                if (this.getBitIsOne(i)) {
                    this.setBit(i, 0u)
                } else {
                    this.setBit(i, 1u)
                }
            }
        }
    }
}