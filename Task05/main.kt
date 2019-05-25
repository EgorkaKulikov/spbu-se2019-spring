import kotlin.system.exitProcess

fun forcedExit(message: String, status: Int = 1) {
    println(message)
    exitProcess(status)
}

fun main(args: Array<String>) {
    ConsoleView(args)
}