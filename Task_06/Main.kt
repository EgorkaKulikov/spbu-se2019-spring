fun main(){
    val dir = "/Users/ivanlyschev/IdeaProjects/Task06/test.bmp"

    val pictures = Array<Picture>(3){Picture(dir)}

    val myHemming = Hemming()

    for (i in 0 until 3){
        val picBitBody = pictures[i].getBitBody()
        val codedPicBody = myHemming.codePicture(picBitBody)
        val corruptedPicBody = myHemming.corrupt(5*(i+1), codedPicBody)
        val decodedCorruptedBody = myHemming.deCode(corruptedPicBody)
        pictures[i].setBitBody(decodedCorruptedBody)
        var name = ""
        name= when(i){
            0 -> "five"
            1 -> "ten"
            else -> "fifteen"
        }
        pictures[i].makePic(name +"PercentDamaged")
        val fixedBody = myHemming.fixErrors(corruptedPicBody)
        val fixedDecoded = myHemming.deCode(fixedBody)
        pictures[i].setBitBody(fixedDecoded)
        pictures[i].makePic(name+"PercentDamagedFixed")
    }

}