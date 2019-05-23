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

            var indexOfFile = 0
            for (i in (file.name.length - 1) downTo 0) {
                if (file.name[i] == '/') {
                    indexOfFile = i + 1
                    break
                }
            }
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
        else
            print(date)
    }

    fun printFolderSize(size: Long?) {
        if (size == null)
            print("this folder does not exist")
        else
            print(size)
    }

}