import java.util.zip.*


object Viev {
    fun printTime() {
        val defaultTime = "00:00:00"
        if (ZipModel.time != defaultTime) {
            println(ZipModel.time)
        }
    }


    fun printDate() {
        val defaultDate = "01.01.1980"
        if (ZipModel.date != defaultDate) {
            println(ZipModel.date)
        }

    }


    fun printSize() {
        println(ZipModel.size)
    }


    fun printArchive(path: String) {
        val listOfEntry = ZipFile(path).entries().toList()
        val sortedList = listOfEntry.sortedBy { it.name }
        //sorted by name, default sort is time
        var i = 0
        while (i != sortedList.size) {
            if (!sortedList[i].isDirectory) {
                println(sortedList[i].name.substringAfterLast('/'))
            }
            else {
                println("${(sortedList[i].name.dropLast(1)).substringAfterLast('/')}:")
                i = printDirectory(sortedList, i + 1, sortedList[i].name) - 1
                if (i == -1){
                    return
                }
            }

            i++
        }

    }

    private fun printDirectory(listOfEntry: List<ZipEntry>, curIndex: Int, directoryName: String): Int {
        val newOffset =  " ".repeat(directoryName.length)
        val newDirectory = directoryName
        var i = curIndex
        while (listOfEntry[i].name.contains(newDirectory)) {
            if(i == listOfEntry.size - 1){
                return -1
            }
            if (!listOfEntry[i].isDirectory)
                println("$newOffset${listOfEntry[i].name.substringAfterLast('/')}")
            else {
                println("$newOffset${(listOfEntry[i].name.dropLast(1)).substringAfterLast('/')}:")
                i = printDirectory(listOfEntry, i + 1, listOfEntry[i].name) - 1
            }
            i++
        }
    return i
    }
}
