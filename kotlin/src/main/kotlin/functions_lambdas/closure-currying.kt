package functions_lambdas


//for currying
val liner =
    { a: Double ->
        { b: Double ->
            { c: Double ->
                { x: Double ->
                    { y: Double -> a*x + b*y + c }
                }
            }
        }
    }

fun liner2(a: Double, b: Double, c: Double) = { x:Double, y:Double -> a*x + b*y + c}

fun main() {

    //closure
    val x0 = 5.0
    val y0 = 6.0

    val a = 2.0
    val b = 3.0
    val c = 4.0

    val z1 = liner2(a, b, c)
    val z10 = z1(x0, y0)
    println("closure: z1(x0, y0) = $z10")

    val z2 = { x:Double, z:Double -> a*x + b*z + c }
    val z20 = z2(x0, y0)
    println("closure: z2(x0, y0) = $z20")

    //currying
    val z3 = liner(a)(b)(c)
    val z30 = z3(x0)(y0)
    println("curring: z3(x0, y0) = $z30")

}
