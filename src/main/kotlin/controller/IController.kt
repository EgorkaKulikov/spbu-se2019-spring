interface IController {
    val model: Model
    val view: Viewer

    fun parseArguments() {}
    fun validateArguments() {}
    fun runModel() {}
}