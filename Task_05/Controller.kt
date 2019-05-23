import com.sun.jdi.ArrayReference

class Controller(){
    fun makeRequest(commandsInput: Array<String>){
        when{
            ((commandsInput[0] =="?")||(commandsInput[0]=="help"))&&(commandsInput.size == 1) -> {
                print("Commands:\n open \n folderInfo \n fileInfo \n")

            }
            (commandsInput[1]=="open")&&(commandsInput.size == 2)-> {
                val folder = Model(commandsInput[0])
                val view = View()
                view.viewZip(folder.zipFile)

            }
            (commandsInput[1]=="folderInfo")&&(commandsInput.size == 3) -> {
                val folder = Model(commandsInput[0]).findInFile(commandsInput[2])
                val view = View()
                view.printFolderInfo(folder)

            }
            (commandsInput[1]=="fileInfo")&&(commandsInput.size == 3) ->{
                val folder = Model(commandsInput[0]).findInFile(commandsInput[2])
                val view = View()
                view.printFileInfo(folder)
            }
            else -> {
                println("Wrong input, enter '?' for help")
            }

        }

    }

}