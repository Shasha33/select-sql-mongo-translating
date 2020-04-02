package com.test.translator

open class Condition(val left: String, val right: String, private val operation: String) {
    open fun toMongoString(): String {
        return "$left: {\$$operation: $right}"
    }
}

class EqCondition(left: String, right: String) : Condition(left, right, "eq") {
    override fun toMongoString(): String {
        return "$left: $right"
    }
}

class NeCondition(left: String, right: String) : Condition(left, right, "ne")

class GtCondition(left: String, right: String) : Condition(left, right, "gt")

class LtCondition(left: String, right: String) : Condition(left, right, "lt")
