package avl

enum class BalanceFactor {
    LEFT_HEAVY,
    BALANCED,
    RIGHT_HEAVY,
    ;

    val increased
        get() = when (this) {
            LEFT_HEAVY -> BALANCED
            BALANCED -> RIGHT_HEAVY
            else -> throw IllegalStateException("This is maximum value")
        }

    val decreased
        get() = when (this) {
            RIGHT_HEAVY -> BALANCED
            BALANCED -> LEFT_HEAVY
            else -> throw IllegalStateException("This is minimum value")
        }
}
