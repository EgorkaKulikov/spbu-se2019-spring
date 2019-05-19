 fun main(args: Array<String>) {
        val controller = Controller(args[0])
        controller.handleCommand(args.drop(1))
    }