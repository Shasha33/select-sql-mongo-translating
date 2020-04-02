package com.test.translator

import com.test.parser.SelectLexer
import com.test.parser.SelectParser
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

fun translateSelect(sqlLine: String): String {
    val lexer = SelectLexer(CharStreams.fromString(sqlLine))
    val parser = SelectParser(CommonTokenStream(lexer))
    val visitor = SelectVisitor()

    parser.select().accept(visitor)
    val selectOperation = visitor.collector
    return selectOperation.toMongoLine()
}
