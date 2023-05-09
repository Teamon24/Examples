package org.home.other.sequance

/**
 * Difference between sequence and stream essential logic.
 * Sequence applies all chain functions to each element separately,
 * stream applies one chain function separately to all elements.
 */

data class A(val string: String, val int: Int, val double: Double)

fun main() {
    val elementsToTake = 1

    val stringFilter = 1
    val intFilter = 1
    val doubleFilter = 1.0

    val title = "Take $elementsToTake elements: string > $stringFilter, int > $intFilter, double: $doubleFilter"

    printlnTitle(title)

    val list = listOf(
        A("10", 1, 1.0),
        A("20", 2, 2.0),
        A("30", 3, 3.0),
        A("40", 4, 4.0),
        A("50", 5, 5.0),
        A("60", 6, 6.0),
        A("70", 7, 7.0),
        A("80", 8, 8.0),
        A("90", 9, 9.0),
        A("10", 10, 10.0),
    )
    var mark = streamMark
    val lengthsList =
        list
            .filter { value -> check(value.string.length, stringFilter, mark) { these, that -> these > that } }
            .filter { value ->  check(value.int, intFilter, mark) { these, that -> these > that } }
            .filter { value -> check(value.double, doubleFilter, mark) { these, that -> these > that } }
            .take(elementsToTake)

    mark = sequenceMark
    val lengthsSequence =
        list.asSequence()
            .filter { value -> check(value.string.length, stringFilter, mark) { these, that -> these > that } }
            .filter { value ->  check(value.int, intFilter, mark) { these, that -> these > that } }
            .filter { value -> check(value.double, doubleFilter, mark) { these, that -> these > that } }
            .take(elementsToTake)

    printResult(lengthsList)
    printResult(lengthsSequence.toList())
}

private fun<T> check(double: T, doubleFilter: T, mark: String, condition: (T, T) -> Boolean): Boolean {
    return condition(double, doubleFilter).also { println("[${mark}]: $double passed") }
}
