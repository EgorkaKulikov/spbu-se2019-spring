class Graph(size: Int) {

    data class Path(val begin: Int, val end: Int, val length: Int = INVALID_LENGTH)

    companion object {
        const val INVALID_LENGTH = -1
        const val ZERO_LENGTH = 0
        val VALID_LENGTHS = 1..100

        fun createWithRandomPaths(size: Int, pathsCount: Int): Graph {
            val graph = Graph(size)
            graph.addRandomPaths(pathsCount)

            return graph
        }
    }

    private val table = Array(size) { Array(size) { INVALID_LENGTH } }

    private fun setTableValue(begin: Int, end: Int, length: Int) {
        table[begin][end] = length
        table[end][begin] = length
    }

    fun setPathLength(begin: Int, end: Int, length: Int) {
        if (begin == end || length !in VALID_LENGTHS)
            return

        setTableValue(begin, end, length)
    }

    fun getPathLength(begin: Int, end: Int) = table[begin][end]

    fun getGaps(): MutableList<Path> {
        val gaps = mutableListOf<Path>()

        for (begin in 0 until table.size)
            for (end in begin + 1 until table.size)
                if (table[begin][end] !in VALID_LENGTHS)
                    gaps.add(Path(begin, end))

        return gaps
    }

    fun getPaths(): MutableList<Path> {
        val paths = mutableListOf<Path>()

        for (begin in 0 until table.size)
            for (end in begin + 1 until table.size)
                if (table[begin][end] in VALID_LENGTHS)
                    paths.add(Path(begin, end, table[begin][end]))

        return paths
    }

    fun addRandomPaths(count: Int) {
        val gaps = getGaps()
        gaps.shuffle()

        val needsToCreate = if (count < gaps.size) count else gaps.size

        for (i in 0 until needsToCreate) {
            val path = gaps[i]

            setTableValue(path.begin, path.end, VALID_LENGTHS.random())
        }
    }

    fun printInfo() {
        val paths = getPaths()
        var maxLength = VALID_LENGTHS.first
        var minLength = VALID_LENGTHS.last

        for (path in paths) {
            if (path.length > maxLength)
                maxLength = path.length
            if (path.length < minLength)
                minLength = path.length
        }

        println("Graph has ${table.size} vertices and ${paths.size} paths")

        if (paths.isNotEmpty()) {
            println("Maximum length is $maxLength")
            println("Minimum length is $minLength")
        }
    }

    private fun generateMinimumLengthsArray(begin: Int): Array<Int> {
        if (begin !in 0 until table.size)
            return arrayOf()

        val minimumLengths = Array(table.size) { INVALID_LENGTH }
        minimumLengths[begin] = ZERO_LENGTH

        return minimumLengths
    }

    fun getMinimumLengthsWithDijkstra(begin: Int): Array<Int> {
        val lengths = generateMinimumLengthsArray(begin)
        val unfinished = Array(table.size) { it }

        var currentIndex = begin
        var currentLength = 0

        fun setCurrentIndex(index: Int) {
            currentIndex = index
            currentLength = lengths[unfinished[index]]
        }

        for (lastUnfinished in lengths.size - 1 downTo 1) {
            val currentVertex = unfinished[currentIndex]
            unfinished[currentIndex] = unfinished[lastUnfinished]

            for (i in 0 until lastUnfinished) {
                val vertex = unfinished[i]
                val length = getPathLength(currentVertex, vertex)

                if (length in VALID_LENGTHS) {
                    val altLength = currentLength + length

                    if (lengths[vertex] !in ZERO_LENGTH..altLength)
                        lengths[vertex] = altLength
                }
            }

            currentIndex = -1

            for (i in currentIndex + 1 until lastUnfinished) {
                val length = lengths[unfinished[i]]

                if (length in ZERO_LENGTH until currentLength ||
                    length >= ZERO_LENGTH && currentIndex == -1)
                    setCurrentIndex(i)
            }

            if (currentIndex == -1)
                break
        }

        return lengths
    }

    fun getMinimumLengthsWithBellmanFord(begin: Int): Array<Int> {
        val lengths = generateMinimumLengthsArray(begin)

        val paths = getPaths()

        for (pass in 0 until lengths.size) {
            for (path in paths) {
                val beginLength = lengths[path.begin]
                val endLength = lengths[path.end]
                val alternativeForBegin = endLength + path.length
                val alternativeForEnd = beginLength + path.length

                if (beginLength >= ZERO_LENGTH && endLength !in ZERO_LENGTH..alternativeForEnd)
                    lengths[path.end] = alternativeForEnd

                if (endLength >= ZERO_LENGTH && beginLength !in ZERO_LENGTH..alternativeForBegin)
                    lengths[path.begin] = alternativeForBegin
            }
        }

        return lengths
    }
}
