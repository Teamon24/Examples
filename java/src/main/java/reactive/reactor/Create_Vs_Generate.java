package reactive.reactor;

import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * <table style="width: 100%; border: 1px solid #69a075; border-collapse: collapse; overflow-wrap: break-word;">
 * <thead>
 * <tr style="background-color: #69a075;color: #0000">
 * <th>Flux Create</th>
 * <th>Flux Generate</th>
 * </tr>
 * </thead>
 * <tbody>
 *
 * <tr>
 * <td>This method accepts an instance of <em>Consumer&lt;FluxSink&gt;</em></td>
 * <td>This method accepts an instance of <em>Consumer&lt;SynchronousSink&gt;</em></td>
 * </tr>
 * <tr>
 * <td>Create method calls the consumer only once</td>
 * <td>Generate method calls the consumer method multiple times based on the need of the downstream application</td>
 * </tr>
 * <tr>
 * <td>A consumer can emit 0..N elements immediately</td>
 * <td>Can emit only one element</td>
 * </tr>
 * <tr>
 * <td>The publisher is unaware of the downstream state. Therefore create accepts an Overflow strategy as an additional parameter for flow control</td>
 * <td>The publisher produces elements based on the downstream application need</td>
 * </tr>
 * <tr>
 * <td>The <em>FluxSink</em> lets us emit elements using multiple threads if required</td>
 * <td>Not useful for multiple threads as it emits only one element at a time</td>
 * </tr>
 * </tbody>
 * </table>
 */
public interface Create_Vs_Generate {
    String letters = "abcdefghijklmnopqrstuvwxyz";

    static void main(String[] args) throws InterruptedException {
        characterGenerator();
        characterCreator();
    }

    /**
     * <p><strong>The {@link Flux#generate} method calculates and emits the values on demand</strong>.
     * It is preferred to use in cases where it is
     * <strong>too expensive to calculate elements that may not be used downstream</strong>.
     * It can also be used if the emitted events are influenced by the state of the application.
     */
    private static void characterGenerator() {
        int quantity = 5;
        Flux<Character> nextFlux = Characters.generate().take(quantity);

        StepVerifier.create(nextFlux)
            .expectNext(get(letters, quantity))
            .expectComplete()
            .verify();
    }

    /**
     * <p>{@link Flux#create} method in {@link Flux} is used when
     * we want to <strong>calculate multiple (0 to infinity) values that are not influenced by the application’s state</strong>.
     * This is because the underlying {@link Flux#create} method keeps calculating the elements.
     *
     * <p>Besides, the downstream system determines how many elements it needs.
     * Therefore, if the downstream system is unable to keep up, already emitted elements are either buffered or removed.
     */
    private static void characterCreator() throws InterruptedException {
        int firstSequenceLength = 3;
        int secondSequenceLength = 2;
        List<Character> sequence1 = Characters.generate().take(firstSequenceLength).collectList().block();
        List<Character> sequence2 = Characters.generate().take(secondSequenceLength).collectList().block();

        CharacterCreator characterCreator = new CharacterCreator();
        Thread producer1 = new Thread(() -> characterCreator.consume(sequence1));
        Thread producer2 = new Thread(() -> characterCreator.consume(sequence2));

        List<Character> consolidated = new ArrayList<>();
        characterCreator.createCharacters().subscribe(consolidated::add);

        startAndJoin(producer1);
        startAndJoin(producer2);

        List<Character> expected = getExpected(firstSequenceLength, secondSequenceLength);

        assertThat(consolidated).containsExactlyElementsOf(expected);
        System.out.println(expected);
        System.out.println(consolidated);
    }

    private static List<Character> getExpected(int firstSequenceLength, int secondSequenceLength) {
        List<Character> expected = Stream.concat(
            Arrays.stream(get(letters, firstSequenceLength)),
            Arrays.stream(get(letters, secondSequenceLength))
        ).collect(Collectors.toList());
        return expected;
    }

    private static void startAndJoin(Thread producer1) throws InterruptedException {
        producer1.start();
        producer1.join();
    }

    static Character[] get(String letters, int quantity) {
        char[] chars = Arrays.copyOf(letters.toCharArray(), quantity);
        return new String(chars).chars()
            .mapToObj(i -> (char) i).toArray(Character[]::new);
    }

    class Characters {

        public static Flux<Character> generate() {

            return Flux.generate(() -> 97, (state, sink) -> {
                char value = (char) state.intValue();
                sink.next(value);
                if (value == 'z') {
                    sink.complete();
                }
                return state + 1;
            });
        }
    }

    class CharacterCreator {
        private Consumer<List<Character>> consumer;

        public void consume(List<Character> characters) {
            this.consumer.accept(characters);
        }

        public Flux<Character> createCharacters() {
            return Flux.create(sink -> CharacterCreator.this.consumer = items -> items.forEach(sink::next));
        }
    }
}
