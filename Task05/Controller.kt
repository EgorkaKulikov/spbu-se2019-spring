import java.util.zip.*;

fun main(args: Array<String>){
    lateinit var zip: ZipFile
    val path: String

    if(args[0].takeLast(4) != ".zip"){
        println("Please, input .zip file")
    }
    else {
        path = args[0]
        try {
            zip = ZipFile(path)
        }
        catch (e: NoSuchFileException) {
            println("No file found, try again")
        }

        when {
            args.size >= 2 && args[1] == "view" -> viewArchive(zip)
            args.size >= 3 && args[1] == "folder" -> findFolder(zip, args[2])
            args.size >= 3 && args[1] == "file" -> findFile(zip, args[2])
            else -> println("""Please, input correct command in one of the following format:
                        archive_path view
                        archive_path folder folder_name
                        archive_path file file_name""")
        }
    }
}