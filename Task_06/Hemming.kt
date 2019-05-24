import java.util.*
import kotlin.math.pow

class Hemming(){
    var wordLenInBytes = 1

    private val wordLen = wordLenInBytes*8
    private val additionalBits = calcAdditionalBits(wordLen)

    private fun calcAdditionalBits(wordLen : Int):Int{
        var countOfBits = 0
        while(2f.pow(countOfBits).toInt()<= wordLen){
            countOfBits++
        }


        return countOfBits
    }

    fun fixErrors(damaged : Array<BitSet>):Array<BitSet>{
        val fixed = Array<BitSet>(damaged.size){ BitSet(wordLen+additionalBits) }
        for(i in 0 until damaged.size){
            fixed[i]=fixBitSetErr(damaged[i])
        }
        return fixed

    }

    private fun deCodeBitSet(coded:BitSet):BitSet{
        val deCoded = BitSet(wordLen)
        var k = 0
        for(i in 0 until coded.size()) {
            if ((i + 1) == 2f.pow(k).toInt())
                k++
            else
                deCoded[i-k]=coded[i]
        }
        return deCoded

    }

    fun deCode(coded: Array<BitSet>):Array<BitSet>{
        val decoded = Array<BitSet>(coded.size*wordLen/8){ BitSet(wordLen) }
        for(i in 0 until coded.size){
            val deCodedBitSet = deCodeBitSet(coded[i])
            for(k in 0 until wordLen/8)
            for (j in 0 until 8)
                decoded[wordLen/8*i+k].set(j,deCodedBitSet[j+8*k])
        }
        return decoded
    }

    private fun fixBitSetErr(fixingBitSet: BitSet):BitSet{
        val assistBitSet: BitSet = BitSet(wordLen+additionalBits)
        for(i in 0 until wordLen+additionalBits)
            assistBitSet[i]=fixingBitSet[i]

        for (i in 0 until additionalBits){
            var value = false
            for (k in 2f.pow(i).toInt()-1 until wordLen+additionalBits step 2f.pow(i+1).toInt()) {
                for (j in 0 until 2f.pow(i).toInt()) {
                    if ((k == 2f.pow(i).toInt() - 1) && (j == 0))
                        continue
                    value = value.xor(fixingBitSet.get(k + j))
                }
            }
            assistBitSet[2f.pow(i).toInt()-1] = value
        }


        var damagedBit =0
        for(i in 0 until fixingBitSet.length())
            if(assistBitSet[i].xor(fixingBitSet[i]))
                damagedBit+=(i+1)
        if(damagedBit != 0)
            fixingBitSet.flip(damagedBit-1)
        return fixingBitSet

    }
    fun corrupt(percentage: Int, corruptingBitSet: Array<BitSet>):Array<BitSet>{
        for(i in 0..corruptingBitSet.size*wordLen/8/100*percentage)
        {
            val bitSetId = (0 until corruptingBitSet.size).random()
            val bitId = (0 until wordLen+additionalBits).random()
            corruptingBitSet[bitSetId].flip(bitId)

        }
        return corruptingBitSet
    }

    fun codePicture(bits: Array<BitSet>):Array<BitSet>{
        val size :Int = bits.size/wordLen*8
        val codedPicture = Array<BitSet>(size){ BitSet(wordLen+additionalBits) }
        for(i in 0 until size) {
            val bitsToCode = Array<BitSet>(wordLen/8){ BitSet(wordLen) }
            for (j in 0 until bitsToCode.size)
                for(k in 0 until 8) {
                    bitsToCode[j].set(k, bits[wordLen/8 * i + j][k])
                }

                //bitsToCode[j] = bits[2*i+j]
            codedPicture[i] = code(bitsToCode)
        }
        return codedPicture
    }

    private fun code(bitSets: Array<BitSet>):BitSet{
        val wordBitSet = BitSet(wordLen)
        for (i in 0 until bitSets.size){
            for(k in 0 until 8){
                wordBitSet.set(k+i*8,bitSets[i][k])
            }
        }

        val coded = BitSet(wordLen+additionalBits)
        var k=0
        for(i in 0 until coded.size()){
            if(i+1 == 2f.pow(k).toInt()){
                k++
            }
            else
                coded[i]=wordBitSet[i-k]
        }

        for (i in 0 until additionalBits){
            var value = false
            for ( p in 2f.pow(i).toInt()-1 until coded.length() step 2f.pow(i+1).toInt()) {
                for (j in 0 until 2f.pow(i).toInt())
                    value = value.xor(coded.get(p + j))
            }
            coded[2f.pow(i).toInt()-1] = value
        }
        return coded
    }
}