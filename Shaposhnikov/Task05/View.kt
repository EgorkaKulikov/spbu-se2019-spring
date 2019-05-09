import java.util.zip.*

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
    var entry : ZipEntry
    val input = zip.getZipInput()
    var offset : String
    while(true){
        offset = ""
        entry = input.nextEntry ?: break
        if (!entry.isDirectory)
            println(entry.name.substringAfterLast('/'))
        else {
            println("${(entry.name.dropLast(1)).substringAfterLast('/')}:")
            printDirectory(input, entry.name, offset)
        }
    }
}

private fun printDirectory(input : ZipInputStream, directory : String, offset : String){
    val newOffset = "    $offset\\"
    var nextEntry = input.nextEntry ?: return
    while(nextEntry.name.contains(directory)){
        if (!nextEntry.isDirectory)
            println("$newOffset${nextEntry.name.substringAfterLast('/')}")
        else {
            println("$newOffset${(nextEntry.name.dropLast(1)).substringAfterLast('/')}:")
            printDirectory(input, nextEntry.name, newOffset)
        }
        nextEntry = input.nextEntry ?: return
    }
}