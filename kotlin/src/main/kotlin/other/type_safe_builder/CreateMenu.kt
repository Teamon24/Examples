package patterns.misc.ex2

import patterns.misc.ex2.Menu.Companion.menu

fun main() {
    val menu = menu {
        - "A" {
            - "A"
            - "B" }
        - "B" {
            - "A" {
                - "A"
                - "B"}
            - "B"
            - "C"}
        - "C" {
            - "A" {
                - "A"
                - "B" {
                    - "A"
                    - "B"
                    - "C"
                    - "D"
                    - "E"
                    - "F"
                }}}
        - "D"
    }
    println(menu)
}


