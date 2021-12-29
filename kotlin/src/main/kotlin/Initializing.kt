import java.util.LinkedList

fun createList(
    size: Int,
    listClass: Class<*>,
    getElement: () -> Any
): MutableList<Any> {
    val list = getEmptyList(listClass)
    for (i in 0 until size) {
        list[i] = getElement()
    }
    return list
}

fun getEmptyList(listClass: Class<*>): MutableList<Any> = when (listClass) {
    is ArrayList<*> -> ArrayList<Any>()
    is LinkedList<*> -> LinkedList<Any>()
    else -> throw RuntimeException("There is no case for class: ${listClass.simpleName}")
}
