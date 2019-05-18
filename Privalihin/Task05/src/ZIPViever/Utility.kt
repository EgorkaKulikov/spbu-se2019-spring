package ZIPViever

fun getIndentedName(fullName: String): String {
    val indent = java.lang.StringBuilder()
    var isMainFolder = true
    val result: StringBuilder = java.lang.StringBuilder()

    for (i in 0.until(fullName.length)) {
        if (fullName[i] != '/') {
            result.append(fullName[i])
        }
        else {
            if (i < fullName.length - 1) {
                if (!isMainFolder) {
                    for (i in 1.until(result.length)) {
                        indent.append('.')
                    }

                    indent.append('|')
                }
                else {
                    isMainFolder = false
                }

                result.clear()
            }
        }
    }

    return indent.append(result).toString()
}

fun getName(fullName: String): String {
    val result: StringBuilder = java.lang.StringBuilder()

    for (i in 0.until(fullName.length)) {
        if (fullName[i] != '/') {
            result.append(fullName[i])
        }
        else {
            if (i < fullName.length - 1) {
                result.clear()
            }
        }
    }

    return result.toString()
}