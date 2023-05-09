package org.home.other.sequance

/**
 * Difference between sequence and stream essential logic.
 * Sequence applies all chain functions to each element separately,
 * stream applies one chain function separately to all elements.
 */
typealias Mark = String
val words = "The quick brown fox jumps over the lazy dog and this dog with eagerness chases the fox."
            .split(Regex("[ .]+")).filter { it.isNotBlank() }

const val streamMark = "stream"
const val sequenceMark = "sequence"
fun maxLength(mark: String) = words.maxOf { it.length } + sequenceMark.length - mark.length

fun main() {

    val filterValue = 3
    val elementsToTake = 3

    val title = "Take $elementsToTake elements those have length more than $filterValue"
    printlnTitle(title)

    val lengthsList =
        words
            .filter { it.hasLengthMoreThan(streamMark, filterValue) }
            .take(elementsToTake)

    val lengthsSequence =
        words.asSequence()
            .filter { it.hasLengthMoreThan(sequenceMark, filterValue) }
            .take(elementsToTake)

    printResult(lengthsList)
    printResult(lengthsSequence.toList())
}

fun <A> printResult(lengthsList: List<A>) {
    println("result: $lengthsList")
    println()
}

fun String.hasLengthMoreThan(mark: Mark, filterValue: Int): Boolean {
    val filtered = length > filterValue
    printMessage(mark, this, filtered, filterValue)
    return filtered
}

private fun printMessage(mark: Mark, word: String, filtered: Boolean, filterValue: Int) {
    val indent = " ".repeat(maxLength(mark) - word.length)
    val passed = when {
        filtered -> " > $filterValue (passed)"
        else -> ""
    }
    println("[$mark] $indent$word: ${word.length}$passed")
}

fun printlnTitle(title: String) {
    println("-".repeat(title.length))
    println(title)
    println("-".repeat(title.length))
    println()
}

