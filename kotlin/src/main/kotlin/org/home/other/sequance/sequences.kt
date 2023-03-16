package org.home.other.sequance

/**
 * Difference between
 */
fun main() {

    val oddNumbers = sequence {
        yield(1)
        yieldAll(listOf(3, 5))
        yieldAll(generateSequence(7) { it + 2 })
    }
    println(oddNumbers.take(35).toList())
}
