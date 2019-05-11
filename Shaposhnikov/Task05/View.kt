import java.util.zip.*

var IS_PRINTED : Boolean = true
var NEXT_ENTRY : ZipEntry? = null
//these two global vars are designed to not miss any entries
//when recursive [printFolder] closes

fun  findFile(zip : PKZip, fileName : String){
    val input = zip.getZipInput()
    var file: ZipEntry
    try {
        file = input.nextEntry
    }
    catch(e: Exception){
        println("[${zip.archive}] is empty.")
        return
    }
    while (file.name.substringAfterLast('/') != fileName){
        try{
            file = input.nextEntry
        }
        catch(e: Exception){
            println("There is no [$fileName] in [${zip.archive}]")
            return
        }
    }
    println("$fileName exists in [${file.name.substringBeforeLast('/')}]:")
    println("Size is ${file.size}")
    println("Date of modification in format {YYYY-MM-DD.T.HH:MM:SS}:")
    println("\t${file.timeLocal}")
}

fun getFolder(zip : PKZip, folderName : String){
    var size : Long = 0
    var entry : ZipEntry
    val input = zip.getZipInput()
    val directory = "/$folderName/"
    while(true){
        try{
            entry = input.nextEntry
        }
        catch(e: Exception){
            if(size <= 0) {
                println("There is no [$folderName] in [${zip.archive}]")
                return
            }
            break
        }
        if (entry.name.contains(directory))
            size += entry.size
        if (!entry.name.contains(directory) && size > 0)
            break
    }
    print("Size of [$folderName]'s folder content is : ")
    print(
        when {
            size > 1 shl 30 -> "${size/(1 shl 30)} gb and ${(size%(1 shl 30))/(1 shl 20)} mb."
            size > 1 shl 20 -> "${size/(1 shl 20)} mb and ${(size%(1 shl 20))/1024} kb."
            size > 1024 -> "${size/1024} kb."
            else -> "$size b."
        }
    )

}

fun printZip(zip : PKZip){
    val input = zip.getZipInput()
    var offset : String
    while(true){
        offset = ""
        if(IS_PRINTED) NEXT_ENTRY = input.nextEntry ?: break
        IS_PRINTED = false
        if (!(NEXT_ENTRY?.isDirectory ?: return)) {
            println(NEXT_ENTRY!!.name.substringAfterLast('/'))
            IS_PRINTED = true
        }
        else {
            println("${(NEXT_ENTRY!!.name.dropLast(1)).substringAfterLast('/')}:")
            printFolder(input, NEXT_ENTRY!!.name, offset)
        }
    }
}

private fun printFolder(input : ZipInputStream
                           , directory : String
                           , offset : String){
    val newOffset = "    $offset\\"
    NEXT_ENTRY = input.nextEntry ?: return
    while(NEXT_ENTRY?.name?.startsWith(directory) ?: return){
        if(!NEXT_ENTRY!!.isDirectory) {
            println("$newOffset${NEXT_ENTRY!!.name.substringAfterLast('/')}")
            IS_PRINTED = true
        }
        else {
            println("$newOffset${(NEXT_ENTRY!!.name.dropLast(1)).substringAfterLast('/')}:")
            IS_PRINTED = true
            printFolder(input, NEXT_ENTRY!!.name, newOffset)
        }
        if(IS_PRINTED)
            NEXT_ENTRY = input.nextEntry ?: return
        IS_PRINTED = false
    }
}