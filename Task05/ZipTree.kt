import java.util.zip.ZipFile

class ZipTree (zipFile: ZipFile){
    var root = Node()
    class Node {
        val children : MutableList<Node> = mutableListOf()
        var parent : Node? = null
        var value : String = ""
    }
    init {
        val names : MutableList<String> = mutableListOf()
        for (e in zipFile.entries()) {
            if (e.isDirectory){
                names.add(e.name.dropLast(1))
            } else {
                names.add(e.name)
            }
        }
        names.sort()

        root.value = "root"
        var currentNode = Node()
        currentNode.value = names[0]
        currentNode.parent = root
        root.children.add(currentNode)

        for (i in 1..names.size-1) {
            val newNode = Node()
            newNode.value = names[i]

            val pathDiff = names[i-1].split('/').size - names[i].split('/').size
            if (pathDiff >= 0) {
                for (j in 1..pathDiff) {
                    currentNode = (currentNode.parent)!!
                }
                newNode.parent = currentNode.parent
                currentNode.parent!!.children.add(newNode)
            } else {
                newNode.parent = currentNode
                currentNode.children.add(newNode)
            }
            currentNode = newNode
        }
    }
}