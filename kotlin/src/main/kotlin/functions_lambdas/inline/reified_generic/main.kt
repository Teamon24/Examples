package functions_lambdas.inline.reified_generic

import java.awt.TextArea
import java.util.*
import javax.swing.tree.TreeNode

//https://kotlinlang.org/docs/reference/inline-functions.html#reified-type-parameters

fun <T> TreeNode.findParentOfType(clazz: Class<T>): T? {
    var p = parent
    while (p != null && !clazz.isInstance(p)) {
        p = p.parent
    }
    @Suppress("UNCHECKED_CAST")
    return p as T?
}

inline fun <reified T> TreeNode.findParentOfType(): T? {
    var p = parent
    while (p != null && p !is T) {
        p = p.parent
    }
    return p as T?
}

fun main(s: Array<String>) {
    treeNode().findParentOfType(TextArea::class.java)
    treeNode().findParentOfType<TextArea>()
}

fun treeNode(): TreeNode {
    return object : TreeNode {
        override fun isLeaf(): Boolean { TODO("not implemented") }
        override fun getChildCount(): Int { TODO("not implemented") }
        override fun getParent(): TreeNode { TODO("not implemented") }
        override fun getChildAt(childIndex: Int): TreeNode { TODO("not implemented") }
        override fun getIndex(node: TreeNode?): Int { TODO("not implemented") }
        override fun getAllowsChildren(): Boolean { TODO("not implemented") }
        override fun children(): Enumeration<out TreeNode> { TODO("not implemented") }
    }
}
