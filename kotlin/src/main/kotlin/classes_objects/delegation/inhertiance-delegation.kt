package classes_objects.delegation

interface Base {
    val message: String
    fun print(): String
    fun println(): String
}

class BaseImpl() : Base {
    override val message = "BaseImpl message"
    override fun print(): String = "print of BaseImpl"
    // only this method will delegate to Derived
    override fun println(): String = "println of BaseImpl\n"
}

class Derived(b: Base) : Base by b {
    // this method won't delegate
    override val message = "Derived message"
    override fun print(): String = "print of Derived"
    // method "override fun println()" will be delegated from b
}

fun main() {
    val b = BaseImpl()
    val derived = Derived(b)
    sout(derived, Derived::print)
    sout(derived, Derived::println)
    sout(derived, {derived.message})
    sout(b, {b.message})
}

private fun <T: Any> sout(t: T, method: (T) -> Any) {
    println("${t::class.simpleName} invoke: ${method(t)}")
}



