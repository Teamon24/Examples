import org.home.classes_objects.delegation.Delegate

var Any.extensionProperty by Delegate("initial-extension-property")
var Int.extensionProperty by Delegate("initial-extension-property")
var Long.extensionProperty by Delegate("initial-extension-property")
var Double.extensionProperty by Delegate("initial-extension-property")
var String.extensionProperty by Delegate("initial-extension-property")

fun main() {
    val any: Any = 1
    val int = 5
    val long = 5L
    val double = 5.0
    val string = "5"

    any.extensionProperty = "any-extension-property"
    int.extensionProperty = "int-extension-property"
    long.extensionProperty = "long-extension-property"
    double.extensionProperty = "double-extension-property"
    string.extensionProperty = "string-extension-property"


    arrayOf(any, int, long, double, string).map { println(it.extensionProperty)}
}
