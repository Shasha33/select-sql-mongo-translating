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

    @Test
    fun translateSelectLimitSkipTest() {
        checkResult(
            "SELECT * FROM collection LIMIT 2 SKIP 1",
            "db.collection.find({}).limit(2).skip(1)"
        )
    }

    @Test
    fun translateSelectSingleWhereNeqTest() {
        checkResult(
            "SELECT * FROM fruits WHERE count <> 0",
            "db.fruits.find({count: {\$ne: 0}})"
        )
    }

    @Test
    fun translateSelectSingleWhereLtTest() {
        checkResult(
            "SELECT * FROM fruits WHERE count < 0",
            "db.fruits.find({count: {\$lt: 0}})"
        )
    }

    @Test
    fun translateSelectSingleWhereGtExample() {
        checkResult(
            "SELECT * FROM fruits WHERE count > 0",
            "db.fruits.find({count: {\$gt: 0}})"
        )
    }

    @Test
    fun translateSelectSingleWhereEqTest() {
        checkResult(
            "SELECT * FROM fruits WHERE count = 0",
            "db.fruits.find({count: 0})"
        )
    }

    @Test
    fun translateSelectAllCollectionTest() {
        checkResult(
            "SELECT * FROM sums",
            "db.sums.find({})"
        )
    }

    @Test
    fun translateSelectWithColumnsAndConditionsTest() {
        checkResult(
            "SELECT name, age FROM pets WHERE age > 3",
            "db.pets.find({age: {\$gt: 3}}, {name: 1, age: 1})"
        )
    }

    @Test
    fun translateSelectWithColumnsAndLimitTest() {
        checkResult(
            "SELECT name, age FROM pets LIMIT 10",
            "db.pets.find({}, {name: 1, age: 1}).limit(10)"
        )
    }

    @Test
    fun translateSelectSeveralColumnConditionsTest() {
        checkResult(
            "SELECT * FROM pets WHERE name = 'doggo' AND name <> 'ivan'",
            "db.pets.find({name: 'doggo', name: {\$ne: 'ivan'}})"
        )
    }

    @Test
    fun translateSelectSeveralAndConditionsTest() {
        checkResult(
            "SELECT * FROM pets WHERE age > 10 AND name <> 'ivan' AND age < 100 AND name <> 'petr'",
            "db.pets.find({age: {\$gt: 10}, name: {\$ne: 'ivan'}, age: {\$lt: 100}, name: {\$ne: 'petr'}})"
        )
    }

    @Test
    fun translateSelectFilteredByOtherFieldsTest() {
        checkResult(
            "SELECT age FROM pets WHERE name = 'ivan'",
            "db.pets.find({name: 'ivan'}, {age: 1})"
        )
    }

}