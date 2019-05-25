import java.util.zip.*

class View() {



    fun viewZip(data_:ZipFile) {
        val data = data_
        val zipFiles = data.entries().toList().sortedBy { it.name }
        for (i in zipFiles) {
            var path = i.name.split('/')
            for (i in 0 until path.size) {
                if (path[i] == "")
                    path = path.dropLast(1)
            }
            if (path.size > 1) {

                for (i in 1 until path.size - 1)
                    print("   ")
                print("|--->")


            }
            println(path[path.size - 1])


        }
    }
    fun printFolderInfo(folders: MutableList<ZipEntry>) {
        if (folders.isEmpty()) {
            println("can't find folder!")
            return
        }


        for (folder in folders) {
            val folderSize = folder.size
            println(folder.name + " found")
            println("size is: $folderSize bytes")
            println()
        }
    }

    fun printFileInfo(filesWithSameName: MutableList<ZipEntry>) {
        if (filesWithSameName.isEmpty()) {
            println("can't find file!")
            return
        }
        for (file in filesWithSameName) {


            println(file.name + " found")
            val creationTime :String = try {
                "creation time is: "+file.creationTime.toString()
            }
            catch (e :java.lang.NullPointerException){
               "can't read creation time"

            }
            println("$creationTime")
            println()
        }
    }

}




