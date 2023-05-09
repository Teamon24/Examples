package org.home.classes_objects.delegation

interface A {
    val message: String
    fun method1(): String
    fun method2(): String
}

class B : A {
    override val message = "message of B"
    override fun method1(): String = "method1 of B"
    override fun method2(): String = "method2 of B"
}

class C(b: A) : A by b {
    override val message = "message of C"
    override fun method1(): String = "method1 of C"
    // method "override fun println()" will be delegated from b
}

class D(b: B) : A by b {
    override val message = "message of D"
    override fun method1(): String = "method1 of D"
    // method "override fun println()" will be delegated from b
}

fun main() {
    val b = B()
    val c = C(b)
    val d = D(b)
    val c2 = C(d)

    val delegatingAndDelegated = hashMapOf(
        b to b,
        c to b,
        d to b,
        c2 to d
    )

    delegatingAndDelegated.forEach { (delegating, delegated) ->
        sout(delegating, delegated) { it.message }
        sout(delegating, delegated) { delegating.method1() }
        sout(delegating, delegated) { delegating.method2() }
        println()
    }
}

private fun <T: A> sout(t: T, delegating: T, method: (T) -> Any) {
    if (t == delegating) {
        println("${simpleName(t)} invoke: ${method(t)}")
    } else {
        println("${simpleName(t)} (by ${simpleName(delegating)}) invoke: ${method(t)}")
    }
}

private fun <T : A> simpleName(t: T) = t::class.simpleName



