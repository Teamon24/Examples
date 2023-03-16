package org.home.coroutine

import kotlinx.coroutines.newFixedThreadPoolContext
import kotlinx.coroutines.runBlocking

fun main() {
     runBlocking {
        val coroutineDispatcher = newFixedThreadPoolContext(2, "asyncs")
        (1..10)
            .associateWith { it }
            .execute(coroutineDispatcher) { index, n ->
                throw RuntimeException("index: $index")
            }
            .filterNotNull()
    }
}