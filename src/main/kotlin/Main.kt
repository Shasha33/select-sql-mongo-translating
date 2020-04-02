package com.test

import com.test.translator.translateSelect

fun main() {
    val line = readLine() ?: return
    val mognoCommandString = translateSelect(line)
    println(mognoCommandString)
}