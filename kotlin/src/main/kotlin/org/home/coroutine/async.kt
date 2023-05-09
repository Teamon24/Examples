package org.home.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.home.coroutine.base.CustomThreadFactory
import java.util.concurrent.Executors

fun main() {
    val intRange = 1..10

    val executor = Executors
        .newFixedThreadPool(3, CustomThreadFactory("asyncs", false))
        .asCoroutineDispatcher()

    val deferreds1 = intRange
        .associateWith { it }
        .awaitAsyncs(executor) { key, value ->
            val timeMillis = key * 1000L
            threadPrintln("delay is $timeMillis ms")
            delay(timeMillis)
            threadPrintln("${key}:${value}")
        }

    val launched = CoroutineScope(executor).launch {
        deferreds1.forEach { it.await() }
    }

    val deferreds2 = intRange
        .toList()
        .awaitAsyncs(executor) { element ->
            val timeMillis = element * 1000L
            threadPrintln("delay is $timeMillis ms")
            delay(timeMillis)
            threadPrintln("$element")
            element
        }

    val asynced = CoroutineScope(executor).async {
        threadPrintln("im awaiting for deferreds2")
        deferreds2.map { it.await() }
        threadPrintln("deferreds2 are done")
    }

    runBlocking {
        threadPrintln("im in runBlocking")
        launched.join()
        threadPrintln("launched is done")
        asynced.join()
        threadPrintln("asynced is done")
    }

    executor.close()
}
