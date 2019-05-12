const val pathJava = "..."
const val pathIntelliJ = "..."
const val pathProduction = "..."
const val pathKotlinLib = "..."
//вместо насоящих путей до java.exe и т. д. пока поставлены многоточия, чтобы проект
//заработал, необходимо пока прописывать пути вручную

fun main(args: Array<String>) {
    val argsViewProcess = if (args.isEmpty())
        arrayOf()
    else
        arrayOf("-openArchive") + args
    ProcessBuilder(
        "cmd", "/c",
        "start $pathJava \"-javaagent:$pathIntelliJ\\lib\\idea_rt.jar=61388:" +
                "$pathIntelliJ\\bin\" -Dfile.encoding=UTF-8 " +
                "-classpath $pathProduction;" +
                "$pathKotlinLib\\kotlin-stdlib.jar;" +
                "$pathKotlinLib\\kotlin-reflect.jar;" +
                "$pathKotlinLib\\kotlin-test.jar;" +
                "$pathKotlinLib\\kotlin-stdlib-jdk7.jar;" +
                "$pathKotlinLib\\kotlin-stdlib-jdk8.jar view.ViewProcessKt " +
                argsViewProcess.joinToString(separator = "\" \"", prefix = "\"", postfix = "\"")
    ).start()
}
