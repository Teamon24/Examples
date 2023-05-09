package org.home.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun <E> List<E>.launchAll(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    exceptionHandler: CoroutineExceptionHandler = printStackTrace(),
    mapping: suspend (E) -> Unit
): List<Job> {
    if (this.isEmpty()) return listOf()
    return this.map { element -> launch(dispatcher, exceptionHandler) { mapping(element) } }
}


fun <E, R> List<E>.awaitAsyncs(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    exceptionHandler: CoroutineExceptionHandler = printStackTrace(),
    mapping: suspend (E) -> R?
): List<Deferred<R?>> {
    if (this.isEmpty()) return arrayListOf()
    val coroutineScope = CoroutineScope(dispatcher)
    val tasks = this.map { coroutineScope.async(exceptionHandler) { mapping(it) } }
    return tasks
}

fun <K, V> Map<K, V>.launchAll(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    exceptionHandler: CoroutineExceptionHandler = printStackTrace(),
    mapping: suspend (K, V) -> Unit
): List<Job> {
    if (this.isEmpty()) return listOf()
    return this.map { (key, value) -> launch(dispatcher, exceptionHandler) { mapping(key, value) } }
}

fun <K, V, R> Map<K, V>.awaitAsyncs(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    exceptionHandler: CoroutineExceptionHandler = printStackTrace(),
    mapping: suspend (K, V) -> R
): List<Deferred<R?>> {
    val map = this
    if (map.isEmpty()) return arrayListOf()
    val coroutineScope = CoroutineScope(dispatcher)
    val tasks = map.map { (key, value) ->
        coroutineScope.async(exceptionHandler) { mapping(key, value) }
    }
    return tasks
}

private fun launch(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    exceptionHandler: CoroutineExceptionHandler = printStackTrace(),
    launchBody: suspend CoroutineScope.() -> Unit
): Job {
    return CoroutineScope(dispatcher).launch(exceptionHandler, block = launchBody)
}

private inline fun <E, R> Collection<E>.runCatchingEach(block: E.() -> R): Collection<R?> {
    return this.map { element ->
        try {
            return@map block(element)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
            return@map null
        }
    }
}

fun printStackTrace() = CoroutineExceptionHandler { _, exception ->
    println("CoroutineExceptionHandler: ")
    exception.printStackTrace()
}

fun noHandling() = CoroutineExceptionHandler { _, exception -> println("No handling for \"${exception}: ${exception.message}\"")}

fun threadPrintln(message: String) = println("[${Thread.currentThread().name}]: $message")
