package com.test.translator

class SelectOperationCollector(private val collection: String) {
    val columns = mutableListOf<String>()
    val where = mutableListOf<Condition>()
    val ranges = mutableListOf<String>()

    fun toMongoLine(): String {
        var conditionsString = where.joinToString(", ", "{", "}") { it.toMongoString() }
        val columnsString = columns.joinToString(", ") { "$it: 1" }
        if (columnsString.isNotEmpty()) {
            conditionsString += ", {$columnsString}"
        }
        val findString = "find($conditionsString)"
        return listOf("db", collection, findString).plus(ranges)
                .filter { it.isNotEmpty() }
                .joinToString(".")
    }
}