package view

import java.util.concurrent.TimeUnit

fun String.smartSplit(delimiter: Char): List<String> {
    val ans = mutableListOf<String>()
    val stringBuilder = StringBuilder()
    var doubleQuotesWereBefore = false
    for (char in "$this ") {
        if (char == delimiter && ! doubleQuotesWereBefore) {
            if (stringBuilder.toString() != "") {
                ans.add(stringBuilder.toString())
                stringBuilder.clear()
            }
        }
        else {
            if (char == '"')
                doubleQuotesWereBefore = ! doubleQuotesWereBefore
            else
                stringBuilder.append(char)
        }
    }
    return ans.toList()
}

@kotlin.ExperimentalUnsignedTypes
fun main(args: Array<String>) {
    var viewCondition = View.execute(args.toList(), VCHelp())
    if (viewCondition is VCExit)
        return
    println(viewCondition)
    val cleanViewProcess = ProcessBuilder("cmd", "/c", "cls").inheritIO()
    while (true) {
        val userCommandString = readLine()!!
        cleanViewProcess.start().waitFor(100, TimeUnit.SECONDS)
        println("Подождите, идёт обработка вашего запроса ...")
        viewCondition = View.execute(userCommandString.smartSplit(' '), viewCondition)
        if (viewCondition is VCExit)
            return
        cleanViewProcess.start().waitFor(100, TimeUnit.SECONDS)
        println(viewCondition)
    }
}
