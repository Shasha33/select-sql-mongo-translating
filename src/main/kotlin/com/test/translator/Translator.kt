package com.test.translator

import com.test.parser.SelectLexer
import com.test.parser.SelectParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

/**
 * Translates simple select sql command to mongoDB find call.
 * Supports columns names filter,
 * WHERE conditions with constant value as right operand and column name as a left,
 * only <, >, <> and = operators allowed,
 * OFFSET and LIMIT.
 * @param sqlLine correct SELECT request line
 */
fun translateSelect(sqlLine: String): String {
    val lexer = SelectLexer(CharStreams.fromString(sqlLine))
    val parser = SelectParser(CommonTokenStream(lexer))
    val visitor = SelectVisitor()

    parser.select().accept(visitor)
    val selectOperation = visitor.collector
    return selectOperation.toMongoLine()
}
