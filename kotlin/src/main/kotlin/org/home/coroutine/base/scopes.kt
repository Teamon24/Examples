package org.home.coroutine.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.home.coroutine.threadPrintln
import kotlin.coroutines.CoroutineContext

fun main() {
    coroutineChildren()
}
/**
 * #Scopes
 * **Scope is used to create and manage the coroutine, and it’s responsible for
 * the coroutine’s lifecycle**. At the same time, the **coroutine context is a holder of data** represented as a set of elements that are **associated with the coroutine**.
 * Job and Dispatcher are important elements of this set that define how to execute the coroutine.
 *
 *
 * ##Coroutine Scope
 *
 * To launch a coroutine, we need to use a coroutine builder like launch or async.
 * These builder functions are actually extensions of the CoroutineScope interface.
 * So, whenever we want to launch a coroutine, we need to start it in some scope.
 *
 * The scope creates relationships between coroutines inside it and allows us to
 * manage the lifecycles of these coroutines. There are several scopes provided by
 * the kotlinx.coroutines library that we can use when launching a coroutine.
 * There’s also a way to create a custom scope.
 *
 */
fun scopes() {}


/**
 * ##Global scope
 * One of the simplest ways to run a coroutine is to use **[GlobalScope]**.
 * The lifecycle of this scope is tied to the lifecycle of the whole application.
 * This means that the scope will stop running either after all of its coroutines have been completed
 * or when the application is stopped.
 *
 * It’s worth mentioning that coroutines launched using GlobalScope do not keep the process alive.
 * They behave similarly to daemon threads. So, even when the application stops,
 * some active coroutines will still be running.
 *
 * This can easily create resource or memory leaks.
 */
@OptIn(DelicateCoroutinesApi::class)
fun globalScope() = GlobalScope.launch {
    delay(500L)
    println("Coroutine launched from GlobalScope")
}

/**
 * ##[runBlocking]
 * Another scope that comes right out of the box is runBlocking.
 * From the name, we might guess that it creates a scope and runs a coroutine in a blocking way.
 * This means it blocks the current thread until all childrens’ coroutines complete their executions.
 *
 * **It is not recommended to use this scope because threads are expensive
 * and will depreciate all the benefits of coroutines.**
 *
 * The most suitable place for using runBlocking is the very top level of the application, which is the main function.
 * Using it in main will ensure that the app will wait until all child jobs inside runBlocking complete.
 * Another place where this scope fits nicely is in tests that access suspending functions.
 */
private fun runBlocking() = runBlocking {
    launch { println("run blocking") }
}

/**
 *
 * ##[coroutineScope]
 * For all the cases when we don’t need thread blocking, we can use coroutineScope.
 * Similarly to runBlocking, it will wait for its children to complete.
 * But unlike runBlocking, this scope doesn’t block the current thread but only suspends it because coroutineScope is a suspending function.
 *
 * Differences between runBlocking and coroutineScope are shown in [RunBlockingVsCoroutineScope].
 */
suspend fun coroutineScopeParagraph() = coroutineScope {
    launch {
        println("coroutine context: ${this.coroutineContext}")
    }
}

/**
 * ##Custom coroutine scope
 * There might be cases when we need to have some specific behavior of the scope to get a different approach
 * in managing the coroutines.
 * To achieve that, we can implement the **[CoroutineScope]** interface and implement our custom scope for coroutine handling.
 */
class CustomScope() : CoroutineScope {
    override val coroutineContext: CoroutineContext

    init {
        this.coroutineContext = object : CoroutineContext {
            override fun <R> fold(initial: R, operation: (R, CoroutineContext.Element) -> R): R {
                TODO("Not yet implemented")
            }

            override fun <E : CoroutineContext.Element> get(key: CoroutineContext.Key<E>): E? {
                TODO("Not yet implemented")
            }

            override fun minusKey(key: CoroutineContext.Key<*>): CoroutineContext {
                TODO("Not yet implemented")
            }
        }
    }
}



/**
 * ## Coroutine Contex
 *
 * Now, let’s take a look at the role of **[CoroutineContext]** here.
 * The context is a holder of data that is needed for the coroutine.
 * Basically, it’s an indexed set of elements where each element in the set has a unique key.
 *
 * The important elements of the coroutine context are the Job of the coroutine and the Dispatcher.
 *
 * Kotlin provides an easy way to add these elements to the coroutine context using the “+” operator:
*/
fun coroutineContext(): Job {
    return CoroutineScope(Dispatchers.Default).let {
        val coroutineContext = it.coroutineContext
        val job = it.launch(coroutineContext + Job()) {
            println("Coroutine works in thread ${Thread.currentThread().name}")
        }
        println()
        return job
    }
}

/**
 * ##Job in the Context
 *
 * Job of a coroutine is to handle the launched coroutine.
 * For example, it can be used to wait for coroutine completion explicitly.
 *
 * Since Job is a part of the coroutine context, it can be accessed using the coroutineContext[Job] expression.
*/
fun job() { }

/**
 * ##Coroutine Context and Dispatchers.
 *
 * Another important element of the context is Dispatcher. It determines what threads the coroutine will use for its execution.
 * Kotlin provides several implementations of CoroutineDispatcher that we can pass to the CoroutineContext:
 * - [Dispatchers.Default] uses a shared thread pool on the JVM. By default, the number of threads is equal to the number of CPUs available on the machine.
 * - [Dispatchers.IO] is designed to offload blocking IO operations to a shared thread pool.
 * - [Dispatchers.Main] is present only on platforms that have main threads, such as Android and iOS.
 * - [Dispatchers.Unconfined] doesn’t change the thread and launches the coroutine in the caller thread.
 * The important thing here is that after suspension, it resumes the
 * coroutine in the thread that was determined by the suspending function.
*/
fun contextAndDispatchers() {}

/**
 * ##Switching the Context.
 *
 * Sometimes, we must change the context during coroutine execution while staying in the same coroutine.
 * We can do this **using the [withContext] function**. It will call the specified
 * suspending block with a given coroutine context.
 * The outer coroutine suspends until this block completes and returns the result.
 *
 * The context of the withContext block will be the merged contexts of the coroutine and the context passed to withContext.
*/
@OptIn(DelicateCoroutinesApi::class)
fun switchingContext() {
    newSingleThreadContext("Context 1").use { ctx1 ->
        newSingleThreadContext("Context 2").use { ctx2 ->
            runBlocking(ctx1) {
                threadPrintln("Coroutine are started")
                withContext(ctx2) {
                    threadPrintln("Coroutine are working")
                }
                threadPrintln("Coroutine are switched")
            }
        }
    }
    println()
}

/**
 * ##Children of a Coroutine.
 *
 * When we launch a coroutine inside another coroutine, it inherits the outer coroutine’s context,
 * and the job of the new coroutine becomes a child job of the parent coroutine’s job.
 * Cancellation of the parent coroutine leads to cancellation of the child coroutine as well.
 *
 * We can override this parent-child relationship using one of two ways:
 * - Explicitly specify a different scope when launching a new coroutine.
 * - Pass a different Job object to the context of the new coroutine.
 *
 * In both cases, the new coroutine will not be bound to the scope of the parent coroutine.
 * It will execute independently, meaning that canceling the parent coroutine won’t affect the new coroutine.
 */
fun coroutineChildren() = runBlocking {
    val parentsCoroutines = 1..2
    val childCoroutines = 1..3
    val jobs: List<Job> = (parentsCoroutines).map { parentNumber ->
        // This coroutine is joined on inside [runBlocking] to allow the last [println]
        launch parent@{
            // The [coroutineScope] block cannot be left until the 2 coroutines launched inside have finished
            threadPrintln("${p(parentNumber)} launched")
            coroutineScope {
                childCoroutines.map { childNumber ->
                    launch child@{
                        if (parentNumber == 2) {
                            this@parent.cancel("${p(parentNumber)} canceled")
                        }
                        if (childNumber == 1) {
                            this@parent.launch {
                                delay(parentNumber, childNumber, 4000)
                                threadPrintln("${pAndC(parentNumber, childNumber)}: active is ${this@child.isActive}")
                            }
                            return@child
                        }

                        delay(parentNumber, childNumber, 1000)

                    }
                }
            }

            threadPrintln("${p(parentNumber)} finished ")
        }
    }
    threadPrintln("created all coroutines")
    jobs.joinAll()
    threadPrintln("finished all coroutines")
    println()
}

private suspend fun delay(parentNumber: Int, childNumber: Int, delayTime: Long) {
    threadPrintln("${pAndC(parentNumber, childNumber)} launched")
    delay(delayTime)
    threadPrintln("${pAndC(parentNumber, childNumber)} finished")
}

private fun pAndC(parentNumber: Int, childNumber: Int) = "${p(parentNumber)}-${c(childNumber)}"

private fun c(childNumber: Int) = "Child[$childNumber]"
private fun p(parentNumber: Int) = "Parent[$parentNumber]"

private fun CoroutineScope.getContextLinkName() =
    Regex(".?(@.+),").find(this.coroutineContext.toString())!!.value.drop(1).dropLast(1)