import java.util.zip.*

object ZipModel {
    var curZipInputs: ZipInputStream? = null
    var date = "01.01.1980"
    var time = "00:00:00"
    var size = 0L

    fun setZipInput(zipInput: ZipInputStream) {
        curZipInputs = zipInput
    }


    fun newDirectoryInfo(name: String){
        if (curZipInputs == null) return
        var entry: ZipEntry?
        while (true) {
            entry = curZipInputs?.nextEntry
            if (entry == null){
                Viev.printSize()
                return
            }
            if (entry.name.contains(name)) {
                size += entry.size
            }
        }
    }


    fun findFileInfo(name: String){
        if (curZipInputs == null) return
        var entry: ZipEntry?
        while (true) {
            entry = curZipInputs?.nextEntry
            if (entry == null){
                println("No file $name found")
                return
            }
            if (entry.name.contains(name)) {
                newFileInfo(entry)
                break
            }
            }

        }


    private fun newFileInfo(entry: ZipEntry){
        val resList = entry.lastModifiedTime.toString().split("T", "Z")
        date = resList[0]
        time = resList[1]
        Viev.printDate()
        Viev.printTime()
    }


    }
