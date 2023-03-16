package org.home.functions_lambdas


//for currying
val currying =
    { a: Double ->
        { b: Double ->
            { c: Double ->
                { x: Double ->
                    { y: Double -> a*x + b*y + c }
                }
            }
        }
    }

fun closure(a: Double, b: Double, c: Double) = { x:Double, y:Double -> a*x + b*y + c}

fun main() {

    /*----------/ CLOSURE /----------*/
    val (x0, y0) = arrayOf(5.0, 6.0)
    val (a, b, c) = arrayOf(2.0, 3.0, 4.0)

    val z1 = closure(a, b, c)
    val z10 = z1(x0, y0)
    println("closure: z1(x0, y0) = $z10")

    val z2 = { x:Double, z:Double -> a*x + b*z + c }
    val z20 = z2(x0, y0)
    println("closure: z2(x0, y0) = $z20")

    /*----------/ CURRYING /----------*/
    val z3 = currying(a)(b)(c)
    val z30 = z3(x0)(y0)
    println("curring: z3(x0, y0) = $z30")

}
