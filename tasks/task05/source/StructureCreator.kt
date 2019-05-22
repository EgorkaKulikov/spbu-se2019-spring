fun createStructure(name: String): DirectoryInfo? {
    val structure = createStructureOfZipFile(name)

    structure?.updateSize()

    return structure
}
