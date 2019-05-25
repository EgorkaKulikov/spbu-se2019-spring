interface IView {
    fun showZipStructure(root: Directory)
    fun showDirectories(dirs: Array<Directory>)
    fun showFiles(files: Array<FileStructure>)
}

class ConsoleView(args: Array<String>): IView {
    private val presenter = Presenter(this)

    init {
        presenter.runCommand(args)
    }

    override fun showDirectories(dirs: Array<Directory>) {
        if (dirs.isEmpty()) {
            println("No such directories found")
            return
        }
        if (dirs.size == 1) {
            showDirectory(dirs[0])
            return
        }
        println("Found ${dirs.size} directories with given name:")
        for (curDir in dirs) {
            showDirectory(curDir)
            println()
        }
    }

    override fun showFiles(files: Array<FileStructure>) {
        if (files.isEmpty()) {
            println("No such files found")
            return
        }
        if (files.size == 1) {
            showFile(files[0])
            return
        }
        println("Found ${files.size} files with given name:")
        for (curFile in files) {
            showFile(curFile)
            println()
        }
    }

    private fun showFile(file: FileStructure) {
        println("${file.fullpath} info:")
        println("   last modified: ${file.timestamp}")
        println("   size: ${file.size} bytes")
    }

    private fun showDirectory(dir: Directory) {
        println("${dir.fullpath} info:")
        println("   size: ${dir.size} bytes")
    }

    override fun showZipStructure(root: Directory) {
        buildTree(root, 0)
    }

    private fun buildTree(dir: Directory, depth: Int) {
        for (i in 1..depth)
            print("     ")
        println(dir.name)
        for (nextDir in dir.folders) {
            buildTree(nextDir.value, depth + 1)
        }
        for (file in dir.files) {
            for (i in 0..depth)
                print("     ")
            println(file.value.name)
        }
    }
}