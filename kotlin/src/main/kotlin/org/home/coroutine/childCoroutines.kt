package org.home.coroutine

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.runBlocking

/**
 * Each parent coroutine had to wait until their children finished. This was enabled by using coroutineScope, ensuring each coroutine launched inside it had completed before moving on.
 */
fun main() {
    runBlocking {
        val parentsCoroutines = 1..2
        val childCoroutines = 1..2
        val jobs: List<Job> = (parentsCoroutines).map { parentNumber ->
            // This coroutine is joined on inside [runBlocking] to allow the last [println]
            launch(Dispatchers.Default) {
                // The [coroutineScope] block cannot be left until the 2 corountines launched inside have finished
                coroutineScope {
                    threadPrint("launched parent-$parentNumber")
                    childCoroutines.map { childNumber ->
                        launch {
                            threadPrint("launched child-$childNumber by parent-$parentNumber")
                            delay(100)
                            threadPrint("finished child-$childNumber by parent-$parentNumber")
                        }
                    }
                }
                threadPrint("finished parent-$parentNumber")
            }
        }
        threadPrint("created all coroutines")
        jobs.joinAll()
        threadPrint("finished all coroutines")
    }
}