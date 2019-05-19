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
        printSubTree(tree.root,0)
    }

    fun printCreationDateTime (ready : Boolean, date : FileTime) {
        if (ready) {
            println("Creation date is $date")
        } else {
            println("No such file")
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