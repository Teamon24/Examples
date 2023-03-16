package org.home.other.pattern_match

import org.home.other.pattern_match.ReferenceType.Companion.isTheCoolest


fun main() {

    val x = null
    val a = 1
    val b = 1
    val c = 2

    when (x) {
        a == b -> doSomething()
        b == c -> doSomethingElse()
        1 -> print("x == 1")
        2 -> print("x == 2")
        3, 4 -> print("x == 3 or x == 4")
        in 1..10 -> print("in range")
        is String -> print("I guess it's not even a number")
        is Number -> print("I guess it's not even a string")
        else -> doSomethingElse()
    }

    val type = ReferenceType()
    val moreComplexCheck2 = { type: ReferenceType -> true }

    when {
        isTheCoolest(type) -> doSomething()
        moreComplexCheck(type) -> doSomething()
        moreComplexCheck2(type) -> doSomething()
    }
}

class ReferenceType {
    companion object {
        @JvmStatic
        fun isTheCoolest(another: ReferenceType) = true
    }
}

fun moreComplexCheck(subject: ReferenceType) = true
fun doSomething() {}
fun doSomethingElse(){}
