package reactive.reactor;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactive.reactor.Utils.Backpressing;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Long.MAX_VALUE;
import static java.lang.System.out;
import static org.assertj.core.api.Assertions.assertThat;
import static reactive.reactor.Utils.list;
import static utils.ConcurrencyUtils.sleep;

interface ReactorDemo {}

/**
 * <p><strong>Reactive Streams Specification</strong>
 * <p>Before we look at Reactor, we should look at the <strong>Reactive Streams Specification</strong>. This is what Reactor implements, and it lays the groundwork for the library.
 *
 * <p>Essentially, Reactive Streams is a specification for asynchronous stream processing.
 * <p>In other words, a system where <strong>lots of events are being produced and consumed asynchronously</strong>. Think about a stream of thousands of stock updates per second coming into a financial application, and for it to have to respond to those updates in a timely manner.
 *
 * <p>One of the main goals of this is <strong>to address the problem of BACKPRESSURE</strong>. If we have a producer which is emitting events to a consumer faster than it can process them, then eventually the consumer will be overwhelmed with events, running out of system resources.
 *
 * <p>Backpressure means that our consumer should be able to tell the producer how much data to send in order to prevent this, and this is what is laid out in the specification.
 */
interface p0__Reactor_introducing {}

/**
 * <p><strong>{@link Flux}</strong>
 * is a stream that can emit 0..n elements.
 *
 * <p><strong>{@link Mono}</strong>
 * is a stream of 0..1 elements.
 *
 * This looks and behaves almost exactly the same as the Flux, only this time we are limited to no more than one element.
 *
 * <p><strong>Why Not Only Flux?</strong>
 * Before experimenting further, it’s worth highlighting why we have these two data types.
 *
 * <p>First, it should be noted that both Flux and Mono are implementations of the Reactive Streams <strong><a href="https://www.reactive-streams.org/reactive-streams-1.0.3-javadoc/org/reactivestreams/Publisher.html">Publisher interface</a></strong>. Both classes are compliant with the specification, and we could use this interface in their place:
 *
 * <p>But really, knowing this cardinality is useful. This is because a few operations only make sense for one of the two types and because it can be more expressive (imagine findOne() in a repository).
 */
interface p1__Producing_astream_of_data {
    static void main(String[] args) {
        Flux<Integer> flux = Flux.just(1, 2, 3, 4);
        Mono<Integer> mono = Mono.just(1);
        Publisher<String> publisher = Mono.just("foo");
    }
}

interface p2__Subscribing_to_astream {

    /**
     * <p>Let’s use the <strong>{@link Flux#subscribe}</strong> to collect all the elements in a stream.
     * <p><strong>The data won’t start flowing until we subscribe</strong>. Notice that we have added some logging as well, this will be helpful when we look at what’s happening behind the scenes.
     *
     * <p>With logging in place, we can use it to visualize how the data is flowing through our stream:
     * <pre>
     *  {@code
     *  20:25:19.550 [main] INFO  reactor.Flux.Array.1 - | onSubscribe([Synchronous Fuseable] FluxArray.ArraySubscription)
     *  20:25:19.553 [main] INFO  reactor.Flux.Array.1 - | request(unbounded)
     *  20:25:19.553 [main] INFO  reactor.Flux.Array.1 - | onNext(1)
     *  20:25:19.553 [main] INFO  reactor.Flux.Array.1 - | onNext(2)
     *  20:25:19.553 [main] INFO  reactor.Flux.Array.1 - | onNext(3)
     *  20:25:19.553 [main] INFO  reactor.Flux.Array.1 - | onNext(4)
     *  20:25:19.553 [main] INFO  reactor.Flux.Array.1 - | onComplete()
     *  }
     * </pre>
     *
     * <p><i>First of all, everything is running on the main thread. Let’s not go into any details about this, as we’ll be taking a further look at concurrency later on in this article</i>.
     *
     * <p>Now let’s go through the sequence that we have logged one by one:
     * <lu>
     *  <li>{@link Subscriber#onSubscribe} – This is called when we subscribe to our stream</li>
     *  <li>{@link Subscription#request} – When we call subscribe, behind the scenes we are creating a Subscription. This subscription requests elements from the stream. In this case, it defaults to unbounded, meaning it requests every single element available</li>
     *  <li>{@link Subscriber#onNext} – This is called on every single element</li>
     *  <li>{@link Subscriber#onComplete} – This is called last, after receiving the last element. There’s actually an onError() as well, which would be called if there is an exception, but in this case, there isn’t.    </li>
     * </lu>
     *
     * <p>This is the flow laid out in the <strong><a href="https://www.reactive-streams.org/reactive-streams-1.0.3-javadoc/org/reactivestreams/Subscriber.html">Subscriber interface</a></strong> as part of the Reactive Streams Specification, and in reality, that’s what’s been instantiated behind the scenes in our call to onSubscribe(). It’s a useful method, but to better understand what’s happening let’s provide a Subscriber interface directly.
     * <p>We can see that each possible stage in the above flow maps to a method in the Subscriber implementation. It just happens that Flux has provided us with a helper method to reduce this verbosity.
     */
    static void main(String[] args) {
        List<Integer> target = new ArrayList<>();
        List<Integer> source = List.of(1, 2, 3, 4);

        Flux.fromIterable(source)
            .log()
            .subscribe(target::add);

        assertThat(target).containsAll(source);

        Flux.fromIterable(source)
            .log()
            .subscribe(new Subscriber<>() {
                @Override public void onSubscribe(Subscription s) { s.request(MAX_VALUE); }
                @Override public void onNext(Integer integer) { target.add(integer); }
                @Override public void onError(Throwable t) { }
                @Override public void onComplete() { }
            });
    }
}


interface p3__Comparison_to_java_8_streams {

    /**
     * <p>It still might appear that we have something synonymous to a Java 8 Stream doing collect.
     * <p>Only we don’t.
     *
     * <p>The core difference is that <strong>Reactive is a push model</strong>, whereas the <strong>Java 8 Streams are a pull model</strong>. In a <strong>reactive approach, events are pushed to the subscribers as they come in</strong>.
     *
     * <p>The next thing to notice is a <strong>Streams terminal operator is pulling all the data and returning a result</strong>. With Reactive we could have an infinite stream coming in from an external resource, with multiple subscribers attached and removed on an ad hoc basis. We can also do things like combine streams, throttle streams, and apply backpressure.
     */
    static void main(String[] args) {
        List<Integer> collected = Stream.of(1, 2, 3, 4).collect(Collectors.toList());
    }
}

interface p4__Backpressure {
    /**
     * <p>The next thing we should consider is backpressure. In our example, the subscriber is telling the producer to push every single element at once. This could end up becoming overwhelming for the subscriber, consuming all of its resources.
     *
     * <p><strong>Backpressure is when a downstream can tell an upstream to send it less data in order to prevent it from being overwhelmed</strong>.
     *
     * <p>We can modify our Subscriber implementation to apply backpressure. Let’s tell the upstream to only send two elements at a time by using request():
     */
    static void main(String[] args) {
        List<Integer> target = new ArrayList<>();
        int backpressureAmount = 2;
        Flux<Integer> flux = Flux.fromIterable(list(10));

        flux.subscribe(out::println);
        flux
            .log()
            .subscribe(new Subscriber<>() {
                private Backpressing backpressing;

                @Override public void onSubscribe(Subscription subscription) {
                    subscription.request(backpressureAmount);
                    this.backpressing = new Backpressing(subscription);
                }

                @Override public void onNext(Integer integer) {
                    target.add(integer);
                    backpressing.request(backpressureAmount);
                }

                @Override public void onError(Throwable t) { }
                @Override public void onComplete() { }
            });
    }
}

interface p5__Operating_on_streams {
    List<Integer> integers = new ArrayList<>();
    List<String> strings = new ArrayList<>();
    List<Integer> source = list(10);
    Flux<Integer> firstFlux = Flux.fromIterable(source);

    static void main(String[] args) {
        map();
        combine();
    }
    
    /**
     * <li><strong>map()</strong> will be applied when onNext() is called.</li>
     */
    static void map() {
        firstFlux.log()
            .map(printAndDouble())
            .subscribe(integers::add);
    }

    /**
     * <ul><li><strong>zip()</strong> function will combine two fluxes.</li></ul>
     *
     * <p>Here, we are creating another Flux that keeps incrementing by one and
     * streaming it together with our original one. We can see how these work together by inspecting the logs:
     * <pre>
     *     {@code
     *      20:04:38.064 [main] INFO  reactor.Flux.Array.1 - | onSubscribe([Synchronous Fuseable] FluxArray.ArraySubscription)
     *      20:04:38.065 [main] INFO  reactor.Flux.Array.1 - | onNext(1)
     *      20:04:38.066 [main] INFO  reactor.Flux.Range.2 - | onSubscribe([Synchronous Fuseable] FluxRange.RangeSubscription)
     *      20:04:38.066 [main] INFO  reactor.Flux.Range.2 - | onNext(0)
     *      20:04:38.067 [main] INFO  reactor.Flux.Array.1 - | onNext(2)
     *      20:04:38.067 [main] INFO  reactor.Flux.Range.2 - | onNext(1)
     *      20:04:38.067 [main] INFO  reactor.Flux.Array.1 - | onNext(3)
     *      20:04:38.067 [main] INFO  reactor.Flux.Range.2 - | onNext(2)
     *      20:04:38.067 [main] INFO  reactor.Flux.Array.1 - | onNext(4)
     *      20:04:38.067 [main] INFO  reactor.Flux.Range.2 - | onNext(3)
     *      20:04:38.067 [main] INFO  reactor.Flux.Array.1 - | onComplete()
     *      20:04:38.067 [main] INFO  reactor.Flux.Array.1 - | cancel()
     *      20:04:38.067 [main] INFO  reactor.Flux.Range.2 - | cancel()
     *     }
     * </pre>
     * <p>Note how we now have <strong>one subscription per Flux</strong>. The onNext() calls are also alternated, so the index of each element in the stream will match when we apply the zip() function.
     */
    static void combine() {
        Flux<Integer> secondFlux = Flux.range(0, Integer.MAX_VALUE);
        firstFlux
            .log()
            .map(i -> i * 2)
            .zipWith(secondFlux, combineAsString())
            .subscribe(strings::add);

        assertThat(strings).containsExactly(
            "First Flux: 2, Second Flux: 0",
            "First Flux: 4, Second Flux: 1",
            "First Flux: 6, Second Flux: 2",
            "First Flux: 8, Second Flux: 3");
    }

    private static BiFunction<Integer, Integer, String> combineAsString() {
        return (i1, i2) ->
            String.format("First Flux: %d, Second Flux: %d", i1, i2);
    }

    private static Function<Integer, Integer> printAndDouble() {
        return i -> {
            out.printf("{%s}:{%s}\n", i, Thread.currentThread());
            return i * 2;
        };
    }
}

/**
 * <p>Currently, we’ve focused primarily on <strong>cold streams</strong>. These are <strong>static, fixed-length streams</strong> that are easy to deal with. A more realistic use case for reactive might be something that happens infinitely.
 *
 * <p>For example, we could have a stream of mouse movements that constantly needs to be reacted to or a Twitter feed. These types of streams are called <strong>hot streams</strong>, as they <strong>are always running and can be subscribed to at any point in time, missing the start of the data</strong>.
 *
 * <p>A Hot stream <strong>doesn't necessarily need a Subscriber to start pumping data</strong>.
 * <p>A Hot stream <strong>does not create new data for each new subscription</strong>.
 *
 */
interface p6__Hot_and_Cold_streams {

     static void main(String[] args) throws InterruptedException {

         Supplier<Flux<Long>> coldStream = () -> Flux.interval(Duration.ofMillis(500));
         cold(coldStream.get().takeWhile(second -> second < 6));
         share(coldStream.get().takeWhile(second -> second < 6));
         publish(coldStream.get());
     }

    /**
     * <p>One way to create a hot stream is by converting a cold stream into one.
     * <p>By calling {@link ConnectableFlux#publish} we are given a {@link ConnectableFlux}.
     * This means that <strong>calling {@link Flux#subscribe} won’t cause it to start emitting</strong>,
     * allowing us to add multiple subscriptions.
     *
     * <p>On {@link ConnectableFlux#connect}, a flux will start emitting.
     *
     * <p>{@link Flux#sample} method with an interval of two seconds. Now values will only be pushed
     * to our subscriber in the period.
     */
    static void publish(Flux<Long> coldStream) throws InterruptedException {
        List<Long> target1 = new ArrayList<>();
        List<Long> target2 = new ArrayList<>();
        List<Long> target3 = new ArrayList<>();
        List<Long> target4 = new ArrayList<>();

        int seconds = 2;

        ConnectableFlux<Long> hotStream = coldStream.publish();

        hotStream.subscribe(subscriber(target1, "target1"));
        hotStream.connect();
        sleep(seconds);

        hotStream.subscribe(subscriber(target2, "target2"));
        sleep(seconds);

        hotStream.subscribe(subscriber(target3, "target3"));
        sleep(seconds);

        hotStream.subscribe(subscriber(target4, "target4"));
        sleep(seconds);

        out.println(target1);
        out.println(target2);
        out.println(target3);
        out.println(target4);
        assertThat(target1).isNotEmpty();
        assertThat(target2).isNotEmpty();
        assertThat(target3).isNotEmpty();
    }

    private static Consumer<Long> subscriber(List<Long> target, String name) {
        return (it) -> {
            target.add(it);
            out.println(it + " -> " + name);
        };
    }

    static void share(Flux<Long> cold) throws InterruptedException {
        Flux<Long> clockTicks = cold.share();
        subscribeTwo(clockTicks, "hot#1", "hot#2");
    }

    static void cold(Flux<Long> cold) throws InterruptedException {
        subscribeTwo(cold, "cold#1", "cold#2");
    }

    private static void subscribeTwo(Flux<Long> cold, String firstName, String secondName) throws InterruptedException {
        cold.subscribe(tick -> System.out.println(firstName + " <-" + tick));
        Thread.sleep(2000);
        cold.subscribe(tick -> System.out.println("\t\t\t" + tick + "-> " + secondName));
        Thread.sleep(4000);
    }
}

interface p7__Concurrency {

    /**
     * <p>All of our above <strong>examples have currently run on the main thread</strong>. However, <strong>we can control which thread our code runs on if we want</strong>. The Scheduler interface provides an abstraction around asynchronous code, for which many implementations are provided for us.
     * <p><strong>The Parallel scheduler will cause our subscription to be run on a different thread</strong>, which we can prove by looking at the logs. We see the first entry comes from the main thread and the Flux is running in another thread called <strong>parallel-1</strong>.
     * <pre>
     *     {@code
     *      [main] DEBUG reactor.util.Loggers$LoggerFactory - Using Slf4j logging framework
     *      [<strong>parallel-1</strong>] INFO  reactor.Flux.Array.1 - | onSubscribe([Synchronous Fuseable] FluxArray.ArraySubscription)
     *      [<strong>parallel-1</strong>] INFO  reactor.Flux.Array.1 - | request(unbounded)
     *      [<strong>parallel-1</strong>] INFO  reactor.Flux.Array.1 - | onNext(1)
     *      [<strong>parallel-1</strong>] INFO  reactor.Flux.Array.1 - | onNext(2)
     *      [<strong>parallel-1</strong>] INFO  reactor.Flux.Array.1 - | onNext(3)
     *      [<strong>parallel-1</strong>] INFO  reactor.Flux.Array.1 - | onNext(4)
     *      [<strong>parallel-1</strong>] INFO  reactor.Flux.Array.1 - | onComplete() }
     * </pre>
     */
    static void main(String[] args) {
        List<Integer> target = new ArrayList<>();
        List<Integer> source = list(10);
        Flux.fromIterable(source)
            .log()
            .subscribeOn(Schedulers.parallel())
            .subscribe(target::add);
    }
}

interface Utils {
    static List<Integer> list(int endExclusive) {
        return IntStream.range(1, endExclusive).boxed().collect(Collectors.toList());
    }

    class Backpressing extends AtomicInteger {
        private final Subscription subscription;
        private int value;
        public Backpressing(Subscription subscription) {
            this.subscription = subscription;
        }

        public void request(Integer limit) {
            value++;
            if (value == limit) {
                subscription.request(limit);
                value = 0;
            }
        }
    }
}



