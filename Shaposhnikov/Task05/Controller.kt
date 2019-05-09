fun main(args : Array<String>){
    if (!((args[0] == "folder" && args.size == 3)
        || (args[0] == "file" && args.size == 3)
        || (args[0] == "print" && args.size == 2))) {
        println("Wrong input arguments")
        println("Type one of the following commands:")
        println("'folder [FolderName] [~/ZipArchiveName]'")
        println("'file [FileName] [~/ZipArchiveName]'")
        println("'print [~/ZipArchiveName]'")
        return
    }
    val archiveName = if (args[0] == "print") args[1] else args[2]
    val zip = PKZip(archiveName)
    when(args[0]){
        "print" -> printZip(zip)
        "file" -> findFile(zip, args[1])
        "folder" -> getFolder(zip, args[1])
    }
}