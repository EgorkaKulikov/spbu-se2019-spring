class view (){
    fun printStructure(tree: folder, _tabs: Int) {
        var tabs = _tabs
        for (i in 0..(tabs-1))
            print("  ")
        println(tree.name)
        tabs += 1
        for (file in tree.internalFiles) {
            for (i in 0..(tabs-1))
                print("  ")

            var indexOfFile = file.name.lastIndexOf('/')
            indexOfFile++
            val fileName = file.name.substring(indexOfFile)
            println(fileName)
        }
        for (folder in tree.internalFolders) {
            printStructure(folder, tabs)
        }
    }

    fun printFileDate(date: String?) {
        if (date == null)
            print("this file does not exist")
        else {
            print(date.replace('T', ' ')
                .replace('Z', ' '))
        }
    }

    fun printFolderSize(size: Long?) {
        if (size == null)
            print("this folder does not exist")
        else
            println("$size bytes")
    }

    fun printMenu() {
        println("Error!")
        println("first argument - zip file")
        println("second argument - getZipStructure for getting structure or")
        println("second and third arguments are getFileDate and file name or")
        println("second and third arguments are getFolderSize and folder name")
    }
}