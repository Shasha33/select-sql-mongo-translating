package com.test.translator

import com.test.parser.SelectLexer
import com.test.parser.SelectParser
import org.antlr.v4.runtime.*
import java.lang.Exception

/**
 * Translates simple select sql command to mongoDB find call.
 * Supports columns names filter,
 * WHERE conditions with constant value as right operand and column name as a left,
 * only <, >, <> and = operators allowed,
 * OFFSET and LIMIT.
 * @param sqlLine correct SELECT request line
 * @throws ParseException
 */
fun translateSelect(sqlLine: String): String {
    val errorListener = object : BaseErrorListener() {
        override fun syntaxError(
            recognizer: Recognizer<*, *>?,
            offendingSymbol: Any?,
            line: Int,
            charPositionInLine: Int,
            msg: String?,
            e: RecognitionException?
        ) {
            throw ParseException("Syntax error at $line:$charPositionInLine")
        }
    }

    val lexer = SelectLexer(CharStreams.fromString(sqlLine))
    lexer.removeErrorListeners()
    lexer.addErrorListener(errorListener)

    val parser = SelectParser(CommonTokenStream(lexer))
    parser.removeErrorListeners()
    parser.addErrorListener(errorListener)

    val visitor = SelectVisitor()

    parser.select().accept(visitor)
    val selectOperation = visitor.collector
    return selectOperation.toMongoLine()
}

class ParseException(message: String) : Exception(message)