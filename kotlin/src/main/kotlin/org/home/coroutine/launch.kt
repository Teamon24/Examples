package org.home.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {
    launchMany()
    launchOneInScope()
}

private fun launchMany() {
    runBlocking {
        val jobs: List<Job> = (1..5).map {
            launch(context = Dispatchers.Default) {
                threadPrint("Launched coroutine: $it")
                delay(100)
                threadPrint("Finished coroutine: $it")
            }
        }
        threadPrint("Created all coroutines")
        jobs.joinAll()
        threadPrint("Finished all coroutines")
    }
}

private fun launchOneInScope() {
    val job = CoroutineScope(Dispatchers.Default).launch {
        threadPrint("coroutine is being executed")
        delay(5000)
        threadPrint("coroutine is executed")
    }
    threadPrint("dispatched coroutine")
    runBlocking {
        threadPrint("waiting for coroutine")
        job.join()
        threadPrint("going further")
    }
}