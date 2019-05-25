fun pathTo(string: String): List<String> {
    val path = mutableListOf<String>()

    for (entry in string.split('/', '\\')) {
        if (entry != "") {
            path.add(entry)
        }
    }

    return path
}
