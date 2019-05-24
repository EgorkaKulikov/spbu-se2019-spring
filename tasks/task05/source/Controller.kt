fun apply(arguments: Array<String>, view: View) {

    fun makeCommandError(): String {
        var message = "Please use one of the following commands:"

        for (command in Command.all) {
            message += "\n    ${command.format}"
        }

        return message
    }

    if (arguments.isEmpty()) {
        view.showError(makeCommandError())
        return
    }

    fun chooseCommand(): Command? {
        for (command in Command.all) {
            if (arguments[0] == command.name) {
                return command
            }
        }

        return null
    }

    val command = chooseCommand()

    if (command == null) {
        view.showError(makeCommandError())
        return
    }

    if (arguments.size != command.countOfArguments) {
        view.showError("Please use the following format:" + "\n    ${command.format}")
        return
    }

    val structure = createStructure(arguments[1])

    if (structure == null) {
        view.showError("File ${arguments[1]} has unknown format or does not exists")
        return
    }

    command.doSomething(arguments, structure, view)
}
