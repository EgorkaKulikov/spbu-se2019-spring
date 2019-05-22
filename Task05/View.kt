import java.nio.file.attribute.FileTime

class View {

    fun printTree ( tree : ZipTree) {
        fun printSubTree(node : ZipTree.Node, depth : Int) {
            for (i in 1..depth) {
                print(' ')
            }
            if (node.value.lastIndexOf("/") == -1) {
                println(node.value)
            } else {
                println(node.value.substring(node.value.lastIndexOf("/")+1))
            }
            for (i in node.children) {
                printSubTree(i,depth + 1)
            }
        }
        for (node in tree.root.children)
        {
            printSubTree(node,0)
        }
    }

    fun printCreationDateTime (ready : Int, date : FileTime) {
        when (ready) {
            0 -> println("No such file")
            1 -> println("Creation date and time is not stated")
            2 -> println("Creation date is $date")
        }
    }

    fun printSize (ready : Boolean, size: Long) {
        if (ready) {
            println("Size of folder is $size")
        } else {
            println("No such folder")
        }
    }

    fun printMessage(message : String) {
        println(message)
    }

}