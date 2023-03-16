package org.home.coroutine


fun threadPrint(message: String) = println("[${Thread.currentThread().name}]: %s".format(message))