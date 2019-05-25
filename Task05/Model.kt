import java.util.zip.*;

fun viewArchive(archive: ZipFile){
    val entries = archive.entries()

    printArchive(entries)
}

fun findFolder(archive: ZipFile, name: String){
    val entries = archive.entries()
    var size: Long = -1

    while(entries.hasMoreElements()){
        val curElem = entries.nextElement() as ZipEntry
        if(curElem.getName().substringBeforeLast('/').substringAfterLast('/') == name){
            size = curElem.getSize()
        }
    }
    printFolder(name, size)
}

fun findFile(archive: ZipFile, name: String){
    val entries = archive.entries()
    var date: Long = -1

    while(entries.hasMoreElements()){
        val curElem = entries.nextElement() as ZipEntry
        if(curElem.getName().substringAfterLast('/') == name){
            date = curElem.getTime()
        }
    }
    printFile(name, date)
}