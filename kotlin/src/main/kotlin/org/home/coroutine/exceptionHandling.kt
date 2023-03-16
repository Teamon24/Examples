package org.home.coroutine

import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

fun main() {
    val quantity = 2
    val jobs = (1..quantity)
        .associateWith { it }
        .launchAll(coroutineExceptionHandler = printStackTrace()) { i, _ ->
            threadPrint("coroutine \"$i\" is being executed")
            throw RuntimeException("index: $i")
        }

    runBlocking {
        threadPrint("waiting for coroutines")
        jobs.joinAll()
        threadPrint("coroutines are executed")
    }

    (1..quantity)
        .associateWith { it }
        .execute { i, _ ->
            threadPrint("coroutine \"$i\" is being executed")
            throw IllegalArgumentException("index: $i")
        }

}