package com.test

import com.test.translator.ParseException
import com.test.translator.translateSelect

fun main() {
    val line = readLine() ?: return
    try {
        val mognoCommandString = translateSelect(line)
        println(mognoCommandString)
    } catch (e: ParseException) {
        println(e.message)
    }
}