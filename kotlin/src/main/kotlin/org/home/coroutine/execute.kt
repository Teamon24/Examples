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

fun <E, R> List<E>.execute(
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default,
    block: (E) -> R
): List<R?> {
    if (this.isEmpty()) return arrayListOf()
    val tasks = this.map { element -> async(coroutineDispatcher) { block(element) } }
    return ArrayList<R?>(tasks.size).apply {
        runBlocking() {
            val results = tasks.runCatchingEach { await() }
            addAll(results)
        }
    }
}

fun <K, V> Map<K, V>.launchAll(
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default,
    coroutineExceptionHandler: CoroutineExceptionHandler = printStackTrace(),
    task: (K, V) -> Unit
): List<Job> {
    if (this.isEmpty()) return listOf()
    return this.map { (key, value) -> launch(coroutineDispatcher, coroutineExceptionHandler) { task(key, value) } }
}

fun <K, V, R> Map<K, V>.execute(
    coroutineDispatcher: CoroutineDispatcher = Dispatchers.Default,
    task: (K, V) -> R
): List<R?> {
    val map = this
    if (map.isEmpty()) return arrayListOf()
    val tasks = map.map { (key, value) -> async(coroutineDispatcher) { task(key, value) } }
    return ArrayList<R?>(tasks.size).apply {
        runBlocking() {
            val results = tasks.runCatchingEach { await() }
            addAll(results)
        }
    }
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

fun <R> async(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    task: suspend CoroutineScope.() -> R
): Deferred<R> {
    return CoroutineScope(dispatcher).async(block = task)
}

fun launch(
    dispatcher: CoroutineDispatcher = Dispatchers.Default,
    coroutineExceptionHandler: CoroutineExceptionHandler = printStackTrace(),
    task: suspend CoroutineScope.() -> Unit
): Job {
    return CoroutineScope(dispatcher).launch(
        coroutineExceptionHandler,
        block = task
    )
}

fun printStackTrace() = CoroutineExceptionHandler { _, exception ->
    println("CoroutineExceptionHandler: ")
    exception.printStackTrace()
}