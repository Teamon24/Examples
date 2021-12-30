package other.sequance

/**
 * Difference between sequence and stream essential logic.
 * Sequence applies all chain functions to each elements separately,
 * stream applies one chain function separately to all elements.
 */
typealias Mark = String
val words = "The quick brown fox jumps over the lazy dog and this dog with eagerness chases the fox."
            .split(Regex("[ .]+")).filterNot { it == "" }

const val streamMark = "stream"
const val sequenceMark = "sequence"
val maxLength = words.maxOf { it.length }

fun main() {

    val firstValue = 3
    val elementsToTake = 1

    val lengthsList =
        words
            .filter { lengthMoreThan(streamMark, it, firstValue) }
            .take(elementsToTake)

    val lengthsSequence =
        words.asSequence()
            .filter { lengthMoreThan(sequenceMark, it, firstValue) }
            .take(elementsToTake)

    printResult(lengthsList)
    printResult(lengthsSequence.toList())
}

private fun printResult(lengthsList: List<String>) {
    println("result: $lengthsList")
    println()
}

private fun lengthMoreThan(mark: Mark, word: String, filterValue: Int): Boolean {
    val filtered = word.length > filterValue
    printMessage(mark, word, filtered, filterValue)
    return filtered
}

private fun printMessage(mark: Mark, word: String, filtered: Boolean, filterValue: Int) {
    val indent = " ".repeat(maxLength - word.length)
    val passed = when {
        filtered -> " > $filterValue (passed)"
        else -> ""
    }
    println("[$mark] $indent$word: ${word.length}$passed")
}

