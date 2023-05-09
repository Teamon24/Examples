package org.home.coroutine.io

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.future.await
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

val uri = URI("http://localhost:8080")



fun main() {
    withJdkClientBlocking("Running with JDK11 client using blocking send()")
    withJdkClientNonBlocking("Running with JDK11 client using non-blocking sendAsync()")
    withKtorHttpClient()
}

fun withJdkClientBlocking(s: String) {

    val client = HttpClient.newHttpClient()
    runExample {
        // Sometimes you can't avoid coroutines with blocking I/O methods.
        // These must be always be dispatched by Dispatchers.IO.
        withContext(Dispatchers.IO) {
            // Kotlin compiler warns this is a blocking I/O method.
            val response = client.send(
                HttpRequest.newBuilder(uri).build(),
                HttpResponse.BodyHandlers.ofString()
            )
            // Return status code.
            response.statusCode()
        }
    }
}
fun withJdkClientNonBlocking(s: String) {
    println(s)

    val httpExecutor = Executors.newSingleThreadExecutor()
    val client = HttpClient.newBuilder().executor(httpExecutor).build()
    try {
        runExample {
            // We use `.await()` for interoperability with `CompletableFuture`.
            val response = client.sendAsync(
                HttpRequest.newBuilder(uri).build(),
                HttpResponse.BodyHandlers.ofString()
            ).await()
            // Return status code.
            response.statusCode()
        }
    } finally {
        httpExecutor.shutdown()
    }
}

fun withKtorHttpClient() {
    println("Running with Ktor client")

    // Non-blocking I/O does not imply unlimited connections to a host.
    // You are still limited by the number of ephemeral ports (an other limits like file descriptors).
    // With no configurable thread limit, you can configure the max number of connections.
    // Note that HTTP/2 allows concurrent requests with a single connection.
    HttpClient(CIO) { engine { maxConnectionsCount = 128 } }.use { client ->
        runExample {
            // KtorClient.get() is a suspend fun, so suspension is implicit here
            val response = client.get(HttpRequestBuilder(uri.toURL()))
            // Return status code.
            response.status.value
        }
    }
}

fun runExample(block: suspend () -> Int) {
    var successCount = 0
    var failCount = 0

    Executors.newSingleThreadExecutor().asCoroutineDispatcher().use { dispatcher ->
        measureTimeMillis {
            runBlocking(dispatcher) {
                requests(block).awaitAll().forEach {
                    if (it in 200..399) ++successCount else ++failCount
                }
            }
        }.also {
            println("Successfully sent ${successCount + failCount} requests in ${it}ms: $successCount were successful and $failCount failed.")
        }
    }
}

private fun CoroutineScope.requests(block: suspend () -> Int): MutableList<Deferred<Int>> {
    val responses = mutableListOf<Deferred<Int>>()
    repeat(128) { responses += async { block() } }
    return responses
}
