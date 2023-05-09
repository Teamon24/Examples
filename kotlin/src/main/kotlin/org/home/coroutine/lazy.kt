package org.home.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis
import kotlinx.coroutines.async
import kotlinx.coroutines.delay

/**
 * Optionally, async can be made lazy by setting its start parameter to CoroutineStart.LAZY.
 * In this mode it only starts the coroutine when its result is required by await,
 * or if its Job's start function is invoked.
 */
fun main() = runBlocking<Unit> {
    val dispatcher = Dispatchers.Unconfined
    val delayTime = 3000L
    val scope = this
    notLazyCoroutines(dispatcher, scope, delayTime)
    lazyCoroutines(dispatcher, scope, delayTime)
}

private suspend fun lazyCoroutines(
    dispatcher: CoroutineDispatcher,
    scope: CoroutineScope,
    delayTime: Long,
) {
    val time = measureTimeMillis {
        val one = scope.lazyAsync(dispatcher) { doSomething(1) }
        val two = scope.lazyAsync(dispatcher) { doSomething(2) }
        threadPrintln("Computing...")
        startAndAwaitFor(delayTime, one, two)
    }
    println("Completed in $time ms")
    repeat(2) { println() }

}

private suspend fun notLazyCoroutines(
    dispatcher: CoroutineDispatcher,
    scope: CoroutineScope,
    delayTime: Long,
) {
    val time = measureTimeMillis {
        val one = scope.async(dispatcher) { doSomething(1) }
        val two = scope.async(dispatcher) { doSomething(2) }
        threadPrintln("Computing...")
        startAndAwaitFor(delayTime, one, two)
    }
    println("Completed in $time ms")
    repeat(2) { println() }
}

private fun CoroutineScope.lazyAsync(
    dispatcher: CoroutineDispatcher,
    function: suspend CoroutineScope.() -> Int,
) = async(context = dispatcher, start = CoroutineStart.LAZY, block = function)

private suspend fun startAndAwaitFor(
    delayTime: Long,
    one: Deferred<Int>,
    two: Deferred<Int>,
) {
    delay(delayTime)
    one.start()
    two.start()
    println("The answer is ${one.await() + two.await()}")
}

suspend fun doSomething(number: Int): Int {
    threadPrintln("$number is started")
    delay(1000L) // pretend we are doing something useful here
    threadPrintln("$number is done")
    return number
}
