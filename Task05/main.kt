import view.View

@kotlin.ExperimentalUnsignedTypes
fun main(args: Array<String>) {
    val argsViewProcess = if (args.isEmpty())
        arrayOf("-createNewWindow")
    else
        arrayOf("-openNewWindow -openArchive") + args
    View.execute(argsViewProcess.toList(), null)
}
