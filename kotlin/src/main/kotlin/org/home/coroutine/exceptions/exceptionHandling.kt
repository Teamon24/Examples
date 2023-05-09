package org.home.coroutine.exceptions

import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking
import org.home.coroutine.awaitAsyncs
import org.home.coroutine.launchAll
import org.home.coroutine.noHandling
import org.home.coroutine.printStackTrace
import org.home.coroutine.threadPrintln
import kotlin.random.Random

fun main() {
    val quantity = 2
    val jobs = (1..quantity)
        .toList()
        .launchAll(exceptionHandler = printStackTrace()) { number ->
            threadPrintln("coroutine \"$number\" is being executed")
            delay(randomSleep())
            throw RuntimeException("index: $number")
        }

    runBlocking {
        threadPrintln("waiting for coroutines")
        jobs.joinAll()
        threadPrintln("coroutines are executed")
    }

    (1..quantity)
        .toList()
        .awaitAsyncs(exceptionHandler = noHandling()) { number ->
            threadPrintln("coroutine \"$number\" is being executed")
            delay(randomSleep())
            throw IllegalArgumentException("index: $number")
        }
}

private fun randomSleep() = Random(1).nextLong(1000)