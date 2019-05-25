package presenter

import exceptions.DecryptionException

internal abstract class Container {
    var listObjects: List<Object> = listOf()
    var listObjectsWasUpdate: Boolean = false
    fun addInListObjects(element: Object) {
        listObjects = listObjects.plus(element)
        listObjectsWasUpdate = true
    }
    var compressedSizeAllObjects: Int = 0
        get() {
            if (listObjectsWasUpdate)
                updateSizesAllObjects()
            return field
        }
        private set
    var uncompressedSizeAllObjects: Int = 0
        get() {
            if (listObjectsWasUpdate)
                updateSizesAllObjects()
            return field
        }
        private set
    private fun updateSizesAllObjects() {
        listObjectsWasUpdate = false
        compressedSizeAllObjects = 0
        uncompressedSizeAllObjects = 0
        for (obj in listObjects) {
            when (obj) {
                is File -> {
                    compressedSizeAllObjects += obj.compressedSize
                    uncompressedSizeAllObjects += obj.uncompressedSize
                }
                is Folder -> {
                    compressedSizeAllObjects += obj.compressedSizeAllObjects
                    uncompressedSizeAllObjects += obj.uncompressedSizeAllObjects
                }
            }
        }
    }
    fun add(element: Object, path: List<String>) {
        if (path.isEmpty()) {
            if (listObjects.find { element.name == it.name } == null)
                addInListObjects(element)
            else
                throw DecryptionException("Files with equals names founded")
        }
        else {
            when (val nextPackage = listObjects.find { path[0] == it.name }) {
                null -> throw DecryptionException("Necessary package didn't found")
                is File -> throw DecryptionException("Process wait package, but found file")
                is Folder -> nextPackage.add(element, path.subList(1, path.size))
                else -> throw Exception("Was founded unexpected class")
            }
        }
    }
    fun find(elementName: String): List<Object> {
        val foundElement = listObjects.find { it.name == elementName }
        val answer = if (foundElement == null)
            mutableListOf()
        else
            mutableListOf(foundElement)
        for (obj in listObjects) {
            when (obj) {
                is File -> {}
                is Folder -> answer.addAll(obj.find(elementName))
                else -> throw Exception("Was founded unexpected class")
            }
        }
        return answer.toList()
    }
}
