package org.home.other.type_safe_builder

typealias MenuItemName = String

class Menu(private val items: MutableList<MenuItem> = ArrayList(), private var counter: Int = 0) {

    operator fun MenuItem.unaryMinus() {
        val menu = this@Menu
        val menuItem = this
        menu.items.add(menuItem)
    }

    operator fun MenuItemName.unaryMinus() {
        val menu = this@Menu
        val menuItemName = this@unaryMinus
        val newMenuItem = createMenuItem(menu, menuItemName)
        menu.items.add(newMenuItem)
    }

    operator fun MenuItemName.invoke(init: MenuItem.() -> Unit): MenuItem {
        val menu = this@Menu
        val menuItemName = this@invoke
        val newMenuItem = createMenuItem(menu, menuItemName)
        newMenuItem.init()
        return newMenuItem
    }

    private fun createMenuItem(menu: Menu, menuItemName: MenuItemName): MenuItem {
        menu.counter = menu.counter + 1
        return MenuItem(menu.counter, menuItemName, "${menu.counter}")
    }

    companion object {
        fun menu(init: Menu.() -> Unit): Menu {
            val menu = Menu()
            menu.init()
            return menu
        }
    }
}

class MenuItem(
    private val number: Int,
    private val title: String,
    private val numberPath: String = "",
    private val namePath: String = title,
    private var counter: Int = 0
) {
    private val items: MutableList<MenuItem> = ArrayList()

    operator fun MenuItem.unaryMinus() {
        val menuItem = this@MenuItem
        menuItem.items.add(this@unaryMinus)
    }

    operator fun MenuItemName.unaryMinus(): MenuItem {
        val menuItem = this@MenuItem
        val menuItemName = this@unaryMinus
        val newMenuItem = createMenuItem(menuItem, menuItemName)
        menuItem.items.add(newMenuItem)
        return newMenuItem
    }

    operator fun MenuItemName.invoke(function: MenuItem.() -> Unit): MenuItem {
        val menuItem = this@MenuItem
        val menuItemName = this@invoke
        val newMenuItem = createMenuItem(menuItem, menuItemName)
        newMenuItem.function()
        return newMenuItem
    }

    private fun createMenuItem(currentMenuItem: MenuItem, menuItemName: MenuItemName): MenuItem {
        currentMenuItem.counter = currentMenuItem.counter + 1
        val newNumberPath = "${currentMenuItem.numberPath}.${currentMenuItem.counter}"
        val newNamePath = "${currentMenuItem.namePath}.$menuItemName"

        return MenuItem(currentMenuItem.counter, menuItemName, newNumberPath, newNamePath)
    }

    override fun toString(): String {
        return "$number.$title"
    }
}