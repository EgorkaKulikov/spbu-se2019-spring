import java.io.*
import java.util.zip.*
import kotlin.system.exitProcess

class PKZip(val archive: String){
    fun getZipInput() : ZipInputStream{
        try{
            return ZipInputStream(FileInputStream(archive))
        }
        catch(e: Exception){
            println("Couldn't open [$archive] as ZipInputStream")
            exitProcess(2)
        }
    }
}