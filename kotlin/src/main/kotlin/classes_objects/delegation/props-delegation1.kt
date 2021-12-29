package classes_objects.delegation

import kotlin.reflect.KProperty

class Example {
    //p is to be called as "delegating" property?
    var p: String by Delegate("initial-delegated-property-value")
}

class Delegate(var property: String) {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return this.property
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        this.property = value
    }
}

fun main() {
    val example = Example()
    println("Delegated property value")
    println("before setting: ${example.p}")
    example.p = "new-value"
    println("after setting: ${example.p}")
}