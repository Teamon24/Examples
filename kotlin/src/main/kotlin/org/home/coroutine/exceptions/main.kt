package org.home.coroutine.exceptions

import kotlinx.coroutines.*

/**
 * If something goes wrong inside the code of the concurrentSum function,
 * and it throws an exception,
 * all the coroutines that were launched in one scope will be cancelled.
 */
fun main() = runBlocking<Unit> {
    try {
        failedConcurrentSum()
    } catch (e: ArithmeticException) {
        println("Computation failed with ArithmeticException")
    }
}

/**
 * Two coroutines in one coroutine scope.
 */
suspend fun failedConcurrentSum(): Int = coroutineScope {
    val one = async {
        try {
            delay(Long.MAX_VALUE) // Emulates very long computation
            42
        } finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> {
        println("Second child throws an exception")
        throw ArithmeticException()
    }
    one.await() + two.await()
}