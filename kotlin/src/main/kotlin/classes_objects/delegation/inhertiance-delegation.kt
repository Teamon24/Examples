package classes_objects.delegation

interface Base {
    val message: String
    fun method1(): String
    fun method2(): String
}

class BaseImpl : Base {
    override val message = "message of BaseImpl"
    override fun method1(): String = "method1 of BaseImpl"
    override fun method2(): String = "method2 of BaseImpl"
}

class Derived(b: Base) : Base by b {
    override val message = "message of Derived"
    override fun method1(): String = "method1 of Derived"
    // method "override fun println()" will be delegated from b
}

class DerivedImpl(b: BaseImpl) : Base by b {
    override val message = "message of DerivedImpl"
    override fun method1(): String = "method1 of DerivedImpl"
    // method "override fun println()" will be delegated from b
}

fun main() {
    val b = BaseImpl()
    val derived = Derived(b)
    val derivedImpl = DerivedImpl(b)
    val derived2 = Derived(derivedImpl)

    val delegatingAndDelegated = hashMapOf(
        b to b,
        derived to b,
        derivedImpl to b,
        derived2 to derivedImpl
    )

    delegatingAndDelegated.forEach { (delegating, delegated) ->
        sout(delegating, delegated) { delegating.method1() }
        sout(delegating, delegated) { delegating.method2() }
        sout(delegating, delegated) { it.message }
        println()
    }
}

private fun <T: Base> sout(t: T, delegating: T, method: (T) -> Any) {
    if (t == delegating) {
        println("${simpleName(t)} invoke: ${method(t)}")
    } else {
        println("${simpleName(t)} (by ${simpleName(delegating)}) invoke: ${method(t)}")
    }
}

private fun <T : Base> simpleName(t: T) = t::class.simpleName



