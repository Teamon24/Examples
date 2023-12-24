package org.home

import kotlin.math.max


object PrintUtils {
    @JvmStatic
    fun printAsTable(rows: List<List<*>>, alignLeft: Boolean = true, delimiter: String = " ") {
        println(formatAsTable(rows, alignLeft, delimiter))
    }

    @JvmStatic
    fun formatAsTable(rows: List<List<*>>, alignLeft: Boolean = true, delimiter: String = " "): String {
        val rowsAsString = getRowsAsStrings(rows, alignLeft, delimiter, false)
        val result = StringBuilder()

        rowsAsString.forEach { rowAsString ->
            result.append(rowAsString).append("\n")
        }

        return result.toString()
    }

    @JvmStatic
    fun getRowsAsStrings(
        rows: List<List<*>>,
        isCellAlignLeft: Boolean = true,
        delimiter: String = " ",
        isDelimiterAlignLeft: Boolean = true,
    ): ArrayList<String> {

        val maxLengths = IntArray(rows[0].size)
        for (row in rows) {
            for (i in row.indices) {
                maxLengths[i] = max(maxLengths[i], row[i].toString().length)
            }
        }

        val formattedRows = ArrayList<String>()
        for (row in rows) {
            val formatBuilder = StringBuilder()
            row.mapIndexed { j, cell ->
                val cellString = cell.toString()
                val alignLength = maxLengths[j] - cellString.length
                if (isCellAlignLeft) {
                    formatBuilder.apply {
                        append(cellString)
                        if (isDelimiterAlignLeft && j < row.size - 1) {
                            append(delimiter)
                        }
                        append(align(alignLength))
                        if (!isDelimiterAlignLeft && j < row.size - 1) {
                            append(delimiter)
                        }
                    }
                } else {
                    formatBuilder.apply {
                        append(align(alignLength))
                        append(cellString)
                        if (j < row.size - 1) {
                            append(delimiter)
                        }
                    }
                }
            }
            formattedRows.add(formatBuilder.toString())
            formatBuilder.clear()
        }

        return formattedRows
    }

    private fun align(align: Int) = " ".repeat(align)

    @JvmStatic
    fun main(args: Array<String>) {
        val rows: MutableList<List<String>> = ArrayList()
        val headers = listOf(
            "Database",
            "Maintainer",
            "First public release date",
            "Latest stable version",
            "Latest release date")
        rows.add(headers)
        rows.add(listOf("4D (4th Dimension)", "4D S.A.S.", "1984", "v16.0", "2017-01-10"))
        rows.add(listOf("ADABAS", "Software AG", "1970", "8.1", "2013-06"))
        rows.add(listOf("Adaptive Server Enterprise", "SAP AG", "1987", "16.0", "2015"))
        rows.add(listOf("Apache Derby", "Apache", "2004", "10.14.1.0", "2017-10-22"))

        printAsTable(rows, alignLeft = true)
        printAsTable(rows, alignLeft = false)
        printAsTable(rows, alignLeft = true, delimiter = "|")
        printAsTable(rows, alignLeft = false, delimiter = "|")
    }
}
