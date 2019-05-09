import java.util.Observer
import java.util.Observable

class Viewer(model: Model): Observer {

    private val filesToDraw = mutableListOf< Pair<String, String> >()
    private val foldersToDraw = mutableListOf< Pair<String, Int> >()

    init {
        model.addObserver(this)
    }

    override fun update(model: Observable, data: Any?) {
        when (data) {
            is Archive -> drawTree(data)
        }
    }



    private fun drawTree(data: Archive) {

        println("   Structure:")
        for (item in data.files) {

            // Draw margin
            print("# ")
            for (index in 1..item.depth)
                print("│----- ")


            if (item.isFolder)
                println("[${item.name}]")
            else
                println(item.name)


            if (item.timestamp != null)
                filesToDraw.add( Pair(item.path, item.timestamp) )
            if (item.size != null)
                foldersToDraw.add( Pair(item.path, item.size) )
        }
        println()


        if (data.targetFileStated) {
            if (filesToDraw.isNotEmpty())
                writeFilesInfo()
            else
                println("   No such file in archive.")
        }

        if (data.targetFolderStated) {
            if (foldersToDraw.isNotEmpty())
                writeFoldersInfo()
            else
                println("   No such directory in archive.")
        }
    }



    private fun writeFoldersInfo() {
        println("   Found:")
        for (folder in foldersToDraw) {
            println("${folder.first.padEnd(80)} ${folder.second} bytes")
        }
    }



    private fun writeFilesInfo() {
        println("   Found:")
        for (file in filesToDraw) {
            println("${file.first.padEnd(80)} ${file.second}")
        }
    }

}