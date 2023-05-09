package org.home.coroutine.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.home.other.sequance.printlnTitle
import java.util.concurrent.Executors
import kotlin.system.measureTimeMillis

typealias Func = suspend () -> Any
typealias FuncInScope = suspend CoroutineScope.(Func) -> Any

private const val number = 10
private const val delayTime = 500L

private val coroutineScope: FuncInScope = { func -> coroutineScope { func() } }
private val runBlocking: FuncInScope = { func -> runBlocking { func() } }

private val dispatcher = Executors.newFixedThreadPool(2).asCoroutineDispatcher()


fun main() {
    suspending()
    canceling()

    dispatcher.close()
}

private fun suspending() {
    printlnTitle("SUSPENDING")
    case(::suspendingDemo, "coroutineScope", coroutineScope)
    case(::suspendingDemo, "runBlocking", runBlocking)
}

private fun canceling() {
    printlnTitle("CANCELING")
    case(::cancelingDemo, "coroutineScope", coroutineScope)
    case(::cancelingDemo, "runBlocking", runBlocking)
}

private fun case(
    demo: (caseName: String, scope: FuncInScope) -> Any,
    caseName: String,
    coroutineScope: FuncInScope,
) {
    measureTimeMillis {
        demo(caseName, coroutineScope)
    }.also {
        println("coroutineScopeTimeInMills = $it")
        println()
    }
}

/**
 * #1. Launching Coroutines
 * Both runBlocking and coroutineScope are coroutine builders, which means they are used to launch
 * coroutines, but we use them in different contexts.
 *
 * When we use coroutineScope to build and launch a coroutine, we create a suspension point.
 * Suspension points are places in the code where Kotlin may suspend the current coroutine.
 * However, **we cannot create a suspension point when there is nothing to suspend, so we cannot
 * invoke coroutineScope outside the scope of an existing coroutine**.
 *
 * We sometimes need to launch a coroutine from outside an existing coroutine scope, such as from
 * the main method or a unit test. This is where we would use runBlocking, which bridges blocking
 * and suspendable code. **We can invoke runBlocking outside the scope of any existing coroutine**. As its name suggests, **the coroutine launched by runBlocking will block the current thread**; it does
 * not create a suspension point, even if used inside another coroutine.
 */
fun launchingCoroutines() {}

/**
 * #2. Suspending Coroutines
 * ##Suspending [coroutineScope] Coroutines
 *
 * Despite each coroutine invoking [delay] with 500 milliseconds, our total runtime won't be much
 * more than that. We will see also that some coroutines were started on pool-1-thread-1 but
 * completed on pool-1-thread-2, and vice versa.
 *
 * Kotlin was able to suspend each coroutine at the [delay] invocation’s suspension point.
 * Since a suspended coroutine does not block any threads, another coroutine could step in,
 * use the thread to launch its own [delay] invocation, and then also be suspended.
 * After the 500ms delay, each coroutine could resume on any inactive thread in the pool.
 *
 * **The coroutines launched by coroutineScope are suspendable**.
 *
 *
 * ##Suspending [runBlocking] Coroutines
 * Each coroutines will be completed on the same thread it started on, and our total runtime will be more than coroutineScope case time. The coroutine launched by [runBlocking] ignored the suspension point created by the [delay] call. **runBlocking coroutines are not suspendable**.
 */
fun suspendingDemo(s: String, scope: FuncInScope) = runBlocking {
    (1..number).forEach {
        launch(dispatcher) {
            scope {
                println(startMessage(it, s))
                delay(delayTime)
                println(endMessage(it, s))
            }
        }
    }
}

/**
 * #3. Canceling Coroutines.
 * The two coroutine builders also differ in whether their coroutines may be canceled.
 *
 * ## Canceling [coroutineScope] Coroutines.
 * We can use the Job reference returned by launch to cancel a coroutine 100ms after launching it.
 * We will see that the coroutine will be canceled at delay(500)‘s suspension point shortly after
 * our 100ms delay.
 *
 * ## Canceling [runBlocking] Coroutines
 * The same thing but using runBlocking in place of coroutineScope.
 *
 * We will see here that **coroutines launched using runBlocking are not cancelable**. Since
 * cancellation occurs at suspension points, and runBlocking coroutines are not suspendable and
 * do not have suspension points, the coroutine was allowed to complete its execution.
 */
private fun cancelingDemo(s: String, param: FuncInScope) = runBlocking {
    val job = launch {
        param {
            println(cancelingStartMessage(s))
            delay(delayTime)
            println(cancelingEndMessage(s))
        }
    }
    delay(100)
    job.cancel()
}

private fun startMessage(it: Int, scope: String) = "Start #$it in $scope on ${Thread.currentThread().name}"
private fun endMessage(it: Int, scope: String) = "End #$it in $scope on ${Thread.currentThread().name}"

private fun cancelingStartMessage(s: String) = "Start $s..."
private fun cancelingEndMessage(s: String) = "End $s..."
