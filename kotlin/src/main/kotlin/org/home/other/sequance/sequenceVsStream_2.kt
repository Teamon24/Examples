package org.home.other.sequance

/**
 * Difference between sequence and stream essential logic.
 * Sequence applies all chain functions to each element separately,
 * stream applies one chain function separately to all elements.
 */

data class A(val string: String, val int: Int, val double: Double)

fun main() {
    val elementsToTake = 2

    val stringFilter = "1"
    val intFilter = 1
    val doubleFilter = 1.0

    val title = "Take $elementsToTake elements: string > $stringFilter, int > $intFilter, double > $doubleFilter"

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
        A("100", 10, 10.0),
    )
    var mark = streamMark
    val filteredList =
        list
            .filter { a -> check(a.string, stringFilter, mark) { value, filter -> value > filter } }
            .filter { a -> check(a.int, intFilter, mark) { value, filter -> value > filter } }
            .filter { a -> check(a.double, doubleFilter, mark) { value, filter -> value > filter } }
            .take(elementsToTake)

    mark = sequenceMark
    val filteredSequence =
        list.asSequence()
            .filter { a -> check(a.string, stringFilter, mark) { value, filter -> value > filter } }
            .filter { a -> check(a.int, intFilter, mark) { value, filter -> value > filter } }
            .filter { a -> check(a.double, doubleFilter, mark) { value, filter -> value > filter } }
            .take(elementsToTake)

    printResult(filteredList)
    printResult(filteredSequence.toList())
}

private fun<T> check(value: T, filter: T, mark: String, condition: (T, T) -> Boolean): Boolean {
    return condition(value, filter).also { println("[${mark}]: $value passed") }
}
