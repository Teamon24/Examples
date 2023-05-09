package org.home.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    launchMany()
    launchOneInScope()
}

private fun launchMany() {
    runBlocking {
        val jobs: List<Job> = (1..5).map {
            launch(start = CoroutineStart.LAZY, context = Dispatchers.Default) {
                threadPrintln("Launched coroutine: $it")
                delay(100)
                threadPrintln("Finished coroutine: $it")
            }
        }
        threadPrintln("Created all coroutines")
        jobs.onEach { it.start() }.onEach { it.join() }
        threadPrintln("Finished all coroutines")
    }
}

private fun launchOneInScope() {
    val job = CoroutineScope(Dispatchers.Default).launch {
        threadPrintln("coroutine is being executed")
        delay(5000)
        threadPrintln("coroutine is executed")
    }

    threadPrintln("dispatched coroutine")
    runBlocking {
        threadPrintln("waiting for coroutine")
        job.join()
        threadPrintln("going further")
    }
}