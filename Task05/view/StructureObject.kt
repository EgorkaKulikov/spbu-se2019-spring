package view

internal class StructureObject(val name: String) {
    var listObjects: List<StructureObject>? = null
        set(value) { if (field == null) field = value }
}
