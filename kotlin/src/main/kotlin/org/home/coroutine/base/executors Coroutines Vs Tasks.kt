@file:Suppress("SameParameterValue")

package org.home.coroutine.base

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.home.coroutine.threadPrintln
import java.util.concurrent.Executors
import java.util.concurrent.FutureTask
import java.util.concurrent.ThreadFactory
import kotlin.random.Random

private fun sleep(): Long = Random(1).nextLong(100, 4000)

fun main() {

    val nThreads = 2
    val counter = 2
    val executionsNumber = 4
    val times = timer {
        runBlocking {
            coroutinesPool(nThreads, executionsNumber, counter)
        }
    } to timer {
        tasksPool(nThreads, executionsNumber, counter)
    }

    println("coroutine pool time: ${times.first}")
    println("tasks pool time: ${times.second}")
}

private suspend fun coroutinesPool(nThreads: Int, executionsNumber: Int, counter: Int) {

    val poolContext = executorService(nThreads, "COROUTINE POOL").asCoroutineDispatcher()
    val deferreds = arrayListOf<Deferred<Any>>()

    coroutineScope {
        for (i in 0..executionsNumber) {
            val deferred = async(context = poolContext) {
                printExecuting(i)
                for (j in 0..counter) {
                    printCounting(i, j)
                    delay(sleep())
                }
                printDone(i)
            }
            deferreds.add(deferred)
        }
    }

    deferreds.map { it.await() }
    poolContext.close()
}

private fun executorService(nThreads: Int, name: String) =
    Executors.newFixedThreadPool(nThreads, CustomThreadFactory(name))

private fun tasksPool(nThreads: Int, executionsNumber: Int, counter: Int) {
    val threadPool = executorService(nThreads, "TASK POOL");
    val tasks = arrayListOf<FutureTask<Unit>>()
    for (i in 0..executionsNumber) {
        val task = FutureTask {
            printExecuting(i)
            for (j in 0..counter) {
                printCounting(i, j)
                Thread.sleep(sleep())
            }
            printDone(i)
        }
        tasks.add(task)
    }
    tasks.map { threadPool.submit(it) }.map { it.get() }
    threadPool.shutdownNow()
}

private fun taskName(i: Int) = "task #$i"
private fun printDone(i: Int) = threadPrintln("${taskName(i)} is done")
private fun printCounting(i: Int, j: Int) = threadPrintln("${taskName(i)} - $j")
private fun printExecuting(i: Int) = threadPrintln("${taskName(i)} is executing")


class CustomThreadFactory(private var name: String, val log: Boolean = true) : ThreadFactory {
    private var threadId = 1

    override fun newThread(runnable: Runnable): Thread {
        val thread = Thread(runnable, name())
        if (log) {
            threadPrintln("thread \"${thread.name}\" was created")
        }
        threadId++
        return thread
    }

    private fun name(): String {
        return if (name.isEmpty()) "#$threadId" else "$name:#$threadId"
    }
}
