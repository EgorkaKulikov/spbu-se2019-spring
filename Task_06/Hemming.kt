import java.util.*
import kotlin.math.pow

class Hemming(){

    fun fixErrors(damaged : Array<BitSet>):Array<BitSet>{
        val fixed = Array<BitSet>(damaged.size){ BitSet(21) }
        for(i in 0 until damaged.size){
            fixed[i]=fixBitSetErr(damaged[i])
        }
        return fixed

    }

    private fun deCodeBitSet(coded:BitSet):BitSet{
        val deCoded = BitSet(16)
        var k = 0
        for(i in 0 until 21) {
            if ((i + 1) == 2f.pow(k).toInt())
                k++
            else
                deCoded[i-k]=coded[i]
        }
        return deCoded

    }

    fun deCode(coded: Array<BitSet>):Array<BitSet>{
        val decoded = Array<BitSet>(coded.size*2){ BitSet(16) }
        for(i in 0 until coded.size){
            val deCodedBitSet = deCodeBitSet(coded[i])
            for (j in 0 until 8)
                decoded[2*i].set(j,deCodedBitSet[j])
            for (j in 0 until 8)
                decoded[2*i+1].set(j,deCodedBitSet[j+8])
        }
        return decoded
    }

    private fun fixBitSetErr(fixingBitSet: BitSet):BitSet{
        val asistBitSet: BitSet = BitSet(21)
        for(i in 0 until 21)
            asistBitSet[i]=fixingBitSet[i]

        for (i in 0..4){
            var value = false
            for (k in 2f.pow(i).toInt()-1 until 21 step 2f.pow(i+1).toInt()) {
                for (j in 0 until 2f.pow(i).toInt()) {
                    if ((k == 2f.pow(i).toInt() - 1) && (j == 0))
                        continue
                    value = value.xor(fixingBitSet.get(k + j))
                }
            }
            asistBitSet[2f.pow(i).toInt()-1] = value
        }


        var damagedBit =0
        for(i in 0 until fixingBitSet.length())
            if(asistBitSet[i].xor(fixingBitSet[i]))
                damagedBit+=(i+1)
        if(damagedBit != 0)
            fixingBitSet.flip(damagedBit-1)
        return fixingBitSet

    }
    fun corrupt(percentage: Int, corruptingBitSet: Array<BitSet>):Array<BitSet>{
        for(i in 0..corruptingBitSet.size/100*percentage)
        {
            val bitSetId = (0 until corruptingBitSet.size).random()
            val bitId = (0..21).random()
            corruptingBitSet[bitSetId].flip(bitId)

        }
        return corruptingBitSet
    }

    fun codePicture(bits: Array<BitSet>):Array<BitSet>{
        val size :Int = bits.size/2
        val codedPicture = Array<BitSet>(size){ BitSet(21) }
        for(i in 0 until size)
            codedPicture[i]=code(bits[i*2],bits[i*2+1])
        return codedPicture
    }

    private fun code(bitSet1: BitSet,bitSet2: BitSet):BitSet{
        val wordBitSet = BitSet(16)
        for (i in 0..7)
            wordBitSet.set(i,bitSet1[i])
        for (i in 8..15)
            wordBitSet.set(i,bitSet2[i-8])
        val coded = BitSet(21)
        var k=0
        for(i in 0 until 21){
            if(i+1 == 2f.pow(k).toInt()){
                k++
            }
            else
                coded[i]=wordBitSet[i-k]
        }

        for (i in 0..4){
            var value = false
            for ( p in 2f.pow(i).toInt()-1 until 21 step 2f.pow(i+1).toInt()) {
                for (j in 0 until 2f.pow(i).toInt())
                    value = value.xor(coded.get(p + j))
            }
            coded[2f.pow(i).toInt()-1] = value
        }
        return coded
    }
}