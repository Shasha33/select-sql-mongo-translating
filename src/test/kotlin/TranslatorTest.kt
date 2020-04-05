package com.test.translator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class TranslatorTest {

    private fun checkResult(sqlLine: String, mongoLine: String) {
        val result = translateSelect(sqlLine)
        assertEquals(mongoLine, result)
    }

    @Test
    fun translateSelectExample() {
        checkResult(
            "SELECT * FROM sales LIMIT 10",
            "db.sales.find({}).limit(10)")
    }

    @Test
    fun translateSelectColumnsExample() {
        checkResult(
            "SELECT name, surname FROM collection",
            "db.collection.find({}, {name: 1, surname: 1})")
    }

    @Test
    fun translateSelectOffsetLimitExample() {
        checkResult(
            "SELECT * FROM collection OFFSET 5 LIMIT 10",
            "db.collection.find({}).skip(5).limit(10)"
        )
    }

    @Test
    fun translateSelectWhereExample() {
        checkResult(
            "SELECT * FROM customers WHERE age > 22 AND name = 'Vasya'",
            "db.customers.find({age: {\$gt: 22}, name: 'Vasya'})"
        )
    }


}