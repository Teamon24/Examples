package org.home.coroutine

import kotlinx.coroutines.Runnable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

const val sleepTime = 500L

fun main() {
    val threadsNumber = 6
    val times = threadsNumber * 5

    val fixedThreadPool = Executors.newFixedThreadPool(threadsNumber)

    val tasks = tasks(times)
    val threads = threads(times)

    println("coroutinesTime: ${ timer {
            runBlocking {
                repeat(times) {
                    launch(fixedThreadPool(threadsNumber)) {
                        delay(sleepTime)
                    }
                }
            }
        }
    }")

    println("threadsPoolTime: ${timer { fixedThreadPool.getAll(tasks) }}")
    println("threadsTime: ${ timer {
            threads.onEach { it.start() }
            threads.forEach { it.join() }
        }
    }")

    fixedThreadPool.awaitTermination(100L, TimeUnit.MILLISECONDS)
}

private fun fixedThreadPool(threadsNumber: Int) = newFixedThreadPoolContext(threadsNumber, "kotlin coroutines pool")

private fun ExecutorService.getAll(tasks: List<Runnable>) = tasks.map { submit(it) }.map { it.get() }

private fun tasks(times: Int): List<Runnable> {
    return (0..times).map {
        Runnable { Thread.sleep(sleepTime) }
    }
}

private fun threads(times: Int): List<Thread> {
    val threads = (0..times).map {
        thread(start = false) {
            Thread.sleep(sleepTime)
        }
    }
    return threads
}


fun timer(function: () -> Unit): Long {
    val start = System.currentTimeMillis()
    function()
    val finish = System.currentTimeMillis()
    return finish - start
}
