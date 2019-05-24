fun main(){
    val dir = "src/original.bmp"

    val pictures = Array<Picture>(3){Picture(dir)}

    val myHemming = Hemming()
    myHemming.wordLenInBytes=2

    for (i in 0 until pictures.size){
        val picBitBody = pictures[i].bitBody
        val codedPicBody = myHemming.codePicture(picBitBody)
        val corruptedPicBody = myHemming.corrupt(5*(i+1), codedPicBody)
        val decodedCorruptedBody = myHemming.deCode(corruptedPicBody)
        pictures[i].bitBody = decodedCorruptedBody
        var name = ""
        name= when(i){
            0 -> "five"
            1 -> "ten"
            else -> "fifteen"
        }
        pictures[i].makePic(name +"PercentDamaged")
        val fixedBody = myHemming.fixErrors(corruptedPicBody)
        val fixedDecoded = myHemming.deCode(fixedBody)
        pictures[i].bitBody = fixedDecoded
        pictures[i].makePic(name+"PercentDamagedFixed")

    }

}