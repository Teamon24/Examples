package other.sequance

/**
 * Difference between sequence and stream essential logic.
 * Sequence applies all chain functions to each elements separately,
 * stream applies one chain function separately to all elements.
 */
fun main() {
    val words = "The quick brown fox jumps over the lazy dog and this dog with eagerness chases the fox.".split(" ")
    val firstValue = 3
    val secondValue = 4
    val elementsToTake = 1
    val lengthsList =
        words
            .filter { println("filter: $it"); it.length > firstValue }
            .map { println("length: ${it.length}"); it.length }
            .filter { println("filter: $it"); it > secondValue }
            .take(elementsToTake)


    val lengthsSequence =
        words.asSequence()
            .filter { println("filter: $it"); it.length > firstValue }
            .map { println("length: ${it.length}"); it.length }
            .filter { println("filter: $it"); it > secondValue }
            .take(elementsToTake)


    println(lengthsList)
    println()
    println(lengthsSequence.toList())

}
