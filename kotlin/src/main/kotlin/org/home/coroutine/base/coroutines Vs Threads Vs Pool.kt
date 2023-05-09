package org.home.coroutine.base

import kotlinx.coroutines.Runnable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.home.PrintUtils
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.concurrent.thread


private const val sleepTime = 10L

fun main() {

    val comparisonsTable = arrayListOf<List<Any>>()
    val stepsNumber = 50

    val coroutinesHeader = "coroutines time"
    val threadsHeader = "threads time"
    val threadPoolTaskHeader = "thread pool tasks time"

    val start = 4000
    val tasksNumbers = 5000

    val step = (tasksNumbers - start) / stepsNumber

    for (i in start until tasksNumbers step step) {
        val comparisons = comparison(i).apply { add(0, sleepTime) }
        comparisonsTable.add(comparisons)
        println("times: $i")
    }

    comparisonsTable.add(0, listOf("sleep time", "times", coroutinesHeader, threadsHeader, threadPoolTaskHeader))
    PrintUtils.printAsTable(comparisonsTable, alignLeft = false, delimiter = " | ")
}

private fun comparison(times: Int): ArrayList<Any> {
    val coroutinesTime = timer { coroutines(times) }
    val threadTimes = timer { threads(times) }
    val threadPoolTasksTime = timer { threadPoolTasks(times) }
    return arrayListOf(times, coroutinesTime, threadTimes, threadPoolTasksTime)
}

private fun threads(times: Int) {
    createThreads(times)
        .onEach { it.start() }
        .map { it.join() }
}

private fun threadPoolTasks(times: Int) {
    Executors.newFixedThreadPool(times).apply {
        getAll(tasks(times))
        shutdownNow()
    }
}

private fun coroutines(times: Int) {
    runBlocking {
        repeat(times) {
            launch {
                delay(sleepTime)
            }
        }
    }
}

private fun ExecutorService.getAll(tasks: List<Runnable>) = tasks.map { submit(it) }.map { it.get() }

private fun tasks(times: Int) = (0..times).map { Runnable { Thread.sleep(sleepTime) } }
private fun createThreads(times: Int) = (1..times).map { sleepingThread(sleepTime) }
private fun sleepingThread(sleepTime: Long) = thread(start = false) { Thread.sleep(sleepTime) }


fun timer(function: () -> Unit): Long {
    val start = System.currentTimeMillis()
    function()
    val finish = System.currentTimeMillis()
    return finish - start
}
