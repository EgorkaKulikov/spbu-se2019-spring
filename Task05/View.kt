import java.util.*
import java.util.zip.ZipEntry

fun printArchive(file: Enumeration<out ZipEntry>){
    while(file.hasMoreElements())
    {
        val curElem = file.nextElement() as ZipEntry
        if(curElem.isDirectory){
            println(curElem.getName().substringBeforeLast('/'))
        }
        else{
            println("${curElem.getName()} ${curElem.getSize()} bytes")
        }
    }
}

fun printFolder(name: String, size: Long){
    if(size == -1L){
        println("No such Directory")
    }
    else{
        println("Folder $name weigh $size bytes")
    }
}

fun printFile(name: String, date: Long){
    if(date == -1L){
        println("No such File")
    }
    else{
        println("File $name was modify ${Date(date)}")
    }
}