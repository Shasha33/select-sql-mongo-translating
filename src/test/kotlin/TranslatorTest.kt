package com.test.translator

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

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

    @Test
    fun lowerCaseSelectTest() {
        checkResult(
            "select * from sales",
            "db.sales.find({})"
        )
    }

    @Test
    fun capitalLettersSelectTest() {
        checkResult(
            "Select * From sales",
            "db.sales.find({})"
        )
    }

    @Test
    fun alternatingCaseSelectTest() {
        checkResult(
            "SeLeCt name FrOm pets WhErE age < 10",
            "db.pets.find({age: {\$lt: 10}}, {name: 1})"
        )
    }

    @Test
    fun digitsInCollectionNameTest() {
        checkResult(
            "SELECT * FROM col1ection23",
            "db.col1ection23.find({})"
        )
    }

    @Test
    fun digitsInFieldNameTest() {
        checkResult(
            "SELECT tota1 FROM sales",
            "db.sales.find({}, {tota1: 1})"
        )
    }

    @Test
    fun startsWithUnderscoreFieldNameTest() {
        checkResult(
            "SELECT _total FROM sales",
            "db.sales.find({}, {_total: 1})"
        )
    }

    @Test
    fun typoInSelectTest() {
        assertThrows<ParseException> { translateSelect("SELCT * FROM sales") }
    }

    @Test
    fun redundantCommaTest() {
        assertThrows<ParseException> { translateSelect("SELECT name, age, FROM pets") }
    }

    @Test
    fun incorrectCollectionNameTest() {
        assertThrows<ParseException> { translateSelect("SELECT * FROM #collection") }
    }

    @Test
    fun incorrectFieldNameTest() {
        assertThrows<ParseException> { translateSelect("SELECT @name FROM collection") }
    }
}